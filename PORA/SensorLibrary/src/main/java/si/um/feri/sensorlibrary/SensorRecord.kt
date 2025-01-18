package si.um.feri.sensorlibrary

import java.util.UUID

class SensorRecord(
    var sensor: Sensor,
    val uuid: String = UUID.randomUUID().toString(),
    ) {
        override fun toString(): String {
            return "PersonRecord(person=$sensor, uuid='$uuid')"
        }
    }