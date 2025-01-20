package si.um.feri.serverless

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import si.um.feri.serverless.databinding.ActivityMessageBinding
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MessageActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityMessageBinding
    private lateinit var spinnerEventName: Spinner
    private lateinit var spinnerMessageType: Spinner
    private lateinit var editTextMessage: EditText
    private lateinit var buttonSend: Button
    private val eventNames = mutableListOf<String>()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currentLatitude: Double? = null
    private var currentLongitude: Double? = null

    // For alert message
    private lateinit var sensorManager: SensorManager
    private var temperatureSensor: Sensor? = null
    var desiredTemperature: Float? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_message)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeButtons()

        // Initialize views
        spinnerEventName = findViewById(R.id.spinner1)
        spinnerMessageType = findViewById(R.id.spinner2)
        editTextMessage = findViewById(R.id.editMessage)
        buttonSend = findViewById(R.id.sendBtn)

        // Initialize Location Client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Initialize Sensor Manager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        val savedTemp = getSavedDesiredTemperature()
        if (savedTemp != null && savedTemp != Float.MIN_VALUE) {
            val temperatureInput: EditText = findViewById(R.id.temperatureInput)
            temperatureInput.setText(savedTemp.toString())
            desiredTemperature = savedTemp
        }

        // automatic start sensor if temp is set
        if (desiredTemperature != null) {
            temperatureSensor?.let {
                sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
            }
        }


        // Initialize Buttons
        val saveTempButton: Button = findViewById(R.id.saveTempButton)
        val temperatureInput: EditText = findViewById(R.id.temperatureInput)

        saveTempButton.setOnClickListener {
            val tempInput = temperatureInput.text.toString()
            if (tempInput.isNotEmpty()) {
                desiredTemperature = tempInput.toFloat()
                saveDesiredTemperature(desiredTemperature!!)
                Toast.makeText(this, "Threshold set to $desiredTemperature°C", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a valid temperature", Toast.LENGTH_SHORT).show()
            }
        }

        // Fetch event names from the API
        fetchEventNames()

        // Populate the message type spinner
        val messageTypes = arrayOf("Event Info", "Security", "Alert")
        spinnerMessageType.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, messageTypes)

        // Get the current location
        fetchLocation()
    }

    private fun initializeButtons() {
        binding.messageBackBtn.setOnClickListener {
            finish()
        }
        binding.sendBtn.setOnClickListener {
            sendMessage()
        }
    }

    private fun fetchEventNames() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/events")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@MessageActivity, "Failed to load events", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseString = response.body?.string()
                    if (responseString != null) {
                        try {
                            val eventsArray = org.json.JSONArray(responseString)
                            for (i in 0 until eventsArray.length()) {
                                val eventObject = eventsArray.getJSONObject(i)
                                val eventName = eventObject.getString("name")
                                eventNames.add(eventName)
                            }

                            runOnUiThread {
                                spinnerEventName.adapter = ArrayAdapter(
                                    this@MessageActivity,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    eventNames
                                )
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            runOnUiThread {
                                Toast.makeText(this@MessageActivity, "Error parsing events", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@MessageActivity, "Failed to fetch events", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                100
            )
            return
        }

        val locationRequest = com.google.android.gms.location.LocationRequest.Builder(
            com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
            3000 // Request location every 1 second
        ).build()

        val locationCallback = object : com.google.android.gms.location.LocationCallback() {
            override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                val location = locationResult.lastLocation
                if (location != null) {
                    currentLatitude = location.latitude
                    currentLongitude = location.longitude
                    Toast.makeText(
                        this@MessageActivity,
                        "Location updated: $currentLatitude, $currentLongitude",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Remove updates once we get a location to save resources
                    fusedLocationClient.removeLocationUpdates(this)
                } else {
                    Toast.makeText(this@MessageActivity, "Unable to fetch location", Toast.LENGTH_SHORT).show()
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }


    private fun sendMessage() {
        val selectedEvent = spinnerEventName.selectedItem?.toString() ?: ""
        val selectedType = spinnerMessageType.selectedItem?.toString() ?: ""
        val messageContent = editTextMessage.text.toString()

        if (selectedEvent.isEmpty() || selectedType.isEmpty() || messageContent.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        val messageJson = JSONObject().apply {
            put("name", selectedEvent)
            put("type", selectedType)
            put("content", messageContent)
            put("current_time", currentTime)
            put("location", JSONObject().apply {
                put("latitude", currentLatitude ?: 0.0)
                put("longitude", currentLongitude ?: 0.0)
            })
        }

        val client = OkHttpClient()
        val body = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            messageJson.toString()
        )
        val request = Request.Builder()
            .url("https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/createMessage")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@MessageActivity, "Failed to send message", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MessageActivity, "Message sent successfully", Toast.LENGTH_SHORT).show()
                        editTextMessage.text.clear()
                    } else {
                        Toast.makeText(this@MessageActivity, "Failed to send message", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    // for Alert temperature
    override fun onResume() {
        super.onResume()
        if (desiredTemperature != null) {
            temperatureSensor?.let {
                sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
            }
        }
    }


    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            val currentTemperature = event.values[0] // Get the current temperature
            Log.d("TemperatureSensor", "Current temperature: $currentTemperature")
            desiredTemperature?.let {
                if (currentTemperature >= it) {
                    sendTemperatureAlert(currentTemperature)
                }
            }
        }
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed
    }

    private fun sendTemperatureAlert(currentTemperature: Float) {
        val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val messageJson = JSONObject().apply {
            put("name", "Temperature Alert")
            put("type", "Alert")
            put("content", "Extreme temperature of $currentTemperature°C has been measured.")
            put("current_time", currentTime)
            put("location", JSONObject().apply {
                put("latitude", currentLatitude ?: 0.0)
                put("longitude", currentLongitude ?: 0.0)
            })
        }

        val client = OkHttpClient()
        val body = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            messageJson.toString()
        )
        val request = Request.Builder()
            .url("https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/createMessage")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@MessageActivity, "Failed to send alert", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MessageActivity, "Temperature alert sent", Toast.LENGTH_SHORT).show()
                        // Clear the threshold to prevent further alerts
                        desiredTemperature = null
                        saveDesiredTemperature(Float.MIN_VALUE)
                    } else {
                        Toast.makeText(this@MessageActivity, "Failed to send alert", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun saveDesiredTemperature(temp: Float) {
        val sharedPreferences = getSharedPreferences("TemperaturePrefs", MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putFloat("desiredTemperature", temp)
            apply()
        }
    }

    private fun getSavedDesiredTemperature(): Float? {
        val sharedPreferences = getSharedPreferences("TemperaturePrefs", MODE_PRIVATE)
        return if (sharedPreferences.contains("desiredTemperature")) {
            sharedPreferences.getFloat("desiredTemperature", Float.MIN_VALUE)
        } else null
    }
}
