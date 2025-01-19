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
    private val onSensorLongPressed: (Int) -> Unit,
    private val onSensorShortPressed: (Int) -> Unit
) : RecyclerView.Adapter<SensorRecordAdapter.SensorRecordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorRecordViewHolder {
        val binding = SensorRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SensorRecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SensorRecordViewHolder, position: Int) {
        val sensorRecord = sensorRecords[position]
        holder.bind(sensorRecord)
        holder.itemView.setOnClickListener {
            onSensorShortPressed(position) // Trigger short press for editing
        }
        holder.itemView.setOnLongClickListener {
            onSensorLongPressed(position)
            true
        }
    }

    override fun getItemCount(): Int = sensorRecords.size

    inner class SensorRecordViewHolder(private val binding: SensorRecordBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(sensorRecord: SensorRecord) {
            val sensor = sensorRecord.sensor

            binding.typeTxt.text = sensor.type
            binding.rangeTxt.text = "From ${sensor.rangeFrom} to ${sensor.rangeTo}"
            binding.frequencyTxt.text = "Every ${sensor.frequency}"
            binding.locationTxt.text = sensor.location
            binding.enableSensorSwitch.isChecked = sensor.enabled

            binding.enableSensorSwitch.setOnCheckedChangeListener { _, isChecked ->
                onSwitchToggle(sensorRecord, isChecked)
            }
        }
    }
}

