package io.github.takusan23.composeiconshop.Compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.twotone.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object ModalSetting {
    /** [ModalSettingSheetContent]の第一引数の関数の引数がこれと同じ場合はデフォルトを選択した。 */
    const val CLICK_DEFAULT = 0

    /** [ModalSettingSheetContent]の第一引数の関数の引数がこれと同じ場合はアウトラインを選択した。 */
    const val CLICK_OUTLINE = 1

    /** [ModalSettingSheetContent]の第一引数の関数の引数がこれと同じ場合はラウンドを選択した。 */
    const val CLICK_ROUNDED = 2

    /** [ModalSettingSheetContent]の第一引数の関数の引数がこれと同じ場合はシャープを選択した。 */
    const val CLICK_SHARP = 3

    /** [ModalSettingSheetContent]の第一引数の関数の引数がこれと同じ場合はツートーンを選択した。 */
    const val CLICK_TWO_TONE = 4
}

/**
 * 設定画面のModalSheetに表示させる内容
 *
 * @param onButtonClick アイコンの種類変更ボタンを押した時。
 * */
@Composable
fun ModalSettingSheetContent(
    onButtonClick: (Int) -> Unit,
) {
    Column {
        // ボタン
        ModalSettingButtons(onButtonClick = { onButtonClick(it) })
    }
}

/**
 * アウトラインに変更とか
 * */
@Composable
fun ModalSettingButtons(
    onButtonClick: (Int) -> Unit,
) {
    Column {
        val buttonModifier = Modifier
            .weight(1f)
            .padding(5.dp)

        Text(
            text = "Icon Type",
            modifier = Modifier.padding(5.dp),
            fontSize = 20.sp
        )

        Row {
            TextButton(
                onClick = { onButtonClick(0) },
                modifier = buttonModifier
            ) {
                Column {
                    Icon(
                        imageVector = Icons.Default.Home,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "Default",
                        textAlign = TextAlign.Center
                    )
                }
            }
            TextButton(
                onClick = { onButtonClick(1) },
                modifier = buttonModifier
            ) {
                Column {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "Outlined",
                        textAlign = TextAlign.Center
                    )
                }
            }
            TextButton(
                onClick = { onButtonClick(2) },
                modifier = buttonModifier
            ) {
                Column {
                    Icon(
                        imageVector = Icons.Rounded.Home,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "Rounded",
                        textAlign = TextAlign.Center
                    )
                }
            }
            TextButton(
                onClick = { onButtonClick(3) },
                modifier = buttonModifier
            ) {
                Column {
                    Icon(
                        imageVector = Icons.Sharp.Home,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "Sharp",
                        textAlign = TextAlign.Center
                    )
                }
            }
            TextButton(
                onClick = { onButtonClick(4) },
                modifier = buttonModifier
            ) {
                Column {
                    Icon(
                        imageVector = Icons.TwoTone.Home,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "TwoTone",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}