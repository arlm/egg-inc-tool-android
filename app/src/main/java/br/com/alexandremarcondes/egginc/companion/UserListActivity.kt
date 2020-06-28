package br.com.alexandremarcondes.egginc.companion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.stateFor
import androidx.ui.core.Alignment
import androidx.ui.core.Alignment.Horizontal
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.layout.*
import androidx.ui.layout.ColumnScope.gravity
import androidx.ui.material.Card
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import br.com.alexandremarcondes.egginc.companion.data.DataRepository
import br.com.alexandremarcondes.egginc.companion.data.impl.BlockingFakeDataRepository
import br.com.alexandremarcondes.egginc.companion.data.model.FarmerLevel
import br.com.alexandremarcondes.egginc.companion.data.model.User
import br.com.alexandremarcondes.egginc.companion.ui.*
import ei.Ei

class UserListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (application as EggIncCompanionApp).container

        setContent {
            EggIncCompanionTheme {
                UserList(appContainer.usersRepository, false)
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
private fun UserListContent(state: RefreshableUiState<List<User>>) {
    val (showSnackbarError, updateShowSnackbarError) = stateFor(state) {
        state is RefreshableUiState.Error
    }

    Stack(modifier = Modifier.fillMaxSize()) {
        state.currentData?.let { users -> UserListContentBody(users) }

        ErrorSnackbar(
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
                UserCard(user)
            }
        }
    }
}

@Composable
private fun UserCard(user: User) {
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

@Composable
private fun Loading() {
    Stack(modifier = Modifier.fillMaxSize()) {
        Text(
            "Loading...",
            modifier = Modifier.gravity(Alignment.Center)
        )
    }
}

@Composable
private fun RefreshIndicator() {
    Surface(elevation = 10.dp, shape = CircleShape) {
        CircularProgressIndicator(Modifier.preferredSize(50.dp).padding(4.dp))
    }
}

@Preview("Indicator" , group = "Refresh")
@Composable
private fun  RefreshIndicatorPreview() {
    EggIncCompanionTheme {
        RefreshIndicator()
    }
}

@Preview("Card", group = "Elements")
@Composable
private fun  UserCardPreview() {
    EggIncCompanionTheme {
        UserCard(loadFakeUsers().first())
    }
}

@Preview("Content", group = "Elements")
@Composable
private fun  UserListContentPreview() {
    EggIncCompanionTheme {
        UserListContentBody(loadFakeUsers())
    }
}

@Preview("Content Dark", group = "UserList")
@Composable
private fun UserListPreview() {
    EggIncCompanionTheme(darkTheme = true) {
        UserListContentBody(loadFakeUsers())
    }
}

@Preview("Full UI Refreshing", group = "UI")
@Composable
private fun FullUiRefreshingPreview() {
    EggIncCompanionTheme {
        UserList(BlockingFakeDataRepository(ContextAmbient.current), true)
    }
}

@Preview("Full UI Non-Refreshing", group = "UI")
@Composable
private fun FullUiNonRefreshingPreview() {
    EggIncCompanionTheme {
        UserList(BlockingFakeDataRepository(ContextAmbient.current), false)
    }
}

@Composable
private fun loadFakeUsers(): List<User> {
    return previewDataFrom(BlockingFakeDataRepository(ContextAmbient.current)::getUsers)
}