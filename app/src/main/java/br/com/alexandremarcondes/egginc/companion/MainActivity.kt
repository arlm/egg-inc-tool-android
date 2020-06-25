package br.com.alexandremarcondes.egginc.companion

import android.R.style
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.ContentScale
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.drawBackground
import androidx.ui.graphics.Color
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
                InitialMenu()
            }
        }
    }
}

@Composable
fun InitialMenu() {
    Column (modifier = Modifier.padding(4.dp)) {
        HomeCard(
            image = imageResource(R.drawable.coop_card),
            title =  "Manage Coops",
            subTitle = "23 active contracts")
        HomeCard(
            image = imageResource(R.drawable.user_card),
            title =  "View Users",
            subTitle = "23 profiles registered")
    }
}

@Composable
fun HomeCard(image: ImageAsset, title: String, subTitle: String) {
    Card(
            shape =  MaterialTheme.shapes.medium,
            modifier = Modifier.preferredSize(280.dp, 240.dp).padding(4.dp)
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EggIncCompanionTheme {
        InitialMenu()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewDark() {
    EggIncCompanionTheme(darkTheme = true) {
        InitialMenu()
    }
}