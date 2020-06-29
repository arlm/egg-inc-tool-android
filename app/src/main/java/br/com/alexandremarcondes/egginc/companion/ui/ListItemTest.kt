package br.com.alexandremarcondes.egginc.companion.ui

import android.content.res.Configuration
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.graphics.ImageAsset
import androidx.ui.graphics.vector.VectorAsset
import androidx.ui.layout.Column
import androidx.ui.layout.preferredSize
import androidx.ui.material.Checkbox
import androidx.ui.material.Divider
import androidx.ui.material.ListItem
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Call
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import br.com.alexandremarcondes.egginc.companion.R

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun ListItemDemoPreview() {
    EggIncCompanionTheme(darkTheme = false) {
        ListItemDemo()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ListItemDemoDarkPreview() {
    EggIncCompanionTheme(darkTheme = true) {
        ListItemDemo()
    }
}

@Composable
fun ListItemDemo() {
    val icon24 = imageResource(R.drawable.egg_antimatter)
    val icon40 = imageResource(R.drawable.egg_dilithium)
    val icon56 = imageResource(R.drawable.egg_darkmatter)
    val vectorIcon = Icons.Default.Call
    VerticalScroller {
        OneLineListItems(icon24, icon40, icon56, vectorIcon)
        TwoLineListItems(icon24, icon40)
        ThreeLineListItems(icon24, vectorIcon)
    }
}

@Composable
fun OneLineListItems(
    icon24x24: ImageAsset,
    icon40x40: ImageAsset,
    icon56x56: ImageAsset,
    vectorIcon: VectorAsset
) {
    Column {
        ListItem(text = { Text("One line list item with no icon") })
        Divider()
        ListItem(
            text = { Text("פריט ברשימה אחת עם תמונה.") },
            icon = { Image(icon24x24,
                modifier = Modifier.preferredSize(24.dp, 24.dp) ) }
        )
        Divider()
        ListItem(
            text = { Text("One line list item with 24x24 icon") },
            icon = { Image(icon24x24,
                modifier = Modifier.preferredSize(24.dp, 24.dp) ) }
        )
        Divider()
        ListItem(
            text = { Text("One line list item with 40x40 icon") },
            icon = { Image(icon40x40,
                modifier = Modifier.preferredSize(40.dp, 40.dp) ) }
        )
        Divider()
        ListItem(
            text = { Text("One line list item with 56x56 icon") },
            icon = { Image(icon56x56,
                modifier = Modifier.preferredSize(56.dp, 56.dp) ) }
        )
        Divider()
        ListItem(
            text = { Text("One line clickable list item") },
            icon = { Image(icon56x56,
                modifier = Modifier.preferredSize(56.dp, 56.dp)) },
            onClick = {}
        )
        Divider()
        ListItem(
            text = { Text("One line list item with trailing icon") },
            trailing = { Icon(vectorIcon) }
        )
        Divider()
        ListItem(
            text = { Text("One line list item") },
            icon = { Image(icon40x40,
                modifier = Modifier.preferredSize(40.dp, 40.dp) ) },
            trailing = { Icon(vectorIcon) }
        )
        Divider()
    }
}

@Composable
// TODO(popam, b/159689286): material icons instead of ImageAsset when they can have custom sizes
fun TwoLineListItems(icon24x24: ImageAsset, icon40x40: ImageAsset) {
    Column {
        ListItem(
            text = { Text("Two line list item") },
            secondaryText = { Text("Secondary text") }
        )
        Divider()
        ListItem(
            text = { Text("Two line list item") },
            overlineText = { Text("OVERLINE") }
        )
        Divider()
        ListItem(
            text = { Text("Two line list item with 24x24 icon") },
            secondaryText = { Text("Secondary text") },
            icon = { Image(icon24x24,
                modifier = Modifier.preferredSize(24.dp, 24.dp) ) }
        )
        Divider()
        ListItem(
            text = { Text("Two line list item with 40x40 icon") },
            secondaryText = { Text("Secondary text") },
            icon = { Image(icon40x40,
                modifier = Modifier.preferredSize(40.dp, 40.dp) ) }
        )
        Divider()
        ListItem(
            text = { Text("Two line list item with 40x40 icon") },
            secondaryText = { Text("Secondary text") },
            trailing = { Text("meta") },
            icon = { Image(icon40x40,
                modifier = Modifier.preferredSize(40.dp, 40.dp) ) }
        )
        Divider()
        var checked by state { false }
        ListItem(
            text = { Text("Two line list item") },
            secondaryText = { Text("Secondary text") },
            icon = { Image(icon40x40,
                modifier = Modifier.preferredSize(40.dp, 40.dp) ) },
            trailing = {
                Checkbox(checked, onCheckedChange = { checked = !checked })
            }
        )
        Divider()
    }
}

@Composable
fun ThreeLineListItems(icon24x24: ImageAsset, vectorIcon: VectorAsset) {
    Column {
        ListItem(
            text = { Text("Three line list item") },
            secondaryText = {
                Text(
                    "This is a long secondary text for the current list item, " +
                            "displayed on two lines"
                )
            },
            singleLineSecondaryText = false,
            trailing = { Text("meta") }
        )
        Divider()
        ListItem(
            text = { Text("Three line list item") },
            overlineText = { Text("OVERLINE") },
            secondaryText = { Text("Secondary text") }
        )
        Divider()
        ListItem(
            text = { Text("Three line list item with 24x24 icon") },
            secondaryText = {
                Text(
                    "This is a long secondary text for the current list item " +
                            "displayed on two lines"
                )
            },
            singleLineSecondaryText = false,
            icon = { Image(icon24x24,
                modifier = Modifier.preferredSize(24.dp, 24.dp) ) }
        )
        Divider()
        ListItem(
            text = { Text("Three line list item with trailing icon") },
            secondaryText = {
                Text(
                    "This is a long secondary text for the current list" +
                            " item, displayed on two lines"
                )
            },
            singleLineSecondaryText = false,
            trailing = { Icon(vectorIcon) }
        )
        Divider()
        ListItem(
            text = { Text("Three line list item") },
            overlineText = { Text("OVERLINE") },
            secondaryText = { Text("Secondary text") },
            trailing = { Text("meta") }
        )
        Divider()
    }
}