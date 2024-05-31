import com.github.miachm.sods.Sheet
import com.github.miachm.sods.SpreadSheet
import data.Su
import java.io.File

class KautianParser {
    private var suMutableMap: MutableMap<String, Su> = mutableMapOf()

    // return map<subakId, Su>
    fun parse(f: File): Map<String, Su> {
        suMutableMap.clear()

        val spreadSheet = SpreadSheet(f)
        parseSubak(spreadSheet.sheets[0])
        parseGihang(spreadSheet.sheets[1])

        return suMutableMap.toMap()
    }

    private fun parseSubak(sheet: Sheet) {
        printSheetInfo(sheet)

        for (i in 1..<sheet.maxRows) {
            if (sheet.getRange(i, 0).value.toString().isEmpty()) {
                break
            }

            val subakId = (sheet.getRange(i, 0).value as Double).toInt().toString()
            val subakLuiheng = sheet.getRange(i, 1).value?.toString() ?: ""
            val hanji = sheet.getRange(i, 2).value.toString()
            val lomaji = sheet.getRange(i, 3).value?.toString() ?: ""
            val hunlui = sheet.getRange(i, 4).value?.toString() ?: ""

            println("subakId: $subakId, hanji: $hanji, lomaji: $lomaji")

            val currentSu = Su
            currentSu.setSubak(
                subakId,
                subakLuiheng,
                hanji,
                lomaji,
                hunlui
            )

            suMutableMap[currentSu.subakId] = currentSu
        }
    }

    private fun parseGihang(sheet: Sheet) {
        printSheetInfo(sheet)

        var currentIndex = 1
        while (currentIndex < sheet.maxRows) {
            val stringBuilder = StringBuilder()
            val currentSubakId: String = (sheet.getRange(currentIndex, 0).value as Double).toInt().toString()
            val finalGihangIndex = getFinalGihangIndex(sheet, currentIndex)
            println("currentSubakId: $currentSubakId, currentIndex: $currentIndex, finalGihangIndex: $finalGihangIndex")

            if (finalGihangIndex == currentIndex) {
                val suseng = sheet.getRange(currentIndex, 2).value?.toString() ?: ""
                val kaisoeh = sheet.getRange(currentIndex, 3).value?.toString() ?: ""

                if (suseng.isEmpty()) {
                    stringBuilder.append(kaisoeh)
                } else {
                    stringBuilder.append("【$suseng】$kaisoeh")
                }
            } else {
                val firstGihangId = (sheet.getRange(currentIndex, 1).value as Double).toInt()
                for (i in currentIndex..<finalGihangIndex + 1) {
                    val gihangId = (sheet.getRange(i, 1).value as Double).toInt()
                    val suseng = sheet.getRange(i, 2).value?.toString() ?: ""
                    val kaisoeh = sheet.getRange(i, 3).value?.toString() ?: ""

                    val currentGihangNumber = gihangId - firstGihangId + 1

                    if (suseng.isEmpty()) {
                        stringBuilder.append("$currentGihangNumber. $kaisoeh")
                    } else {
                        stringBuilder.append("$currentGihangNumber.【$suseng】$kaisoeh")
                    }
                    if (i < finalGihangIndex) {
                        stringBuilder.append("\n")
                    }
                }
            }

            val currentSu = suMutableMap[currentSubakId]
            currentSu!!.setGihang(stringBuilder.toString())

            println(stringBuilder.toString())

            currentIndex = finalGihangIndex + 1
        }
    }

    private fun getFinalGihangIndex(sheet: Sheet, currentIndex: Int): Int {
        var foundIndex = currentIndex

        val currentGihangSubokId = (sheet.getRange(currentIndex, 0).value as Double).toInt().toString()
        for (i in currentIndex + 1..<sheet.maxRows) {
            val nextGihangSubokId = (sheet.getRange(i, 0).value as Double).toInt().toString()
            if (nextGihangSubokId == currentGihangSubokId) {
                foundIndex = i
            }
        }

        return foundIndex
    }

    private fun printSheetInfo(sheet: Sheet) {
        println("----------------------------------------")
        println("Sheet name: ${sheet.name}")
        println("Sheet row amount: ${sheet.maxRows}")
        println("Sheet column amount: ${sheet.maxColumns}")
        println("----------------------------------------")
    }
}