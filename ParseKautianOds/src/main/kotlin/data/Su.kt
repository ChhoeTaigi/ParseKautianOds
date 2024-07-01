package data

class Su {
    // 詞目
    lateinit var subakId: String
    lateinit var subakLuiheng: String
    lateinit var hanji: String
    lateinit var lomaji: String
    lateinit var hunlui: String

    // 義項
    // Map<gihangId, Gihang>
    val gihangMap: MutableMap<String, Gihang> = mutableMapOf()
    var gihangMergedString: String = ""

    // 例句
    var lekuMergedString: String = ""

    // 又唸作
    val iuliamchoArrayList = ArrayList<Iuliamcho>()

    // 合音唸作
    val hapimliamchoArrayList = ArrayList<Hapimliamcho>()

    // 俗唸作
    val siokliamchoArrayList = ArrayList<Siokliamcho>()
}