package br.com.alexandremarcondes.egginc.companion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.layout.padding
import androidx.ui.layout.preferredSize
import androidx.ui.material.Card
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import br.com.alexandremarcondes.egginc.companion.ui.EggIncCompanionTheme
import ei.Ei

class UserListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var users = listOf<Ei.DeviceInfo>()

        setContent {
            EggIncCompanionTheme {
                UserList(users, false)
            }
        }
    }
}

@Composable
fun UserList(users: List<Ei.DeviceInfo>, refreshingState: Boolean) {
    if (refreshingState) {
        Loading()
    } else {
        UserCard(users.first())
        /*
        SwipeToRefreshLayout(refreshingState = refreshingState,
            onRefresh = {  },
            refreshIndicator = { RefreshIndicator()
            }
        ) {
            UserListContent(users)
        }
        */
    }
}

/*
@Composable
fun UserListContent(users: List<Ei.DeviceInfo>) {
    VerticalScroller(modifier = Modifier) {
        users.forEach { user ->
            UserCard(user)
        }
    }
}
*/

@Composable
fun UserCard(user: Ei.DeviceInfo) {
    Card(
        shape =  MaterialTheme.shapes.small,
        modifier = Modifier
            .preferredSize(280.dp, 80.dp)
            .padding(4.dp),
        elevation = 6.dp) {
        Text(text = "ID: ${user.deviceId}")
    }
}

@Composable
fun Loading() {
    Text("Loading...")
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

@Preview("Card", group = "UserList")
@Composable
private fun  UserCardPreview() {
    EggIncCompanionTheme {
        UserCard(fakeUsers[0])
    }
}

/*
@Preview("Content", group = "UserList")
@Composable
private fun  UserListContentPreview() {
    EggIncCompanionTheme {
        UserListContent(fakeUsers)
    }
}

@Preview("Non-Updating", group = "UserList")
@Composable
private fun  UpdatingUserListPreview() {
    EggIncCompanionTheme {
        UserList(fakeUsers, false)
    }
}

@Preview("Updating", group = "UserList")
@Composable
private fun UserListPreview() {
    EggIncCompanionTheme {
        UserList(fakeUsers, true)
    }
}

*/