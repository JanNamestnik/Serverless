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
import kotlin.random.Random
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// za povezovanje na bazo
import okhttp3.*
import java.io.IOException
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.google.gson.*
import com.google.gson.annotations.Expose

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

import org.bson.types.ObjectId
import java.text.SimpleDateFormat
import java.util.Date

import org.bson.Document
import scraping.*

// FETCHING EVENTS ------------------------------------------------------------------------------------------------
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
                println(jsonResponse)
                val events = parseEventsFromJson(jsonResponse)
                onResult(events)
            }
        }
    })
}


class ObjectIdDeserializer : JsonDeserializer<ObjectId> {
    override fun deserialize(json: JsonElement?, typeOfT: java.lang.reflect.Type?, context: JsonDeserializationContext?): ObjectId {
        return if (json!!.isJsonObject) {
            val jsonObject = json.asJsonObject
            val hexString = jsonObject.get("\$oid").asString
            ObjectId(hexString)
        } else {
            ObjectId(json.asString)
        }
    }
}

class DateDeserializer : JsonDeserializer<Date> {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

    override fun deserialize(json: JsonElement?, typeOfT: java.lang.reflect.Type?, context: JsonDeserializationContext?): Date {
        return dateFormat.parse(json!!.asString)
    }
}

fun parseEventsFromJson(jsonResponse: String): List<Event> {
    val gson = GsonBuilder()
        .registerTypeAdapter(ObjectId::class.java, ObjectIdDeserializer())
        .registerTypeAdapter(Date::class.java, DateDeserializer())
        .create()
    val eventType = object : TypeToken<List<Event>>() {}.type
    return gson.fromJson(jsonResponse, eventType)
}



// FETCHING USERS -------------------------------------------------------------------------------------------------
fun fetchUsers(onResult: (List<User>) -> Unit) {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/users")
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            response.body?.string()?.let { jsonResponse ->
                val users = parseUsersFromJson(jsonResponse)
                onResult(users)
            }
        }
    })
}

fun parseUsersFromJson(jsonResponse: String): List<User> {
    val gson = GsonBuilder()
        .registerTypeAdapter(ObjectId::class.java, ObjectIdDeserializer())
        .registerTypeAdapter(Date::class.java, DateDeserializer())
        .create()
    val eventType = object : TypeToken<List<User>>() {}.type
    return gson.fromJson(jsonResponse, eventType)
}

// FETCHING REVIEWS -------------------------------------------------------------------------------------------------
fun fetchReviews(onResult: (List<Review>) -> Unit) {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/reviews")
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            response.body?.string()?.let { jsonResponse ->
                val reviews = parseReviewsFromJson(jsonResponse)
                onResult(reviews)
            }
        }
    })
}

fun parseReviewsFromJson(jsonResponse: String): List<Review> {
    val gson = GsonBuilder()
        .registerTypeAdapter(ObjectId::class.java, ObjectIdDeserializer())
        .create()
    val eventType = object : TypeToken<List<Review>>() {}.type
    return gson.fromJson(jsonResponse, eventType)
}

// FETCHING CATEGORIES ----------------------------------------------------------------------------------------------
fun fetchCategories(onResult: (List<Category>) -> Unit) {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/categories")
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            response.body?.string()?.let { jsonResponse ->
                val categories = parseCategoriesFromJson(jsonResponse)
                onResult(categories)
            }
        }
    })
}

fun parseCategoriesFromJson(jsonResponse: String): List<Category> {
    val gson = GsonBuilder()
        .registerTypeAdapter(ObjectId::class.java, ObjectIdDeserializer())
        .registerTypeAdapter(Date::class.java, DateDeserializer())
        .create()
    val eventType = object : TypeToken<List<Category>>() {}.type
    return gson.fromJson(jsonResponse, eventType)
}

