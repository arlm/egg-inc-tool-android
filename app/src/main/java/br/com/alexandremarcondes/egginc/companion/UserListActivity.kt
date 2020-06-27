package br.com.alexandremarcondes.egginc.companion

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
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.layout.*
import androidx.ui.material.Card
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import br.com.alexandremarcondes.egginc.companion.data.DataRepository
import br.com.alexandremarcondes.egginc.companion.data.impl.BlockingFakeDataRepository
import br.com.alexandremarcondes.egginc.companion.ui.*
import ei.Ei

class UserListActivity (
    val repository: DataRepository
) : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EggIncCompanionTheme {
                UserList(repository, false)
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
fun UserListContent(state: RefreshableUiState<List<Ei.DeviceInfo>>) {
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
fun UserListContentBody(users: List<Ei.DeviceInfo>) {
    Stack(modifier = Modifier.fillMaxSize()) {
        VerticalScroller(modifier = Modifier.fillMaxSize()) {
            users.forEach { user ->
                UserCard(user)
            }
        }
    }
}

@Composable
fun UserCard(user: Ei.DeviceInfo) {
    Card(
        shape =  MaterialTheme.shapes.small,
        modifier = Modifier
            .preferredHeight(80.dp)
            .fillMaxWidth()
            .padding(4.dp),
        elevation = 6.dp) {
        Column(modifier = Modifier.padding(4.dp)) {
            Text(text = "ID: ${user.deviceId}")
        }
    }
}

@Composable
fun Loading() {
    Stack(modifier = Modifier.fillMaxSize()) {
        Text(
            "Loading...",
            modifier = Modifier.gravity(Alignment.Center)
        )
    }
}

@Composable
fun RefreshIndicator() {
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
private fun loadFakeUsers(): List<Ei.DeviceInfo> {
    return previewDataFrom(BlockingFakeDataRepository(ContextAmbient.current)::getUsers)
}