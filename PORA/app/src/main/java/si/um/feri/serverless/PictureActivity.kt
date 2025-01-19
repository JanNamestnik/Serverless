package si.um.feri.serverless

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import si.um.feri.serverless.databinding.ActivityPictureBinding
import java.io.File
import java.io.IOException
import java.io.InputStream
import android.util.Log


class PictureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPictureBinding

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_CAMERA_PERMISSION = 2
    }

    private lateinit var photoFile: File
    private lateinit var adapter: PersonRecordAdapter

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

        setupRecyclerView()
        loadAndDisplayPersons()
        initializeButtons()
    }

    private fun initializeButtons() {
        binding.takePictureBtn.setOnClickListener {
            // Check if the camera permission is granted
            if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                // Request the camera permission
                requestPermissions(arrayOf(android.Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
            }
        }

        binding.imageButton.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        val app = application as MyApplication
        adapter = PersonRecordAdapter(app.personRecords) { personRecord ->
            val message = "Clicked on ${personRecord.person.age}, ${personRecord.person.gender}"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }


    private fun loadAndDisplayPersons() {
        try {
            val app = application as MyApplication

            val file = File(filesDir, "api_response.json")
            if (file.exists()) {
                val jsonInput: InputStream = file.inputStream()
                app.personRecords.clear()
                app.loadPersonRecordsFromJson(jsonInput)
            }

            adapter.notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to load JSON", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (photoFile.exists()) {
                // Send the photo file to the API
                sendPhotoToApi(photoFile)
            } else {
                Log.e("IMAGE_ERROR", "Photo file does not exist.")
            }
        }
    }

    private fun sendPhotoToApi(photoFile: File) {
        val client = OkHttpClient()

        // Create multipart request body
        val body = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "file", photoFile.name,
                RequestBody.create("image/jpeg".toMediaTypeOrNull(), photoFile)
            )
            .build()

        // Build the request
        val request = Request.Builder()
            .url("http://10.0.2.2:5000/predict")
            .post(body)
            .addHeader("accept", "application/json")
            .addHeader("Content-Type", "multipart/form-data")
            .build()

        // Execute the request
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("API_ERROR", "Failed to connect to API: ${e.message}")
                runOnUiThread {
                    Toast.makeText(this@PictureActivity, "Failed to connect to API", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseString = response.body?.string()
                    Log.d("API_RESPONSE", "API Response: $responseString")

                    saveJsonResponse(responseString)

                    runOnUiThread {
                        loadAndDisplayPersons()
                    }
                } else {
                    val errorBody = response.body?.string()
                    Log.e("API_ERROR", "API error: ${response.code} - ${response.message}")
                    Log.e("API_ERROR_BODY", "Error body: $errorBody")

                    runOnUiThread {
                        Toast.makeText(this@PictureActivity, "API error: ${response.code}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun saveJsonResponse(response: String?) {
        if (response == null) return

        val file = File(filesDir, "api_response.json")
        file.writeText(response)
    }

    private fun openCamera() {
        val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)

        // Create a file for the photo
        photoFile = File(filesDir, "captured_photo.jpg")
        val uri = androidx.core.content.FileProvider.getUriForFile(
            this,
            "${applicationContext.packageName}.provider",
            photoFile
        )

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted; open the camera
                openCamera()
            } else {
                // Permission denied; show a message
                Toast.makeText(this, "Camera permission is required to take a photo", Toast.LENGTH_SHORT).show()
            }
        }
    }
}