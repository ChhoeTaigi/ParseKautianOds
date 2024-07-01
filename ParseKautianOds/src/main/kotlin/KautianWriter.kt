import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import data.Su

class KautianWriter {
    fun write(suMap: Map<String, Su>) {
        val csvList = ArrayList<ArrayList<String>>()

        val csvHeaderRowList = arrayListOf(
            "詞目id",
            "詞目類型",
            "漢字",
            "羅馬字",
            "分類",
            "義項merged",
            "例句merged")
        csvList.add(csvHeaderRowList)

        suMap.forEach { (subakId, su) ->
            val csvRowList = arrayListOf(
                su.subakId,
                su.subakLuiheng,
                su.hanji,
                su.lomaji,
                su.hunlui,
                su.gihangMergedString,
                su.lekuMergedString)
            csvList.add(csvRowList)
        }

        val writer = csvWriter {
            quote {
                char = '\"'
            }
        }
        writer.writeAll(csvList, "output.csv", append = false)
    }
}
