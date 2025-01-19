package si.um.feri.serverless

import android.annotation.SuppressLint
import android.app.Application
import org.json.JSONArray
import org.json.JSONObject
import si.um.feri.personlibrary.Person
import si.um.feri.personlibrary.PersonRecord
import java.io.InputStream

class MyApplication : Application() {

    var personRecords = mutableListOf<PersonRecord>()

    @SuppressLint("NewApi")
    fun loadPersonRecordsFromJson(jsonInput: InputStream) {
        try {
            // Read the JSON file from the InputStream
            val jsonString = jsonInput.bufferedReader().use { it.readText() }

            // Parse the JSON string
            val jsonObject = JSONObject(jsonString)
            val resultsArray: JSONArray = jsonObject.getJSONArray("results")

            // Iterate over the JSON array and populate the personRecords list
            for (i in 0 until resultsArray.length()) {
                val personObject = resultsArray.getJSONObject(i)

                val age = personObject.getString("age")
                val gender = personObject.getString("gender")
                val faceImageEncoded = personObject.getString("face_image")

                // Create a Person and PersonRecord object
                val person = Person(age, gender, faceImageEncoded)
                val personRecord = PersonRecord(person)

                // Add to the list
                personRecords.add(personRecord)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
