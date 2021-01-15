package io.github.takusan23.composeiconshop

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Android
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import io.github.takusan23.composeiconshop.Navigation.IconDetailNavigationData
import io.github.takusan23.composeiconshop.Navigation.NavigationNames
import io.github.takusan23.composeiconshop.Screen.DetailScreen
import io.github.takusan23.composeiconshop.Screen.HomeScreen
import io.github.takusan23.composeiconshop.UI.IconSearchAppBar
import io.github.takusan23.composeiconshop.ui.theme.ComposeIconShopTheme

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainActivityViewModel>()

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Jetpack Composeでレイアウト作成
        setContent {

            // 画面遷移
            val screenLiveData = viewModel.screenLiveData.observeAsState()
            // 検索窓を表示させるか
            val isShowSearchBox = remember { mutableStateOf(false) }
            // 検索ワード
            val searchWord = remember { mutableStateOf("") }

            ComposeIconShopTheme {
                Scaffold(
                    topBar = {
                        // タイトルバー
                        IconSearchAppBar(
                            titleText = when (screenLiveData.value?.screenName) {
                                NavigationNames.DETAIL -> (screenLiveData.value!! as IconDetailNavigationData).iconName
                                else -> stringResource(id = R.string.app_name)
                            },
                            // HomeScreen以外で検索欄出してほしくない
                            onClickSearchIcon = {
                                isShowSearchBox.value = !isShowSearchBox.value
                                searchWord.value = ""
                            },
                            isShowSearchBox = (isShowSearchBox.value && screenLiveData.value?.screenName == NavigationNames.HOME),
                            isShowSearchIcon = (screenLiveData.value?.screenName == NavigationNames.HOME),
                            searchText = searchWord.value,
                            onChangeSearchText = { searchWord.value = it },
                            isShowBackIcon = screenLiveData.value?.screenName != NavigationNames.HOME, // HomeScreen以外で表示
                            onClickBackIcon = { viewModel.gotoHome() }
                        )
                    }
                ) {
                    when (screenLiveData.value?.screenName) {
                        NavigationNames.HOME -> HomeScreen(
                            viewModel = viewModel,
                            iconSearch = searchWord.value
                        )
                        NavigationNames.DETAIL -> DetailScreen(iconName = (screenLiveData.value!! as IconDetailNavigationData).iconName)
                    }
                }
            }
        }

        // バックキー
        onBackPressedDispatcher.addCallback(this) {
            // もどる
            viewModel.gotoHome()
        }
    }

}

