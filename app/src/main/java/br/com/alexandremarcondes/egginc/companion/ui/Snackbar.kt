package br.com.alexandremarcondes.egginc.companion.ui

import android.content.res.Configuration
import androidx.compose.*
import androidx.ui.animation.animatedFloat
import androidx.ui.core.DensityAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.contentColor
import androidx.ui.layout.padding
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Snackbar
import androidx.ui.material.TextButton
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import kotlinx.coroutines.delay


@Composable
fun AnimatingSnackbar(state: SnackbarState, modifier: Modifier = Modifier) {
    val height = 48.dp
    val heightPx = with(DensityAmbient.current) { height.toIntPx().value.toFloat() }
    val offset = animatedFloat(heightPx)
    var dismissing by state { false }

    onCommit(dismissing, state.currentSnack) {
        state.currentSnack ?: return@onCommit
        if (dismissing) {
            offset.animateTo(heightPx) { _, _ ->
                dismissing = false
                state.dismissCurrentSnack()
            }
        } else {
            offset.animateTo(0f)
        }
    }

    if (state.currentSnack != null) {
        // this is a prototype coroutine + compose API (see appendix for code listing)
        // It will launch a new coroutine any time `snack` changes, cancelling the previous (if any)
        launchInComposition(state.currentSnack) {
            delay(1000)
            dismissing = true
        }
        Snackbar(
            text = { Text(
                state.currentSnack!!,
                Modifier.padding(start = 16.dp, end = 16.dp)
            ) },
            modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun NonAnimatingSnackbar(state: SnackbarState, modifier: Modifier = Modifier) {
    state.currentSnack?.let { snack ->
        // this is a prototype coroutine + compose API (see appendix for code listing)
        // It will launch a new coroutine any time `snack` changes, cancelling the previous (if any)
        launchInComposition(snack) {
            delay(1000)
            state.dismissCurrentSnack()
        }
        Snackbar(
            text = { Text(
                state.currentSnack!!,
                Modifier.padding(start = 16.dp, end = 16.dp)
            ) },
            modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun ErrorSnackbar(
    text: String,
    showError: Boolean,
    modifier: Modifier = Modifier,
    onErrorAction: () -> Unit = { },
    onDismiss: () -> Unit = { }
) {
    if (showError) {
        // Make Snackbar disappear after 5 seconds if the user hasn't interacted with it
        launchInComposition {
            delay(timeMillis = 5000L)
            onDismiss()
        }

        Snackbar(
            modifier = modifier.padding(16.dp),
            text = { Text(text) },
            action = {
                TextButton(
                    onClick = {
                        onErrorAction()
                        onDismiss()
                    },
                    contentColor = contentColor()
                ) {
                    Text(
                        text = "RETRY",
                        color = MaterialTheme.colors.snackbarAction
                    )
                }
            }
        )
    }
}

@Preview("Error", group = "Error",
    widthDp = 411,
    heightDp = 111,
    showBackground = true)
@Composable
fun ErrorSnackbarPreview() {
    EggIncCompanionTheme {
        ErrorSnackbar("Can't update latest news", showError = true)
    }
}

@Preview("Error Dark", group = "Error",
    widthDp = 411,
    heightDp = 111,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ErrorSnackbarDarkPreview() {
    EggIncCompanionTheme(darkTheme = true) {
        ErrorSnackbar("Can't update latest dark news" , showError = true)
    }
}

@Preview("Animated", group = "Animated",
    widthDp = 411,
    heightDp = 111,
    showBackground = true)
@Composable
fun AnimatedSnackbarPreview() {
    EggIncCompanionTheme {
        AnimatingSnackbar(object : SnackbarState{
            override val currentSnack: String?
                get() = "Snackbar Message"

            override fun dismissCurrentSnack() {
            }
        })
    }
}

@Preview("Animated Dark", group = "Animated",
    widthDp = 411,
    heightDp = 111,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AnimatedSnackbarDarkPreview() {
    EggIncCompanionTheme(darkTheme = true) {
        AnimatingSnackbar(object : SnackbarState{
            override val currentSnack: String?
                get() = "Dark Snackbar Message"

            override fun dismissCurrentSnack() {
            }
        })
    }
}

@Preview("Non-Animated", group = "Non-Animated",
    widthDp = 411,
    heightDp = 111,
    showBackground = true)
@Composable
fun NonAnimatedSnackbarPreview() {
    EggIncCompanionTheme {
        NonAnimatingSnackbar(object : SnackbarState{
            override val currentSnack: String?
                get() = "Snackbar Message"

            override fun dismissCurrentSnack() {
            }
        })
    }
}

@Preview("Non-Animated Dark", group = "Non-Animated",
    showBackground = true,
    widthDp = 411,
    heightDp = 111,
    uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NonAnimatedSnackbarDarkPreview() {
    EggIncCompanionTheme(darkTheme = true) {
        NonAnimatingSnackbar(object : SnackbarState{
            override val currentSnack: String?
                get() = "Dark Snackbar Message"

            override fun dismissCurrentSnack() {
            }
        })
    }
}