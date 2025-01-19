package si.um.feri.serverless

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import si.um.feri.sensorlibrary.SensorRecord
import si.um.feri.serverless.databinding.SensorRecordBinding

class SensorRecordAdapter(
    private val sensorRecords: List<SensorRecord>,
    private val onSwitchToggle: (SensorRecord, Boolean) -> Unit,
    private val onSensorLongPressed: (Int) -> Unit
) : RecyclerView.Adapter<SensorRecordAdapter.SensorRecordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorRecordViewHolder {
        val binding = SensorRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SensorRecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SensorRecordViewHolder, position: Int) {
        val sensorRecord = sensorRecords[position]
        holder.bind(sensorRecord)
        // Long press listener for deletion
        holder.itemView.setOnLongClickListener {
            onSensorLongPressed(position)
            true
        }
    }

    override fun getItemCount(): Int = sensorRecords.size

    inner class SensorRecordViewHolder(private val binding: SensorRecordBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(sensorRecord: SensorRecord) {
            val sensor = sensorRecord.sensor

            binding.typeTxt.text = sensor.type
            binding.rangeTxt.text = "From " + sensor.rangeFrom.toString() + " to " + sensor.rangeTo.toString()
            binding.frequencyTxt.text = "Every " + sensor.frequency
            binding.locationTxt.text = sensor.location
            binding.enableSensorSwitch.isChecked = sensor.enabled

            // Handle switch toggle
            binding.enableSensorSwitch.setOnCheckedChangeListener { _, isChecked ->
                onSwitchToggle(sensorRecord, isChecked)
            }
        }
    }
}
