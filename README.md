# ComposeIconShop

`Jetpack Compose`のアイコンを見るアプリ  
アウトラインとかツートーンとか

<p align="center">
    <img src="https://imgur.com/C3LbNQ0.png" width="200">
    <img src="https://imgur.com/sWAq5Mf.png" width="200">
    <img src="https://imgur.com/Gkb1NAZ.png" width="200">
    <img src="https://imgur.com/ZC890Lq.png" width="200">
</p>

# 仕組み
[マテリアルアイコンのソースコードからアイコン一覧みたいなやつを見つけてきたので](https://github.com/google/material-design-icons/blob/master/font/MaterialIconsOutlined-Regular.codepoints)  
これを使ってアイコン一覧テキストをJSON配列に変換します。これはNode.jsでやってる(download/src/index.js)  

生成したJSONファイルをAndroidの`assets`に配置します。

あとはJSONファイルを解析し、リフレクションを使ってアイコンを呼び出しています。

# 実行方法
## 必要なもの
- Android アプリ開発環境
    - Android StudioはCanary版を利用してください。
- Node.js
    - アプリの実行には使いませんが、アイコンの名前を取得する際に利用します
    - まあ無くても動く

## アイコンデータ取得
`download`内にNode.jsで実行できるプログラムが入っています。  
プログラムを実行すると、Androidの`src/main/assets/icon.json`が更新されます。

```
$ cd download
$ npm run dev
```

## トラブルシューティング
- Unresolved reference ~
    - あんのに無いって言われる場合
    - `Build` > `Clean Project` を実行
    - 名前が悪い？

# 技術的な話

## 画面遷移
`ViewModel`に置いた`LiveData`を利用して画面遷移をしています。どうやるのが正解なのかは知らない  

とりあえずデータクラスを用意して

```kotlin
/**
 * 画面遷移で使うデータクラス
 *
 * @param screenName 遷移先画面の名前
 * */
open class NavigationData(val screenName: String)

/**
 * アイコン詳細画面へ切り替える際に利用するデータクラス
 *
 * @param iconName アイコンの名前。
 * @param screenName 遷移先画面の名前
 * */
class IconDetailNavigationData(screenName: String, val iconName: String) :
    NavigationData(screenName)
```

あとはデータクラスをLiveDataへ

```kotlin
/**
 * GoTo家キャンペーン
 *
 * [screenLiveData]へ[NavigationNames.HOME]を送信します
 * */
fun gotoHome() {
    screenLiveData.postValue(NavigationData(NavigationNames.HOME))
}
```

`Jetpack Compose`側はこうなってる  
画面も`Fragment`ではなく`Jetpack Compose`で作成しています。

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // Jetpack Composeでレイアウト作成
    setContent {
        // 画面遷移
        val screenLiveData = viewModel.screenLiveData.observeAsState()
        when (screenLiveData.value?.screenName) {
            NavigationNames.HOME -> HomeScreen(viewModel = viewModel,iconSearch = searchWord.value,iconType = iconType.value)
            NavigationNames.DETAIL -> DetailScreen(iconName = (screenLiveData.value!! as IconDetailNavigationData).iconName)
        }
    }
}
```


## アイコン取得のためにリフレクション
アイコン一覧の配列等は（多分）ない。 

ちなみに本家は`Python`でアイコン一覧を取得している模様。本家README参照（ https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/material/material/icons/README.md ）  
ソースコードを見に行けばアイコン一覧が見ようと思えば見れる（ https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/material/material/icons/generator/raw-icons/filled/ ）  
ただし、Kotlinファイルはライブラリ生成時に生成されるらしく？見てもない。

まず、アイコンを返す関数があるクラスを取得するために、`クラスの名前`からクラスを取得する`Class.forName()`を利用します。 

- `filled`なところはアイコンの種類。アウトラインなアイコンが欲しい場合は`outlined`を代わりに書く
- `AndroidKt`はアイコン名。アイコン名+`Kt`を付ける。

```kotlin
val iconClass = Class.forName("androidx.compose.material.icons.filled.AndroidKt")
```

その次に、Staticなアイコンを返すメソッドがあるので取得します。  
先ほど用意したクラスはインスタンス化しなくていいはずです。Staticなメソッドを呼ぶだけなので  

メソッド名は `get`+アイコン名 だと思います。第二引数は戻り値の型。アウトラインを取得するようにした場合は`Icons.Outlined::class.java`になると思う

```kotlin
val getIconFunction = iconClass.getMethod("getAndroid", Icons.Filled::class.java)
```

最後に取得したメソッドを呼びます。

```kotlin
val icon = getIconFunction.invoke(null, Icons.Filled::class.java) as ImageVector
```

あとはこれを`Icon`に入れてあげれば完成

```kotlin
Icon(imageVector = icon)
```

詳しくは各Iconのクラスを見て`KotlinをJavaに変換して`見てみてください。パスがそのまま書いてある

# 仕様
- 一部のアイコンが欠けている
    - Jetpack Composeのアイコン一覧どっかにねえかな
    - JSON生成でミスってんのかわからん
