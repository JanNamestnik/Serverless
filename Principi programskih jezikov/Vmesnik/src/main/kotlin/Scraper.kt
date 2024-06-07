package scraping

import it.skrape.core.htmlDocument
import it.skrape.fetcher.BrowserFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.DocElement
import it.skrape.selects.html5.*
import it.skrape.selects.text
import com.google.gson.annotations.Expose

import org.bson.types.ObjectId

import com.google.gson.*
import java.lang.reflect.Type



data class Location(
    @Expose val type: String,
    @Expose val coordinates: List<Double>
)

data class Event(
    @Expose(serialize = false, deserialize = false)
    val _id: ObjectId?,

    @Expose
    val name: String?,

    @Expose
    val address: String?,

    @Expose
    val startTime: String?,

    @Expose
    val date_start: String?,

    @Expose
    val date_end: String?,

    @Expose
    val description: String?,

    @Expose
    val contact: String?,

    @Expose
    val category: ObjectId?,

    @Expose
    val location: Location?,

    @Expose
    val eventImage: String?,

    @Expose
    val price: Int = 0,

    @Expose
    val attendees: List<String> = emptyList(),

    @Expose
    val owner: ObjectId?
)


fun getCategoryID(category: String): ObjectId {
    return when (category) {
        "Razstava" -> ObjectId("6643ef1e35e389b1272f6b82")
        "Športne prireditve" -> ObjectId("6642325dff14c4a75477259a")
        "Vinsko-kulinarične prireditve" -> ObjectId("66579370914872027c245c3e")
        "Delavnice" -> ObjectId("66579337914872027c245c3d")
        "Koncert" -> ObjectId("66423238ff14c4a754772598")
        "Festival" -> ObjectId("66423250ff14c4a754772599")
        "Drugo" -> ObjectId("6643ef3e35e389b1272f6b83")
        else -> ObjectId("6643ef3e35e389b1272f6b83")
    }
}


fun dateSeparatorVisitMaribor(elements: List<List<DocElement>?>): Triple<String?, String?, String?> {
    val datePattern = """(\d{1,2}\.\s\d{1,2}\.\s\d{4})""".toRegex()
    val timePattern = """(\d{1,2}:\d{2})""".toRegex()

    val matchResult = elements[0]?.let { datePattern.findAll(it.text) }
    val timeMatchResult = elements[0]?.let { timePattern.find(it.text) }

    val dates = matchResult?.map { it.value }?.toList()

    val firstDate = dates?.getOrNull(0)
    val secondDate = dates?.getOrNull(1)
    val startTime = timeMatchResult?.value

    return Triple(firstDate, secondDate, startTime)
}

fun getEvent(s: String?): Event? {
    if (s == null) return null
    return skrape(BrowserFetcher) {
        request {
            url = s
        }

        response {
            htmlDocument {
                val eventName = try {
                    h2 { withClass = "heading__title"; findFirst { text } }
                } catch (e: Exception) {
                    null
                }
                val address = try {
                    i { withClass = "fa-map-marker-alt"; findFirst { siblings } }
                } catch (e: Exception) {
                    null
                }
                val date = try {
                    i { withClass = "fa-calendar-alt"; findFirst { siblings } }
                } catch (e: Exception) {
                    null
                }
                val (firstDate, secondDate, startTime) = dateSeparatorVisitMaribor(listOf(date))
                val description = try {
                    div { withClass = "paragraph-emphasis"; findFirst { p { findFirst { text } } } }
                } catch (e: Exception) {
                    null
                }
                val contact = try {
                    i { withClass = "fa-user"; findFirst { siblings } }
                } catch (e: Exception) {
                    null
                }
                val category = try {
                    i { withClass = "fa-star"; findFirst { siblings } }
                } catch (e: Exception) {
                    null
                }
                val eventImage = try {
                    picture { img { findFirst { attributes["data-src"] } } }
                } catch (e: Exception) {
                    null
                }
                val price = 0

                val coordinates = try {
                    val scriptText = script { findAll { text } }
                    val regex = """\s*LatLng\s*\(([-\d.]+),\s*([-\d.]+)\)\s*""".toRegex()
                    val matchResult = regex.find(scriptText)
                    val latitude = matchResult?.groups?.get(1)?.value?.toDoubleOrNull()
                    val longitude = matchResult?.groups?.get(2)?.value?.toDoubleOrNull()
                    if (latitude != null && longitude != null) {
                        listOf(longitude, latitude)
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    null
                }

                Event(
                    _id = null,
                    name = eventName,
                    address = address?.text,
                    startTime = startTime,
                    date_start = firstDate,
                    date_end = secondDate,
                    description = description,
                    contact = contact?.text,
                    category = category?.text?.let { getCategoryID(it) },
                    location = Location("Point", coordinates ?: listOf(15.649242, 46.559036)),
                    eventImage = "https://www.visitmaribor.si$eventImage",
                    price = price,
                    attendees = emptyList(),
                    owner = ObjectId("6651c0a0278d45f6f2502b7b")
                )
            }
        }
    }
}

fun fetchEvents(maxEvents: Int = 10): List<Event> {
    val events = mutableListOf<Event>()
    skrape(BrowserFetcher) {
        request {
            url = "https://www.visitmaribor.si/si/kaj-poceti/dogodki-in-prireditve/vsi-dogodki-in-prireditve/"
        }

        response {
            htmlDocument {
                a {
                    withClass = "catalogue-item"
                    findAll {
                        var count = 0
                        forEach {
                            if (count >= maxEvents) return@forEach
                            if (it.attributes["href"] != null) {
                                getEvent(it.attributes["href"])?.let { event ->
                                    events.add(event)
                                    count++
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    return events
}


class ObjectIdSerializer : JsonSerializer<ObjectId>, JsonDeserializer<ObjectId> {
    override fun serialize(src: ObjectId, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        // Custom JSON element representing an ObjectId
        val obj = JsonObject()
        obj.add("\$oid", JsonPrimitive(src.toHexString()))
        return obj
    }

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ObjectId {
        val hexString = json.asJsonObject.get("\$oid").asString
        return ObjectId(hexString)
    }
}
