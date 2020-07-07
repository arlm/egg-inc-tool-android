package br.com.alexandremarcondes.egginc.companion.screens

import android.content.res.Configuration
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.FilledTextField
import androidx.ui.material.MaterialTheme
import androidx.ui.res.imageResource
import androidx.ui.savedinstancestate.savedInstanceState
import androidx.ui.text.SpanStyle
import androidx.ui.text.annotatedString
import androidx.ui.text.font.FontStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.text.withStyle
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import br.com.alexandremarcondes.egginc.companion.R
import br.com.alexandremarcondes.egginc.companion.data.AppDatabase
import br.com.alexandremarcondes.egginc.companion.data.impl.FakeAppDatabase
import br.com.alexandremarcondes.egginc.companion.data.model.Settings
import br.com.alexandremarcondes.egginc.companion.ui.EggIncCompanionTheme
import br.com.alexandremarcondes.egginc.companion.ui.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun Setup(
    database: AppDatabase,
    navigateTo: (Screen) -> Unit
) {
    var id by savedInstanceState { "" }
    val image =
        imageResource(R.drawable.user_id)

    val baseStyle =
        SpanStyle(color = MaterialTheme.colors.onBackground)
    val menuStyle = SpanStyle(
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic
    )
    val arrowStyle =
        SpanStyle(fontWeight = FontWeight.Bold)

    val text = annotatedString {
        withStyle(style = baseStyle) {
            append("Go to ")
            withStyle(style = menuStyle) { append("Main Menu") }
            withStyle(style = arrowStyle) { append(" -> ") }
            withStyle(style = menuStyle) { append("Settings") }
            withStyle(style = arrowStyle) { append(" -> ") }
            withStyle(style = menuStyle) { append("Privacy & Data") }
            append(" and look on the last line of the window.")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier.padding(
                5.dp
            )
        ) {
            FilledTextField(
                value = id,
                onValueChange = { id = it },
                label = { Text("Enter your Egg Inc! ID") },
                modifier = Modifier.weight(0.75f, fill = true)
            )
            Spacer(
                modifier = Modifier.width(
                    5.dp
                )
            )
            Button(
                text = { Text("Save") },
                onClick = {
                    val settings =
                        Settings(
                            eggIncId = id
                        )
                    GlobalScope.launch(Dispatchers.IO) {
                        database.settingsDao().insert(settings)
                    }.invokeOnCompletion {
                        GlobalScope.launch(Dispatchers.Main) {
                            navigateTo(Screen.Home)
                        }
                    }
                },
                modifier = Modifier.weight(0.25f)
                    .gravity(align = Alignment.CenterVertically)
            )
        }
        Spacer(
            modifier = Modifier.height(
                5.dp
            )
        )
        Text(text)
        Spacer(
            modifier = Modifier.height(
                15.dp
            )
        )
        Image(
            asset = image,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1.771f)
        )
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