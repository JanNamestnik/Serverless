import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {
    var selectedScreen by remember { mutableStateOf("Add event") }

    MaterialTheme {
        Row(modifier = Modifier.fillMaxSize()) {
            Sidebar(selectedScreen) { selectedScreen = it }
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
            icon = Icons.Default.Person,
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

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
