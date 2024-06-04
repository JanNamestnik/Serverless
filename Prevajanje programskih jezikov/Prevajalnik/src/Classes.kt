import kotlin.math.*

interface Sentence {
    fun toGeoJson(slovar: MutableMap<String, Double>): String
}

interface IzrazSentence {
    fun evaluate(slovar: MutableMap<String, Double>): Double
}

interface BooleanSentence {
    fun evaluate(slovar: MutableMap<String, Double>): Boolean
}

class Zemlejvid(private val besedilo: Besedilo, private val mesta: List<Mesto>) : Sentence {
    override fun toGeoJson(slovar: MutableMap<String, Double>): String {
        var placesGeoJson = mesta.joinToString(", ") { it.toGeoJson(slovar) }
        //placesGeoJson = placesGeoJson.substring(0, placesGeoJson.length - 2)
        return """
        {
            "type": "FeatureCollection",
            "features": [
                $placesGeoJson
            ]
        }
        """.trimIndent()
    }
}

class Mesto(private val besedilo: Besedilo, private val objekti: List<Objekt>, private val ulice: List<Ulica>, private val narava: List<Narava>) : Sentence {
    override fun toGeoJson(slovar: MutableMap<String, Double>): String {
        val objektiGeoJson = objekti.joinToString(", ") { it.toGeoJson(slovar) }
        var uliceGeoJson = ulice.joinToString(",") { it.toGeoJson(slovar) }
        val naravaGeoJson = narava.joinToString(", ") { it.toGeoJson(slovar) }

        return """
        
                    $objektiGeoJson,
                    $uliceGeoJson ${ if(naravaGeoJson.isNotEmpty()) "," else ""}
                    $naravaGeoJson
             
        """.trimIndent()
    }
}

abstract class Narava : Sentence

class Reka(private val besedilo: Besedilo, private val ukazi: List<Ukaz>) : Narava() {
    override fun toGeoJson(slovar: MutableMap<String, Double>): String {
        // ukazi lahko vračajo prazni geojson in moreš odstarati vse odvečne vejice

        var ukaziGeoJson =" "
        for (ukaz in ukazi){
            var appendable = ukaz.toGeoJson(slovar)
            if(appendable != "")
                appendable += ","
            ukaziGeoJson += appendable
        }
        ukaziGeoJson = ukaziGeoJson.substring(0, ukaziGeoJson.length - 1)

        var returnString = ""
        //i want to search ukaziGeoJson to find if there is more then one ,
        val regex = Regex("\"type\"")

        if( regex.findAll(ukaziGeoJson).count() != 1){
            returnString =   """{
                "type": "GeometryCollection",
                
                "geometries": [
                 $ukaziGeoJson
                ]}
            """.trimIndent()
        }
        else{
            returnString = ukaziGeoJson
        }

        return """
        {
            "type": "Feature",
            "properties": { "name": ${besedilo.besedilo}},
            "geometry":   
                $returnString
        }
        """.trimIndent()
    }
}

class Gozd(private val besedilo: Besedilo, private val ukazi: List<Ukaz>) : Narava() {
    override fun toGeoJson(slovar: MutableMap<String, Double>): String {
        // ukazi lahko vračajo prazni geojson in moreš odstarati vse odvečne vejice

        var ukaziGeoJson =" "
        for (ukaz in ukazi){
            var appendable = ukaz.toGeoJson(slovar)
            if(appendable != "")
                appendable += ","
            ukaziGeoJson += appendable
        }
        ukaziGeoJson = ukaziGeoJson.substring(0, ukaziGeoJson.length - 1)

        var returnString = ""
        val regex = Regex("\"type\"")

        if( regex.findAll(ukaziGeoJson).count() != 1){
            returnString =   """{
                "type": "GeometryCollection",
                
                "geometries": [
                 $ukaziGeoJson
                ]}
            """.trimIndent()
        }
        else{
            returnString = ukaziGeoJson
        }

        return """
        {
            "type": "Feature",
            "properties": { "name": ${besedilo.besedilo}},
            "geometry":   
                $returnString
        }
        """.trimIndent()
    }
}

