package io.github.takusan23.composeiconshop

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.github.takusan23.composeiconshop.Navigation.NavigationData
import io.github.takusan23.composeiconshop.Navigation.NavigationNames

/**
 * [io.github.takusan23.composeiconshop.MainActivity]で使うViewModel。画面遷移で使う
 *
 * 画面遷移は[NavigationData]を参照
 * */
class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    /** 画面操作をするLiveData。nullで最初のページ */
    val screenLiveData = MutableLiveData(NavigationData(NavigationNames.HOME))

}