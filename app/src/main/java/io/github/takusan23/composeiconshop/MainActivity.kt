package io.github.takusan23.composeiconshop

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import io.github.takusan23.composeiconshop.Navigation.IconDetailNavigationData
import io.github.takusan23.composeiconshop.Navigation.NavigationData
import io.github.takusan23.composeiconshop.Navigation.NavigationNames
import io.github.takusan23.composeiconshop.Screen.DetailScreen
import io.github.takusan23.composeiconshop.Screen.HomeScreen
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

            ComposeIconShopTheme {
                Scaffold(
                    topBar = {
                        // タイトルバー
                        TopAppBar(
                            title = {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = if (screenLiveData.value is IconDetailNavigationData) {
                                            (screenLiveData.value as IconDetailNavigationData).iconName
                                        } else {
                                            stringResource(id = R.string.app_name)
                                        },
                                        fontSize = 20.sp
                                    )
                                }
                            }
                        )
                    }
                ) {
                    when (screenLiveData.value?.screenName) {
                        NavigationNames.HOME -> HomeScreen(viewModel = viewModel)
                        NavigationNames.DETAIL -> DetailScreen(iconName = (screenLiveData.value!! as IconDetailNavigationData).iconName)
                    }
                }
            }
        }

        // バックキー
        onBackPressedDispatcher.addCallback(this) {
            // もどる
            viewModel.screenLiveData.postValue(NavigationData(NavigationNames.HOME))
        }

    }

}

