package br.com.alexandremarcondes.egginc.companion.screens

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.stateFor
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.clickable
import androidx.ui.layout.*
import androidx.ui.material.Card
import androidx.ui.material.MaterialTheme
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import br.com.alexandremarcondes.egginc.companion.EggIncCompanionApp
import br.com.alexandremarcondes.egginc.companion.data.IDataRepository
import br.com.alexandremarcondes.egginc.companion.data.impl.researchItem1
import br.com.alexandremarcondes.egginc.companion.data.impl.simulationContract1
import br.com.alexandremarcondes.egginc.companion.data.model.Magnitude
import br.com.alexandremarcondes.egginc.companion.data.model.User
import br.com.alexandremarcondes.egginc.companion.ui.*

class UserListActivity : AppCompatActivity() {
    private val navigationViewModel by viewModels<NavigationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (application as EggIncCompanionApp).container

        setContent {
            EggIncCompanionTheme {
                UserList(
                    repository =  appContainer.dataRepository,
                    refreshingState = false,
                    navigateTo = navigationViewModel::navigateTo
                )
            }
        }
    }
}

@Composable
fun UserList(repository: IDataRepository, refreshingState: Boolean, navigateTo: (Screen) -> Unit) {
    if (refreshingState) {
        Loading("user list")
    } else {
        val (state, refreshUsers) = refreshableUiStateFrom(repository::getUsers)

        SwipeToRefreshLayout(
            refreshingState = refreshingState,
            onRefresh = { refreshUsers() },
            refreshIndicator = { RefreshIndicator() }
        ) {
            UserListContent(state, navigateTo)
        }
    }
}

@Composable
private fun UserListContent(state: RefreshableUiState<List<User>>, navigateTo: (Screen) -> Unit) {
    val (showSnackbarError, updateShowSnackbarError) = stateFor(state) {
        state is RefreshableUiState.Error
    }

    Stack(modifier = Modifier.fillMaxSize()) {
        state.currentData?.let { users ->
            UserListContentBody(users, navigateTo)
        }

        ErrorSnackbar(
            text = "Could not refresh the user list!",
            showError = showSnackbarError,
            onDismiss = { updateShowSnackbarError(false) },
            modifier = Modifier.gravity(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun UserListContentBody(users: List<User>, navigateTo: (Screen) -> Unit) {
    Stack(modifier = Modifier.fillMaxSize()) {
        VerticalScroller(modifier = Modifier.fillMaxSize()) {
            users.forEach { user ->
                UserListItem(user, navigateTo)
            }
        }
    }
}

@Composable
private fun UserListItem(user: User, navigateTo: (Screen) -> Unit) {
    Card(
        shape =  MaterialTheme.shapes.small,
        modifier = Modifier
            .preferredHeight(80.dp)
            .fillMaxWidth()
            .padding(4.dp)
            .clickable(onClick = { navigateTo(Screen.User(user.userId)) }),
        elevation = 6.dp) {
        Column(modifier = Modifier.padding(4.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text("${user.userName} (ID: ${user.userId})",
                modifier = Modifier.weight(0.66f, true))
                Text("${Magnitude.fromDouble(user.game?.currentMultiplier ?: 0.0)}",
                modifier = Modifier.weight(0.33f, false),
                textAlign = TextAlign.Right)
            }
            Text("${user.coopFarms.count()} coops")
            Text("${user.contracts?.contractsCount ?: 0} contracts")
        }
    }
}


@Preview("Card", group = "Elements")
@Composable
private fun  UserCardPreview() {
    EggIncCompanionTheme {
        UserListItem(loadFakeUsers().first()) {}
    }
}

@Preview("Content", group = "UserList",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun  UserListContentPreview() {
    EggIncCompanionTheme {
        UserListContentBody(loadFakeUsers()) {}
    }
}

@Preview("Content Dark", group = "UserList",
    widthDp = 411,
    heightDp = 731,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true)
@Composable
private fun UserListPreview() {
    EggIncCompanionTheme(darkTheme = true) {
        UserListContentBody(loadFakeUsers()) {}
    }
}

@Preview("Test", group = "Elements",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun Test() {
    EggIncCompanionTheme {
        Column{
            Text(researchItem1.id)
            Text(simulationContract1.commonResearchList.firstOrNull()?.id ?: "null")
        }
    }
}
