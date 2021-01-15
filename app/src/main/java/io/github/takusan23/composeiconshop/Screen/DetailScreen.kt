package io.github.takusan23.composeiconshop.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.takusan23.composeiconshop.Tool.IconLoad

/**
 * アイコン詳細画面
 * */
@Composable
fun DetailScreen(
    iconName: String
) {
    // アイコン読み込み
    val icon = IconLoad.getIconFromName(iconName = iconName)
    if (icon != null) {
        Column {
            Icon(
                imageVector = icon, modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(10.dp)
            )
            Text(
                text = iconName,
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}
