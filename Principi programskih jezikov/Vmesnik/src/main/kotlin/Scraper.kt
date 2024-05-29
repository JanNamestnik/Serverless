package scraping

import it.skrape.core.htmlDocument
import it.skrape.fetcher.BrowserFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.DocElement
import it.skrape.selects.html5.*
import it.skrape.selects.text

data class Location(
    val type: String,
    val coordinates: List<Double>
)

data class Event(
    val _id: String?,
    val name: String?,
    val address: String?,
    val startTime: String?,
    val date_start: String?,
    val date_end: String?,
    val description: String?,
    val contact: String?,
    val category: String?,
    val location: Location?,
    val eventImage: String?,
    val price: String = "Free",
    val attendees: List<String> = emptyList()
)

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
                val price = try {
                    i { withClass = "fa-euro-sign"; findFirst { siblings } }
                } catch (e: Exception) {
                    "Free"
                }

                // Extract location coordinates (longitude and latitude)
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
                    category = category?.text,
                    location = coordinates?.let { Location("Point", it) },
                    eventImage = "https://www.visitmaribor.si$eventImage",
                    price = "Free",
                    attendees = emptyList()
                )
            }
        }
    }
}

fun fetchEvents(maxEvents: Int = 5): List<Event> {
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
