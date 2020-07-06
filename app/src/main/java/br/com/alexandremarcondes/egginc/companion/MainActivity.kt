package br.com.alexandremarcondes.egginc.companion

import android.content.res.Configuration
import android.os.Bundle
import android.view.Surface
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.onActive
import androidx.compose.setValue
import androidx.ui.animation.Crossfade
import androidx.ui.core.Alignment
import androidx.ui.core.ContentScale
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.graphics.ImageAsset
import androidx.ui.layout.*
import androidx.ui.layout.RowScope.gravity
import androidx.ui.material.*
import androidx.ui.res.imageResource
import androidx.ui.savedinstancestate.savedInstanceState
import androidx.ui.text.SpanStyle
import androidx.ui.text.annotatedString
import androidx.ui.text.font.FontStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.text.style.TextOverflow
import androidx.ui.text.withStyle
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import br.com.alexandremarcondes.egginc.companion.data.AppDatabase
import br.com.alexandremarcondes.egginc.companion.data.IAppContainer
import br.com.alexandremarcondes.egginc.companion.data.impl.FakeAppDatabase
import br.com.alexandremarcondes.egginc.companion.data.model.Settings
import br.com.alexandremarcondes.egginc.companion.ui.EggIncCompanionTheme
import br.com.alexandremarcondes.egginc.companion.ui.NavigationViewModel
import br.com.alexandremarcondes.egginc.companion.ui.Screen
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val navigationViewModel by viewModels<NavigationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as EggIncCompanionApp

        setContent {
            EggIncCompanionTheme {
                AppContent(
                    navigationViewModel = navigationViewModel,
                    app = app.container,
                    rotation = display!!.rotation
                )
            }
        }
    }
}

@Composable
fun Loading(message: String = "") {
    Stack(modifier = Modifier.fillMaxSize()) {
        Text(
            "Loading $message...",
            modifier = Modifier.gravity(Alignment.Center)
        )
    }
}


@Composable
private  fun AppContent(
    navigationViewModel: NavigationViewModel,
    app: IAppContainer,
    rotation: Int) {
    onActive {
        val scope = CoroutineScope((Dispatchers.Main))

        onDispose { scope.cancel() }

        navigationViewModel.navigateTo(Screen.Loading)

        runBlocking {
            GlobalScope.launch(Dispatchers.IO) {
                val settings = app.database.settingsDao().getAll().firstOrNull()

                if (settings == null) {
                    scope.launch { navigationViewModel.navigateTo(Screen.Setup) }
                }
            }
        }.invokeOnCompletion {
            scope.launch {
                if ((it == null || it is CancellationException) &&
                    navigationViewModel.currentScreen == Screen.Loading
                ) {
                    navigationViewModel.navigateTo(Screen.Home)
                }
            }
        }
    }

    Crossfade(navigationViewModel.currentScreen) { screen ->
        Surface(color = MaterialTheme.colors.background) {
            when (screen) {
                is Screen.Home -> InitialMenu(
                    rotation = rotation,
                    navigateTo = navigationViewModel::navigateTo
                )

                is Screen.UserList -> UserList(
                    repository = app.dataRepository,
                    refreshingState = false
                )

                is Screen.CoopList -> CoopList(
                    repository =app.dataRepository,
                    refreshingState = false
                )

                is Screen.User -> TODO()

                is Screen.Coop -> TODO()

                is Screen.Loading -> Loading()

                is Screen.Setup -> Setup(
                    database = app.database,
                    navigateTo = navigationViewModel::navigateTo
                )
            }
        }
    }
}

