package io.github.takusan23.composeiconshop.Tool

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.outlined.Android
import androidx.compose.material.icons.rounded.Android
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * リフレクションを利用して、名前からアイコンを取得して見る
 * */
object IconLoad {

    /** デフォルトアイコン */
    const val ICON_DEFAULT = "Default"

    /** アウトラインアイコン */
    const val ICON_OUTLINED = "Outlined"

    /** 丸みを帯びたアイコン */
    const val ICON_ROUNDED = "Rounded"

    /** 二色で構成されたアイコン */
    const val ICON_TWO_TONE = "TwoTone"

    /** シャープなアイコン */
    const val ICON_SHARP = "Sharp"

    /**
     * アイコンの名前からImageVectorを取得する。
     *
     * リフレクション
     * @param iconName アイコンの名前。パスカルケース（AddCircleみたいな感じ）
     * @param iconType アイコンの形状。アウトラインとか。[ICON_DEFAULT]とかが入る
     * */
    fun getIconFromName(iconName: String, iconType: String = ICON_DEFAULT): ImageVector? {
        try {

            // 形状
            val (iconTypePackageName, iconClassType) = when (iconType) {
                ICON_OUTLINED -> Pair("outlined", Icons.Outlined)
                ICON_ROUNDED -> Pair("rounded", Icons.Rounded)
                ICON_TWO_TONE -> Pair("twotone", Icons.TwoTone)
                ICON_SHARP -> Pair("sharp", Icons.Sharp)
                else -> Pair("filled", Icons.Filled)
            }

            /**
             * 文字列からクラスを生成。
             * androidx.compose.material.icons.filled + パスカルケースになったアイコン名 + kt
             * 例：androidx.compose.material.icons.filled.AndroidKt
             * */
            val iconClass =
                Class.forName("androidx.compose.material.icons.${iconTypePackageName}.${iconName}Kt")

            /**
             * アイコン取得のStaticメソッドを取得。ここらへんはKotlin -> Java 変換を駆使した
             *
             * get + アイコン名　
             * 例；getAndroid
             * */
            val getIconFunction = iconClass.getMethod("get${iconName}", iconClassType::class.java)
            // Staticメソッドを叩く。
            return getIconFunction.invoke(null, iconClassType) as ImageVector
        } catch (e: ClassNotFoundException) {
            // なんかクラスがない時がある
            e.printStackTrace()
            return null
        }
    }
}