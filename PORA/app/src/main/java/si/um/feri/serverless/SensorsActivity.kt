package si.um.feri.serverless

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import si.um.feri.serverless.databinding.ActivitySensorsBinding
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class SensorsActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivitySensorsBinding
    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private var humiditySensor: Sensor? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var isLightEnabled = false
    private var isHumidityEnabled = false
    private var lightFrequencyInMillis: Long = 0
    private var humidityFrequencyInMillis: Long = 0

    private var currentLatitude: Double? = null
    private var currentLongitude: Double? = null

    private var latestLight: Float? = null
    private var latestHumidity: Float? = null

    private val executor = Executors.newScheduledThreadPool(2)
    private var lightTask: ScheduledFuture<*>? = null
    private var humidityTask: ScheduledFuture<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sensors)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivitySensorsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)

        configureNumberPickers()
        initializeButtons()
        loadPreferences()
        fetchLocation()
    }

    private fun configureNumberPickers() {
        configureNumberPicker(binding.humidityHoursPicker, 0, 23)
        configureNumberPicker(binding.humidityMinutesPicker, 0, 59)
        configureNumberPicker(binding.humiditySecondsPicker, 0, 59)

        configureNumberPicker(binding.lightHoursPicker, 0, 23)
        configureNumberPicker(binding.lightMinutesPicker, 0, 59)
        configureNumberPicker(binding.lightSecondsPicker, 0, 59)
    }

    private fun configureNumberPicker(picker: NumberPicker, minValue: Int, maxValue: Int) {
        picker.minValue = minValue
        picker.maxValue = maxValue
    }

    private fun initializeButtons() {
        binding.saveSensorsButton.setOnClickListener {
            savePreferences()
            manageMonitoring()
            Toast.makeText(this, "Sensors configured", Toast.LENGTH_SHORT).show()
        }

        binding.sensorsBackBtn.setOnClickListener {
            finish()
        }
    }

    private fun loadPreferences() {
        val sharedPreferences = getSharedPreferences("SensorsPrefs", MODE_PRIVATE)

        isLightEnabled = sharedPreferences.getBoolean("lightEnabled", false)
        isHumidityEnabled = sharedPreferences.getBoolean("humidityEnabled", false)

        lightFrequencyInMillis = sharedPreferences.getLong("lightFrequency", 0)
        humidityFrequencyInMillis = sharedPreferences.getLong("humidityFrequency", 0)

        binding.lightEnabledSwitch.isChecked = isLightEnabled
        binding.humidityEnabledSwitch.isChecked = isHumidityEnabled

        val tempHours = (lightFrequencyInMillis / 3600000).toInt()
        val tempMinutes = ((lightFrequencyInMillis % 3600000) / 60000).toInt()
        val tempSeconds = ((lightFrequencyInMillis % 60000) / 1000).toInt()

        val humidityHours = (humidityFrequencyInMillis / 3600000).toInt()
        val humidityMinutes = ((humidityFrequencyInMillis % 3600000) / 60000).toInt()
        val humiditySeconds = ((humidityFrequencyInMillis % 60000) / 1000).toInt()

        binding.lightHoursPicker.value = tempHours
        binding.lightMinutesPicker.value = tempMinutes
        binding.lightSecondsPicker.value = tempSeconds

        binding.humidityHoursPicker.value = humidityHours
        binding.humidityMinutesPicker.value = humidityMinutes
        binding.humiditySecondsPicker.value = humiditySeconds
    }

    private fun savePreferences() {
        val sharedPreferences = getSharedPreferences("SensorsPrefs", MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("lightEnabled", binding.lightEnabledSwitch.isChecked)
            putBoolean("humidityEnabled", binding.humidityEnabledSwitch.isChecked)

            val lightMillis = calculateFrequencyInMillis(
                binding.lightHoursPicker.value,
                binding.lightMinutesPicker.value,
                binding.lightSecondsPicker.value
            )
            putLong("lightFrequency", lightMillis)

            val humidityMillis = calculateFrequencyInMillis(
                binding.humidityHoursPicker.value,
                binding.humidityMinutesPicker.value,
                binding.humiditySecondsPicker.value
            )
            putLong("humidityFrequency", humidityMillis)
            apply()
        }

        isLightEnabled = binding.lightEnabledSwitch.isChecked
        isHumidityEnabled = binding.humidityEnabledSwitch.isChecked
        lightFrequencyInMillis = calculateFrequencyInMillis(
            binding.lightHoursPicker.value,
            binding.lightMinutesPicker.value,
            binding.lightSecondsPicker.value
        )
        humidityFrequencyInMillis = calculateFrequencyInMillis(
            binding.humidityHoursPicker.value,
            binding.humidityMinutesPicker.value,
            binding.humiditySecondsPicker.value
        )
    }

    private fun calculateFrequencyInMillis(hours: Int, minutes: Int, seconds: Int): Long {
        return (hours * 3600000 + minutes * 60000 + seconds * 1000).toLong()
    }

    private fun manageMonitoring() {
        if (isLightEnabled) {
            if (lightTask == null || lightTask?.isCancelled == true) {
                lightTask = executor.scheduleWithFixedDelay({
                    sendSensorData("light", latestLight, "https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/createLight")
                }, 0, lightFrequencyInMillis, TimeUnit.MILLISECONDS)
            }
        } else {
            lightTask?.cancel(true)
            lightTask = null
        }

        if (isHumidityEnabled) {
            if (humidityTask == null || humidityTask?.isCancelled == true) {
                humidityTask = executor.scheduleWithFixedDelay({
                    sendSensorData("humidity", latestHumidity, "https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/createHumidity")
                }, 0, humidityFrequencyInMillis, TimeUnit.MILLISECONDS)
            }
        } else {
            humidityTask?.cancel(true)
            humidityTask = null
        }
    }

    private fun sendSensorData(type: String, value: Float?, apiUrl: String) {
        if (value == null) return

        val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val jsonObject = JSONObject().apply {
            put("type", type)
            put("value", value)
            put("current_time", currentTime)
            put("location", JSONObject().apply {
                put("latitude", currentLatitude ?: 0.0)
                put("longitude", currentLongitude ?: 0.0)
            })
        }

        val client = OkHttpClient()
        val body = RequestBody.create("application/json".toMediaTypeOrNull(), jsonObject.toString())
        val request = Request.Builder().url(apiUrl).post(body).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@SensorsActivity, "Failed to send $type data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    runOnUiThread {
                        Toast.makeText(this@SensorsActivity, "$type data sent successfully", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        when (event.sensor.type) {
            Sensor.TYPE_LIGHT -> latestLight = event.values[0]
            Sensor.TYPE_RELATIVE_HUMIDITY -> latestHumidity = event.values[0]
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onResume() {
        super.onResume()
        lightSensor?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL) }
        humiditySensor?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL) }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        executor.shutdownNow()
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
            1000 // Request location every 1 second
        ).build()

        val locationCallback = object : com.google.android.gms.location.LocationCallback() {
            override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                val location = locationResult.lastLocation
                if (location != null) {
                    currentLatitude = location.latitude
                    currentLongitude = location.longitude
                    Toast.makeText(
                        this@SensorsActivity,
                        "Location updated: $currentLatitude, $currentLongitude",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Remove updates once we get a location to save resources
                    fusedLocationClient.removeLocationUpdates(this)
                } else {
                    Toast.makeText(this@SensorsActivity, "Unable to fetch location", Toast.LENGTH_SHORT).show()
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }
}
