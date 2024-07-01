import java.io.File

fun main(args: Array<String> ) {
    val f = File("kautian20240529.ods")
    val kautianLoader = KautianLoader()
    val kautianSheet = kautianLoader.load(f)

    val kautianParser = KautianParser()
    val suMap = kautianParser.parse(kautianSheet)

    println("suMap.size = ${suMap.size}")

    val kautianWriter = KautianWriter()
    kautianWriter.write(suMap)

    println("Oân-sêng!")
}


