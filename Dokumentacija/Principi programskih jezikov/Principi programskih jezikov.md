<a name="readme-top"></a>

<div align="center">
  <h1 align="center">Scraper dogodkov in Vmesnik</h1>

  <p align="center">
    Celovita aplikacija za pridobivanje, upravljanje in prikaz podatkov o dogodkih.
    <br />
    <a href="https://github.com/JanNamestnik/Serverless/tree/main">Projekt</a>
    ·
    <a href="https://github.com/JanNamestnik/Serverless/tree/main/Principi%20programskih%20jezikov/Scraper">Scraper</a>
    ·
    <a href="https://github.com/JanNamestnik/Serverless/tree/main/Principi%20programskih%20jezikov/Vmesnik">Vmesnik</a>
    <br />
    <a href="https://github.com/JanNamestnik/Serverless/tree/devel/Dokumentacija"><strong>Vsa dokumentacija»</strong></a>
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Kazalo vsebine</summary>
  <ol>
    <li>
      <a href="#about-the-project">O projektu</a>
      <ul>
        <li><a href="#built-with">Uporabljena oprema</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Predpogoji</a></li>
        <li><a href="#installation">Namestitev</a></li>
      </ul>
    </li>
    <li><a href="#usage">Uporaba</a></li>
    <li><a href="#contact">Kontakt</a></li>
    <li><a href="#acknowledgments">Viri</a></li>
  </ol>
</details>

<!-- O projektu -->
## O projektu

![Slika!](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Principi%20programskih%20jezikov/Slike/appMain.png)

Ta projekt je namizna aplikacija, zgrajena s Kotlinom za pridobivanje podatkov o dogodkih iz spletnih strani in njihovo upravljanje v uporabniškem vmesniku. Omogoča uporabnikom ogled, urejanje in shranjevanje podatkov o dogodkih v bazi podatkov.