// APP-------------------------------------------------------------------------------------------------------------
@Composable
@Preview
fun App() {
    var selectedScreen by remember { mutableStateOf("Add") }
    var events by remember { mutableStateOf(listOf<Event>()) }
    var users by remember { mutableStateOf(listOf<User>()) }
    var reviews by remember { mutableStateOf(listOf<Review>()) }
    var categories by remember { mutableStateOf(listOf<Category>()) }

    // Fetch events and users when the composable is first launched
    LaunchedEffect(Unit) {
        fetchEvents { fetchedEvents ->
            events = fetchedEvents
        }
        fetchUsers { fetchedUsers ->
            users = fetchedUsers
        }
        fetchReviews { fetchedReviews ->
            reviews = fetchedReviews
        }
        fetchCategories { fetchedCategories ->
            categories = fetchedCategories
        }
    }

    // Fetch data every time the corresponding screen is selected
    LaunchedEffect(selectedScreen) {
        when (selectedScreen) {
            "Events" -> fetchEvents { fetchedEvents ->
                events = fetchedEvents
            }
            "Users" -> fetchUsers { fetchedUsers ->
                users = fetchedUsers
            }
            "Reviews" -> fetchReviews { fetchedReviews ->
                reviews = fetchedReviews
            }
            "Categories" -> fetchCategories { fetchedCategories ->
                categories = fetchedCategories
            }
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
                onAddEvent = { newEvent ->
                    events = events + newEvent
                },
                onUpdateEvent = { updatedEvent ->
                    events = events.map { if (it._id == updatedEvent._id) updatedEvent else it }
                },
                onDeleteEvent = { deletedEvent ->
                    events = events.filter { it._id != deletedEvent._id }
                },
                onAddUser = { newUser ->
                    users = users + newUser
                },
                onDeleteUser = { deletedUser ->
                    users = users.filter { it._id != deletedUser._id }
                },
                onAddReview = { newReview ->
                    reviews = reviews + newReview
                },
                onUpdateReview = { updatedReview ->
                    reviews = reviews.map { if (it.eventId == updatedReview.eventId && it.userId == updatedReview.userId) updatedReview else it }
                },
                onDeleteReview = { deletedReview ->
                    reviews = reviews.filter { it._id != deletedReview._id }
                },
                onAddCategory = { newCategory ->
                    categories = categories + newCategory
                },
                onDeleteCategory = { deletedCategory ->
                    categories = categories.filter { it._id != deletedCategory._id }
                }
            )
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
            label = "Add",
            isSelected = selectedScreen == "Add",
            onClick = { onScreenSelected("Add") }
        )
        Divider(color = Color.LightGray, thickness = 2.dp)
        SidebarButton(
            icon = Icons.Default.List,
            label = "Events",
            isSelected = selectedScreen == "Events",
            onClick = { onScreenSelected("Events") }
        )
        SidebarButton(
            icon = Icons.Default.Person,
            label = "Users",
            isSelected = selectedScreen == "Users",
            onClick = { onScreenSelected("Users") }
        )
        SidebarButton(
            icon = Icons.Default.Star,
            label = "Reviews",
            isSelected = selectedScreen == "Reviews",
            onClick = { onScreenSelected("Reviews") }
        )
        SidebarButton(
            icon = Icons.Default.Menu,
            label = "Categories",
            isSelected = selectedScreen == "Categories",
            onClick = { onScreenSelected("Categories") }
        )
        Divider(color = Color.LightGray, thickness = 2.dp)
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(imageVector = icon, contentDescription = label)
            Spacer(modifier = Modifier.width(8.dp))
            Text(label)
        }
    }
}


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
                "Add" -> AddScreen(
                    onAddEvent = onAddEvent,
                    onAddUser = onAddUser,
                    onAddCategory = onAddCategory,
                    onAddReview = onAddReview
                )
                "Events" -> EventsScreen(events, onUpdateEvent, onDeleteEvent)
                "Users" -> UsersScreen(users, onDeleteUser)
                "Reviews" -> ReviewsScreen(reviews, onUpdateReview, onDeleteReview)
                "Categories" -> CategoriesScreen(categories, onDeleteCategory)
                "Scraper" -> ScraperScreen { newEvents ->
                    newEvents.forEach { onAddEvent(it) }
                }
                "Generator" -> GeneratorScreen { newEvents ->
                    newEvents.forEach { onAddEvent(it) }
                }
                "About" -> AboutScreen()
            }
        }
    }
}




