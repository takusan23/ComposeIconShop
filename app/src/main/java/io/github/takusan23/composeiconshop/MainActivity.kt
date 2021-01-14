package io.github.takusan23.composeiconshop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled._360
import androidx.compose.ui.platform.setContent
import io.github.takusan23.composeiconshop.Screen.HomeScreen
import io.github.takusan23.composeiconshop.ui.theme.ComposeIconShopTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeIconShopTheme {
                Surface(color = MaterialTheme.colors.background) {
                    HomeScreen()
                }
            }
        }

        // val iconList = JSONLoad.load(this)
        // iconList.forEach { icon ->
        //     /**
        //      * androidx.compose.material.icons.filled + パスカルケースになったアイコン名 + kt
        //      * 例：androidx.compose.material.icons.filled.AndroidKt
        //      * */
        //     println("androidx.compose.material.icons.filled.${icon}kt")
        // }

    }

}

