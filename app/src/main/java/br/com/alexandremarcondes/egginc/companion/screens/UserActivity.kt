package br.com.alexandremarcondes.egginc.companion.screens

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
import androidx.ui.layout.Column
import androidx.ui.layout.Stack
import androidx.ui.layout.fillMaxSize
import androidx.ui.tooling.preview.Preview
import br.com.alexandremarcondes.egginc.companion.EggIncCompanionApp
import br.com.alexandremarcondes.egginc.companion.data.DataRepository
import br.com.alexandremarcondes.egginc.companion.data.impl.BlockingFakeDataRepository
import br.com.alexandremarcondes.egginc.companion.data.impl.researchItem1
import br.com.alexandremarcondes.egginc.companion.data.impl.simulationContract1
import br.com.alexandremarcondes.egginc.companion.data.model.User
import br.com.alexandremarcondes.egginc.companion.ui.*

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (application as EggIncCompanionApp).container

        setContent {
            EggIncCompanionTheme {
                UserData(
                    appContainer.dataRepository,
                    false
                )
            }
        }
    }
}

@Composable
fun UserData(repository: DataRepository, refreshingState: Boolean) {
    if (refreshingState) {
        Loading("user data")
    } else {
        val (state, refreshPosts) = refreshableUiStateFrom(repository::getUsers)

        SwipeToRefreshLayout(
            refreshingState = refreshingState,
            onRefresh = { refreshPosts() },
            refreshIndicator = { RefreshIndicator() }
        ) {
            UserContent(
                state
            )
        }
    }
}

@Composable
private fun UserContent(state: RefreshableUiState<List<User>>) {
    val (showSnackbarError, updateShowSnackbarError) = stateFor(state) {
        state is RefreshableUiState.Error
    }

    Stack(modifier = Modifier.fillMaxSize()) {
        state.currentData?.let { users ->
            UserContentBody(
                users
            )
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
private fun UserContentBody(users: List<User>) {
    Stack(modifier = Modifier.fillMaxSize()) {
        VerticalScroller(modifier = Modifier.fillMaxSize()) {

        }
    }
}

@Preview("Full UI Non-Refreshing", group = "UI",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun UserFullUiNonRefreshingPreview() {
    EggIncCompanionTheme {
        UserList(
            BlockingFakeDataRepository(ContextAmbient.current),
            false
        )
    }
}

@Preview("Test",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun UserTest() {
    EggIncCompanionTheme {
        Column{
            Text(researchItem1.id)
            Text(simulationContract1.commonResearchList.firstOrNull()?.id ?: "null")
        }
    }
}
