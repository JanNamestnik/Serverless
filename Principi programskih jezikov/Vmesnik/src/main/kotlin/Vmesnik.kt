import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.serpro69.kfaker.Faker
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random


data class Event(
    val name: String,
    val address: String,
    val startTime: String,
    val dateStart: String,
    val dateEnd: String,
    val description: String,
    val contact: String,
    val category: String,
    val longitude: String,
    val latitude: String
)

@Composable
@Preview
fun App() {
    var selectedScreen by remember { mutableStateOf("Add event") }
    var events by remember { mutableStateOf(listOf(
        Event(
            name = "Lampijončki",
            address = "Študentski kampus gosposvetska",
            startTime = "10:00 AM",
            dateStart = "2024-05-30",
            dateEnd = "2024-05-31",
            description = "Nastopili bodo: modrijani, smetnaki...",
            contact = "info@gmail.com",
            category = "festival",
            longitude = "46.562828",
            latitude = "15.626822"
        )
    )) }

    MaterialTheme {
        Row(modifier = Modifier.fillMaxSize()) {
            Sidebar(selectedScreen) { selectedScreen = it }
            ContentArea(selectedScreen, events, onAddEvent = { newEvent ->
                events = events + newEvent
            }, onUpdateEvent = { updatedEvent ->
                events = events.map { if (it.name == updatedEvent.name) updatedEvent else it }
            })
        }
    }
}



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
        SidebarButton(
            icon = Icons.Default.Add,
            label = "Add event",
            isSelected = selectedScreen == "Add event",
            onClick = { onScreenSelected("Add event") }
        )
        SidebarButton(
            icon = Icons.Default.List,
            label = "Events",
            isSelected = selectedScreen == "Events",
            onClick = { onScreenSelected("Events") }
        )
        SidebarButton(
            icon = Icons.Default.Share,
            label = "Scraper",
            isSelected = selectedScreen == "Scraper",
            onClick = { onScreenSelected("Scraper") }
        )
        SidebarButton(
            icon = Icons.Default.Build,
            label = "Generator",
            isSelected = selectedScreen == "Generator",
            onClick = { onScreenSelected("Generator") }
        )
        Spacer(modifier = Modifier.weight(1f)) // da je zadnji gumb na dnu
        SidebarButton(
            icon = Icons.Default.Info,
            label = "About",
            isSelected = selectedScreen == "About",
            onClick = { onScreenSelected("About") }
        )
    }
}

@Composable
fun SidebarButton(icon: ImageVector, label: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.surface
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = label)
            Spacer(modifier = Modifier.width(8.dp))
            Text(label)
        }
    }
}

@Composable
fun ContentArea(selectedScreen: String, events: List<Event>, onAddEvent: (Event) -> Unit, onUpdateEvent: (Event) -> Unit) {
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
                "Add event" -> AddEventScreen(onAddEvent)
                "Events" -> EventsScreen(events, onUpdateEvent)
                "Scraper" -> ScraperScreen()
                "Generator" -> GeneratorScreen { newEvents ->
                    newEvents.forEach { onAddEvent(it) }
                }
                "About" -> AboutScreen()
            }
        }
    }
}

