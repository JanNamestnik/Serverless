package scraping

import it.skrape.core.htmlDocument
import it.skrape.fetcher.BrowserFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.DocElement
import it.skrape.selects.html5.*
import it.skrape.selects.text

fun dateSeparatorVisitMaribor(elements: List<List<DocElement>>): Triple<String?, String?, String?> {
    val datePattern =  """(\d{1,2}\.\s\d{1,2}\.\s\d{4})""".toRegex()
    val timePattern = """(\d{1,2}:\d{2})""".toRegex()

    val matchResult = datePattern.findAll(elements[0].text)
    val timeMatchResult = timePattern.find(elements[0].text)

    val dates = matchResult.map { it.value }.toList()

    val firstDate = dates.getOrNull(0)
    val secondDate = dates.getOrNull(1)
    val startTime = timeMatchResult?.value

    return Triple(firstDate, secondDate, startTime)
}

fun dateSeparatorEntrio(dateString: String): Pair<String?, String?> {
    val datePattern = """(\d{1,2}\.\d{1,2}\.\d{4})""".toRegex()
    val timePattern = """(\d{1,2}:\d{2})""".toRegex()

    val dateMatchResult = datePattern.find(dateString)
    val timeMatchResult = timePattern.find(dateString)

    val date = dateMatchResult?.value
    val startTime = timeMatchResult?.value

    return Pair(date, startTime)
}

fun getEvent(s: String?) {
    if (s == null) return
    skrape(BrowserFetcher) {
        request {
            url = s
        }

        response {
            htmlDocument {
                println("\n========== Event Visit Maribor ==========")
                try{
                    val eventName = h2 { withClass = "heading__title"; findFirst { text } }
                    println("Name: $eventName")
                }catch (e:Exception){
                    println("Name: Not available")
                }
                try {
                    val address = i { withClass = "fa-map-marker-alt"; findFirst { siblings } }
                    println("Address: ${address.text}")
                }catch (e:Exception){
                    println("Address: Not available")
                }
                try {
                    val date = i { withClass = "fa-calendar-alt"; findFirst { siblings } }
                    var (firstDate, secondDate, startTime) = dateSeparatorVisitMaribor(listOf(date))
                    // V primeru, da je dogodek enodnevni, je drugi datum enak prvemu
                    if (secondDate == null) {
                        secondDate = firstDate
                    }
                    if (startTime == null) {
                        startTime = "When the venue opens" // v primeru, da ni navedenega casa
                    }
                    println("Start Time: $startTime")
                    println("Start Date: $firstDate")
                    println("Finish Date: $secondDate")
                } catch (e: Exception) {
                    println("Date: Not available")
                }
                try {
                    val description = div { withClass = "paragraph-emphasis"; findFirst { p { findFirst { text } } } }
                    println("Description: $description")
                }catch (e:Exception){
                    println("Description: Not available")
                }
                try {
                    val contact = i { withClass = "fa-user"; findFirst { siblings } }
                    println("Contact: ${contact.text}")
                } catch (e: Exception) {
                    println("Contact: Not available")
                }
                try {
                    val category = i { withClass = "fa-star"; findFirst { siblings } }
                    println("Category: ${category.text}")

                } catch (e: Exception) {
                    println("Category: Not available")
                }
                try {
                    val eventImage = picture { img { findFirst { attributes["data-src"] } } }
                    val fullImageUrl = "https://www.visitmaribor.si$eventImage"
                    println("Image url: $fullImageUrl")
                    println("Site url: $s")
                }catch (e: Exception) {
                    println("Image: Not available")
                }
            }
        }
    }
}

fun getEventEntrio(s: String?) {
    if (s == null) return
    skrape(BrowserFetcher) {
        request {
            url = s
        }
        response {
            htmlDocument {
                println("\n========== Entrio Events ==========")
                try {
                    val eventName = h1 { withClass = "event-header__title"; findFirst { text } }
                    println("Name: $eventName")
                }catch (e:Exception){
                    println("Name: Not available")
                }
                try {
                    val address = a { withClass = "event-header__location"; findFirst { text } }
                    println("Location: $address")
                }catch (e:Exception){
                    println("Address: Not available")
                }
                try {
                    val date = p { withClass = "event-header__time"; findFirst { text } }
                    var (startDate, startTime) = dateSeparatorEntrio(date)

                    if (startTime == null) {
                        startTime = "When the venue opens"
                    }
                    println("Start Time: $startTime")
                    println("Start Date: $startDate")
                } catch (e: Exception) {
                    println("Date: Not available")
                }
                try{
                    val description = div { withClass = "event-description__content"; findThird { text } }
                    println("Description: $description")
                }catch (e:Exception) {
                    println("Description: Not available")
                }
                try {
                    val eventImage = img {
                        withClass = "event-banner"
                        findFirst { attributes["src"] }
                    } ?: img {
                        withClass = "event-header__img"
                        findFirst { attributes["src"] }
                    }
                    println("Image url: $eventImage")
                } catch (e: Exception) {
                    println("Image: Not available")
                }
                try {
                    val contact = a { withClass = "contact-organizer__action"; findFirst { text } }
                    println("Contact: $contact")
                }catch(e:Exception){
                    println("Contact: Not available")
                }

            }
        }
    }
}

fun main() {
    val maxEvents = 20 // Najvecje stevilo pridobljenih dogodkov iz vira, zaradi optimizacije

    skrape(BrowserFetcher) {
        request {
            url = "https://www.visitmaribor.si/si/kaj-poceti/dogodki-in-prireditve/vsi-dogodki-in-prireditve/"
        }

        response {
            println("http status code: ${status { code }}")
            println("http status message: ${status { message }}")

            htmlDocument {
                a {
                    withClass = "catalogue-item"

                    findAll {
                        var count = 0 // Initialize counter
                        forEach {
                            if (count >= maxEvents) return@forEach // Gremo ven iz zanke, ko dosežemo maxEvents
                            if (it.attributes["href"] != null) {
                                getEvent(it.attributes["href"])
                                count++
                            }
                        }
                    }
                }
            }
        }
    }
    skrape(BrowserFetcher) {
        request {
            url = "https://www.entrio.si/events?s=&sort=-1&ci%5B%5D=102"
        }
        response {
            println("http status code: ${status { code }}")
            println("http status message: ${status { message }}")

            htmlDocument {
                a {
                    withClass = "event-card__action"
                    findAll {
                        forEach {
                            if (it.attributes["href"] != null) {
                                getEventEntrio(it.attributes["href"])
                            }
                        }
                    }
                }
            }
        }
    }
}



