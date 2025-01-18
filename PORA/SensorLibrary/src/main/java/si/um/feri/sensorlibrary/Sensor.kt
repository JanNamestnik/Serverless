package si.um.feri.sensorlibrary

import java.io.Serializable

class Sensor(
    val type: String,
    val rangeFrom: Double,
    val rangeTo: Double,
    val frequency: String,
    val location: String,
    var enabled: Boolean
) : Serializable
{
    override fun toString(): String {
        return "Sensor(Type: $type, RangeFrom: $rangeFrom, RangeTo: $rangeTo, Time: $frequency, Location: $location, Enabled: $enabled)"
    }
}