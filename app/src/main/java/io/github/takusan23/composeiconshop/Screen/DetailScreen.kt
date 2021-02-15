package io.github.takusan23.composeiconshop.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.takusan23.composeiconshop.Tool.IconLoad

/**
 * アイコン詳細画面
 * */
@Composable
fun DetailScreen(
    iconName: String,
) {
    LazyColumn(content = {
        item {
            IconDetailCard(iconName = iconName, IconLoad.ICON_DEFAULT)
            IconDetailCard(iconName = iconName, IconLoad.ICON_OUTLINED)
            IconDetailCard(iconName = iconName, IconLoad.ICON_ROUNDED)
            IconDetailCard(iconName = iconName, IconLoad.ICON_SHARP)
            IconDetailCard(iconName = iconName, IconLoad.ICON_TWO_TONE)
        }
    })
}

/**
 * アイコンの詳細Card
 *
 * @param iconName アイコン名
 * @param iconType アイコンの種類。[IconLoad.ICON_OUTLINED] など
 * */
@Composable
fun IconDetailCard(iconName: String, iconType: String = IconLoad.ICON_OUTLINED) {
    // アイコン読み込み
    val icon = IconLoad.getIconFromName(iconName = iconName, iconType = iconType)
    if (icon != null) {
        Card(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
        ) {
            Column {
                Text(
                    text = iconType,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp),
                    textAlign = TextAlign.Start,
                )
                Image(
                    imageVector = icon,
                    colorFilter = ColorFilter.tint(if (isSystemInDarkTheme()) Color.White else Color.Black),
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally),
                    contentDescription = "Icon"
                )
            }
        }
    }
}