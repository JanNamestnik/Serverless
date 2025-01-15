package si.um.feri.serverless

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import si.um.feri.personlibrary.PersonRecord
import si.um.feri.serverless.databinding.PersonRecordBinding

class PersonRecordAdapter(
    private val personRecords: List<PersonRecord>,
    private val onItemClick: (PersonRecord) -> Unit,
) : RecyclerView.Adapter<PersonRecordAdapter.PersonRecordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonRecordViewHolder {
        val binding = PersonRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonRecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonRecordViewHolder, position: Int) {
        val personRecord = personRecords[position]
        holder.bind(personRecord, position)
    }

    override fun getItemCount(): Int = personRecords.size

    inner class PersonRecordViewHolder(private val binding: PersonRecordBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(personRecord: PersonRecord, position: Int) {

            val person = personRecord.person
            binding.nameTxt.text = "Person ${position + 1}"
            binding.age.text = person.age
            binding.gender.text = person.gender

            // Set age text color based on the value
            when (person.age) {
                "Under 21" -> binding.age.setTextColor(itemView.context.getColor(android.R.color.holo_red_light))
                "Over 21" -> binding.age.setTextColor(itemView.context.getColor(android.R.color.holo_green_light))
                else -> binding.age.setTextColor(itemView.context.getColor(android.R.color.black))
            }

            // Decode Base64 to Bitmap and display it
            val faceBitmap = decodeBase64ToBitmap(person.faceImage)
            if (faceBitmap != null) {
                binding.imageView.setImageBitmap(faceBitmap)
            } else {
                binding.imageView.setImageResource(R.drawable.baseline_person_24)
            }

            // Handle item click
            binding.root.setOnClickListener {
                onItemClick(personRecord)
            }
        }

        private fun decodeBase64ToBitmap(base64String: String): Bitmap? {
            return try {
                val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}
