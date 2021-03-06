package br.com.alexandremarcondes.egginc.companion.screens

import android.content.res.Configuration
import android.graphics.Insets
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Size
import android.view.Surface
import android.view.WindowInsets
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.onActive
import androidx.ui.animation.Crossfade
import androidx.ui.core.setContent
import androidx.ui.material.*
import androidx.ui.tooling.preview.Preview
import br.com.alexandremarcondes.egginc.companion.EggIncCompanionApp
import br.com.alexandremarcondes.egginc.companion.data.IAppContainer
import br.com.alexandremarcondes.egginc.companion.ui.EggIncCompanionTheme
import br.com.alexandremarcondes.egginc.companion.ui.Loading
import br.com.alexandremarcondes.egginc.companion.ui.NavigationViewModel
import br.com.alexandremarcondes.egginc.companion.ui.Screen
import br.com.alexandremarcondes.egginc.companion.util.getDimensions
import br.com.alexandremarcondes.egginc.companion.util.toIntDPs
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val navigationViewModel by viewModels<NavigationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as EggIncCompanionApp
        val rotation = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            display!!.rotation
        else
            resources.configuration.orientation

        val windowSize = getDimensions(windowManager)

        setContent {
            EggIncCompanionTheme {
                AppContent(
                    navigationViewModel = navigationViewModel,
                    app = app.container,
                    rotation = rotation,
                    widthPx = windowSize.width.toIntDPs(this),
                    heightPx = windowSize.height.toIntDPs(this)
                )
            }
        }
    }

    override fun onBackPressed() {
        if (!navigationViewModel.onBack()) {
            super.onBackPressed()
        }
    }
}

@Composable
private  fun AppContent(
    navigationViewModel: NavigationViewModel,
    app: IAppContainer,
    rotation: Int,
    widthPx: Int,
    heightPx: Int) {
    onActive {
        val scope = CoroutineScope((Dispatchers.Main))

        onDispose { scope.cancel() }

        navigationViewModel.navigateTo(Screen.Loading)

        GlobalScope.launch(Dispatchers.IO) {
            val settings = app.database.settingsDao().getAll().firstOrNull()

            if (settings == null) {
                scope.launch { navigationViewModel.navigateTo(Screen.Setup) }
            }
        }.invokeOnCompletion {
            scope.launch {
                if ((it == null || it is CancellationException) &&
                    navigationViewModel.currentScreen == Screen.Loading) {
                    navigationViewModel.navigateTo(Screen.Home)
                }
            }
        }
    }

    Crossfade(navigationViewModel.currentScreen) { screen ->
        Surface(color = MaterialTheme.colors.background) {
            when (screen) {
                is Screen.Home -> Home(
                    rotation = rotation,
                    navigateTo = navigationViewModel::navigateTo
                )

                is Screen.UserList -> UserList(
                    repository = app.dataRepository,
                    refreshingState = false,
                    navigateTo = navigationViewModel::navigateTo
                )

                is Screen.CoopList -> CoopList(
                    repository = app.dataRepository,
                    refreshingState = false,
                    widthPx =  widthPx,
                    heightPx = heightPx,
                    navigateTo = navigationViewModel::navigateTo
                )

                is Screen.User -> UserData(
                    appContainer = app,
                    refreshingState = false,
                    userId = screen.userId
                )

                is Screen.Coop -> CoopData(
                    repository = app.dataRepository,
                    refreshingState = false,
                    coopId = screen.coopId,
                    contractId = screen.contractId
                )

                is Screen.Loading -> Loading()

                is Screen.Setup -> Setup(
                    database = app.database,
                    navigateTo = navigationViewModel::navigateTo
                )
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
        Home(Surface.ROTATION_0) {}
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
        Home(Surface.ROTATION_180) {}
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
        Home(Surface.ROTATION_90) {}
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
        Home(Surface.ROTATION_270) {}
    }
}
