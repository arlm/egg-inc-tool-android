package br.com.alexandremarcondes.egginc.companion.screens

import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.*
import androidx.ui.animation.animatedFloat
import androidx.ui.core.*
import androidx.ui.foundation.*
import androidx.ui.foundation.gestures.DragDirection
import androidx.ui.foundation.gestures.draggable
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Divider
import androidx.ui.material.ListItem
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import br.com.alexandremarcondes.egginc.companion.EggIncCompanionApp
import br.com.alexandremarcondes.egginc.companion.data.IDataRepository
import br.com.alexandremarcondes.egginc.companion.data.impl.BlockingFakeDataRepository
import br.com.alexandremarcondes.egginc.companion.data.model.Egg
import br.com.alexandremarcondes.egginc.companion.data.model.User
import br.com.alexandremarcondes.egginc.companion.ui.*
import ei.Ei


class CoopListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (application as EggIncCompanionApp).container

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val heightPx = displayMetrics.heightPixels
        val widthPx = displayMetrics.widthPixels

        setContent {
            EggIncCompanionTheme {
                CoopList(
                    appContainer.dataRepository,
                    false,
                    widthPx,
                    heightPx
                )
            }
        }
    }
}

@Composable
fun CoopList(
    repository: IDataRepository,
    refreshingState: Boolean,
    widthPx: Int,
    heightPx: Int
) {
    if (refreshingState) {
        Loading("coop list")
    } else {
        val (state, refreshPosts) = refreshableUiStateFrom(repository::getUsers)

        SwipeToRefreshLayout(
            refreshingState = refreshingState,
            onRefresh = { refreshPosts() },
            refreshIndicator = { RefreshIndicator() }
        ) {
            CoopListContent(state, widthPx, heightPx)
        }
    }
}

@Composable
private fun CoopListContent(
    state: RefreshableUiState<List<User>>,
    widthPx: Int, heightPx: Int
) {
    val (showSnackbarError, updateShowSnackbarError) = stateFor(state) {
        state is RefreshableUiState.Error
    }

    Stack(modifier = Modifier.fillMaxSize()) {
        state.currentData?.let { users ->
            CoopListContentBody(
                users.first().contracts!!,
                widthPx,
                heightPx
            )
        }

        ErrorSnackbar(
            text = "Could not refresh the coop list!",
            showError = showSnackbarError,
            onDismiss = { updateShowSnackbarError(false) },
            modifier = Modifier.gravity(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun CoopListContentBody(
    myContracts: Ei.MyContracts,
    widthPx: Int, heightPx: Int
) {
    Stack(modifier = Modifier.fillMaxSize()) {
        VerticalScroller(modifier = Modifier.fillMaxSize()) {
            myContracts.contractsList.forEach { contract ->
                CoopListItem(contract, widthPx, heightPx)
                Divider()
            }
        }
    }
}

@Composable
private fun CoopListItem(
    localContract: Ei.LocalContract,
    widthPx: Int, heightPx: Int
) {
    val egg = Egg.convert(localContract.contract.egg)
    val icon = imageResource(egg.resource)
    val min = -widthPx * 2 / 3
    val max = widthPx * 2 / 3
    val (minPx, maxPx) = with(DensityAmbient.current) { min.toFloat() to max.toFloat() }
    var position = animatedFloat(0f)
    position.setBounds(minPx, maxPx)

    Box (modifier = Modifier
            .fillMaxWidth()
            .draggable(enabled = true, dragDirection = DragDirection.Horizontal) { delta ->
                    // consume only delta that needed if we hit bounds
                    val old = position.value
                    position.snapTo(position.value + delta)
                    position.value - old
                }) {
        val text = if (localContract.contract.coopAllowed) {
            if (localContract.accepted) "Coop: ${localContract.coopIdentifier}" else "Contract not accepted"
        } else {
            "Solo contract"
        }
        val xOffset =  with(DensityAmbient.current) { position.value.toDp() }

        ListItem(
            modifier = Modifier.offset(x = xOffset, y = 0.dp),
            text = { Text(localContract.contract.name) },
            secondaryText = { Text(localContract.contract.description) },
            singleLineSecondaryText = true,
            overlineText = { Text(text) },
            icon = { Image(icon, modifier = Modifier.preferredSize(56.dp, 56.dp)) }
        )
    }
}

@Preview("Card", group = "Elements")
@Composable
private fun  CoopListItemPreview() {
    EggIncCompanionTheme {
        CoopListItem(
            loadFakeUsers().first().contracts!!.contractsList.first(),
            411,
            731
        )
    }
}

@Preview("Content", group = "Elements",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun  CoopListContentPreview() {
    EggIncCompanionTheme {
        CoopListContentBody(
            loadFakeUsers().first().contracts!!,
            411,
            731
        )
    }
}

@Preview("Content Dark", group = "UserList",
    widthDp = 411,
    heightDp = 731,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true)
@Composable
private fun CoopListPreview() {
    EggIncCompanionTheme(darkTheme = true) {
        CoopListContentBody(
            loadFakeUsers().first().contracts!!,
            411,
            731
        )
    }
}

@Preview("Full UI Refreshing", group = "UI",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun CoopListFullUiRefreshingPreview() {
    EggIncCompanionTheme {
        CoopList(
            BlockingFakeDataRepository.DataRepository,
            true,
            411,
            731
        )
    }
}

@Preview("Full UI Non-Refreshing", group = "UI",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun CoopListFullUiNonRefreshingPreview() {
    EggIncCompanionTheme {
        CoopList(
            BlockingFakeDataRepository.DataRepository,
            false,
            411,
            731
        )
    }
}
