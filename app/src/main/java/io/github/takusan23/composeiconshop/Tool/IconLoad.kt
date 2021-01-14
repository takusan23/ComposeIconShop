package io.github.takusan23.composeiconshop.Tool

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * リフレクションを利用して、名前からアイコンを取得して見る
 * */
object IconLoad {

    /**
     * アイコンの名前からImageVectorを取得する。
     *
     * リフレクション
     * @param iconName アイコンの名前。パスカルケース（AddCircleみたいな感じ）
     * */
    fun getIconFromName(iconName: String): ImageVector? {
        try {
            /**
             * 文字列からクラスを生成。
             * androidx.compose.material.icons.filled + パスカルケースになったアイコン名 + kt
             * 例：androidx.compose.material.icons.filled.AndroidKt
             * */
            val iconClass = Class.forName("androidx.compose.material.icons.filled.${iconName}Kt")

            /**
             * アイコン取得のStaticメソッドを取得。ここらへんはKotlin -> Java 変換を駆使した
             *
             * get + アイコン名　
             * 例；getAndroid
             * */
            val getIconFunction = iconClass.getMethod("get${iconName}", Icons.Filled::class.java)
            // Staticメソッドを叩く。
            return getIconFunction.invoke(null, Icons.Default) as ImageVector
        } catch (e: ClassNotFoundException) {
            // なんかクラスがない時がある
            e.printStackTrace()
            return null
        }
    }
}