class Ulica(private val besedilo: Besedilo, private val ukazi: List<Ukaz>) : Sentence {
    override fun toGeoJson(slovar: MutableMap<String, Double>): String {
        // ukazi lahko vračajo prazni geojson in moreš odstarati vse odvečne vejice

        var ukaziGeoJson =" "
        for (ukaz in ukazi){
            var appendable = ukaz.toGeoJson(slovar)
            if(appendable != "")
                appendable += ","
            ukaziGeoJson += appendable
        }
        ukaziGeoJson = ukaziGeoJson.substring(0, ukaziGeoJson.length - 1)

        var returnString = ""
        val regex = Regex("\"type\"")

        if( regex.findAll(ukaziGeoJson).count() != 1){
            returnString =   """{
                "type": "GeometryCollection",
                
                "geometries": [
                 $ukaziGeoJson
                ]}
            """.trimIndent()
        }
        else{
            returnString = ukaziGeoJson
        }

        return """
        {
            "type": "Feature",
            "properties": { "name": ${besedilo.besedilo}},
            "geometry":   
                $returnString
        }
        """.trimIndent()
    }
}

abstract class Objekt(private val besedilo: Besedilo, private val ukazi: List<Ukaz>) : Sentence

class Stavba(besedilo: Besedilo, ukazi: List<Ukaz>) : Objekt(besedilo, ukazi) {
    private var besedilo = besedilo
    private var ukazi = ukazi
    override fun toGeoJson(slovar: MutableMap<String, Double>): String {
        // ukazi lahko vračajo prazni geojson in moreš odstarati vse odvečne vejice

        var ukaziGeoJson =" "
        for (ukaz in ukazi){
            var appendable = ukaz.toGeoJson(slovar)
            if(appendable != "")
                appendable += ","
                ukaziGeoJson += appendable
        }
        ukaziGeoJson = ukaziGeoJson.substring(0, ukaziGeoJson.length - 1)

        var returnString = ""
        val regex = Regex("\"type\"")

        if( regex.findAll(ukaziGeoJson).count() != 1){
            returnString =   """{
                "type": "GeometryCollection",
                
                "geometries": [
                 $ukaziGeoJson
                ]}
            """.trimIndent()
        }
        else{
            returnString = ukaziGeoJson
        }

        return """
        {
            "type": "Feature",
            "properties": { "name": ${besedilo.besedilo}},
            "geometry":   
                $returnString
        }
        """.trimIndent()
    }
}

class PrireditveniProstor(besedilo: Besedilo, ukazi: List<Ukaz>) : Objekt(besedilo, ukazi) {
    private var besedilo = besedilo
    private var ukazi = ukazi
    override fun toGeoJson(slovar: MutableMap<String, Double>): String {
        // ukazi lahko vračajo prazni geojson in moreš odstarati vse odvečne vejice

        var ukaziGeoJson =" "
        for (ukaz in ukazi){
            var appendable = ukaz.toGeoJson(slovar)
            if(appendable != "")
                appendable += ","
            ukaziGeoJson += appendable
        }
        ukaziGeoJson = ukaziGeoJson.substring(0, ukaziGeoJson.length - 1)

        var returnString = ""
        val regex = Regex("\"type\"")

        if( regex.findAll(ukaziGeoJson).count() != 1){
            returnString =   """{
                "type": "GeometryCollection",
                
                "geometries": [
                 $ukaziGeoJson
                ]}
            """.trimIndent()
        }
        else{
            returnString = ukaziGeoJson
        }

        return """
        {
            "type": "Feature",
            "properties": { "name": ${besedilo.besedilo}},
            "geometry":   
                $returnString
        }
        """.trimIndent()
    }
}

