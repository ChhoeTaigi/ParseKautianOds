import com.github.miachm.sods.Sheet
import com.github.miachm.sods.SpreadSheet
import java.io.File

fun main(args: Array<String> ) {
    val f = File("kautian20240529.ods")
    KautianParser().parse(f)
}


