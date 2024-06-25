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
      <a href="#o-projektu">O projektu</a>
      <ul>
        <li><a href="#glavne-funkcionalnosti-aplikacije">Glavne funkcionalnosti aplikacije</a></li>
        <li><a href="#uporabljena-oprema">Uporabljena oprema</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#kloniranje-repozitorija">Kloniranje repozitorija</a></li>
        <li><a href="#namestitev-potrebne-programske-opreme">Namestitev potrebne programske opreme</a></li>
        <li><a href="#konfiguracija">Konfiguracija</a></li>
        <li><a href="#zagon-scraperja">Zagon scraperja</a></li>
        <li><a href="#opombe">Opombe</a></li>
      </ul>
    </li>
    <li>
        <a href="#opis-projekta">Opis projekta</a>
        <ul>
            <li><a href="#scraper">Scraper</a></li>
            <ul>
                <li><a href="#podatkovni-razredi">Podatkovni razredi</a></li>
                <li><a href="#pomožne-funkcije">Pomožne funkcije</a></li>
                <li><a href="#funkcije-strganja">Funkcije strganja</a></li>
            </ul>
            <li><a href="#vmesnik">Vmesnik</a></li>
            <ul>
                <li><a href="#podatkovni-razredi2">Podatkovni razredi</a></li>
                <li><a href="#fetch-funkcije">Fetch funkcije</a></li>
                <li><a href="#kompozicije">Kompozicije</a></li>
                <li><a href="#pomožne-funkcije2">Pomožne funkcije</a></li>
            </ul>
            <li><a href="#povzetek-delovanja-aplikacije">Povzetek delovanja aplikacije</a></li>
        </ul>
    </li>
    <li><a href="#kontakt">Kontakt</a></li>
    <li><a href="#viri">Viri</a></li>
  </ol>
</details>

<!-- O projektu -->
<h2 id="o-projektu">1. O projektu</h2>

![appMain!](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Principi%20programskih%20jezikov/Slike/appMain.png)

Ta projekt je namizna aplikacija, zgrajena s Kotlinom za pridobivanje podatkov o dogodkih iz spletnih strani in njihovo upravljanje v uporabniškem vmesniku. Omogoča uporabnikom ogled, urejanje in shranjevanje podatkov o dogodkih v bazi podatkov.


<h3 id="glavne-funkcionalnosti-aplikacije">1.1 Glavne funkcionalnosti aplikacije:</h3>

