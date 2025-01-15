package si.um.feri.serverless

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import si.um.feri.serverless.databinding.ActivityPictureBinding
import java.io.InputStream

class PictureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPictureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_picture)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityPictureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadAndDisplayPersons()
        initializeButtons()
    }

    private fun initializeButtons() {
        binding.takePictureBtn.setOnClickListener {
            // start camera
        }

        binding.imageButton.setOnClickListener {
            finish()
        }
    }

    private fun loadAndDisplayPersons() {
        try {
            // Get the application instance
            val app = application as MyApplication

            // Check if the list is already populated
            if (app.personRecords.isEmpty()) {
                // Load the JSON file from assets
                val jsonInput: InputStream = assets.open("test_persons.json")

                // Load the data into MyApplication's personRecords
                app.loadPersonRecordsFromJson(jsonInput)
            }

            // Set up the RecyclerView
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.adapter = PersonRecordAdapter(app.personRecords) { personRecord ->
                // Handle item clicks here
                val message = "Clicked on ${personRecord.person.age}, ${personRecord.person.gender}"
                android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            android.widget.Toast.makeText(this, "Failed to load JSON", android.widget.Toast.LENGTH_SHORT).show()
        }
    }

}