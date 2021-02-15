package io.github.takusan23.composeiconshop

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.takusan23.composeiconshop.Navigation.IconDetailNavigationData
import io.github.takusan23.composeiconshop.Navigation.NavigationNames
import io.github.takusan23.composeiconshop.Screen.DetailScreen
import io.github.takusan23.composeiconshop.Screen.HomeScreen
import io.github.takusan23.composeiconshop.Compose.IconSearchAppBar
import io.github.takusan23.composeiconshop.Compose.ModalSetting
import io.github.takusan23.composeiconshop.Compose.ModalSettingSheetContent
import io.github.takusan23.composeiconshop.Compose.theme.ComposeIconShopTheme

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainActivityViewModel>()

    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Jetpack Composeでレイアウト作成
        this.setContent(null) {

            // 画面遷移
            val screenLiveData = viewModel.screenLiveData.observeAsState()
            // 検索窓を表示させるか
            val isShowSearchBox = remember { mutableStateOf(false) }
            // 検索ワード
            val searchWord = remember { mutableStateOf("") }
            // モーダルシード出すか
            val modalState =
                rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
            // アイコンの種類変更
            val iconType = remember { mutableStateOf(ModalSetting.CLICK_DEFAULT) }
            // 何個並べるか。でふぉ４
            val columnValue = remember { mutableStateOf(4) }

            ComposeIconShopTheme {
                ModalBottomSheetLayout(
                    sheetState = modalState,
                    sheetShape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp),
                    sheetContent = {
                        // モーダルに表示させる内容
                        ModalSettingSheetContent(
                            onButtonClick = { type -> iconType.value = type },
                            column = columnValue.value,
                            onChangeColumn = { col -> columnValue.value = col }
                        )
                    }
                ) {
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
                                onClickBackIcon = { viewModel.gotoHome() },
                                onClickSettingIcon = { modalState.show() }
                            )
                        }
                    ) {
                        when (screenLiveData.value?.screenName) {
                            NavigationNames.HOME -> HomeScreen(
                                viewModel = viewModel,
                                iconSearch = searchWord.value,
                                iconType = iconType.value,
                                column = columnValue.value,
                            )
                            NavigationNames.DETAIL -> DetailScreen(iconName = (screenLiveData.value!! as IconDetailNavigationData).iconName)
                        }
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

