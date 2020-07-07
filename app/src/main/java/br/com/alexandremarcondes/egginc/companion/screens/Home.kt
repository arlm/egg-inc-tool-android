package br.com.alexandremarcondes.egginc.companion.screens

import android.content.res.Configuration
import android.view.Surface
import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.ContentScale
import androidx.ui.core.Modifier
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.graphics.ImageAsset
import androidx.ui.layout.*
import androidx.ui.layout.RowScope.gravity
import androidx.ui.material.Card
import androidx.ui.material.EmphasisAmbient
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ProvideEmphasis
import androidx.ui.res.imageResource
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import br.com.alexandremarcondes.egginc.companion.R
import br.com.alexandremarcondes.egginc.companion.ui.EggIncCompanionTheme
import br.com.alexandremarcondes.egginc.companion.ui.Screen

@Composable
fun Home(rotation: Int, navigateTo: (Screen) -> Unit) {
    when (rotation) {
        Surface.ROTATION_0, Surface.ROTATION_180 ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {
                HomeContent(
                    false,
                    navigateTo
                )
            }
        Surface.ROTATION_90, Surface.ROTATION_270 ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {
                HomeContent(
                    true,
                    navigateTo
                )
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
        onClick = { navigateTo(Screen.CoopList) })
    HomeCard(
        image = imageResource(R.drawable.user_card),
        title = "View Users",
        subTitle = "23 profiles registered",
        portrait = portrait,
        onClick = { navigateTo(Screen.UserList) })
}

@Composable
private fun HomeCard(image: ImageAsset, title: String, subTitle: String, portrait: Boolean, onClick: () -> Unit) {
    val constraint = if (portrait)
        Modifier.preferredSize(280.dp,240.dp).gravity(
            Alignment.CenterVertically
        )
    else
        Modifier.fillMaxWidth().preferredHeight(240.dp)

    Card(
        shape = MaterialTheme.shapes.medium,
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
            Column(
                modifier = Modifier.padding(
                    16.dp
                )
            ) {
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
private fun HomePortraitPreview() {
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
private fun HomePortraitDarkPreview() {
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
private fun HomeLandscapePreview() {
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
private fun HomeLandscapeDarkPreview() {
    EggIncCompanionTheme(darkTheme = true) {
        Home(Surface.ROTATION_270) {}
    }
}
