package br.com.alexandremarcondes.egginc.companion.ui

import android.content.res.Configuration
import androidx.compose.Composable
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.material.Snackbar
import androidx.ui.core.Modifier
import androidx.ui.layout.padding
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TextButton
import androidx.ui.material.snackbarPrimaryColorFor
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun SnackbarDemoPreview() {
    SnackbarDemo(false)
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun SnackbarDemoDarkPreview() {
    SnackbarDemo(true)
}

@Composable
fun SnackbarDemo(darkMode: Boolean) {
    EggIncCompanionTheme(darkTheme = darkMode) {
        VerticalScroller {
            Column(Modifier.padding(12.dp, 0.dp, 12.dp, 0.dp)) {
                val textSpacing = Modifier.padding(top = 12.dp, bottom = 12.dp)
                Text("Default Snackbar", modifier = textSpacing)
                SimpleSnackbar()
                Text("Snackbar with long text", modifier = textSpacing)
                Snackbar(
                    text = { Text("This song already exists in the current playlist") },
                    action = {
                        TextButton(
                            contentColor = snackbarPrimaryColorFor(MaterialTheme.colors),
                            onClick = { /* perform undo */ }
                        ) {
                            Text("ADD THIS SONG ANYWAY")
                        }
                    },
                    actionOnNewLine = true
                )
                Text("Snackbar with only text", modifier = textSpacing)
                Snackbar(
                    text = { Text("This song already exists in the current playlist") }
                )
            }
        }
    }
}

@Composable
fun SimpleSnackbar() {
    Snackbar(
        text = { Text("Action has been done") },
        action = {
            TextButton(
                contentColor = snackbarPrimaryColorFor(MaterialTheme.colors),
                onClick = { /* perform undo */ }
            ) {
                Text("UNDO")
            }
        }
    )
}