* Dodajanje in upravljanje dogodkov v podatkovni bazi
* Prikaz in urejanje vseh podatkov v PB
* Scrapa podatke iz [visitmaribor.si](https://www.visitmaribor.si/si/kaj-poceti/dogodki-in-prireditve/vsi-dogodki-in-prireditve/) in jih vstavi v bazo
* Ustvari izmišljene podatke za namene testiranja


<br />
<h2 id="uporabljena-oprema">1.2 Uporabljena oprema</h2>

* [![Kotlin][Kotlin]][Kotlin-url]
* [![Compose for Desktop][Compose]][Compose-url]
* [![MongoDB][MongoDB]][MongoDB-url]

<br />

<!-- GETTING STARTED -->
<h2 id="getting-started">2. Getting Started</h2>

Če želite pridobiti lokalno kopijo in jo zagnati, sledite tem preprostim korakom.

<h3 id="kloniranje-repozitorija">2.1 Kloniranje repozitorija</h3>

Najprej klonirajte repozitorij iz GitHub-a na vaš lokalni računalnik. V terminalu zaženite naslednji ukaz:
```sh
gh repo clone JanNamestnik/Serverless
```
Potem se pramaknemo v mapu s projektom
```sh
cd Serverless
```

<h3 id="namestitev-potrebne-programske-opreme">2.2 Namestitev potrebne 
programske opreme</h3>

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

<h3 id="konfiguracija">2.3 Konfiguracija</h3>

Preden zaženete scraper, preverite konfiguracijo in prilagodite, če je potrebno.

- Nastavitve MongoDB

Prepričajte se, da je MongoDB strežnik zagnan in deluje na privzetem naslovu (`localhost:27017`). Če MongoDB deluje na drugem naslovu ali portu, posodobite ustrezne nastavitve v kodi.

- Odvisnosti projekta

Projektne odvisnosti so določene v datoteki `build.gradle`. Gradle bo samodejno prenesel potrebne knjižnice ob prvem zagonu projekta.

<h3 id="zagon-scraperja">2.4 Zagon scraperja</h3>

Ko so vse zgornje zahteve izpolnjene, lahko zaženete scraper z naslednjim ukazom:

- Na Linux/macOS:
  ```sh
  ./gradlew run
  ```
- Na Windows:
  ```sh
  gradlew.bat run
  ```

<h3 id="opombe">2.5 Opombe</h3>

- Če naletite na težave, preverite, ali so vse odvisnosti pravilno nameščene in ali je MongoDB strežnik zagnan.
- Za podrobnejše informacije o uporabi Gradle-a in odpravljanju napak obiščite [Gradle dokumentacijo](https://docs.gradle.org/current/userguide/userguide.html).

<br />

<!-- USAGE EXAMPLES -->
<h2 id="opis-projekta">3. Opis projekta</h2>

Aplikacija omogoča pridobivanje podatkov o dogodkih iz spletne strani [visitmaribor.si](https://www.visitmaribor.si/si/kaj-poceti/dogodki-in-prireditve/vsi-dogodki-in-prireditve/), ogled in urejanje podatkov prek uporabniškega vmesnika in shranjevanje vseh podatkov v podatkovno bazo. Aplikacija omogoča dodajanje novih podatkov in tudi generiranje naključnih podatkov o dogodkih za testne namene.

<h2 id="scraper">3.1 Scraper</h2>

Scraper je implementiran v Kotlinu, pri čemer uporablja knjižnico `skrape.it` za naloge spletnega strganja. Koda je razdeljena na več komponent:

- `Podatkovni razredi`, ki definirajo strukturo podatkov, ki jih strgamo.
- `Pomožne funkcije`, ki zagotavljajo pripomočke za razčlenjevanje in preoblikovanje strganih podatkov.
- `Funkcije strganja`, ki izvajajo dejansko spletno strganje in pridobivanje podatkov.

<h3 id="podatkovni-razredi">3.1.1 Podatkovni razredi</h3>

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

<h3 id="pomožne-funkcije">3.1.2 Pomožne funkcije</h3>

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

<h3 id="funkcije-strganja">3.1.3 Funkcije strganja</h3>

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

<br />

<h2 id="vmesnik">3.2 Vmesnik</h2>

Vmesnik je uporabniški vmesnik, ki omogoča prikaz in upravljanje podatkov o dogodkih, uporabnikih, ocenah in kategorijah iz baze podatkov. Vmesnik je zgrajen z uporabo Kotlin in Jetpack Compose za Desktop.

Koda vmesnika je razdeljena na več komponent:

- `Podatkovne razrede`, ki definirajo strukturo podatkov
- `Fetch funkcije` za pridobivanje podatkov, ki pridobijo podatke iz baze podatkov preko HTTP zahtev
- `Kompozicije`, ki ustvarjajo uporabniški vmesnik za prikaz, urejanje in brisanje podatkov
- `Pomožne funkcije`, ki skrbijo za pošiljanje podatkov v bazo, brisanje in posodabljanje podatkov v bazi

<h3 id="podatkovni-razredi2">3.2.1 Podatkovni razredi</h3>

- Razred `User` predstavlja uporabnika z različnimi podrobnostmi

```
data class User(
    @Expose(serialize = false, deserialize = false)
    val _id: ObjectId?,
    @Expose
    val username: String?,
    @Expose
    val email: String?,
    @Expose
    val password: String?,
    @Expose
    val favorites: List<String>,
    @Expose
    val profileImage: String?
)

```

- Razred `Review` predstavlja oceno dogodka.

```
data class Review(
    @Expose(serialize = false, deserialize = false)
    val _id: ObjectId?,
    @Expose
    val eventId: ObjectId,
    @Expose
    val userId: ObjectId,
    @Expose
    val created: String,
    @Expose
    val rating: Int,
    @Expose
    val content: String
)

```

- Razred `Category` predstavlja kategorijo dogodka.

```
data class Category(
    @Expose(serialize = false, deserialize = false)
    val _id: ObjectId?,
    @Expose
    val name: String
)
```

<h3 id="fetch-funkcije">3.2.2 Fetch funkcije</h3>

Koda vsebuje 4 fetch funkcije (posebej za vsak podatkovni razred), ki so si med seboj zelo podobne, zato je spodaj primer samo za pridobivanje dogodkov.
- `fetchEvents`, Pridobi podatke o dogodkih iz API-ja

```
fun fetchEvents(onResult: (List<Event>) -> Unit) {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/events")
        .build()
    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }
        override fun onResponse(call: Call, response: Response) {
            response.body?.string()?.let { jsonResponse ->
                val events = parseEventsFromJson(jsonResponse)
                onResult(events)
            }
        }
    })
}

```

- ker podatke pridobivamo iz JSON formata je potrebno narediti za pretvorbo posebno funkcijo `parseEventsFromJson`, ki razčleni JSON odziv v seznam dogodkov

```
fun parseEventsFromJson(jsonResponse: String): List<Event> {
    val gson = GsonBuilder()
        .registerTypeAdapter(ObjectId::class.java, ObjectIdDeserializer())
        .registerTypeAdapter(Date::class.java, DateDeserializer())
        .create()
    val eventType = object : TypeToken<List<Event>>() {}.type
    return gson.fromJson(jsonResponse, eventType)
}

```

<h3 id="kompozicije">3.2.3 Kompozicije</h3>

Sem spadajo vse funkcije, ki ustvarjajo uporabniški vmesnik za prikaz, urejanje, brisanje podatkov in pošiljanje podatkov v podatkovno bazo

- funkcija `App`, je glavna funkcije kompozicije, ki upravlja vse komponente kompozicije (sidebar in contentArea)

```
@Composable
@Preview
fun App() {
    var selectedScreen by remember { mutableStateOf("Add") }
    var events by remember { mutableStateOf(listof<Event>()) }
    var users by remember { mutableStateOf(listof<User>()) }
    var reviews by remember { mutableStateOf(listof<Review>()) }
    var categories by remember { mutableStateOf(listof<Category>()) }

    LaunchedEffect(Unit) {
        fetchEvents { fetchedEvents -> events = fetchedEvents }
        fetchUsers { fetchedUsers -> users = fetchedUsers }
        fetchReviews { fetchedReviews -> reviews = fetchedReviews }
        fetchCategories { fetchedCategories -> categories = fetchedCategories }
    }

    LaunchedEffect(selectedScreen) {
        when (selectedScreen) {
            "Events" -> fetchEvents { fetchedEvents -> events = fetchedEvents }
            "Users" -> fetchUsers { fetchedUsers -> users = fetchedUsers }
            "Reviews" -> fetchReviews { fetchedReviews -> reviews = fetchedReviews }
            "Categories" -> fetchCategories { fetchedCategories -> categories = fetchedCategories }
        }
    }

    MaterialTheme {
        Row(modifier = Modifier.fillMaxSize()) {
            Sidebar(selectedScreen) { selectedScreen = it }
            ContentArea(
                selectedScreen,
                events,
                users,
                reviews,
                categories,
                onAddEvent = { newEvent -> events = events + newEvent },
                onUpdateEvent = { updatedEvent -> events = events.map { if (it._id == updatedEvent._id) updatedEvent else it } },
                onDeleteEvent = { deletedEvent -> events = events.filter { it._id != deletedEvent._id } },
                onAddUser = { newUser -> users = users + newUser },
                onDeleteUser = { deletedUser -> users = users.filter { it._id != deletedUser._id } },
                onAddReview = { newReview -> reviews = reviews + newReview },
                onUpdateReview = { updatedReview -> reviews = reviews.map { if (it.eventId == updatedReview.eventId && it.userId == updatedReview.userId) updatedReview else it } },
                onDeleteReview = { deletedReview -> reviews = reviews.filter { it._id != deletedReview._id } },
                onAddCategory = { newCategory -> categories = categories + newCategory },
                onDeleteCategory = { deletedCategory -> categories = categories.filter { it._id != deletedCategory._id } }
            )
        }
    }
}

```

- funkcija `Sidebar` je stranska pokončna vrstica, kjer lahko izbiraš med različnimi zasloni

```
@Composable
fun Sidebar(selectedScreen: String, onScreenSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(200.dp)
            .padding(12.dp)
            .border(
                border = BorderStroke(1.dp, Color.LightGray),
                shape = RoundedCornerShape(12.dp),
            )
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SidebarButton(icon = Icons.Default.Add, label = "Add", isSelected = selectedScreen == "Add", onClick = { onScreenSelected("Add") })
        Divider(color = Color.LightGray, thickness = 2.dp)
        SidebarButton(icon = Icons.Default.List, label = "Events", isSelected = selectedScreen == "Events", onClick = { onScreenSelected("Events") })
        SidebarButton(icon = Icons.Default.Person, label = "Users", isSelected = selectedScreen == "Users", onClick = { onScreenSelected("Users") })
        SidebarButton(icon = Icons.Default.Star, label = "Reviews", isSelected = selectedScreen == "Reviews", onClick = { onScreenSelected("Reviews") })
        SidebarButton(icon = Icons.Default.Menu, label = "Categories", isSelected = selectedScreen == "Categories", onClick = { onScreenSelected("Categories") })
        Divider(color = Color.LightGray, thickness = 2.dp)
        SidebarButton(icon = Icons.Default.Share, label = "Scraper", isSelected = selectedScreen == "Scraper", onClick = { onScreenSelected("Scraper") })
        SidebarButton(icon = Icons.Default.Build, label = "Generator", isSelected = selectedScreen == "Generator", onClick = { onScreenSelected("Generator") })
        Spacer(modifier = Modifier.weight(1f))
        SidebarButton(icon = Icons.Default.Info, label = "About", isSelected = selectedScreen == "About", onClick = { onScreenSelected("About") })
    }
}

```

- `ContentArea` je glavno območje za prikaz vsebine na podlagi izbranega zaslona v sidebaru

```
@Composable
fun ContentArea(
    selectedScreen: String,
    events: List<Event>,
    users: List<User>,
    reviews: List<Review>,
    categories: List<Category>,
    onAddEvent: (Event) -> Unit,
    onUpdateEvent: (Event) -> Unit,
    onDeleteEvent: (Event) -> Unit,
    onAddUser: (User) -> Unit,
    onDeleteUser: (User) -> Unit,
    onAddCategory: (Category) -> Unit,
    onDeleteCategory: (Category) -> Unit,
    onAddReview: (Review) -> Unit,
    onUpdateReview: (Review) -> Unit,
    onDeleteReview: (Review) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .border(
                border = BorderStroke(1.dp, Color.LightGray),
                shape = RoundedCornerShape(12.dp),
            )
            .padding(12.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            when (selectedScreen) {
                "Add" -> AddScreen(onAddEvent, onAddUser, onAddCategory, onAddReview)
                "Events" -> EventsScreen(events, onUpdateEvent, onDeleteEvent)
                "Users" -> UsersScreen(users, onDeleteUser)
                "Reviews" -> ReviewsScreen(reviews, onUpdateReview, onDeleteReview)
                "Categories" -> CategoriesScreen(categories, onDeleteCategory)
                "Scraper" -> ScraperScreen { newEvents -> newEvents.forEach { onAddEvent(it) } }
                "Generator" -> GeneratorScreen { newEvents -> newEvents.forEach { onAddEvent(it) } }
                "About" -> AboutScreen()
            }
        }
    }
}

```

- V `Sidebaru` lahko izbiramo med različnimi `Screeni`:
    - `AddScreen`
    - `EventsScreen`
    - `UsersScreen`
    - `ReviewsScreen`
    - `CategoriesScreen`
    - `ScraperScreen`
    - `GeneratorScreen`
    - `AboutScreen`

 - `Add Screen` je zaslon, v katerem lahko ročno dodajamo nove dogodke, uporabnike, kategorije in ocene

![appMain!](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Principi%20programskih%20jezikov/Slike/appMain.png)
    
```
@Composable
fun AddScreen(
    onAddEvent: (Event) -> Unit,
    onAddUser: (User) -> Unit,
    onAddCategory: (Category) -> Unit,
    onAddReview: (Review) -> Unit
) {
    var selectedType by remember { mutableStateOf("Event") }
    val types = listof("Event", "User", "Category", "Review")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Add New", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))

        var expanded by remember { mutableStateOf(false) }
        Box {
            OutlinedTextField(
                value = selectedType,
                onValueChange = { },
                readOnly = true,
                label = { Text("Type") },
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, null, Modifier.clickable { expanded = true })
                },
                modifier = Modifier.fillMaxWidth()
            )
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                types.forEach { type ->
                    DropdownMenuItem(onClick = {
                        selectedType = type
                        expanded = false
                    }) {
                        Text(type)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedType) {
            "Event" -> AddEventForm(onAddEvent)
            "User" -> AddUserForm(onAddUser)
            "Category" -> AddCategoryForm(onAddCategory)
            "Review" -> AddReviewForm(onAddReview)
        }
    }
}

```

- Zasloni `EventsScreen`, `UsersScreen`, `ReviewsScreen` in `CategoriesScreen` so namenjeni prikazu vseh elementov, ki se nahajajo v podatkovni bazi in so vsi narejeni po istem principu, zato je spodaj prikazana koda samo za `EventsScreen`.

```
@Composable
fun EventsScreen(events: List<Event>, onUpdateEvent: (Event) -> Unit, onDeleteEvent: (Event) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Events", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))

        events.forEach { event ->
            EventCard(event, onUpdateEvent, onDeleteEvent)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

```

- Funkcija `EventsScreen`, deluje tako da prikaže vse dogodke, ki se nahajajo v podatkovni bazi na zaslon. Vsak dogodek je stiliran s dodatno funkcijo `EventCard`, ki je nekakšna kartica za prikaz podrobnosti dogodka z možnostmi urejanja in brisanja

![events!](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Principi%20programskih%20jezikov/Slike/events.png)

```
@Composable
fun EventCard(event: Event, onUpdateEvent: (Event) -> Unit, onDeleteEvent: (Event) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            event.name?.let { Text(it, style = MaterialTheme.typography.h6) }
            if (isExpanded) {
                Text("_id: ${event._id}")
                Text("Address: ${event.address}")
                Text("Start Time: ${event.startTime}")
                Text("Start Date: ${event.date_start}")
                Text("End Date: ${event.date_end}")
                Text("Description: ${event.description}")
                Text("Contact: ${event.contact}")
                Text("Category: ${event.category}")
                Text("Location: ${event.location?.coordinates.toString()}")
                Text("Price: ${event.price}")
                Text("Attendees: ${event.attendees.joinToString(", ")}")
                Text("Event Image: ${event.eventImage}")
                Text("Owner: ${event.owner}")
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Button(onClick = { isEditing = true }) {
                        Text("Edit Event")
                    }
                    Button(
                        onClick = { showDeleteConfirmation = true },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                    ) {
                        Text("Delete Event")
                    }
                }
            }
        }
    }

    if (isEditing) {
        EditEventDialog(event = event, onDismiss = { isEditing = false }, onSave = { updatedEvent ->
            onUpdateEvent(updatedEvent)
            isEditing = false
        })
    }

    if (showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmation = false },
            title = { Text("Delete Confirmation") },
            text = { Text("Are you sure you want to delete this event?") },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteConfirmation = false
                        GlobalScope.launch {
                            try {
                                deleteFromDatabase(event._id!!, "https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/deleteEvent")
                                onDeleteEvent(event)
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteConfirmation = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

```

- Da lahko znotraj kartice urejamo vse podrobnosti dogodka, naredimo dodamo funkcijo `EditEventDialog`.

```
@Composable
fun EditEventDialog(event: Event, onDismiss: () -> Unit, onSave: (Event) -> Unit) {
    var name by remember { mutableStateOf(event.name ?: "") }
    var address by remember { mutableStateOf(event.address ?: "") }
    var startTime by remember { mutableStateOf(event.startTime ?: "") }
    var dateStart by remember { mutableStateOf(event.date_start ?: "") }
    var dateEnd by remember { mutableStateOf(event.date_end ?: "") }
    var description by remember { mutableStateOf(event.description ?: "") }
    var contact by remember { mutableStateOf(event.contact ?: "") }
    var category by remember { mutableStateOf(event.category?.toString() ?: "") }
    var longitude by remember { mutableStateOf(event.location?.coordinates?.getOrNull(0)?.toString() ?: "") }
    var latitude by remember { mutableStateOf(event.location?.coordinates?.getOrNull(1)?.toString() ?: "") }
    var eventImage by remember { mutableStateOf(event.eventImage ?: "") }
    var price by remember { mutableStateOf(event.price.toString()) }
    var attendees by remember { mutableStateOf(event.attendees.joinToString(", ")) }
    var owner by remember { mutableStateOf(event.owner?.toString() ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        buttons = {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Edit Event",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    textAlign = TextAlign.Center
                )

                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    item { OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Address") }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(value = startTime, onValueChange = { startTime = it }, label = { Text("Start Time") }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(value = dateStart, onValueChange = { dateStart = it }, label = { Text("Start Date") }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(value = dateEnd, onValueChange = { dateEnd = it }, label = { Text("End Date") }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(value = contact, onValueChange = { contact = it }, label = { Text("Contact") }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Category") }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(value = longitude, onValueChange = { longitude = it }, label = { Text("Longitude") }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(value = latitude, onValueChange = { latitude = it }, label = { Text("Latitude") }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(value = eventImage, onValueChange = { eventImage = it }, label = { Text("Event Image") }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Price") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)) }
                    item { OutlinedTextField(value = attendees, onValueChange = { attendees = it }, label = { Text("Attendees") }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(value = owner, onValueChange = { owner = it }, label = { Text("Owner") }, modifier = Modifier.fillMaxWidth()) }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Button(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            val updatedLocation = Location(
                                type = event.location?.type ?: "Point",
                                coordinates = listof(longitude.toDoubleOrNull() ?: 0.0, latitude.toDoubleOrNull() ?: 0.0)
                            )
                            val updatedEvent = Event(
                                _id = event._id,
                                name = name,
                                address = address,
                                startTime = startTime,
                                date_start = dateStart,
                                date_end = dateEnd,
                                description = description,
                                contact = contact,
                                category = ObjectId(category),
                                location = updatedLocation,
                                eventImage = eventImage,
                                price = price.toIntOrNull() ?: 0,
                                attendees = attendees.split(", ").filter { it.isNotBlank() },
                                owner = ObjectId(owner)
                            )

                            val updateFields = mutableMapof<String, Any>()
                            if (name != event.name) updateFields["name"] = name
                            if (address != event.address) updateFields["address"] = address
                            if (startTime != event.startTime) updateFields["startTime"] = startTime
                            if (dateStart != event.date_start.toString()) updateFields["date_start"] = dateStart
                            if (dateEnd != event.date_end.toString()) updateFields["date_end"] = dateEnd
                            if (description != event.description) updateFields["description"] = description
                            if (contact != event.contact) updateFields["contact"] = contact
                            if (category != event.category.toString()) updateFields["category"] = ObjectId(category)
                            if (longitude != event.location?.coordinates?.getOrNull(0)?.toString()) updateFields["location.coordinates[0]"] = longitude.toDoubleOrNull() ?: 0.0
                            if (latitude != event.location?.coordinates?.getOrNull(1)?.toString()) updateFields["location.coordinates[1]"] = latitude.toDoubleOrNull() ?: 0.0
                            if (eventImage != event.eventImage) updateFields["eventImage"] = eventImage
                            if (price != event.price.toString()) updateFields["price"] = price.toIntOrNull() ?: 0
                            if (attendees != event.attendees.joinToString(", ")) updateFields["attendees"] = attendees.split(", ").filter { it.isNotBlank() }
                            if (owner != event.owner.toString()) updateFields["owner"] = ObjectId(owner)

                            GlobalScope.launch {
                                try {
                                    updateInDatabase(event._id!!, updateFields, "https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/updateEvent")
                                    onSave(updatedEvent)
                                } catch (e: Exception) {
                                    println("Can't find/update the event in the database")
                                }
                            }
                        }
                    ) {
                        Text("Save")
                    }
                }
            }
        },
        modifier = Modifier.padding(20.dp)
    )
}

```

- Izberemo lahko tudi zaslon `GeneratorScreen`, ki omogoča  generiranje naključnih dogodkov. Deluje tako, da vanj vpišemo določene parametre in se dogodek zgenerira in shrani v bazo

![generator!](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Principi%20programskih%20jezikov/Slike/generator.png)

```
@Composable
fun GeneratorScreen(onAddEvents: (List<Event>) -> Unit) {
    var numberOfEvents by remember { mutableStateOf("1") }
    val faker = Faker()

    // State for date range
    var startDateOffset by remember { mutableStateOf("1") }
    var endDateOffset by remember { mutableStateOf("10") }

    // State for location range
    var minLongitude by remember { mutableStateOf("-180.0") }
    var maxLongitude by remember { mutableStateOf("180.0") }
    var minLatitude by remember { mutableStateOf("-90.0") }
    var maxLatitude by remember { mutableStateOf("90.0") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Generate Random Events", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))

        // Input fields for specifying date range
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = startDateOffset,
                onValueChange = { startDateOffset = it },
                label = { Text("Start Date Offset") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = endDateOffset,
                onValueChange = { endDateOffset = it },
                label = { Text("End Date Offset") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Input fields for specifying location range
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = minLongitude,
                onValueChange = { minLongitude = it },
                label = { Text("Min Longitude") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = maxLongitude,
                onValueChange = { maxLongitude = it },
                label = { Text("Max Longitude") },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = minLatitude,
                onValueChange = { minLatitude = it },
                label = { Text("Min Latitude") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = maxLatitude,
                onValueChange = { maxLatitude = it },
                label = { Text("Max Latitude") },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = numberOfEvents,
            onValueChange = { numberOfEvents = it },
            label = { Text("Number of Events") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            val events = (1..numberOfEvents.toInt()).map {
                generateRandomEvent(
                    faker,
                    startDateOffset.toInt(),
                    endDateOffset.toInt(),
                    minLongitude.toDouble(),
                    maxLongitude.toDouble(),
                    minLatitude.toDouble(),
                    maxLatitude.toDouble()
                )
            }

            GlobalScope.launch {
                try {
                    sendEventsToDatabase(events, "https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/createEvent")
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }) {
            Text("Generate and Save to Database")
        }
    }
}

```

- Najbolj pomemben je seveda zaslon `ScraperScreen`, ki skrbi za prikaz vseh pridobljenih dogodkov iz spletne strani. 

![scraped!](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Principi%20programskih%20jezikov/Slike/scraped.png)

```
@Composable
fun ScraperScreen(onAddEvents: (List<Event>) -> Unit) {
    var events by remember { mutableStateOf(listof<Event>()) }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Scrape events", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (events.isEmpty()) {
            Button(
                onClick = {
                    isLoading = true
                    GlobalScope.launch {
                        val fetchedEvents = fetchEvents()
                        events = fetchedEvents
                        isLoading = false
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Fetch Events")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        events.forEach { event ->
            EventCard(event, onUpdateEvent = { updatedEvent ->
                events = events.map { if (it._id == updatedEvent._id) updatedEvent else it }
            }, onDeleteEvent = { deletedEvent ->
                events = events.filter { it._id != deletedEvent._id }
            })
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (events.isNotEmpty()) {
            Button(
                onClick = {
                    GlobalScope.launch {
                        try {
                            sendEventsToDatabase(events, "https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/createEvent")
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Add Events")
            }
        }
    }
}

```
- In čisto na koncu pa imamo še `AboutScreen`, ki pa je nekakšen povzetek in prikaz vseh informacij o aplikaciji

![about!](https://github.com/JanNamestnik/Serverless/blob/devel/Dokumentacija/Principi%20programskih%20jezikov/Slike/about.png)

```
@Composable
fun AboutScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Compose Database Admin", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "This application serves as a database manager, allowing users to enter and display data from events table. Additionally, it acts as an advanced data parser, facilitating the insertion of parsed web data into the database."
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Key Features:",
            style = MaterialTheme.typography.h6
        )
        Text("- Add and manage events in the database")
        Text("- Display and edit events")
        Text("- Scrape data from the visitmaribor.si and insert it into the database")
        Text("- Generate fictional data for testing purposes")
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Technologies Used:",
            style = MaterialTheme.typography.h6
        )
        Text("Kotlin, Jetpack Compose, Compose for Desktop")
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Group name:",
            style = MaterialTheme.typography.h6
        )
        Text("Serverless")
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Members:",
            style = MaterialTheme.typography.h6
        )
        Text("Jan Namestnik, Nejc Cekuta, Metod Golob")
    }
}

```

<h3 id="pomožne-funkcije2">3.2.4 Pomožne funkcije</h3>

So funkcije za pošiljanje podatkov v bazo, brisanje in posodabljanje podatkov v bazi

- Ko podatke v določeni kartici uredimo, se morajo ti tudi poslati in posodobiti v podatkovni bazi, in zato naredimo funkcijo `updateInDatabase`

```
fun updateInDatabase(entityId: ObjectId, updateFields: Map<String, Any>, url: String) {
    val client = OkHttpClient()
    val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()

    val jsonPayload = JsonObject().apply {
        addProperty("_id", entityId.toHexString())
        add("updateFields", Gson().toJsonTree(updateFields))
    }.toString()

    println("Generated JSON: $jsonPayload")

    val body = jsonPayload.toRequestBody(mediaType)

    val request = Request.Builder()
        .url(url)
        .put(body)
        .build()

    try {
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            println(response.body?.string())
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

```

- In podobno kot prej, če želimo določen dogodek izbrisati iz podatkovne baze dodamo funkcijo `deleteFromDatabase`.

```
fun deleteFromDatabase(entityId: ObjectId, url: String) {
    val client = OkHttpClient()
    val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()

    val jsonPayload = JsonObject().apply {
        addProperty("_id", entityId.toHexString())
    }.toString()

    println("Generated JSON: $jsonPayload")

    val body = jsonPayload.toRequestBody(mediaType)

    val request = Request.Builder()
        .url(url)
        .post(body)
        .build()

    try {
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            println(response.body?.string())
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

```

- imamo pa še funkcijo `sendEventsToDatabase`, s katero pošljemo pridobljene dogodke iz spletne strani v podatkovno bazo

```
fun sendEventsToDatabase(events: List<Event>, url: String) {
    val client = OkHttpClient()
    val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()

    events.forEach { event ->
        val json = event.toDatabaseJson()
        println("Generated JSON: $json")  // Print the JSON to check it

        val document = Document.parse(json)

        val body = document.toJson().toRequestBody(mediaType)

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                println(response.body?.string())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

```

<h2 id="povzetek-delovanja-aplikacije">3.3 Povzetek delovanja aplikacije</h2>

Scraper učinkovito pridobiva podatke o dogodkih iz spletne strani [visitmaribor.si](https://www.visitmaribor.si/si/kaj-poceti/dogodki-in-prireditve/vsi-dogodki-in-prireditve/), jih preoblikuje v strukturirane objekte Event za nadaljnjo uporabo. Koda zagotavlja pravilno obravnavo morebitnih napak pri razčlenjevanju in vključuje prilagojeno JSON serializacijo za ObjectId. Aplikacija omogoča celovito upravljanje podatkovne baze z enostavnim uporabniškim vmesnikom, izdelanim z Jetpack Compose. Nudi širok spekter funkcionalnosti za upravljanje dogodkov, uporabnikov, ocen in kategorij ter vključuje orodja za pridobivanje in generiranje podatkov, zaradi česar je primerna za uporabo pri razvoju in testiranju različnih podatkovnih aplikacij.


<br />

<!-- CONTACT -->
<h2 id="kontakt">4. Kontakt</h2>

Ime skupine: Serverless <br/>
Člani skupine: Jan Namestnik, Nejc Cekuta, Metod Golob <br/>
Link do projketa: [Serverless](https://github.com/JanNamestnik/Serverless/tree/main)
<br /><br />

<!-- ACKNOWLEDGMENTS -->
<h2 id="viri">5. Viri</h2>

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
