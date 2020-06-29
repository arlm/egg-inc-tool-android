package br.com.alexandremarcondes.egginc.companion

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.stateFor
import androidx.ui.core.Alignment
import androidx.ui.core.ContextAmbient
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
import br.com.alexandremarcondes.egginc.companion.data.DataRepository
import br.com.alexandremarcondes.egginc.companion.data.impl.BlockingFakeDataRepository
import br.com.alexandremarcondes.egginc.companion.data.model.User
import br.com.alexandremarcondes.egginc.companion.ui.*

class CoopListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (application as EggIncCompanionApp).container

        setContent {
            EggIncCompanionTheme {
                CoopList(appContainer.dataRepository, false)
            }
        }
    }
}

@Composable
fun CoopList(repository: DataRepository, refreshingState: Boolean) {
    if (refreshingState) {
        Loading()
    } else {
        val (state, refreshPosts) = refreshableUiStateFrom(repository::getUsers)

        SwipeToRefreshLayout(
            refreshingState = refreshingState,
            onRefresh = { refreshPosts() },
            refreshIndicator = { RefreshIndicator() }
        ) {
            CoopListContent(state)
        }
    }
}

@Composable
private fun Loading() {
    Stack(modifier = Modifier.fillMaxSize()) {
        Text(
            "Loading coop list...",
            modifier = Modifier.gravity(Alignment.Center)
        )
    }
}


@Composable
private fun CoopListContent(state: RefreshableUiState<List<User>>) {
    val (showSnackbarError, updateShowSnackbarError) = stateFor(state) {
        state is RefreshableUiState.Error
    }

    Stack(modifier = Modifier.fillMaxSize()) {
        state.currentData?.let { users -> CoopListContentBody(users.first().contracts!!) }

        ErrorSnackbar(
            text = "Could not refresh the user list!",
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
    val icon = imageResource(R.drawable.egg_antimatter)

    ListItem(
        text = { Text("Three line list item with 24x24 icon") },
        secondaryText = {
            Text(
                "This is a long secondary text for the current list item " +
                        "displayed on two lines"
            )
        },
        singleLineSecondaryText = false,
        icon = { Image(icon, modifier = Modifier.preferredSize(56.dp, 56.dp) ) }
    )
}


@Preview("Card", group = "Elements")
@Composable
private fun  CoopListItemPreview() {
    EggIncCompanionTheme {
        CoopListItem(loadFakeUsers().first().contracts!!.contractsList.first())
    }
}

@Preview("Content", group = "Elements",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun  CoopListContentPreview() {
    EggIncCompanionTheme {
        CoopListContentBody(loadFakeUsers().first().contracts!!)
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
        CoopListContentBody(loadFakeUsers().first().contracts!!)
    }
}

@Preview("Full UI Refreshing", group = "UI",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun FullUiRefreshingPreview() {
    EggIncCompanionTheme {
        CoopList(BlockingFakeDataRepository(ContextAmbient.current), true)
    }
}

@Preview("Full UI Non-Refreshing", group = "UI",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun FullUiNonRefreshingPreview() {
    EggIncCompanionTheme {
        CoopList(BlockingFakeDataRepository(ContextAmbient.current), false)
    }
}

@Composable
private fun loadFakeUsers(): List<User> {
    return previewDataFrom(BlockingFakeDataRepository(ContextAmbient.current)::getUsers)
}