### Glavne funkcionalnosti aplikacije:
* Dodajanje in upravljanje dogodkov v podatkovni bazi
* Prikaz in urejanje vseh podatkov v PB
* Scrapa podatke iz [visitmaribor.si](https://www.visitmaribor.si/si/kaj-poceti/dogodki-in-prireditve/vsi-dogodki-in-prireditve/) in jih vstavi v bazo
* Ustvari izmišljene podatke za namene testiranja


<br />

## Uporabljena oprema

* [![Kotlin][Kotlin]][Kotlin-url]
* [![Compose for Desktop][Compose]][Compose-url]
* [![MongoDB][MongoDB]][MongoDB-url]

<br />

<!-- GETTING STARTED -->
## Getting Started

Če želite pridobiti lokalno kopijo in jo zagnati, sledite tem preprostim korakom.

### Kloniranje repozitorija
Najprej klonirajte repozitorij iz GitHub-a na vaš lokalni računalnik. V terminalu zaženite naslednji ukaz:
```sh
git clone https://github.com/USERNAME/REPOSITORY_NAME.git
```
Potem se pramaknemo v mapu s projektom
```sh
cd REPOSITORY_NAME
```

### Namestitev potrebne programske opreme

Preden zaženete scraper, boste morali namestiti nekaj programske opreme:

- **JDK (Java Development Kit):** Prepričajte se, da imate nameščen JDK 8 ali novejši. Lahko ga prenesete in namestite iz uradne strani [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html) ali uporabite alternativni JDK, kot je [OpenJDK](https://openjdk.java.net/).

- **Gradle:** Projekt uporablja Gradle za gradnjo in upravljanje odvisnosti. Gradle lahko namestite z uporabo naslednjih navodil:

  - Na Linux/macOS:
    ```sh
    sdk install gradle
    ```
  - Na Windows:
    Prenesite namestitveni program iz uradne spletne strani [Gradle](https://gradle.org/install/).

- **MongoDB:** Podatki se shranjujejo v MongoDB bazo podatkov. Prepričajte se, da imate nameščen MongoDB strežnik. Navodila za namestitev so na voljo na uradni [MongoDB strani](https://docs.mongodb.com/manual/installation/).

### Konfiguracija

Preden zaženete scraper, preverite konfiguracijo in prilagodite, če je potrebno.

### Nastavitve MongoDB

Prepričajte se, da je MongoDB strežnik zagnan in deluje na privzetem naslovu (`localhost:27017`). Če MongoDB deluje na drugem naslovu ali portu, posodobite ustrezne nastavitve v kodi.

### Odvisnosti projekta

Projektne odvisnosti so določene v datoteki `build.gradle`. Gradle bo samodejno prenesel potrebne knjižnice ob prvem zagonu projekta.

### Zagon scraperja

Ko so vse zgornje zahteve izpolnjene, lahko zaženete scraper z naslednjim ukazom:

- Na Linux/macOS:
  ```sh
  ./gradlew run
  ```
- Na Windows:
  ```sh
  gradlew.bat run
  ```

### Opombe
- Če naletite na težave, preverite, ali so vse odvisnosti pravilno nameščene in ali je MongoDB strežnik zagnan.
- Za podrobnejše informacije o uporabi Gradle-a in odpravljanju napak obiščite [Gradle dokumentacijo](https://docs.gradle.org/current/userguide/userguide.html).

<br />

<!-- USAGE EXAMPLES -->
## Uporaba in opis

Aplikacija omogoča pridobivanje podatkov o dogodkih iz spletne strani [visitmaribor.si](https://www.visitmaribor.si/si/kaj-poceti/dogodki-in-prireditve/vsi-dogodki-in-prireditve/), ogled in urejanje podatkov prek uporabniškega vmesnika in shranjevanje vseh podatkov v podatkovno bazo. Aplikacija omogoča dodajanje novih podatkov in tudi generiranje naključnih podatkov o dogodkih za testne namene.

## Scraper
Scraper je implementiran v Kotlinu, pri čemer uporablja knjižnico `skrape.it` za naloge spletnega strganja. Koda je razdeljena na več komponent:

- Podatkovni razredi: Definirajo strukturo podatkov, ki jih strgamo.
- Pomožne funkcije: Zagotavljajo pripomočke za razčlenjevanje in preoblikovanje strganih podatkov.
- Funkcije strganja: Izvajajo dejansko spletno strganje in pridobivanje podatkov.

### Podatkovni razredi
- Razred `Location` predstavlja geografske koordinate dogodka.
  ```
  data class Location(
    @Expose val type: String,
    @Expose val coordinates: List<Double>
  )
  ```


- Razred `Event` predstavlja dogodek z različnimi podrobnostmi.
```
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

```
### Pomožne funkcije
- `getCategoryId`
Ta funkcija preslika imena kategorij na ObjectId.
```
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

```
- `dateSeparatorVisitMaribor` funkcija izloči datum in čas iz elementov dokumenta.

```
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

```

### Funkcije strganja
- `getEvent` funkcija pridobi podrobnosti posameznega dogodka
```
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

```
- `fetchEvents` funkcija pridobi seznam dogodkov s spletne strani

```
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

```

### Povzetek
Scraper učinkovito pridobiva podatke o dogodkih iz spletne strani [visitmaribor.si](https://www.visitmaribor.si/si/kaj-poceti/dogodki-in-prireditve/vsi-dogodki-in-prireditve/), jih preoblikuje v strukturirane objekte `Event` za nadaljnjo uporabo. Koda zagotavlja pravilno obravnavo morebitnih napak pri razčlenjevanju in vključuje prilagojeno JSON serializacijo za `ObjectId`.

<br />

## Vmesnik

<br />

<!-- CONTACT -->
## Kontakt
Ime skupine: Serverless <br/>
Člani skupine: Jan Namestnik, Nejc Cekuta, Metod Golob <br/>
Link do projketa: [Serverless](https://github.com/JanNamestnik/Serverless/tree/main)
<br /><br />

<!-- ACKNOWLEDGMENTS -->
## Viri

* [Thinking in Compose](https://developer.android.com/jetpack/compose/mental-model)
* [Compose for Desktop features](https://github.com/JetBrains/compose-multiplatform/tree/master)
* [Jetpack Compose Tutorial](https://developer.android.com/jetpack/compose/tutorial)
* [Material Components and layouts](https://developer.android.com/jetpack/compose/layouts/material)
* [Side-effects in Compose](https://developer.android.com/develop/ui/compose/side-effects)
* [OkHttp](https://square.github.io/okhttp/)

<p align="right">(<a href="#readme-top">nazaj na vrh</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[Kotlin]: https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white
[Kotlin-url]: https://kotlinlang.org/
[Compose]: https://img.shields.io/badge/Compose%20for%20Desktop-4285F4?style=for-the-badge&logo=compose&logoColor=white
[Compose-url]: https://www.jetbrains.com/compose/
[MongoDB]: https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white
[MongoDB-url]: https://www.mongodb.com/
