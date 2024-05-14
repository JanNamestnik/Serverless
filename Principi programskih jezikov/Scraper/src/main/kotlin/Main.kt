package scraping

import it.skrape.core.htmlDocument
import it.skrape.fetcher.BrowserFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.html5.*
import it.skrape.selects.text

fun getEvent(s: String?) {
    if (s == null) return
    skrape(BrowserFetcher) {
        request {
            url = s
        }

        response {
            htmlDocument {
                println("Name: ${h2 { withClass = "heading__title";findFirst { text } }}")
                val venueIcon = i { withClass = "fa-pencil-alt"; findFirst {siblings} }
                val date = i {withClass = "fa-calendar-alt"; findFirst { siblings }}
                println("Date: ${date.text}")
                println("Address: ${p { withClass = "date"; findFirst{ text } }}")
                println("endDate: ${p { withClass = "location"; findFirst { text } }}")
            }
        }
    }
}

    fun main() {
        println("========== scraping - primer ==========")

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
                                println(it.attributes["href"])
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
