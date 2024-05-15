package scraping

import it.skrape.core.htmlDocument
import it.skrape.fetcher.BrowserFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.DocElement
import it.skrape.selects.html5.*
import it.skrape.selects.text

fun dateSeparator(elements: List<List<DocElement>>): Triple<String?, String?, String?> {
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

fun getEvent(s: String?) {
    if (s == null) return
    val siteHref = s
    skrape(BrowserFetcher) {
        request {
            url = s
        }

        response {
            htmlDocument {
                println("\n========== Event ==========")
                println("Name: ${h2 { withClass = "heading__title"; findFirst { text } }}")
                val address = i { withClass = "fa-map-marker-alt"; findFirst { siblings } }
                println("Address: ${address.text}")
                try {
                    val date = i { withClass = "fa-calendar-alt"; findFirst { siblings } }
                    var (firstDate, secondDate, startTime) = dateSeparator(listOf(date))
                    // V primeru, da je dogodek enodnevni, je drugi datum enak prvemu
                    if (secondDate == null) {
                        secondDate = firstDate
                    }
                    if(startTime == null){
                        startTime = "When the venue opens"
                    }
                    println("Start Time: $startTime")
                    println("Start Date: $firstDate")
                    println("Finish Date: $secondDate")
                }catch (e: Exception){
                    println("Date: Not available")
                }
                val description = div { withClass = "paragraph-emphasis"; findFirst { p {  findFirst{text} } } }
                println("Description: $description")
                try{
                    val contact = i { withClass = "fa-user"; findFirst { siblings } }
                    println("Contact: ${contact.text}")
                }catch (e: Exception){
                    println("Contact: Not available")
                }
                try{
                    val category = i { withClass = "fa-star"; findFirst { siblings } }
                    println("Category: ${category.text}")

                }catch (e: Exception){
                    println("Category: Not available")
                }
                val eventImage = picture {img { findFirst { attributes["data-src"] } }}
                val fullImageUrl = "https://www.visitmaribor.si" + eventImage
                println("Image url: $fullImageUrl")
                println("Site url: $siteHref")
            }
        }
    }
}

    fun main() {
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
                            forEach {
                                if (it.attributes["href"] != null) {
                                    getEvent(it.attributes["href"])
                                }
                            }
                        }
                    }
                }
            }
        }
    }
