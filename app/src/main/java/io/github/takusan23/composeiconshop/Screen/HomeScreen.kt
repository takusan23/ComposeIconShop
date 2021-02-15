package io.github.takusan23.composeiconshop.Screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.takusan23.composeiconshop.MainActivityViewModel
import io.github.takusan23.composeiconshop.Navigation.IconDetailNavigationData
import io.github.takusan23.composeiconshop.Navigation.NavigationNames
import io.github.takusan23.composeiconshop.Tool.IconLoad
import io.github.takusan23.composeiconshop.Tool.JSONLoad
import io.github.takusan23.composeiconshop.Compose.ModalSetting
import kotlinx.coroutines.launch
import java.util.*

/**
 * アイコン一覧表示画面
 *
 * @param viewModel 画面遷移を行うため
 * @param iconSearch 検索機能を利用する場合は検索ワードを入れてね
 * @param [ModalSetting.CLICK_DEFAULT]などを入れるとアイコンの種類を変更できます。
 * */
@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    viewModel: MainActivityViewModel,
    iconSearch: String,
    iconType: Int = ModalSetting.CLICK_DEFAULT,
    column: Int = 4,
) {
    // スクロール位置をLiveDataで受け取る
    val scrollPos = viewModel.homeScreenScrollPos.observeAsState(initial = 0)
    // スクロール位置。Offset
    val offsetPos = viewModel.homeScreenScrollOffset.observeAsState(initial = 0)

    // 検索機能
    val iconNameList = JSONLoad.load(LocalContext.current)
        .filter { name ->
            name.contains(iconSearch) || name.toLowerCase(Locale.ROOT).contains(iconSearch)
        }
    // 一覧表示
    GridIconList(
        iconNameList = iconNameList,
        scroll = scrollPos.value,
        offset = offsetPos.value,
        iconType = iconType,
        gridSize = column,
        onIconClick = { iconName, pos, offset ->
            // 画面遷移前の位置を保存
            viewModel.homeScreenScrollPos.postValue(pos)
            // 画面遷移前の位置を保存
            viewModel.homeScreenScrollOffset.postValue(offset)

            // 画面遷移
            viewModel.screenLiveData.postValue(
                IconDetailNavigationData(
                    NavigationNames.DETAIL,
                    iconName
                )
            )
        }
    )
}

/**
 * アイコン一覧を表示する
 *
 * @param iconNameList アイコンの名前
 * @param onIconClick アイコン押したとき
 * @param gridSize 横に何個並べるか。デフォ4
 * @param scroll スクロールさせる場合は何行目かを入れてね
 * @param [ModalSetting.CLICK_DEFAULT]などを入れるとアイコンの種類を変更できます。
 * */
@ExperimentalFoundationApi
@Composable
fun GridIconList(
    iconNameList: List<String>,
    onIconClick: (String, Int, Int) -> Unit,
    gridSize: Int = 4,
    scroll: Int = 0,
    offset: Int = 0,
    iconType: Int = 0,
) {
    // コルーチン
    val scope = rememberCoroutineScope()
    // スクロールさせる
    val state = rememberLazyListState()
    scope.launch {
        state.snapToItemIndex(scroll, offset)
    }

    // スクロールできるやつ。テーブルレイアウト
    LazyVerticalGrid(
        cells = GridCells.Fixed(gridSize),
        state = state,
        content = {
            itemsIndexed(iconNameList) { index, name ->
                GridItem(
                    iconName = name,
                    iconType = iconType,
                    onIconClick = {
                        // アイコン名と現在のスクロール位置を引数の関数に渡す
                        onIconClick(
                            name,
                            state.firstVisibleItemIndex,
                            state.firstVisibleItemScrollOffset
                        )
                    }
                )
            }
        }
    )
}


/**
 * アイコン一つ一つのレイアウト
 *
 * @param iconName アイコン名
 * @param onIconClick アイコン押したとき
 * @param [ModalSetting.CLICK_DEFAULT]などを入れるとアイコンの種類を変更できます。
 * */
@Composable
fun GridItem(
    iconName: String,
    onIconClick: (String) -> Unit,
    iconType: Int = 0,
) {
    // デフォルトかアウトラインか
    val type = when (iconType) {
        ModalSetting.CLICK_OUTLINE -> IconLoad.ICON_OUTLINED
        ModalSetting.CLICK_ROUNDED -> IconLoad.ICON_ROUNDED
        ModalSetting.CLICK_SHARP -> IconLoad.ICON_SHARP
        ModalSetting.CLICK_TWO_TONE -> IconLoad.ICON_TWO_TONE
        else -> IconLoad.ICON_DEFAULT
    }

    val icon = IconLoad.getIconFromName(iconName, type)

    if (icon != null) {
        TextButton(onClick = { onIconClick(iconName) }) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = icon,
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    contentDescription = "Icon"
                )
                Text(
                    text = iconName,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}