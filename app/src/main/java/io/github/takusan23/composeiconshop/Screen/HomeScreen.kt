package io.github.takusan23.composeiconshop.Screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.takusan23.composeiconshop.MainActivityViewModel
import io.github.takusan23.composeiconshop.Navigation.IconDetailNavigationData
import io.github.takusan23.composeiconshop.Navigation.NavigationNames
import io.github.takusan23.composeiconshop.Tool.IconLoad
import io.github.takusan23.composeiconshop.Tool.JSONLoad
import kotlinx.coroutines.launch
import java.util.*

/**
 * アイコン一覧表示画面
 *
 * @param viewModel 画面遷移を行うため
 * */
@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    viewModel: MainActivityViewModel,
    iconSearch: String,
) {
    // スクロール位置をLiveDataで受け取る
    val scrollPos = viewModel.homeScreenScrollPos.observeAsState(initial = 0)
    // スクロール位置。Offset
    val offsetPos = viewModel.homeScreenScrollOffset.observeAsState(initial = 0)

    // 検索機能
    val iconNameList = JSONLoad.load(AmbientContext.current)
        .filter { name ->
            name.contains(iconSearch) || name.toLowerCase(Locale.ROOT).contains(iconSearch)
        }
    // 一覧表示
    GridIconList(
        iconNameList = iconNameList,
        scroll = scrollPos.value,
        offset = offsetPos.value,
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
 * */
@ExperimentalFoundationApi
@Composable
fun GridIconList(
    iconNameList: List<String>,
    onIconClick: (String, Int, Int) -> Unit,
    gridSize: Int = 4,
    scroll: Int = 0,
    offset: Int = 0,
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
            this.itemsIndexed(iconNameList) { index, name ->
                GridItem(
                    iconName = name,
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
 * */
@Composable
fun GridItem(
    iconName: String,
    onIconClick: (String) -> Unit,
) {
    val icon = IconLoad.getIconFromName(iconName)

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
                        .height(50.dp)
                )
                Text(
                    text = iconName,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}