// ADD SCREEN --------------------------------------------------------------------------------------------------------
@Composable
fun AddScreen(
    onAddEvent: (Event) -> Unit,
    onAddUser: (User) -> Unit,
    onAddCategory: (Category) -> Unit,
    onAddReview: (Review) -> Unit
) {
    var selectedType by remember { mutableStateOf("Event") }
    val types = listOf("Event", "User", "Category", "Review")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Add New", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown Menu for Selecting Type
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
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
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

        // Conditional UI based on selected type
        when (selectedType) {
            "Event" -> AddEventForm(onAddEvent)
            "User" -> AddUserForm(onAddUser)
            "Category" -> AddCategoryForm(onAddCategory)
            "Review" -> AddReviewForm(onAddReview)
        }
    }
}

// EVENT FORM ------------------------------------------------------------------------------------------------
@Composable
fun AddEventForm(onAddEvent: (Event) -> Unit) {
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
    var eventImage by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var attendees by remember { mutableStateOf("") }
    var owner by remember { mutableStateOf("") }

    val categories = listOf("concert", "festival", "sport", "community event", "educational event", "performance", "conference", "exhibition", "other")

    // State to hold the new event
    var newEvent by remember { mutableStateOf<Event?>(null) }

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
            modifier = Modifier.fillMaxWidth()
        )

        // Date End
        OutlinedTextField(
            value = dateEnd,
            onValueChange = { dateEnd = it },
            label = { Text("Date End") },
            modifier = Modifier.fillMaxWidth()
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

        // Event Image
        OutlinedTextField(
            value = eventImage,
            onValueChange = { eventImage = it },
            label = { Text("Event Image") },
            modifier = Modifier.fillMaxWidth()
        )

        // Price
        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        // Save Button
        Button(
            onClick = {
                // Create the event and pass it to the onAddEvent lambda
                val location = Location(
                    type = "Point",
                    coordinates = listOf(
                        longitude.toDoubleOrNull() ?: 0.0,
                        latitude.toDoubleOrNull() ?: 0.0
                    )
                )
                val event = Event(
                    _id = null,
                    name = name,
                    address = address,
                    startTime = startTime,
                    date_start = dateStart,
                    date_end = dateEnd,
                    description = description,
                    contact = contact,
                    category =  getCategoryID(selectedCategory),
                    location = location,
                    eventImage = eventImage,
                    price = price.toIntOrNull() ?: 0,
                    attendees = attendees.split(", ").filter { it.isNotBlank() },
                    owner = ObjectId("6651c0a0278d45f6f2502b7b")
                )

                // Update the newEvent state
                newEvent = event

                // Add event to the database array and send to the database
                newEvent?.let {
                    GlobalScope.launch {
                        sendEventsToDatabase(listOf(it), "https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/createEvent")
                    }
                }

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
                eventImage = ""
                price = ""
                attendees = ""
                owner = ""
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Add Event")
        }
    }
}
// funckije sendToDatabase in toJson za user, review, category----------------------------------------------------
inline fun <reified T> T.toDatabaseJson(): String {
    val gson = GsonBuilder()
        .registerTypeAdapter(ObjectId::class.java, ObjectIdSerializer())
        .excludeFieldsWithoutExposeAnnotation()
        .create()
    return gson.toJson(this)
}