class AvtobusnaPostaja(besedilo: Besedilo, ukazi: List<Ukaz>) : Objekt(besedilo, ukazi) {
    private var besedilo = besedilo
    private var ukazi = ukazi
    override fun toGeoJson(slovar: MutableMap<String, Double>): String {
        // ukazi lahko vračajo prazni geojson in moreš odstarati vse odvečne vejice

        var ukaziGeoJson =" "
        for (ukaz in ukazi){
            var appendable = ukaz.toGeoJson(slovar)
            if(appendable != "")
                appendable += ","
            ukaziGeoJson += appendable
        }
        ukaziGeoJson = ukaziGeoJson.substring(0, ukaziGeoJson.length - 1)

        var returnString = ""
        val regex = Regex("\"type\"")

        if( regex.findAll(ukaziGeoJson).count() != 1){
            returnString =   """{
                "type": "GeometryCollection",
                
                "geometries": [
                 $ukaziGeoJson
                ]}
            """.trimIndent()
        }
        else{
            returnString = ukaziGeoJson
        }

        return """
        {
            "type": "Feature",
            "properties": { "name": ${besedilo.besedilo}},
            "geometry":   
                $returnString
        }
        """.trimIndent()
    }
}

class NakupnoMesto(besedilo: Besedilo, ukazi: List<Ukaz>) : Objekt(besedilo, ukazi) {
    private var besedilo = besedilo
    private var ukazi = ukazi
    override fun toGeoJson(slovar: MutableMap<String, Double>): String {
        // ukazi lahko vračajo prazni geojson in moreš odstarati vse odvečne vejice

        var ukaziGeoJson =" "
        for (ukaz in ukazi){
            var appendable = ukaz.toGeoJson(slovar)
            if(appendable != "")
                appendable += ","
            ukaziGeoJson += appendable
        }
        ukaziGeoJson = ukaziGeoJson.substring(0, ukaziGeoJson.length - 1)

        var returnString = ""
        val regex = Regex("\"type\"")

        if( regex.findAll(ukaziGeoJson).count() != 1){
            returnString =   """{
                "type": "GeometryCollection",
                
                "geometries": [
                 $ukaziGeoJson
                ]}
            """.trimIndent()
        }
        else{
            returnString = ukaziGeoJson
        }

        return """
        {
            "type": "Feature",
            "properties": { "name": ${besedilo.besedilo}},
            "geometry":   
                $returnString
        }
        """.trimIndent()
    }
}

abstract class Ukaz : Sentence

class ImeSpremenljivke(public  val besedilo: String) : Ukaz() {
    override fun toGeoJson(slovar: MutableMap<String, Double>): String {
        return ""
    }
}

class DefinicijaSpremenljivke(private val imeSpremenljivke: ImeSpremenljivke, private val izraz: Izraz) : Ukaz() {
    override fun toGeoJson(slovar: MutableMap<String, Double>): String {
        slovar.put(imeSpremenljivke.besedilo, izraz.evaluate(slovar))
        return ""

    }
}

class RedefinicijaSpremenljivke(private val imeSpremenljivke: ImeSpremenljivke, private val izraz: Izraz) : Ukaz() {
    override fun toGeoJson(slovar: MutableMap<String, Double>): String {
        slovar[imeSpremenljivke.besedilo] = izraz.evaluate(slovar)
        return ""
    }
}

class Krog(private val tocka1: Tocka, private val izraz: Izraz) : Ukaz() {
    override fun toGeoJson(slovar: MutableMap<String, Double>): String {
        val numPoints= 1000
        val coordinates = mutableListOf<List<Double>>()
        val angleStep = 2 * Math.PI / numPoints

        for (i in 0 until numPoints) {
            val angle = i * angleStep
            val latitude = tocka1.x.evaluate(slovar) + (izraz.evaluate(slovar) / 6378137) * (180 / Math.PI) * sin(angle)
            val longitude = tocka1.y.evaluate(slovar) + (izraz.evaluate(slovar) / 6378137) * (180 / Math.PI) * cos(angle) / cos(tocka1.x.evaluate(slovar) * Math.PI / 180)
            coordinates.add(listOf(longitude, latitude))
        }

        // Closing the circle by adding the first point again
        coordinates.add(coordinates[0])
        // Implement GeoJSON conversion for Krog
        return """
       {
            "type": "Polygon",
            "coordinates": [[
                ${coordinates.map { "[${it[0]}, ${it[1]}]" }.joinToString(", ")}
            ]]
        }
        """.trimIndent()
    }

