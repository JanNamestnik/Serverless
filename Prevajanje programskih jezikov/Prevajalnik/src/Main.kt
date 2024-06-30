import java.io.File

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val Scanner = Scanner(ForForeachFFFAutomaton, File("C:\\Users\\metod\\Desktop\\Faks\\4.semester\\ProjektniPraktikum\\Serverless\\Prevajanje programskih jezikov\\Prevajalnik\\src\\examples\\example10.txt").inputStream())

    val parser = Parser(Scanner)
    val zemlejvid= parser.parse()
    var slovar = mutableMapOf<String,Double>()
    print(zemlejvid.toGeoJson(slovar))

    //val dictionary = mutableMapOf<String, Double>()
}