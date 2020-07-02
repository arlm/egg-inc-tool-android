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
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.*
import androidx.ui.material.Card
import androidx.ui.material.MaterialTheme
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import br.com.alexandremarcondes.egginc.companion.data.DataRepository
import br.com.alexandremarcondes.egginc.companion.data.impl.BlockingFakeDataRepository
import br.com.alexandremarcondes.egginc.companion.data.model.FarmerLevel
import br.com.alexandremarcondes.egginc.companion.data.model.User
import br.com.alexandremarcondes.egginc.companion.data.impl.researchItem1
import br.com.alexandremarcondes.egginc.companion.data.impl.simulationContract1
import br.com.alexandremarcondes.egginc.companion.ui.*

class UserListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (application as EggIncCompanionApp).container

        setContent {
            EggIncCompanionTheme {
                UserList(appContainer.dataRepository, false)
            }
        }
    }
}

@Composable
fun UserList(repository: DataRepository, refreshingState: Boolean) {
    if (refreshingState) {
        Loading()
    } else {
        val (state, refreshPosts) = refreshableUiStateFrom(repository::getUsers)

        SwipeToRefreshLayout(
            refreshingState = refreshingState,
            onRefresh = { refreshPosts() },
            refreshIndicator = { RefreshIndicator() }
        ) {
            UserListContent(state)
        }
    }
}

@Composable
private fun Loading() {
    Stack(modifier = Modifier.fillMaxSize()) {
        Text(
            "Loading user list...",
            modifier = Modifier.gravity(Alignment.Center)
        )
    }
}

@Composable
private fun UserListContent(state: RefreshableUiState<List<User>>) {
    val (showSnackbarError, updateShowSnackbarError) = stateFor(state) {
        state is RefreshableUiState.Error
    }

    Stack(modifier = Modifier.fillMaxSize()) {
        state.currentData?.let { users -> UserListContentBody(users) }

        ErrorSnackbar(
            text = "Could not refresh the user list!",
            showError = showSnackbarError,
            onDismiss = { updateShowSnackbarError(false) },
            modifier = Modifier.gravity(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun UserListContentBody(users: List<User>) {
    Stack(modifier = Modifier.fillMaxSize()) {
        VerticalScroller(modifier = Modifier.fillMaxSize()) {
            users.forEach { user ->
                UserListItem(user)
            }
        }
    }
}

@Composable
private fun UserListItem(user: User) {
    Card(
        shape =  MaterialTheme.shapes.small,
        modifier = Modifier
            .preferredHeight(80.dp)
            .fillMaxWidth()
            .padding(4.dp),
        elevation = 6.dp) {
        Column(modifier = Modifier.padding(4.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text("${user.userName} (ID: ${user.userId})",
                modifier = Modifier.weight(0.66f, true))
                Text("${FarmerLevel.fromDouble(user.game?.currentMultiplier ?: 0.0)}",
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
        UserListItem(loadFakeUsers().first())
    }
}

@Preview("Content", group = "Elements",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun  UserListContentPreview() {
    EggIncCompanionTheme {
        UserListContentBody(loadFakeUsers())
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
        UserListContentBody(loadFakeUsers())
    }
}

@Preview("Full UI Refreshing", group = "UI",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun FullUiRefreshingPreview() {
    EggIncCompanionTheme {
        UserList(BlockingFakeDataRepository(ContextAmbient.current), true)
    }
}

@Preview("Full UI Non-Refreshing", group = "UI",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun FullUiNonRefreshingPreview() {
    EggIncCompanionTheme {
        UserList(BlockingFakeDataRepository(ContextAmbient.current), false)
    }
}

@Preview("Test",
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

@Composable
private fun loadFakeUsers(): List<User> {
    return previewDataFrom(BlockingFakeDataRepository(ContextAmbient.current)::getUsers)
}