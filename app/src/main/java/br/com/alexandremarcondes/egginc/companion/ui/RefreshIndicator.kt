package br.com.alexandremarcondes.egginc.companion.ui

import android.content.res.Configuration
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.layout.padding
import androidx.ui.layout.preferredSize
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.Surface
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp

@Composable
fun RefreshIndicator() {
    Surface(elevation = 10.dp, shape = CircleShape) {
        CircularProgressIndicator(Modifier.preferredSize(50.dp).padding(4.dp))
    }
}

@Preview("Indicator",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun  RefreshIndicatorPreview() {
    EggIncCompanionTheme {
        RefreshIndicator()
    }
}

@Preview("Indicator Dark",
    widthDp = 411,
    heightDp = 731,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true)
@Composable
private fun  RefreshIndicatorDarkPreview() {
    EggIncCompanionTheme {
        RefreshIndicator()
    }
}