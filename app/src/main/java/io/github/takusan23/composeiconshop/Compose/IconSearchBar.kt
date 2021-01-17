package io.github.takusan23.composeiconshop.Compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AppSettingsAlt
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import io.github.takusan23.composeiconshop.R

/**
 * 検索窓付きのAppBar
 * @param titleText タイトル名
 * */
@Composable
fun IconSearchAppBar(
    titleText: String = stringResource(id = R.string.app_name),
    onClickSearchIcon: () -> Unit,
    isShowSearchBox: Boolean,
    isShowSearchIcon: Boolean,
    searchText: String,
    onChangeSearchText: (String) -> Unit,
    isShowBackIcon: Boolean = false,
    onClickBackIcon: () -> Unit = {},
    onClickSettingIcon:()->Unit = {},
) {
    TopAppBar(
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (isShowSearchBox) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = searchText,
                        onValueChange = { onChangeSearchText(it) },
                    )
                } else {
                    Text(
                        text = titleText,
                        fontSize = 20.sp
                    )
                }
            }
        },
        actions = {
            // 設定
            IconButton(onClick = { onClickSettingIcon() }) {
                Icon(imageVector = Icons.Outlined.AppSettingsAlt)
            }
            // 検索
            if (isShowSearchIcon) {
                IconButton(onClick = { onClickSearchIcon() }) {
                    if (isShowSearchBox) {
                        // クリアボタン
                        Icon(imageVector = Icons.Outlined.Clear)
                    } else {
                        // 虫眼鏡
                        Icon(imageVector = Icons.Outlined.Search)
                    }
                }
            }
        },
        // こういう使い方していいのかは不明。関数を渡すかどうかをifでチェック
        navigationIcon = if (isShowBackIcon) {
            {
                IconButton(onClick = { onClickBackIcon() }) {
                    Icon(imageVector = Icons.Outlined.ArrowBack)
                }
            }
        } else null
    )
}