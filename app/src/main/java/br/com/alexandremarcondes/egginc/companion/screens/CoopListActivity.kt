package br.com.alexandremarcondes.egginc.companion.screens

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.stateFor
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
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

class CoopListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (application as EggIncCompanionApp).container

        setContent {
            EggIncCompanionTheme {
                CoopList(
                    appContainer.dataRepository,
                    false
                )
            }
        }
    }
}

@Composable
fun CoopList(repository: IDataRepository, refreshingState: Boolean) {
    if (refreshingState) {
        Loading("coop list")
    } else {
        val (state, refreshPosts) = refreshableUiStateFrom(repository::getUsers)

        SwipeToRefreshLayout(
            refreshingState = refreshingState,
            onRefresh = { refreshPosts() },
            refreshIndicator = { RefreshIndicator() }
        ) {
            CoopListContent(
                state
            )
        }
    }
}

@Composable
private fun CoopListContent(state: RefreshableUiState<List<User>>) {
    val (showSnackbarError, updateShowSnackbarError) = stateFor(state) {
        state is RefreshableUiState.Error
    }

    Stack(modifier = Modifier.fillMaxSize()) {
        state.currentData?.let { users ->
            CoopListContentBody(
                users.first().contracts!!
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
private fun CoopListContentBody(myContracts: ei.Ei.MyContracts) {
    Stack(modifier = Modifier.fillMaxSize()) {
        VerticalScroller(modifier = Modifier.fillMaxSize()) {
            myContracts.contractsList.forEach { contract ->
                CoopListItem(contract)
                Divider()
            }
        }
    }
}

@Composable
private fun CoopListItem(localContract: ei.Ei.LocalContract) {
    val egg = Egg.convert(localContract.contract.egg) ?: Egg.UNKNOWN
    val icon = imageResource(egg.resource)

    if (localContract.contract.coopAllowed) {
        val coopText = if (localContract.accepted) "Coop: ${localContract.coopIdentifier}" else "Contract not accepted"
        ListItem(
            text = { Text(localContract.contract.name) },
            secondaryText = { Text(localContract.contract.description) },
            singleLineSecondaryText = true,
            overlineText = { Text(coopText) },
            icon = { Image(icon, modifier = Modifier.preferredSize(56.dp, 56.dp) ) }
        )

    } else {
        ListItem(
            text = { Text(localContract.contract.name) },
            secondaryText = { Text(localContract.contract.description) },
            singleLineSecondaryText = true,
            overlineText = { Text("Solo contract") },
            icon = { Image(icon, modifier = Modifier.preferredSize(56.dp, 56.dp) ) }
        )
    }
}


@Preview("Card", group = "Elements")
@Composable
private fun  CoopListItemPreview() {
    EggIncCompanionTheme {
        CoopListItem(
            loadFakeUsers().first().contracts!!.contractsList.first()
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
            loadFakeUsers().first().contracts!!
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
            loadFakeUsers().first().contracts!!
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
            true
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
            false
        )
    }
}
