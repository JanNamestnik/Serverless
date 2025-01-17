package si.um.feri.serverless

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import si.um.feri.serverless.databinding.ActivitySimulationBinding

class SimulationActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySimulationBinding

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

        initializeButtons()
    }


    private fun initializeButtons() {
        binding.simulationBackBtn.setOnClickListener{
            finish()
        }

        binding.addSensorBtn.setOnClickListener{
            //val intent = Intent(this, AddSensorActivity::class.java)
            //startActivity(intent)
        }
    }
}