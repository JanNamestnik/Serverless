package si.um.feri.serverless

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONObject
import si.um.feri.sensorlibrary.Sensor
import si.um.feri.sensorlibrary.SensorRecord
import si.um.feri.serverless.databinding.ActivitySimulationBinding
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class SimulationActivity : AppCompatActivity() {

    companion object {
        private const val ADD_SENSOR_REQUEST_CODE = 1001
        private const val EDIT_SENSOR_REQUEST_CODE = 1002
        private const val SENSOR_PREFS_KEY = "sensor_records"
    }

    private lateinit var binding: ActivitySimulationBinding
    private lateinit var adapter: SensorRecordAdapter
    private val sensorRecords = mutableListOf<SensorRecord>()
    private var editingPosition: Int? = null // Keep track of which sensor is being edited
    private val handlers = mutableMapOf<String, Handler>() // Handlers for each sensor to manage API calls

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_simulation)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivitySimulationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadPreferences() // Load saved states
        initializeButtons()
    }

    private fun setupRecyclerView() {
        adapter = SensorRecordAdapter(sensorRecords,
            onSwitchToggle = { sensorRecord, isEnabled ->
                toggleSensor(sensorRecord, isEnabled)
            },
            onSensorLongPressed = { position ->
                deleteSensor(position)
            },
            onSensorShortPressed = { position ->
                editSensor(position)
            }
        )
        binding.recyclerViewSensors.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewSensors.adapter = adapter
    }

    private fun initializeButtons() {
        binding.simulationBackBtn.setOnClickListener {
            finish()
        }

        binding.addSensorBtn.setOnClickListener {
            val intent = Intent(this, AddSensorActivity::class.java)
            startActivityForResult(intent, ADD_SENSOR_REQUEST_CODE)
        }
    }

    private fun loadPreferences() {
        val sharedPreferences = getSharedPreferences("SensorPrefs", MODE_PRIVATE)
        val gson = Gson()

        // Load the JSON string from SharedPreferences
        val json = sharedPreferences.getString(SENSOR_PREFS_KEY, null)
        if (!json.isNullOrEmpty()) {
            val type = object : TypeToken<MutableList<Sensor>>() {}.type
            val sensors: MutableList<Sensor> = gson.fromJson(json, type)

            sensorRecords.clear()
            sensorRecords.addAll(sensors.map { SensorRecord(sensor = it) })
            adapter.notifyDataSetChanged()
        }
    }

    private fun savePreferences() {
        val sharedPreferences = getSharedPreferences("SensorPrefs", MODE_PRIVATE)
        val gson = Gson()

        // Convert the sensor list to a JSON string
        val json = gson.toJson(sensorRecords.map { it.sensor })

        // Save the JSON string to SharedPreferences
        with(sharedPreferences.edit()) {
            putString(SENSOR_PREFS_KEY, json)
            apply()
        }
    }

    private fun toggleSensor(sensorRecord: SensorRecord, isEnabled: Boolean) {
        sensorRecord.sensor.enabled = isEnabled

        // Save the updated state
        savePreferences()

        if (isEnabled) {
            startSendingData(sensorRecord)
        } else {
            stopSendingData(sensorRecord)
        }

        // Notify the user
        val message = if (isEnabled) {
            "${sensorRecord.sensor.type} enabled."
        } else {
            "${sensorRecord.sensor.type} disabled."
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun startSendingData(sensorRecord: SensorRecord) {
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                sendDataToApi(sensorRecord)
                val frequencyInMillis = parseFrequency(sensorRecord.sensor.frequency)
                handler.postDelayed(this, frequencyInMillis)
            }
        }
        handlers[sensorRecord.sensor.type] = handler
        handler.post(runnable)
    }

    private fun stopSendingData(sensorRecord: SensorRecord) {
        val handler = handlers[sensorRecord.sensor.type]
        handler?.removeCallbacksAndMessages(null)
        handlers.remove(sensorRecord.sensor.type)
    }

    private fun sendDataToApi(sensorRecord: SensorRecord) {
        val sensor = sensorRecord.sensor
        val randomValue = Random.nextDouble(sensor.rangeFrom, sensor.rangeTo)

        // Resolve latitude and longitude using the simulation field
        val geocoder = android.location.Geocoder(this, Locale.getDefault())
        val addresses = try {
            geocoder.getFromLocationName(sensor.location, 1)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }

        // Default coordinates if location resolution fails
        val latitude = addresses?.firstOrNull()?.latitude ?: 46.056946
        val longitude = addresses?.firstOrNull()?.longitude ?: 14.505751

        // Create the JSON object
        val jsonObject = JSONObject().apply {
            put("type", sensor.type.lowercase(Locale.getDefault()))
            put("value", randomValue)
            put("current_time", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date()))
            put("simulated", true) // New field to indicate simulation
            put("location", JSONObject().apply {
                put("latitude", latitude)
                put("longitude", longitude)
            })
        }

        val apiUrl = when (sensor.type.lowercase(Locale.getDefault())) {
            "humidity (%)" -> "https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/createHumidity"
            "light (lux)" -> "https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/createLight"
            else -> {
                android.util.Log.e("API_ERROR", "Unknown sensor type: ${sensor.type}")
                return
            }
        }

        // Log the JSON object and API URL
        android.util.Log.d("SEND_DATA", "API URL: $apiUrl")
        android.util.Log.d("SEND_DATA", "JSON: ${jsonObject.toString()}")

        val client = OkHttpClient()
        val requestBody = RequestBody.create("application/json".toMediaType(), jsonObject.toString())
        val request = Request.Builder().url(apiUrl).post(requestBody).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                android.util.Log.e("API_ERROR", "Failed to send data: ${e.message}")
                runOnUiThread {
                    Toast.makeText(this@SimulationActivity, "Failed to send data for ${sensor.type}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    android.util.Log.d("SEND_DATA", "Data sent successfully for ${sensor.type}")
                    runOnUiThread {
                        Toast.makeText(this@SimulationActivity, "Data sent for ${sensor.type}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    android.util.Log.e("API_ERROR", "API response error: ${response.code} - ${response.message}")
                    runOnUiThread {
                        Toast.makeText(this@SimulationActivity, "Failed to send data for ${sensor.type}: ${response.code}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }


    private fun parseFrequency(frequency: String): Long {
        val parts = frequency.split("h:", "m:", "s")
        val hours = parts[0].toIntOrNull() ?: 0
        val minutes = parts[1].toIntOrNull() ?: 0
        val seconds = parts[2].toIntOrNull() ?: 0
        return (hours * 3600 + minutes * 60 + seconds) * 1000L
    }

    private fun deleteSensor(position: Int) {
        val sensorToDelete = sensorRecords[position]

        // Show a confirmation dialog
        AlertDialog.Builder(this)
            .setTitle("Delete Sensor")
            .setMessage("Are you sure you want to delete ${sensorToDelete.sensor.type}?")
            .setPositiveButton("Yes") { _, _ ->
                stopSendingData(sensorToDelete)
                sensorRecords.removeAt(position)
                adapter.notifyItemRemoved(position)

                // Save the updated list
                savePreferences()

                Toast.makeText(this, "${sensorToDelete.sensor.type} deleted.", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun editSensor(position: Int) {
        val sensorToEdit = sensorRecords[position]
        editingPosition = position

        val intent = Intent(this, AddSensorActivity::class.java)
        intent.putExtra("editing_sensor", sensorToEdit.sensor)
        startActivityForResult(intent, EDIT_SENSOR_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val updatedSensor = data?.getSerializableExtra("new_sensor") as? Sensor
            if (updatedSensor != null) {
                when (requestCode) {
                    ADD_SENSOR_REQUEST_CODE -> {
                        sensorRecords.add(SensorRecord(sensor = updatedSensor))
                        adapter.notifyDataSetChanged()
                        Toast.makeText(this, "${updatedSensor.type} added to the list.", Toast.LENGTH_SHORT).show()
                    }
                    EDIT_SENSOR_REQUEST_CODE -> {
                        val position = editingPosition
                        if (position != null) {
                            sensorRecords[position].sensor = updatedSensor
                            adapter.notifyItemChanged(position)
                            Toast.makeText(this, "${updatedSensor.type} updated.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                savePreferences()
            }
        }
    }

    override fun onStop() {
        super.onStop()

        // Stop all sensors and set them to off
        sensorRecords.forEach { sensorRecord ->
            if (sensorRecord.sensor.enabled) {
                stopSendingData(sensorRecord) // Stop the handler for API calls
                sensorRecord.sensor.enabled = false // Set the sensor to off
            }
        }

        // Save the updated state to preferences
        savePreferences()

        // Notify the user or log the event
        android.util.Log.d("SimulationActivity", "All sensors turned off when leaving the activity.")
    }

}
