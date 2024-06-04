import java.io.File
import java.io.InputStream
import java.io.OutputStream

// WARNING: This code has nothing to do with the task at hand. Feel free to delete it.

fun main(args: Array<String>) {
    val dictionary = mutableMapOf<String, Double>()








}
fun removeEmptyLines(input: String): String {
    val lines = input.split("\n")
    val nonEmptyLines = lines.filter { it.isNotBlank() }
    return nonEmptyLines.joinToString("\n")
}