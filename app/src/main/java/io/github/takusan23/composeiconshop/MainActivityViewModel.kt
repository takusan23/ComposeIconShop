package io.github.takusan23.composeiconshop

import android.app.Application
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.github.takusan23.composeiconshop.Navigation.NavigationData
import io.github.takusan23.composeiconshop.Navigation.NavigationNames
import io.github.takusan23.composeiconshop.Tool.IconLoad
import io.github.takusan23.composeiconshop.Tool.JSONLoad
import kotlinx.coroutines.launch

/**
 * [io.github.takusan23.composeiconshop.MainActivity]で使うViewModel。画面遷移で使う
 *
 * 画面遷移は[NavigationData]を参照
 * */
class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    /** Context */
    private val context = application.applicationContext

    /** 画面操作をするLiveData。nullで最初のページ */
    val screenLiveData = MutableLiveData(NavigationData(NavigationNames.HOME))

    /** 画面遷移前にスクロールしていた位置 */
    val homeScreenScrollPos = MutableLiveData(0)

    /** 画面遷移前にスクロールしていた位置の微調整で使う */
    val homeScreenScrollOffset = MutableLiveData(0)

    /**
     * GoTo家キャンペーン
     *
     * [screenLiveData]へ[NavigationNames.HOME]を送信します
     * */
    fun gotoHome() {
        screenLiveData.postValue(NavigationData(NavigationNames.HOME))
    }

}