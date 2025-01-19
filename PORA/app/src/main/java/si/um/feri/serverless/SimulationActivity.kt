package si.um.feri.serverless

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import si.um.feri.sensorlibrary.Sensor
import si.um.feri.sensorlibrary.SensorRecord
import si.um.feri.serverless.databinding.ActivitySimulationBinding

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

        // Notify the user
        val message = if (isEnabled) {
            "${sensorRecord.sensor.type} enabled."
        } else {
            "${sensorRecord.sensor.type} disabled."
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun deleteSensor(position: Int) {
        val sensorToDelete = sensorRecords[position]

        // Show a confirmation dialog
        AlertDialog.Builder(this)
            .setTitle("Delete Sensor")
            .setMessage("Are you sure you want to delete ${sensorToDelete.sensor.type}?")
            .setPositiveButton("Yes") { _, _ ->
                // Remove the sensor from the list
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
                        // Add a new sensor
                        sensorRecords.add(SensorRecord(sensor = updatedSensor))
                        adapter.notifyDataSetChanged()
                        Toast.makeText(this, "${updatedSensor.type} added to the list.", Toast.LENGTH_SHORT).show()
                    }
                    EDIT_SENSOR_REQUEST_CODE -> {
                        // Update an existing sensor
                        val position = editingPosition
                        if (position != null) {
                            sensorRecords[position].sensor = updatedSensor
                            adapter.notifyItemChanged(position)
                            Toast.makeText(this, "${updatedSensor.type} updated.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                // Save the updated list
                savePreferences()
            }
        }
    }
}
