package si.um.feri.personlibrary

data class Person(
    val age: String,
    val gender: String,
    val faceImage: String
)
{
    override fun toString(): String {
        return "Person(Age: $age, Gender: $gender, image=$faceImage)"
    }
}