package si.um.feri.personlibrary

import java.util.UUID

class PersonRecord(
    var person: Person,
    val uuid: String = UUID.randomUUID().toString(),
) {
    override fun toString(): String {
        return "PersonRecord(person=$person, uuid='$uuid')"
    }
}