    private fun calculateDistance(t1: Tocka, t2: Tocka, slovar: MutableMap<String, Double>): Double {
        val x1 = t1.x.evaluate(slovar)
        val y1 = t1.y.evaluate(slovar)
        val x2 = t2.x.evaluate(slovar)
        val y2 = t2.y.evaluate(slovar)
        return kotlin.math.sqrt((x2 - x1).pow(2) + (y2 - y1).pow(2))
    }
}

class Stirikotnik(private val tocka1: Tocka, private val tocka2: Tocka, private val tocka3: Tocka, private val tocka4: Tocka) : Ukaz() {
    override fun toGeoJson(slovar: MutableMap<String, Double>): String {
        // Implement GeoJSON conversion for Stirikotnik
        return """
        {
            "type": "Polygon",
            "coordinates": [[
                [${tocka1.x.evaluate(slovar)}, ${tocka1.y.evaluate(slovar)}],
                [${tocka2.x.evaluate(slovar)}, ${tocka2.y.evaluate(slovar)}],
                [${tocka3.x.evaluate(slovar)}, ${tocka3.y.evaluate(slovar)}],
                [${tocka4.x.evaluate(slovar)}, ${tocka4.y.evaluate(slovar)}],
                [${tocka1.x.evaluate(slovar)}, ${tocka1.y.evaluate(slovar)}]
            ]]
        }
        """.trimIndent()
    }
}

class Trikotnik(private val tocka1: Tocka, private val tocka2: Tocka, private val tocka3: Tocka) : Ukaz() {
    override fun toGeoJson(slovar: MutableMap<String, Double>): String {
        // Implement GeoJSON conversion for Trikotnik
        return """
        {
            "type": "Polygon",
            "coordinates": [[
                [${tocka1.x.evaluate(slovar)}, ${tocka1.y.evaluate(slovar)}],
                [${tocka2.x.evaluate(slovar)}, ${tocka2.y.evaluate(slovar)}],
                [${tocka3.x.evaluate(slovar)}, ${tocka3.y.evaluate(slovar)}],
                [${tocka1.x.evaluate(slovar)}, ${tocka1.y.evaluate(slovar)}]
            ]]
        }
        """.trimIndent()
    }
}
data class Point(val lat: Double, val lon: Double)
class Crta(private val tocka1: Tocka, private val tocka2: Tocka, private val ukrivljensko: Izraz, ) : Ukaz() {private fun degToRad(deg: Double): Double = deg * PI / 180.0

    private fun radToDeg(rad: Double): Double = rad * 180.0 / PI

    private fun toCartesian(point: Point): Triple<Double, Double, Double> {
        val latRad = degToRad(point.lat)
        val lonRad = degToRad(point.lon)
        val x = cos(latRad) * cos(lonRad)
        val y = cos(latRad) * sin(lonRad)
        val z = sin(latRad)
        return Triple(x, y, z)
    }

    private fun toLatLon(cartesian: Triple<Double, Double, Double>): Point {
        val (x, y, z) = cartesian
        val hyp = sqrt(x * x + y * y)
        val lat = radToDeg(atan2(z, hyp))
        val lon = radToDeg(atan2(y, x))
        return Point(lat, lon)
    }
    override fun toGeoJson(slovar: MutableMap<String, Double>): String {



        val start = Point(tocka1.x.evaluate(slovar), tocka1.y.evaluate(slovar))
        val end = Point(tocka2.x.evaluate(slovar), tocka2.y.evaluate(slovar))

        val startCartesian = toCartesian(start)
        val endCartesian = toCartesian(end)

        val numberOfPoints = 100
        val coordinates = mutableListOf<List<Double>>()

        for (i in 0..numberOfPoints) {
            val t = i / numberOfPoints.toDouble()

            val x = startCartesian.first * (1 - t) + endCartesian.first * t
            val y = startCartesian.second * (1 - t) + endCartesian.second * t
            val z = startCartesian.third * (1 - t) + endCartesian.third * t

            val length = sqrt(x * x + y * y + z * z)
            val normX = x / length
            val normY = y / length
            val normZ = z / length

            val offset = ukrivljensko.evaluate(slovar) * sin(t * PI) * 1
            val offsetX = normX + offset * normY
            val offsetY = normY + offset * normZ
            val offsetZ = normZ + offset * normX

            val offsetLength = sqrt(offsetX * offsetX + offsetY * offsetY + offsetZ * offsetZ)
            val finalX = offsetX / offsetLength
            val finalY = offsetY / offsetLength
            val finalZ = offsetZ / offsetLength

            val finalPoint = toLatLon(Triple(finalX, finalY, finalZ))
            coordinates.add(listOf(finalPoint.lon, finalPoint.lat))
        }

        val geoJson = """
            {
                "type": "LineString",
                "coordinates": ${coordinates.map { it.toString() }}
            }
        """.trimIndent()

        return geoJson
    }
}

