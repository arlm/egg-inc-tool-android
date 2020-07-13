package br.com.alexandremarcondes.egginc.companion.screens

import android.content.Context
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
import androidx.ui.res.imageResource
import androidx.ui.text.font.FontWeight
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import br.com.alexandremarcondes.egginc.companion.EggIncCompanionApp
import br.com.alexandremarcondes.egginc.companion.R
import br.com.alexandremarcondes.egginc.companion.data.IAppContainer
import br.com.alexandremarcondes.egginc.companion.data.impl.fakeLegacyContractsContracts
import br.com.alexandremarcondes.egginc.companion.data.impl.user1
import br.com.alexandremarcondes.egginc.companion.data.model.Egg
import br.com.alexandremarcondes.egginc.companion.data.model.LegacyContracts
import br.com.alexandremarcondes.egginc.companion.data.model.Magnitude
import br.com.alexandremarcondes.egginc.companion.data.model.User
import br.com.alexandremarcondes.egginc.companion.ui.*
import br.com.alexandremarcondes.egginc.companion.util.Constants
import br.com.alexandremarcondes.egginc.companion.util.formatMagnitude

class UserActivity : AppCompatActivity() {
    private var activityContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityContext = this
        val appContainer = (application as EggIncCompanionApp).container

        setContent {
            EggIncCompanionTheme {
                UserData(
                    appContainer,
                    false
                )
            }
        }
    }
}

@Composable
fun UserData(appContainer: IAppContainer, refreshingState: Boolean) {
    if (refreshingState) {
        Loading("user data")
    } else {
        val (state, refreshPosts) = refreshableUiStateFrom(appContainer.dataRepository::getUsers)

        SwipeToRefreshLayout(
            refreshingState = refreshingState,
            onRefresh = { refreshPosts() },
            refreshIndicator = { RefreshIndicator() }
        ) {
            UserContent(state, appContainer.legacyContracts)
        }
    }
}

@Composable
private fun UserContent(state: RefreshableUiState<List<User>>, legacyContracts: LegacyContracts) {
    val (showSnackbarError, updateShowSnackbarError) = stateFor(state) {
        state is RefreshableUiState.Error
    }

    Stack(modifier = Modifier.fillMaxSize()) {
            UserContentBody(state.currentData!!.first(), legacyContracts)

        ErrorSnackbar(
            text = "Could not refresh the user list!",
            showError = showSnackbarError,
            onDismiss = { updateShowSnackbarError(false) },
            modifier = Modifier.gravity(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun UserContentBody(user: User, legacyContracts: LegacyContracts) {
    Stack(modifier = Modifier.fillMaxSize()) {
        VerticalScroller(modifier = Modifier.fillMaxSize()) {
            UserTitle(user)
            CurrentContracts(user, legacyContracts)
            ArchivedContracts(user, legacyContracts)
            Trophies(user)
        }
    }
}

@Composable
private fun UserTitle(user: User) {
    val egg = Egg.convert(user.game!!.maxEggReached)
    val icon = imageResource(egg.resource)
    val soulEgg = imageResource(R.drawable.egg_soul)
    val prophecyEgg = imageResource(R.drawable.egg_of_prophecy)

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        val (name, maxEgg, soulEggImg, soulEggs, prophecyEggImg, prophecyEggs, hasHyperloopStation, ranking) = createRefs()

        Text(user.userName,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .constrainAs(name) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )

        Image(icon, modifier = Modifier
            .preferredSize(56.dp, 56.dp)
            .constrainAs(maxEgg) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
        )

        Text(Magnitude.fromDouble(user.earningsBonus).description,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End,
            modifier = Modifier
                .constrainAs(ranking) {
                    top.linkTo(parent.top)
                    start.linkTo(name.end, margin = 5.dp)
                    end.linkTo(maxEgg.start, margin = 5.dp)
                }
        )

        Image(soulEgg, modifier = Modifier
            .preferredSize(24.dp, 24.dp)
            .constrainAs(soulEggImg) {
                top.linkTo(name.bottom)
                end.linkTo(ranking.start)
            }
        )

        Text(user.game?.soulEggsD?.formatMagnitude() ?: "0",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .constrainAs(soulEggs) {
                    top.linkTo(name.bottom)
                    start.linkTo(soulEggImg.end)
                }
        )

        Image(prophecyEgg, modifier = Modifier
            .preferredSize(24.dp, 24.dp)
            .constrainAs(prophecyEggImg) {
                top.linkTo(name.bottom)
                start.linkTo(soulEggs.end, margin = 5.dp)
            }
        )

        Text(user.game?.eggsOfProphecy?.formatMagnitude() ?: "0",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .constrainAs(prophecyEggs) {
                    top.linkTo(name.bottom)
                    start.linkTo(prophecyEggImg.end)
                }
        )
    }
}

@Composable
private fun CurrentContracts(user: User, legacyContracts: LegacyContracts) {
    val doneContracts = legacyContracts.currentDoneContracts(user.contracts?.archiveList ?: emptyList())
    val currentContracts = legacyContracts.currentContracts()

    Text("${doneContracts.size}/${currentContracts.size} current contracts",
        modifier = Modifier.padding(5.dp)
    )
}

@Composable
private fun ArchivedContracts(user: User, legacyContracts: LegacyContracts) {
    val doneContracts = legacyContracts.doneContracts(user.contracts?.archiveList ?: emptyList())
    val legacyContractsCount = legacyContracts.archivedContracts().count()

    Text("${doneContracts.size}/${legacyContractsCount} contracts realized",
        modifier = Modifier.padding(5.dp)
    )
}

@Composable
private fun Trophies(user: User) {
    val allAchieved = user.game?.eggMedalLevelList?.all { it >= 5 } ?: false
    val toAchieve =  Constants.TOTAL_EGGS - (user.game?.eggMedalLevelList?.count { it >= 5 } ?: 0)
    val text = if (allAchieved) "All trophies acquired" else "Missing $toAchieve trophies"

    Text(text,
        modifier = Modifier.padding(5.dp)
    )
}

@Preview("Title", group = "Parts",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun UserTitlePreview() {
    EggIncCompanionTheme {
        UserTitle(user1)
    }
}

@Preview("Current Contracts", group = "Parts",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun CurrentContractsPreview() {
    EggIncCompanionTheme {
        CurrentContracts(user1, LegacyContracts(fakeLegacyContractsContracts) )
    }
}

@Preview("Archived Contracts", group = "Parts",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun ArchivedContractsPreview() {
    EggIncCompanionTheme {
        ArchivedContracts(user1, LegacyContracts(fakeLegacyContractsContracts))
    }
}

@Preview("Trophies", group = "Parts",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun TrophiesPreview() {
    EggIncCompanionTheme {
        Trophies(user1)
    }
}

@Preview("Full UI Non-Refreshing", group = "UI",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun UserFullUiNonRefreshingPreview() {
    EggIncCompanionTheme {
        UserContentBody(user1, LegacyContracts(fakeLegacyContractsContracts))
    }
}
