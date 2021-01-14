package io.github.takusan23.composeiconshop.Tool

import android.content.Context
import org.json.JSONArray

object JSONLoad {

    /**
     * assetsにあるicon.jsonを読み込む
     *
     * @param context コンテキスト
     * */
    fun load(context: Context): ArrayList<String> {
        // 結果が入る配列
        val iconNameArray = arrayListOf<String>()
        val jsonFile = context.assets.open("icon.json")
        val jsonString = jsonFile.bufferedReader().readText()
        // JSONパース
        val jsonArray = JSONArray(jsonString)
        for (i in 0 until jsonArray.length()){
            val iconName = jsonArray.getString(i)
            iconNameArray.add(iconName)
        }
        return iconNameArray
    }
}