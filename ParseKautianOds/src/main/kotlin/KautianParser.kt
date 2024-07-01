import data.*
import sheet.*

class KautianParser {
    private var suMutableMap: MutableMap<String, Su> = mutableMapOf()

    // return map<subakId, Su>
    fun parse(kautianSheet: KautianSheet): Map<String, Su> {
        suMutableMap.clear()

        parseSubakSheet(kautianSheet.subakSheet)
        parseGihangSheet(kautianSheet.gihangSheet, kautianSheet.lekuSheet)
        parseIuliamchoSheet(kautianSheet.iuliamchoSheet)
        parseHapimliamchoSheet(kautianSheet.hapimliamchoSheet)
        parseSiokliamchoSheet(kautianSheet.siokliamchoSheet)

        println("KautianParser Oân-sêng!")

        return suMutableMap.toMap()
    }

    private fun parseSubakSheet(sheet: ArrayList<SubakRow>) {
        for (i in 0..<sheet.size) {
            if (sheet[i].subakId.isEmpty()) {
                continue
            }

            val su = Su()
            su.subakId = sheet[i].subakId
            su.subakLuiheng = sheet[i].subakLuiheng
            su.hanji = sheet[i].hanji.replace("（", "(").replace("）", ")")
            su.lomaji = sheet[i].lomaji.replace("（", "(").replace("）", ")")
            su.hunlui = sheet[i].hunlui

            suMutableMap[su.subakId] = su
        }
    }

    private fun parseGihangSheet(gihangSheet: ArrayList<GihangRow>, lekuSheet: java.util.ArrayList<LekuRow>) {
        // Map<gihangId, ArrayList<Leku>>
        val gihangLekuMap: MutableMap<String, ArrayList<Leku>> = mutableMapOf()

        for (i in 0..<lekuSheet.size) {
            val leku = Leku()
            leku.gihangId = lekuSheet[i].gihangId
            leku.lekuId = lekuSheet[i].lekuId
            leku.hanji = lekuSheet[i].hanji
            leku.lomaji = lekuSheet[i].lomaji
            leku.hoagi = lekuSheet[i].hoagi

            if (gihangLekuMap[leku.gihangId] == null) {
                gihangLekuMap[leku.gihangId] = ArrayList()
            }
            gihangLekuMap[leku.gihangId]!!.add(leku)
        }

        for (i in 0..<gihangSheet.size) {
            val gihang = Gihang()
            gihang.subakId = gihangSheet[i].subakId
            gihang.gihangId = gihangSheet[i].gihangId
            gihang.suseng = gihangSheet[i].suseng
            gihang.kaisoeh = gihangSheet[i].kaisoeh

            if (gihangLekuMap[gihang.gihangId] != null) {
                gihang.lekuArrayList.addAll(gihangLekuMap[gihang.gihangId]!!)
            }

            suMutableMap[gihang.subakId]!!.gihangMap[gihang.gihangId] = gihang
        }

        suMutableMap.forEach { (subakId, su) ->
            if (su.gihangMap.isEmpty()) {
                return@forEach
            }

            val gihangMergeStringBuilder = StringBuilder()
            val lekuMergeStringBuilder = StringBuilder()
            var gihangIndex = 1
            su.gihangMap.forEach { (gihangId, gihang) ->
                var susengString = ""
                if (gihang.suseng.isNotEmpty()) {
                    susengString = "【${gihang.suseng}】"
                }
                gihangMergeStringBuilder.append("($gihangIndex) $susengString${gihang.kaisoeh}")
                if (gihangIndex < su.gihangMap.size) {
                    gihangMergeStringBuilder.append("\n")
                }

                if (gihang.lekuArrayList.isNotEmpty()) {
                    val lekuStringBuilder = StringBuilder()
                    for (i in 0..<gihang.lekuArrayList.size) {
                        val leku = gihang.lekuArrayList[i]
                        lekuStringBuilder.append("${leku.hanji} ${leku.lomaji} 『${leku.hoagi}』")
                        if (i < gihang.lekuArrayList.size - 1) {
                            lekuStringBuilder.append("；")
                        }
                    }

                    lekuMergeStringBuilder.append("($gihangIndex) $lekuStringBuilder")
                    if (gihangIndex < su.gihangMap.size) {
                        lekuMergeStringBuilder.append("\n")
                    }
                }

                gihangIndex++
            }

            su.gihangMergedString = gihangMergeStringBuilder.toString()
            su.lekuMergedString = lekuMergeStringBuilder.toString()
        }
    }

