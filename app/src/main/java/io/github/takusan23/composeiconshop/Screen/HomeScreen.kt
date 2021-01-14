package io.github.takusan23.composeiconshop.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddRoad
import androidx.compose.material.icons.outlined.AcUnit
import androidx.compose.material.icons.outlined.Android
import androidx.compose.material.icons.outlined._3dRotation
import androidx.compose.material.icons.outlined._6FtApart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.unit.dp
import io.github.takusan23.composeiconshop.Tool.IconLoad
import io.github.takusan23.composeiconshop.Tool.JSONLoad

@Composable
fun HomeScreen() {
    val context = AmbientContext.current
    // アイコン一覧読み出し
    val iconNameList = JSONLoad.load(context)
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
        LazyColumn(content = {
            this.items(iconNameList) { iconName ->
                val icon = IconLoad.getIconFromName(iconName)
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                    )
                }
            }
        })
    }
}