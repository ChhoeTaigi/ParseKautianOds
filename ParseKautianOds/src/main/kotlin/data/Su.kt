package data

object Su {
    // 詞目
    lateinit var subakId: String
    lateinit var subakLuiheng: String
    lateinit var hanji: String
    lateinit var lomaji: String
    lateinit var hunlui: String

    // 義項
    lateinit var gihangMergedString: String

    fun setSubak(subakId: String, subakLuiheng: String, hanji: String, lomaji: String, hunlui: String) {
        Su.subakId = subakId
        Su.subakLuiheng = subakLuiheng
        Su.hanji = hanji
        Su.lomaji = lomaji
        Su.hunlui = hunlui
    }

    fun setGihang(gihang: String) {
        Su.gihangMergedString = gihang
    }
}