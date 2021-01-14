const axios = require('axios')
const _ = require('lodash')
const fs = require('fs')
const { type } = require('os')

main()

function main() {
    axios.get("https://raw.githubusercontent.com/google/material-design-icons/master/font/MaterialIconsOutlined-Regular.codepoints")
        .then(res => {
            /**
             * 360 e577
             * 3d_rotation e84d
             * 4k e072
             * 5g ef38
             * ↓こんな風の配列に
             * [
             *  360,
             *  3d_rotation,
             *  4k,
             *  5g,
             * ]
             */
            const iconList = res.data.split('\n').map(text => text.split(' ')[0])

            /**
             * それからスネークケースからパスカルケースに変換する
             * settings_applications → settingsApplications
             */
            const iconNameList = iconList
                .map(icon => _snake2Pascal(icon)) // lodashでらくらく変換
                .map(icon => {
                    if (!isNaN(parseInt(icon.split(0, 1)))) {
                        // 数字スタートの場合は先頭に_をつける
                        return `_${icon}`
                    } else {
                        return icon
                    }
                })
                .filter(icon => icon !== '')


            /**
             * 保存する
             */
            fs.writeFile('../app/src/main/assets/icon.json', JSON.stringify(iconNameList), {}, (err) => { })
        })
}

/**
 * https://stackoverflow.com/questions/44082153/javascript-method-for-changing-snake-case-to-pascalcase
 */
function _snake2Pascal(string) {
    return string.split("/")
        .map(snake => snake.split("_")
            .map(substr => substr.charAt(0)
                .toUpperCase() +
                substr.slice(1))
            .join(""))
        .join("/");
}