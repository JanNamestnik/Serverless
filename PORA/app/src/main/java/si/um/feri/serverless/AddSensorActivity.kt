package si.um.feri.serverless

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import si.um.feri.sensorlibrary.Sensor
import si.um.feri.serverless.databinding.ActivityAddSensorBinding
import java.io.IOException
import java.util.*

class AddSensorActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityAddSensorBinding
    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_sensor)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityAddSensorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeButtons()
        setupTypeDropdown()
        setupNumberPickers()
        setupSwitchListener()

        initializeMap()

        // Check if we're editing an existing sensor
        val sensorToEdit = intent.getSerializableExtra("editing_sensor") as? Sensor
        if (sensorToEdit != null) {
            prefillFields(sensorToEdit)
        }
    }

    private fun prefillFields(sensor: Sensor) {
        binding.dataTypeSpinner.setSelection((binding.dataTypeSpinner.adapter as ArrayAdapter<String>).getPosition(sensor.type))
        binding.rangeFromInput.setText(sensor.rangeFrom.toString())
        binding.rangeToInput.setText(sensor.rangeTo.toString())
        val frequencyParts = sensor.frequency.split("h:", "m:", "s")
        binding.hourPicker.value = frequencyParts[0].toIntOrNull() ?: 0
        binding.minutePicker.value = frequencyParts[1].toIntOrNull() ?: 0
        binding.secondPicker.value = frequencyParts[2].toIntOrNull() ?: 0
        binding.locationInput.setText(sensor.location)
        binding.addSensorSwitch.isChecked = sensor.enabled
    }

    private fun initializeButtons() {
        binding.addSensorsBackBtn.setOnClickListener {
            finish()
        }

        binding.saveSensorBtn.setOnClickListener {
            val type = binding.dataTypeSpinner.selectedItem.toString()
            val rangeFrom = binding.rangeFromInput.text.toString().toDoubleOrNull() ?: 0.0
            val rangeTo = binding.rangeToInput.text.toString().toDoubleOrNull() ?: 0.0
            val frequency = "${binding.hourPicker.value}h:${binding.minutePicker.value}m:${binding.secondPicker.value}s"
            val location = binding.locationInput.text.toString()
            val isEnabled = binding.addSensorSwitch.isChecked

            // Create a new Sensor object
            val sensor = Sensor(
                type = type,
                rangeFrom = rangeFrom,
                rangeTo = rangeTo,
                frequency = frequency,
                location = location,
                enabled = isEnabled
            )

            // Return the sensor to SimulationActivity
            val resultIntent = Intent()
            resultIntent.putExtra("new_sensor", sensor)
            setResult(RESULT_OK, resultIntent)
            finish() // Close AddSensorActivity
        }


        // Search button functionality
        binding.mapSearchButton.setOnClickListener {
            val locationName = binding.locationInput.text.toString()
            if (locationName.isNotEmpty()) {
                searchLocation(locationName)
            } else {
                Toast.makeText(this, "Please enter a location to search", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupTypeDropdown() {
        val types = arrayOf("Humidity (%)", "Light (lux)")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, types)
        binding.dataTypeSpinner.adapter = adapter
    }

    private fun setupNumberPickers() {
        binding.hourPicker.minValue = 0
        binding.hourPicker.maxValue = 23
        binding.minutePicker.minValue = 0
        binding.minutePicker.maxValue = 59
        binding.secondPicker.minValue = 0
        binding.secondPicker.maxValue = 59
    }

    private fun setupSwitchListener() {
        binding.addSensorSwitch.setOnCheckedChangeListener { _, isChecked ->
            binding.addSensorSwitch.text = if (isChecked) "On" else "Off"
        }
    }

    private fun initializeMap() {
        binding.mapFragment.onCreate(null)
        binding.mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Set an initial location (e.g., Slovenia)
        val initialLocation = LatLng(46.1512, 14.9955)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 7f))

        // Allow the user to set a marker by tapping on the map
        googleMap.setOnMapClickListener { location ->
            googleMap.clear()
            googleMap.addMarker(MarkerOptions().position(location).title("Selected Location"))

            // Use Geocoder to convert coordinates to a location name
            val locationName = getAddressFromLatLng(location)
            binding.locationInput.setText(locationName)
        }
    }

    private fun searchLocation(locationName: String) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: MutableList<Address>? = geocoder.getFromLocationName(locationName, 1)
            if (addresses != null) {
                if (addresses.isNotEmpty()) {
                    val address = addresses[0]
                    val latLng = LatLng(address.latitude, address.longitude)

                    // Move the map and place a marker
                    googleMap.clear()
                    googleMap.addMarker(MarkerOptions().position(latLng).title(locationName))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                } else {
                    Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Unable to search location: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getAddressFromLatLng(latLng: LatLng): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        return try {
            val addresses: MutableList<Address>? = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (addresses?.isNotEmpty() == true) {
                val address = addresses[0]
                // Return a human-readable address (e.g., city or locality)
                address.locality ?: address.getAddressLine(0) ?: "Unknown Location"
            } else {
                "Unknown Location"
            }
        } catch (e: IOException) {
            e.printStackTrace()
            "Unknown Location"
        }
    }

    override fun onStart() {
        super.onStart()
        binding.mapFragment.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapFragment.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapFragment.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapFragment.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapFragment.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapFragment.onLowMemory()
    }
}