class Pointer(private val tocka1:Tocka) : Ukaz(){
    override fun toGeoJson(slovar: MutableMap<String, Double>): String {

        return """
        {
            "type": "Point",
            "coordinates": 
                [${tocka1.x.evaluate(slovar)}, ${tocka1.y.evaluate(slovar)}]
            
        }
            
        """.trimIndent()

    }
}

class Ponovi(private val pogoj: BooleanIzraz, private val ukazi: List<Ukaz>) : Ukaz() {
    override fun toGeoJson(slovar: MutableMap<String, Double>): String {
        var returnString= ""
        while(pogoj.evaluate(slovar)){
            for(ukaz in ukazi){
                var appendString = ukaz.toGeoJson(slovar)
                if(appendString != "")
                    appendString += ","
                returnString += appendString
            }
        }
        returnString = returnString.substring(0, returnString.length - 1)

        return returnString
    }
}

abstract class Izraz : IzrazSentence

class Plus(private val izraz1: Izraz, private val izraz2: Izraz) : Izraz() {
    override fun evaluate(slovar: MutableMap<String, Double>): Double {
        return izraz1.evaluate(slovar) + izraz2.evaluate(slovar)
    }
}

class Minus(private val izraz1: Izraz, private val izraz2: Izraz) : Izraz() {
    override fun evaluate(slovar: MutableMap<String, Double>): Double {
        return izraz1.evaluate(slovar) - izraz2.evaluate(slovar)
    }
}

class Krat(private val izraz1: Izraz, private val izraz2: Izraz) : Izraz() {
    override fun evaluate(slovar: MutableMap<String, Double>): Double {
        return izraz1.evaluate(slovar) * izraz2.evaluate(slovar)
    }
}

class Deljenje(private val izraz1: Izraz, private val izraz2: Izraz) : Izraz() {
    override fun evaluate(slovar: MutableMap<String, Double>): Double {
        return izraz1.evaluate(slovar) / izraz2.evaluate(slovar)
    }
}

class SpremenljikaIzraz(private val imeSpremenljivke: ImeSpremenljivke) : Izraz() {
    override fun evaluate(slovar: MutableMap<String, Double>): Double {
        return slovar[imeSpremenljivke.besedilo] ?: 0.0
    }
}

class SteviloIzraz(private val stevilo: Double) : Izraz() {
    override fun evaluate(slovar: MutableMap<String, Double>): Double {
        return stevilo
    }
}

class BooleanIzraz(izraz1: Izraz, operator :Token , izraz2: Izraz ) : BooleanSentence
{
    private val izraz1 = izraz1
    private val izraz2 = izraz2
    private val operator = operator
    override fun evaluate(slovar: MutableMap<String, Double>): Boolean {
        return when(operator.symbol){
            Symbol.EQ -> izraz1.evaluate(slovar) == izraz2.evaluate(slovar)
            Symbol.NOT -> izraz1.evaluate(slovar) != izraz2.evaluate(slovar)
            Symbol.RT -> izraz1.evaluate(slovar) > izraz2.evaluate(slovar)
            Symbol.LT -> izraz1.evaluate(slovar) < izraz2.evaluate(slovar)

            else -> false
        }
    }
}
class Tocka(val x: Izraz, val y: Izraz)

class Besedilo(val besedilo: String)

fun main(args: Array<String>) {
    // Add some test cases if necessary
}
