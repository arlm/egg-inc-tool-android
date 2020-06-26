package br.com.alexandremarcondes.egginc.companion

import android.content.res.Configuration
import android.os.Bundle
import android.view.Surface
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.ContentScale
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.graphics.ImageAsset
import androidx.ui.layout.*
import androidx.ui.material.Card
import androidx.ui.material.EmphasisAmbient
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ProvideEmphasis
import androidx.ui.res.imageResource
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import br.com.alexandremarcondes.egginc.companion.ui.EggIncCompanionTheme


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EggIncCompanionTheme {
                InitialMenu(display!!.rotation)
            }
        }
    }
}

@Composable
fun InitialMenu(rotation: Int) {
    when (rotation) {
        Surface.ROTATION_0, Surface.ROTATION_180 ->
            Column (modifier = Modifier.padding(4.dp)) {
                HomeContent()
            }
        Surface.ROTATION_90, Surface.ROTATION_270 ->
            Row (modifier = Modifier.padding(4.dp)) {
                HomeContent()
            }
    }

}

@Composable
private fun HomeContent() {
    HomeCard(
            image = imageResource(R.drawable.coop_card),
            title = "Manage Coops",
            subTitle = "23 active contracts")
    HomeCard(
            image = imageResource(R.drawable.user_card),
            title = "View Users",
            subTitle = "23 profiles registered")
}

@Composable
fun HomeCard(image: ImageAsset, title: String, subTitle: String) {
    Card(
            shape =  MaterialTheme.shapes.medium,
            modifier = Modifier
                    .preferredSize(280.dp, 240.dp)
                    .padding(4.dp)
    ) {
        Column {
            Image(
                asset = image,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .preferredHeight(140.dp)
                    .fillMaxWidth())
            Column(modifier = Modifier.padding(16.dp)) {
                val emphasisLevels = EmphasisAmbient.current
                ProvideEmphasis(emphasisLevels.high) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.h6,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis)
                    Text(
                        text = subTitle,
                        style = MaterialTheme.typography.body2,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis)
                }
            }
        }
    }
}

@Preview("Default Colors",
        group = "Portrait",
        showBackground = true)
@Composable
fun PortraitPreview() {
    EggIncCompanionTheme {
        InitialMenu(Surface.ROTATION_0)
    }
}

@Preview("Dark Colors",
        group = "Portrait",
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        showBackground = true)
@Composable
fun PortraitDarkPreview() {
    EggIncCompanionTheme(darkTheme = true) {
        InitialMenu(Surface.ROTATION_180)
    }
}

@Preview("Default Colors",
        group ="Lanscape",
        showBackground = true,
        widthDp = 720)
@Composable
fun LandscapePreview() {
    EggIncCompanionTheme() {
        InitialMenu(Surface.ROTATION_90)
    }
}

@Preview("Dark Colors",
        group ="Lanscape",
        showBackground = true,
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        widthDp = 720)
@Composable
fun LandscapeDarkPreview() {
    EggIncCompanionTheme(darkTheme = true) {
        InitialMenu(Surface.ROTATION_270)
    }
}