inline fun <reified T> sendToDatabase(item: T, url: String) {
    val client = OkHttpClient()
    val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
    val json = item.toDatabaseJson()
    println("Generated JSON: $json")  // Print the JSON to check it

    // Convert JSON string to Document (BSON)
    val document = Document.parse(json)

    // Create a request body
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


// USER FORM ------------------------------------------------------------------------------------------------
@Composable
fun AddUserForm(onAddUser: (User) -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var favorites by remember { mutableStateOf("") }
    var profileImage by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Add User", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))

        // Name
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        // Password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        // Favorites
        OutlinedTextField(
            value = favorites,
            onValueChange = { favorites = it },
            label = { Text("Favorites") },
            modifier = Modifier.fillMaxWidth()
        )

        // Profile Image
        OutlinedTextField(
            value = profileImage,
            onValueChange = { profileImage = it },
            label = { Text("Profile Image") },
            modifier = Modifier.fillMaxWidth()
        )

        // Save Button
        Button(
            onClick = {
                val user = User(
                    _id = null,
                    username = name,
                    email = email,
                    password = password,
                    favorites = favorites.split(","),
                    profileImage = profileImage
                )

                GlobalScope.launch {
                    sendToDatabase(user, "https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/createUser")
                }

                name = ""
                email = ""
                password = ""
                favorites = ""
                profileImage = ""
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Add User")
        }
    }
}

// CATEGORY FORM ------------------------------------------------------------------------------------------------
@Composable
fun AddCategoryForm(onAddCategory: (Category) -> Unit) {
    var name by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Add Category", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))

        // Name
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        // Save Button
        Button(
            onClick = {
                val category = Category(name = name, _id = null)

                GlobalScope.launch {
                    sendToDatabase(category, "https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/createCategory")
                }

                name = ""
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Add Category")
        }
    }
}

// REVIEW FORM ------------------------------------------------------------------------------------------------
@Composable
fun AddReviewForm(onAddReview: (Review) -> Unit) {
    var Id by remember { mutableStateOf("") }
    var eventId by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }
    var created by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Add Review", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))

        // Event ID
        OutlinedTextField(
            value = eventId,
            onValueChange = { eventId = it },
            label = { Text("Event ID") },
            modifier = Modifier.fillMaxWidth()
        )

        // User ID
        OutlinedTextField(
            value = userId,
            onValueChange = { userId = it },
            label = { Text("User ID") },
            modifier = Modifier.fillMaxWidth()
        )

        // Created Date
        OutlinedTextField(
            value = created,
            onValueChange = { created = it },
            label = { Text("Created Date") },
            modifier = Modifier.fillMaxWidth()
        )

        // Rating
        OutlinedTextField(
            value = rating,
            onValueChange = { rating = it },
            label = { Text("Rating") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Content
        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") },
            modifier = Modifier.fillMaxWidth()
        )

        // Save Button
        Button(
            onClick = {
                val review = Review(
                    _id = null,
                    eventId = ObjectId(eventId),
                    userId = ObjectId(userId),
                    created = created,
                    rating = rating.toInt(),
                    content = content
                )

                GlobalScope.launch {
                    sendToDatabase(review, "https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/createReview")
                }

                Id = ""
                eventId = ""
                userId = ""
                created = ""
                rating = ""
                content = ""
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Add Review")
        }
    }
}

// EVETN SCREEEN ----------------------------------------------------------------------------------------------
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


@Composable
fun EventCard(event: Event, onUpdateEvent: (Event) -> Unit, onDeleteEvent: (Event) -> Unit) {
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
                    Button(onClick = {
                        GlobalScope.launch {
                            try {
                                deleteFromDatabase(event._id!!, "https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/deleteEvent")
                                onDeleteEvent(event)
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                    },
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
}


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
                                coordinates = listOf(longitude.toDoubleOrNull() ?: 0.0, latitude.toDoubleOrNull() ?: 0.0)
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
                            onSave(updatedEvent)

                            val updateFields = mutableMapOf<String, Any>()
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



// USERS --------------------------------------------------------------------------------------------------------
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

@Composable
fun UsersScreen(users: List<User>, onDeleteUser: (User) -> Unit) {
    var userList by remember { mutableStateOf(users) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Users", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))

        userList.forEach { user ->
            UserCard(user, onUpdateUser = { updatedUser ->
                // Update the user in the list
                userList = userList.map {
                    if (it._id == updatedUser._id) updatedUser else it
                }
            }, onDeleteUser = { userToDelete ->
                // Delete the user from the list
                userList = userList.filter { it._id != userToDelete._id }
                onDeleteUser(userToDelete)
            })
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun UserCard(user: User, onUpdateUser: (User) -> Unit, onDeleteUser: (User) -> Unit) {
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
            user.username?.let { Text(it, style = MaterialTheme.typography.h6) }
            if (isExpanded) {
                Text("id: ${user._id}")
                Text("Email: ${user.email}")
                Text("Password: ${user.password}")
                Text("Favorites: ${user.favorites.joinToString(", ")}")
                Text("Profile Image: ${user.profileImage}")
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Button(onClick = { isEditing = true }) {
                        Text("Edit Event")
                    }
                    Button(onClick = {
                        GlobalScope.launch {
                            try {
                                deleteFromDatabase(user._id!!, "https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/deleteUser")
                                onDeleteUser(user)
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                    },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                    ) {
                        Text("Delete Event")
                    }
                }
            }
        }
    }

    if (isEditing) {
        EditUserDialog(user = user, onDismiss = { isEditing = false }, onSave = { updatedUser ->
            onUpdateUser(updatedUser)
            isEditing = false
        })
    }
}

@Composable
fun EditUserDialog(user: User, onDismiss: () -> Unit, onSave: (User) -> Unit) {
    var name by remember { mutableStateOf(user.username ?: "") }
    var email by remember { mutableStateOf(user.email ?: "") }
    var password by remember { mutableStateOf(user.password ?: "") }
    var favorites by remember { mutableStateOf(user.favorites.joinToString(", ")) }
    var profileImage by remember { mutableStateOf(user.profileImage ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        buttons = {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Edit User",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    textAlign = TextAlign.Center
                )

                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    item { name.let { OutlinedTextField(value = it, onValueChange = { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth()) } }
                    item { email.let { OutlinedTextField(value = it, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth()) } }
                    item { password.let { OutlinedTextField(value = it, onValueChange = { password = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth()) } }
                    item { OutlinedTextField(value = favorites, onValueChange = { favorites = it }, label = { Text("Favorites") }, modifier = Modifier.fillMaxWidth()) }
                    item { profileImage.let { OutlinedTextField(value = it, onValueChange = { profileImage = it }, label = { Text("Profile Image") }, modifier = Modifier.fillMaxWidth()) } }
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
                            val updatedUser = User(
                                _id = user._id,
                                username = name,
                                email = email,
                                password = password,
                                favorites = favorites.split(", ").toList(),
                                profileImage = profileImage
                            )
                            onSave(updatedUser)

                            val updateFields = mutableMapOf<String, Any>()
                            if (name != user.username) updateFields["username"] = name
                            if (email != user.email) updateFields["email"] = email
                            if (password != user.password) updateFields["password"] = password
                            if (favorites != user.favorites.joinToString(", ")) updateFields["favorites"] = favorites.split(", ").toList()
                            if (profileImage != user.profileImage) updateFields["profileImage"] = profileImage

                            GlobalScope.launch {
                                updateInDatabase(user._id!!, updateFields, "https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/updateUser")
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


// REVIEWS ----------------------------------------------------------------------------------------------------
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

@Composable
fun ReviewsScreen(reviews: List<Review>, onUpdateReview: (Review) -> Unit, onDeleteReview: (Review) -> Unit) {
    var reviewList by remember { mutableStateOf(reviews) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Reviews", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))

        reviewList.forEach { review ->
            ReviewCard(review, onUpdateReview = { updatedReview ->
                // Update the review in the list
                reviewList = reviewList.map {
                    if (it._id == updatedReview._id) updatedReview else it
                }
            }, onDeleteReview = { reviewToDelete ->
                // Delete the review from the list
                reviewList = reviewList.filter { it._id != reviewToDelete._id }
                onDeleteReview(reviewToDelete)
            })
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ReviewCard(review: Review, onUpdateReview: (Review) -> Unit, onDeleteReview: (Review) -> Unit) {
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
            Text("Rating: ${review.rating}", style = MaterialTheme.typography.h6)
            if (isExpanded) {
                Text("Id: ${review._id}")
                Text("Content: ${review.content}")
                Text("Created: ${review.created}")
                Text("Event ID: ${review.eventId}")
                Text("User ID: ${review.userId}")
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Button(onClick = { isEditing = true }) {
                        Text("Edit Review")
                    }
                    Button(
                        onClick = {
                            GlobalScope.launch {
                                try {
                                    deleteFromDatabase(review._id!!, "https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/deleteReview")
                                    onDeleteReview(review)
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                    ) {
                        Text("Delete Review")
                    }
                }
            }
        }
    }

    if (isEditing) {
        EditReviewDialog(review = review, onDismiss = { isEditing = false }, onSave = { updatedReview ->
            onUpdateReview(updatedReview)
            isEditing = false
        })
    }
}


@Composable
fun EditReviewDialog(review: Review, onDismiss: () -> Unit, onSave: (Review) -> Unit) {
    var eventId by remember { mutableStateOf(review.eventId.toString()) }
    var userId by remember { mutableStateOf(review.userId.toString()) }
    var created by remember { mutableStateOf(review.created) }
    var rating by remember { mutableStateOf(review.rating.toString()) }
    var content by remember { mutableStateOf(review.content) }

    AlertDialog(
        onDismissRequest = onDismiss,
        buttons = {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Edit Review",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    textAlign = TextAlign.Center
                )

                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    item { OutlinedTextField(value = eventId, onValueChange = { eventId = it }, label = { Text("Event ID") }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(value = userId, onValueChange = { userId = it }, label = { Text("User ID") }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(value = created, onValueChange = { created = it }, label = { Text("Created") }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(value = rating, onValueChange = { rating = it }, label = { Text("Rating") }, modifier = Modifier.fillMaxWidth()) }
                    item { OutlinedTextField(value = content, onValueChange = { content = it }, label = { Text("Content") }, modifier = Modifier.fillMaxWidth()) }
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
                            val updatedReview = Review(
                                _id = review._id,
                                eventId = ObjectId(eventId),
                                userId = ObjectId(userId),
                                created = created,
                                rating = rating.toInt(),
                                content = content
                            )
                            onSave(updatedReview)

                            val updateFields = mutableMapOf<String, Any>()
                            if (eventId != review.eventId.toString()) updateFields["eventId"] = ObjectId(eventId)
                            if (userId != review.userId.toString()) updateFields["userId"] = ObjectId(userId)
                            if (created != review.created) updateFields["created"] = created
                            if (rating != review.rating.toString()) updateFields["rating"] = rating.toInt()
                            if (content != review.content) updateFields["content"] = content

                            GlobalScope.launch {
                                updateInDatabase(review._id!!, updateFields, "https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/updateReview")
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



// CATEGORIES -----------------------------------------------------------------------------------------------
data class Category(
    @Expose(serialize = false, deserialize = false)
    val _id: ObjectId?,
    @Expose
    val name: String
)

@Composable
fun CategoriesScreen(initialCategories: List<Category>, onDeleteCategory: (Category) -> Unit) {
    var categories by remember { mutableStateOf(initialCategories) }

    fun handleUpdateCategory(updatedCategory: Category) {
        categories = categories.map { category ->
            if (category._id == updatedCategory._id) updatedCategory else category
        }
    }

    fun handleDeleteCategory(categoryToDelete: Category) {
        categories = categories.filter { it._id != categoryToDelete._id }
        onDeleteCategory(categoryToDelete)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Categories", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))

        categories.forEach { category ->
            CategoryCard(category, onUpdateCategory = ::handleUpdateCategory, onDeleteCategory = ::handleDeleteCategory)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun CategoryCard(category: Category, onUpdateCategory: (Category) -> Unit, onDeleteCategory: (Category) -> Unit) {
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
            Text(category.name, style = MaterialTheme.typography.h6)
            if (isExpanded) {
                Text("Id: ${category._id}")
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Button(onClick = { isEditing = true }) {
                        Text("Edit Category")
                    }
                    Button(
                        onClick = {
                            GlobalScope.launch {
                                try {
                                    deleteFromDatabase(category._id!!, "https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/deleteCategory")
                                    onDeleteCategory(category)
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                    ) {
                        Text("Delete Category")
                    }
                }
            }
        }
    }

    if (isEditing) {
        EditCategoryDialog(category = category, onDismiss = { isEditing = false }, onSave = { updatedCategory ->
            onUpdateCategory(updatedCategory)
            isEditing = false
        })
    }
}


@Composable
fun EditCategoryDialog(category: Category, onDismiss: () -> Unit, onSave: (Category) -> Unit) {
    var name by remember { mutableStateOf(category.name) }

    AlertDialog(
        onDismissRequest = onDismiss,
        buttons = {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Edit Category",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    textAlign = TextAlign.Center
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Button(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            val updatedCategory = Category(category._id, name)
                            onSave(updatedCategory)

                            val updateFields = mutableMapOf<String, Any>()
                            if (name != category.name) updateFields["name"] = name

                            GlobalScope.launch {
                                updateInDatabase(category._id!!, updateFields, "https://eu-central-1.aws.data.mongodb-api.com/app/serverlessapi-uvgsfoc/endpoint/updateCategory")
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



// GENERATOR ---------------------------------------------------------------------------------------------
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
        _id = null,
        name = faker.book.title(),
        address = faker.address.streetAddress(),
        startTime = "${(1..12).random()}:${(0..59).random().toString().padStart(2, '0')} ${if ((0..1).random() == 0) "AM" else "PM"}",
        date_start = randomDate(0, startDateOffset),
        date_end = randomDate(startDateOffset + 1, startDateOffset + endDateOffset),
        description = faker.quote.mostInterestingManInTheWorld(),
        contact = faker.internet.safeEmail(),
        category = ObjectId("6643ef3e35e389b1272f6b83"),
        location = Location(
            type = "Point",
            coordinates = listOf(
                randomCoordinate(minLongitude, maxLongitude),
                randomCoordinate(minLatitude, maxLatitude)
            )
        ),
        eventImage = "https://nekineki",
        price = Random.nextInt(0, 100),  // Random price between 0 and 100
        attendees = emptyList(),
        owner = ObjectId("6651c0a0278d45f6f2502b7b")
    )
}

fun randomDate(startOffset: Int, endOffset: Int): String {
    val startMillis = System.currentTimeMillis() + (startOffset * 24 * 60 * 60 * 1000L)
    val endMillis = System.currentTimeMillis() + (endOffset * 24 * 60 * 60 * 1000L)
    val randomMillis = Random.nextLong(startMillis, endMillis)
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(Date(randomMillis))
}

fun randomCoordinate(min: Double, max: Double): Double {
    return Random.nextDouble(min, max)
}



// SCRAPER -----------------------------------------------------------------------------------------
fun Event.toDatabaseJson(): String {
    val gson = GsonBuilder()
        .registerTypeAdapter(ObjectId::class.java, ObjectIdSerializer())
        .excludeFieldsWithoutExposeAnnotation()
        .create()
    return gson.toJson(this)
}


fun sendEventsToDatabase(events: List<Event>, url: String) {
    val client = OkHttpClient()
    val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()

    events.forEach { event ->
        val json = event.toDatabaseJson()
        println("Generated JSON: $json")  // Print the JSON to check it

        // Convert JSON string to Document (BSON)
        val document = Document.parse(json)

        // Create a request body
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

@Composable
fun ScraperScreen(onAddEvents: (List<Event>) -> Unit) {
    var events by remember { mutableStateOf(listOf<Event>()) }
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




// ABOUT --------------------------------------------------------------------------------------------
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

// MAIN-----------------------------------------------------------------------------------------------------------

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
