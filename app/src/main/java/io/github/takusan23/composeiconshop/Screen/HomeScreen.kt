package io.github.takusan23.composeiconshop.Screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
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

/**
 * アイコン一覧表示画面
 *
 * @param viewModel 画面遷移を行うため
 * */
@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    viewModel: MainActivityViewModel
) {
    val context = AmbientContext.current
    // アイコン一覧読み出し
    val iconNameList = JSONLoad.load(context)
    // 一覧表示
    GridIconList(
        iconNameList,
        onIconClick = {iconName->
            viewModel.screenLiveData.postValue(IconDetailNavigationData(NavigationNames.DETAIL,iconName))
        }
    )
}

/**
 * アイコン一覧を表示する
 *
 * @param iconNameList アイコンの名前
 * @param onIconClick アイコン押したとき
 * */
@ExperimentalFoundationApi
@Composable
fun GridIconList(iconNameList: List<String>, onIconClick: (String) -> Unit) {
    // スクロールできるやつ。テーブルレイアウト
    LazyVerticalGrid(cells = GridCells.Fixed(4), content = {
        this.itemsIndexed(iconNameList) { index, name ->
            GridItem(
                iconName = name,
                onIconClick
            )
        }
    })
}


/**
 * アイコン一つ一つのレイアウト
 *
 * @param iconName アイコン名
 * @param onIconClick アイコン押したとき
 * */
@Composable
fun GridItem(
    iconName:String,
    onIconClick: (String) -> Unit
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