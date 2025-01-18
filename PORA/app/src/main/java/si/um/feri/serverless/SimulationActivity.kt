package si.um.feri.serverless

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import si.um.feri.sensorlibrary.Sensor
import si.um.feri.sensorlibrary.SensorRecord
import si.um.feri.serverless.databinding.ActivitySimulationBinding

class SimulationActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySimulationBinding
    private lateinit var adapter: SensorRecordAdapter
    private val sensorRecords = mutableListOf<SensorRecord>()

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
        adapter = SensorRecordAdapter(sensorRecords) { sensorRecord, isEnabled ->
            toggleSensor(sensorRecord, isEnabled)
        }
        binding.recyclerViewSensors.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewSensors.adapter = adapter
    }

    private fun initializeButtons() {
        binding.simulationBackBtn.setOnClickListener {
            finish()
        }

        binding.addSensorBtn.setOnClickListener {
            android.widget.Toast.makeText(this, "Add Sensor functionality not implemented yet.", android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadPreferences() {
        val sharedPreferences = getSharedPreferences("SensorPrefs", MODE_PRIVATE)

        sensorRecords.addAll(
            listOf(
                SensorRecord(
                    sensor = Sensor(
                        type = "Temperature Sensor",
                        rangeFrom = -20.00,
                        rangeTo = 50.00,
                        frequency = "10 seconds",
                        location = "Room 1",
                        enabled = sharedPreferences.getBoolean("Temperature Sensor", false)
                    )
                ),
                SensorRecord(
                    sensor = Sensor(
                        type = "Humidity Sensor",
                        rangeFrom = 0.00,
                        rangeTo = 100.00,
                        frequency = "5 seconds",
                        location = "Room 2",
                        enabled = sharedPreferences.getBoolean("Humidity Sensor", false)
                    )
                ),
                SensorRecord(
                    sensor = Sensor(
                        type = "Light Sensor",
                        rangeFrom = 0.00,
                        rangeTo = 2000.00,
                        frequency = "1 minute",
                        location = "Room 3",
                        enabled = sharedPreferences.getBoolean("Light Sensor", false)
                    )
                )
            )
        )

        adapter.notifyDataSetChanged()
    }

    private fun toggleSensor(sensorRecord: SensorRecord, isEnabled: Boolean) {
        sensorRecord.sensor.enabled = isEnabled

        // Save the updated state in SharedPreferences
        val sharedPreferences = getSharedPreferences("SensorPrefs", MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean(sensorRecord.sensor.type, isEnabled)
            apply()
        }

        // Notify the user
        val message = if (isEnabled) {
            "${sensorRecord.sensor.type} enabled."
        } else {
            "${sensorRecord.sensor.type} disabled."
        }
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
    }
}