@Composable
fun AddEventScreen(onAddEvent: (Event) -> Unit) {
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var dateStart by remember { mutableStateOf("") }
    var dateEnd by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf("") }

    val categories = listOf("concert", "festival", "sport", "community event", "educational event", "performance", "conference", "exhibition", "other")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Add Event", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))

        // Name
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        // Address
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )

        // Start Time
        OutlinedTextField(
            value = startTime,
            onValueChange = { startTime = it },
            label = { Text("Start Time") },
            modifier = Modifier.fillMaxWidth()
        )

        // Date Start
        OutlinedTextField(
            value = dateStart,
            onValueChange = { dateStart = it },
            label = { Text("Date Start") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Date End
        OutlinedTextField(
            value = dateEnd,
            onValueChange = { dateEnd = it },
            label = { Text("Date End") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Description
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        // Contact
        OutlinedTextField(
            value = contact,
            onValueChange = { contact = it },
            label = { Text("Contact") },
            modifier = Modifier.fillMaxWidth()
        )

        // Category Dropdown
        var expanded by remember { mutableStateOf(false) }
        Box {
            OutlinedTextField(
                value = selectedCategory,
                onValueChange = { },
                readOnly = true,
                label = { Text("Category") },
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, null, Modifier.clickable { expanded = true })
                },
                modifier = Modifier.fillMaxWidth()
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(onClick = {
                        selectedCategory = category
                        expanded = false
                    }) {
                        Text(category)
                    }
                }
            }
        }

        // Longitude
        OutlinedTextField(
            value = longitude,
            onValueChange = { longitude = it },
            label = { Text("Longitude") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Latitude
        OutlinedTextField(
            value = latitude,
            onValueChange = { latitude = it },
            label = { Text("Latitude") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Save Button
        Button(
            onClick = {
                // Create the event and pass it to the onAddEvent lambda
                val event = Event(
                    name = name,
                    address = address,
                    startTime = startTime,
                    dateStart = dateStart,
                    dateEnd = dateEnd,
                    description = description,
                    contact = contact,
                    category = selectedCategory,
                    longitude = longitude,
                    latitude = latitude
                )
                onAddEvent(event)
                // Clear the input fields
                name = ""
                address = ""
                startTime = ""
                dateStart = ""
                dateEnd = ""
                description = ""
                contact = ""
                selectedCategory = ""
                longitude = ""
                latitude = ""
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Add Event")
        }
    }
}


@Composable
fun EventsScreen(events: List<Event>, onUpdateEvent: (Event) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Events", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))

        events.forEach { event ->
            EventCard(event, onUpdateEvent)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun EventCard(event: Event, onUpdateEvent: (Event) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(event.name, style = MaterialTheme.typography.h6)
            if (isExpanded) {
                Text("Address: ${event.address}")
                Text("Start Time: ${event.startTime}")
                Text("Start Date: ${event.dateStart}")
                Text("End Date: ${event.dateEnd}")
                Text("Description: ${event.description}")
                Text("Contact: ${event.contact}")
                Text("Category: ${event.category}")
                Text("Location: ${event.latitude}, ${event.longitude}")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { isEditing = true }) {
                    Text("Edit Event")
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
}

@Composable
fun EditEventDialog(event: Event, onDismiss: () -> Unit, onSave: (Event) -> Unit) {
    var name by remember { mutableStateOf(event.name) }
    var address by remember { mutableStateOf(event.address) }
    var startTime by remember { mutableStateOf(event.startTime) }
    var dateStart by remember { mutableStateOf(event.dateStart) }
    var dateEnd by remember { mutableStateOf(event.dateEnd) }
    var description by remember { mutableStateOf(event.description) }
    var contact by remember { mutableStateOf(event.contact) }
    var category by remember { mutableStateOf(event.category) }
    var longitude by remember { mutableStateOf(event.longitude) }
    var latitude by remember { mutableStateOf(event.latitude) }

    AlertDialog(
        onDismissRequest = onDismiss,
        buttons = {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Edit event",
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
                            onSave(
                                Event(
                                    name = name,
                                    address = address,
                                    startTime = startTime,
                                    dateStart = dateStart,
                                    dateEnd = dateEnd,
                                    description = description,
                                    contact = contact,
                                    category = category,
                                    longitude = longitude,
                                    latitude = latitude
                                )
                            )
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
@Composable
fun ScraperScreen() {
    Text("Scraper screen")
}

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
            onAddEvents(events)
        }) {
            Text("Generate")
        }
    }
}

fun generateRandomEvent(
    faker: Faker,
    startDateOffset: Int,
    endDateOffset: Int,
    minLongitude: Double,
    maxLongitude: Double,
    minLatitude: Double,
    maxLatitude: Double
): Event {
    return Event(
        name = faker.book.title(),
        address = faker.address.streetAddress(),
        startTime = "${(1..12).random()}:${(0..59).random().toString().padStart(2, '0')} ${if ((0..1).random() == 0) "AM" else "PM"}",
        dateStart = randomDate(1, startDateOffset),
        dateEnd = randomDate(startDateOffset + 1, startDateOffset + endDateOffset),
        description = faker.quote.mostInterestingManInTheWorld(),
        contact = faker.internet.safeEmail(),
        category = randomCategory(),
        longitude = randomCoordinate(minLongitude..maxLongitude),
        latitude = randomCoordinate(minLatitude..maxLatitude)
    )
}

fun randomDate(startOffset: Int, endOffset: Int): String {
    val startDate = LocalDate.now().plusDays(startOffset.toLong())
    val endDate = startDate.plusDays(endOffset.toLong())
    val randomDay = Random.nextLong(startDate.toEpochDay(), endDate.toEpochDay())
    val randomDate = LocalDate.ofEpochDay(randomDay)
    return randomDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
}

fun randomCategory(): String {
    val categories = listOf("concert", "festival", "sport", "community event", "educational event", "performance", "conference", "exhibition", "other")
    return categories.random()
}

fun ClosedRange<Double>.random(): Double {
    return (start + (endInclusive - start) * Math.random())
}

fun randomCoordinate(range: ClosedRange<Double>): String {
    return range.random().toString()
}

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


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
