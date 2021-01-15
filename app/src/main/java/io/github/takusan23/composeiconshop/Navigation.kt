package io.github.takusan23.composeiconshop.Navigation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData


/**
 * [io.github.takusan23.composeiconshop.Navigation.NavigationData]の第一引数で使う値
 * */
object NavigationNames {
    /** 一覧画面に遷移する場合はこれ */
    const val HOME = "home"

    /** アイコン詳細画面へ遷移する際はこれ */
    const val DETAIL = "detail"
}

/**
 * 画面遷移で使うデータクラス
 *
 * @param screenName 遷移先画面の名前
 * */
open class NavigationData(val screenName: String)

/**
 * アイコン詳細画面へ切り替える際に利用するデータクラス
 *
 * @param iconName アイコンの名前。
 * @param screenName 遷移先画面の名前
 * */
class IconDetailNavigationData(screenName: String, val iconName: String) :
    NavigationData(screenName)