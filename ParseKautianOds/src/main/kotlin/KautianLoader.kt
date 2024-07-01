import com.github.miachm.sods.Sheet
import com.github.miachm.sods.SpreadSheet
import sheet.*
import java.io.File

class KautianLoader {
    fun load(f: File): KautianSheet {
        val spreadSheet = SpreadSheet(f)

        val subakSheet = loadSubakSheet(spreadSheet.sheets[0])
        val gihangSheet = loadGihangSheet(spreadSheet.sheets[1])
        val lekuSheet = loadLekuSheet(spreadSheet.sheets[2])
        val iuliamchoSheet = loadIuliamchoSheet(spreadSheet.sheets[3])
        val hapimliamchoSheet = loadHapimliamchoSheet(spreadSheet.sheets[4])
        val siokliamchoSheet = loadSiokliamchoSheet(spreadSheet.sheets[5])

        val kautianSheet =
            KautianSheet(subakSheet, gihangSheet, lekuSheet, iuliamchoSheet, hapimliamchoSheet, siokliamchoSheet)

        println("KautianLoader Oân-sêng!")

        return kautianSheet
    }

    private fun loadSubakSheet(sheet: Sheet): ArrayList<SubakRow> {
        printSheetInfo(sheet)

        val arrayList = ArrayList<SubakRow>()

        for (i in 1..<sheet.maxRows) {
            val subakId = (sheet.getRange(i, 0).value as Double).toInt().toString()
            val subakLuiheng = sheet.getRange(i, 1).value?.toString() ?: ""
            val hanji = sheet.getRange(i, 2).value?.toString() ?: ""
            val lomaji = sheet.getRange(i, 3).value?.toString() ?: ""
            val hunlui = sheet.getRange(i, 4).value?.toString() ?: ""

            val row = SubakRow(subakId, subakLuiheng, hanji, lomaji, hunlui)
            arrayList.add(row)
        }

        return arrayList
    }

    private fun loadGihangSheet(sheet: Sheet): ArrayList<GihangRow> {
        printSheetInfo(sheet)

        val arrayList = ArrayList<GihangRow>()

        for (i in 1..<sheet.maxRows) {
            val subakId = (sheet.getRange(i, 0).value as Double).toInt().toString()
            val gihangId = (sheet.getRange(i, 1).value as Double).toInt().toString()
            val suseng = sheet.getRange(i, 2).value?.toString() ?: ""
            val kaisoeh = sheet.getRange(i, 3).value?.toString() ?: ""

            val row = GihangRow(subakId, gihangId, suseng, kaisoeh)
            arrayList.add(row)
        }

        return arrayList
    }

    private fun loadLekuSheet(sheet: Sheet): ArrayList<LekuRow> {
        printSheetInfo(sheet)

        val arrayList = ArrayList<LekuRow>()

        for (i in 1..<sheet.maxRows) {
            val gihangId = (sheet.getRange(i, 0).value as Double).toInt().toString()
            val lekuId = (sheet.getRange(i, 1).value as Double).toInt().toString()
            val hanji = sheet.getRange(i, 2).value?.toString() ?: ""
            val lomaji = sheet.getRange(i, 3).value?.toString() ?: ""
            val hoagi = sheet.getRange(i, 4).value?.toString() ?: ""

            val row = LekuRow(gihangId, lekuId, hanji, lomaji, hoagi)
            arrayList.add(row)
        }

        return arrayList
    }

    private fun loadIuliamchoSheet(sheet: Sheet): ArrayList<IuliamchoRow> {
        printSheetInfo(sheet)

        val arrayList = ArrayList<IuliamchoRow>()

        for (i in 1..<sheet.maxRows) {
            val subakId = (sheet.getRange(i, 0).value as Double).toInt().toString()
            val hanji = sheet.getRange(i, 1).value?.toString() ?: ""
            val lomaji = sheet.getRange(i, 2).value?.toString() ?: ""

            val row = IuliamchoRow(subakId, hanji, lomaji)
            arrayList.add(row)
        }

        return arrayList
    }

    private fun loadHapimliamchoSheet(sheet: Sheet): ArrayList<HapimliamchoRow> {
        printSheetInfo(sheet)

        val arrayList = ArrayList<HapimliamchoRow>()

        for (i in 1..<sheet.maxRows) {
            val subakId = (sheet.getRange(i, 0).value as Double).toInt().toString()
            val hanji = sheet.getRange(i, 1).value?.toString() ?: ""
            val lomaji = sheet.getRange(i, 2).value?.toString() ?: ""

            val row = HapimliamchoRow(subakId, hanji, lomaji)
            arrayList.add(row)
        }

        return arrayList
    }

    private fun loadSiokliamchoSheet(sheet: Sheet): ArrayList<SiokliamchoRow> {
        printSheetInfo(sheet)

        val arrayList = ArrayList<SiokliamchoRow>()

        for (i in 1..<sheet.maxRows) {
            val subakId = (sheet.getRange(i, 0).value as Double).toInt().toString()
            val hanji = sheet.getRange(i, 1).value?.toString() ?: ""
            val lomaji = sheet.getRange(i, 2).value?.toString() ?: ""

            val row = SiokliamchoRow(subakId, hanji, lomaji)
            arrayList.add(row)
        }

        return arrayList
    }

    private fun printSheetInfo(sheet: Sheet) {
        println("----------------------------------------")
        println("Sheet name: ${sheet.name}")
        println("Sheet row amount: ${sheet.maxRows}")
        println("Sheet column amount: ${sheet.maxColumns}")
        println("----------------------------------------")
    }
}