@Composable
private fun Setup(
    database: AppDatabase,
    navigateTo: (Screen) -> Unit
) {
    var id by savedInstanceState { "" }
    val image = imageResource(R.drawable.user_id)

    val baseStyle = SpanStyle(color = MaterialTheme.colors.onBackground)
    val menuStyle = SpanStyle( fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)
    val arrowStyle = SpanStyle( fontWeight = FontWeight.Bold)

    val text = annotatedString {
        withStyle(style = baseStyle) {
            append("Go to ")
            withStyle(style = menuStyle) { append("Main Menu") }
            withStyle(style = arrowStyle) { append(" -> ") }
            withStyle(style = menuStyle) { append("Settings") }
            withStyle(style = arrowStyle) { append(" -> ") }
            withStyle(style = menuStyle) {  append("Privacy & Data") }
            append(" and look on the last line of the window.")
        }
    }

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(5.dp)) {
        Row (modifier = Modifier.padding(5.dp)){
            FilledTextField(
                value = id,
                onValueChange = { id = it },
                label = { Text("Enter your Egg Inc! ID") },
                modifier = Modifier.weight(0.75f, fill = true)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Button(
                text = { Text("Save") },
                onClick = {
                    val settings = Settings(eggIncId = id)
                        GlobalScope.launch(Dispatchers.IO) {
                            database.settingsDao().insert(settings)
                        }.invokeOnCompletion {
                            GlobalScope.launch(Dispatchers.Main) {
                                navigateTo(Screen.Home)
                            }
                        }
                },
                modifier = Modifier.weight(0.25f).gravity(align = Alignment.CenterVertically)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(text)
        Spacer(modifier = Modifier.height(15.dp))
        Image(
            asset = image,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1.771f)
        )
    }
}

@Composable
private fun InitialMenu(rotation: Int, navigateTo: (Screen) -> Unit) {
    when (rotation) {
        Surface.ROTATION_0, Surface.ROTATION_180 ->
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)) {
                HomeContent(false, navigateTo)
            }
        Surface.ROTATION_90, Surface.ROTATION_270 ->
            Row (modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)) {
                HomeContent(true, navigateTo)
            }
    }
}

@Composable
private fun HomeContent(portrait: Boolean = false, navigateTo: (Screen) -> Unit) {
    HomeCard(
            image = imageResource(R.drawable.coop_card),
            title = "Manage Coops",
            subTitle = "23 active contracts",
            portrait = portrait,
            onClick = { navigateTo(Screen.CoopList)})
    HomeCard(
            image = imageResource(R.drawable.user_card),
            title = "View Users",
            subTitle = "23 profiles registered",
            portrait = portrait,
            onClick = { navigateTo(Screen.UserList)})
}

@Composable
private fun HomeCard(image: ImageAsset, title: String, subTitle: String, portrait: Boolean, onClick: () -> Unit) {
    val constraint = if (portrait)
        Modifier.preferredSize(280.dp,240.dp).gravity(Alignment.CenterVertically)
    else
        Modifier.fillMaxWidth().preferredHeight(240.dp)

    Card(
            shape =  MaterialTheme.shapes.medium,
            modifier = constraint
                .padding(4.dp)
                .clickable(onClick = onClick),
            elevation = 6.dp
    ) {
        Column {
            Image(
                asset = image,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .preferredHeight(140.dp)
                    .fillMaxWidth()
            )
            Column(modifier = Modifier.padding(16.dp)) {
                val emphasisLevels = EmphasisAmbient.current
                ProvideEmphasis(emphasisLevels.high) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.h6,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = subTitle,
                        style = MaterialTheme.typography.body2,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview("Default Colors",
        group = "Portrait",
        widthDp = 411,
        heightDp = 731,
        showBackground = true)
@Composable
private fun PortraitPreview() {
    EggIncCompanionTheme {
        InitialMenu(Surface.ROTATION_0) {}
    }
}

@Preview("Dark Colors",
        group = "Portrait",
        widthDp = 411,
        heightDp = 731,
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        showBackground = true)
@Composable
private fun PortraitDarkPreview() {
    EggIncCompanionTheme(darkTheme = true) {
        InitialMenu(Surface.ROTATION_180) {}
    }
}

@Preview("Default Colors",
        group ="Lanscape",
        widthDp = 731,
        heightDp = 411,
        showBackground = true)
@Composable
private fun LandscapePreview() {
    EggIncCompanionTheme {
        InitialMenu(Surface.ROTATION_90) {}
    }
}

@Preview("Dark Colors",
        group ="Lanscape",
        showBackground = true,
        widthDp = 731,
        heightDp = 411,
        uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LandscapeDarkPreview() {
    EggIncCompanionTheme(darkTheme = true) {
        InitialMenu(Surface.ROTATION_270) {}
    }
}

@Preview("Default Colors",
    group ="Setup",
    widthDp = 731,
    heightDp = 411,
    showBackground = true)
@Composable
private fun SetupPreview() {
    EggIncCompanionTheme {
        Setup(FakeAppDatabase()) {}
    }
}

@Preview("Dark Colors",
    group = "Setup",
    widthDp = 411,
    heightDp = 731,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true)
@Composable
private fun SetupDarkPreview() {
    EggIncCompanionTheme(darkTheme = true) {
        Setup(FakeAppDatabase()) {}
    }
}