    private fun parseIuliamchoSheet(sheet: ArrayList<IuliamchoRow>) {
        for (i in 0..<sheet.size) {
            val iuliamcho = Iuliamcho()
            iuliamcho.subakId = sheet[i].subakId
            iuliamcho.hanji = sheet[i].hanji.replace("（", "(").replace("）", ")")
            iuliamcho.lomaji = sheet[i].lomaji.replace("（", "(").replace("）", ")")

            val su = suMutableMap[iuliamcho.subakId]
            if (su == null) {
                println("parseIuliamchoSheet(): su == null, ${iuliamcho.subakId}, ${iuliamcho.hanji}, ${iuliamcho.lomaji}")
                continue
            }

            su.iuliamchoArrayList.add(iuliamcho)

            su.hanji = su.hanji.replace("/", "(又唸作)/")
            su.lomaji = su.lomaji.replace("/", "(又唸作)/")
            su.hanji += "/${iuliamcho.hanji}(又唸作)"
            su.lomaji += "/${iuliamcho.lomaji}(又唸作)"
        }
    }

    private fun parseHapimliamchoSheet(sheet: ArrayList<HapimliamchoRow>) {
        for (i in 0..<sheet.size) {
            val hapimliamcho = Hapimliamcho()
            hapimliamcho.subakId = sheet[i].subakId
            hapimliamcho.hanji = sheet[i].hanji.replace("（", "(").replace("）", ")")
            hapimliamcho.lomaji = sheet[i].lomaji.replace("（", "(").replace("）", ")")

            val su = suMutableMap[hapimliamcho.subakId]
            if (su == null) {
                println("parseHapimliamchoSheet(): su == null, ${hapimliamcho.subakId}, ${hapimliamcho.hanji}, ${hapimliamcho.lomaji}")
                continue
            }

            su.hapimliamchoArrayList.add(hapimliamcho)

            su.hanji = su.hanji.replace("/", "(合音唸作)/")
            su.lomaji = su.lomaji.replace("/", "(合音唸作)/")
            su.hanji += "/${hapimliamcho.hanji}(合音唸作)"
            su.lomaji += "/${hapimliamcho.lomaji}(合音唸作)"
        }
    }

    private fun parseSiokliamchoSheet(sheet: ArrayList<SiokliamchoRow>) {
        for (i in 0..<sheet.size) {
            val siokliamcho = Siokliamcho()
            siokliamcho.subakId = sheet[i].subakId
            siokliamcho.hanji = sheet[i].hanji.replace("（", "(").replace("）", ")")
            siokliamcho.lomaji = sheet[i].lomaji.replace("（", "(").replace("）", ")")

            val su = suMutableMap[siokliamcho.subakId]
            if (su == null) {
                println("parseSiokliamchoSheet(): su == null, ${siokliamcho.subakId}, ${siokliamcho.hanji}, ${siokliamcho.lomaji}")
                continue
            }

            su.siokliamchoArrayList.add(siokliamcho)

            su.hanji = su.hanji.replace("/", "(俗唸作)/")
            su.lomaji = su.lomaji.replace("/", "(俗唸作)/")
            su.hanji += "/${siokliamcho.hanji}(俗唸作)"
            su.lomaji += "/${siokliamcho.lomaji}(俗唸作)"
        }
    }
}