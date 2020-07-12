package br.com.alexandremarcondes.egginc.companion.screens

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
import androidx.ui.res.imageResource
import androidx.ui.text.font.FontWeight
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import br.com.alexandremarcondes.egginc.companion.EggIncCompanionApp
import br.com.alexandremarcondes.egginc.companion.R
import br.com.alexandremarcondes.egginc.companion.data.IDataRepository
import br.com.alexandremarcondes.egginc.companion.data.impl.fakeContract1
import br.com.alexandremarcondes.egginc.companion.data.model.Contract
import br.com.alexandremarcondes.egginc.companion.data.model.ContractGoal
import br.com.alexandremarcondes.egginc.companion.ui.*
import br.com.alexandremarcondes.egginc.companion.util.*

class CoopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (application as EggIncCompanionApp).container

        setContent {
            EggIncCompanionTheme {
                CoopData(
                    appContainer.dataRepository,
                    false
                )
            }
        }
    }
}

@Composable
fun CoopData(repository: IDataRepository, refreshingState: Boolean) {
    if (refreshingState) {
        Loading("coop data")
    } else {
        val (state, refreshPosts) = refreshableUiStateFrom(repository::getContracts)

        SwipeToRefreshLayout(
            refreshingState = refreshingState,
            onRefresh = { refreshPosts() },
            refreshIndicator = { RefreshIndicator() }
        ) {
            CoopContent(
                state
            )
        }
    }
}

@Composable
private fun CoopContent(state: RefreshableUiState<List<Contract>>) {
    val (showSnackbarError, updateShowSnackbarError) = stateFor(state) {
        state is RefreshableUiState.Error
    }

    Stack(modifier = Modifier.fillMaxSize()) {
        CoopContentBody(state.currentData!!.first())

        ErrorSnackbar(
            text = "Could not refresh the user list!",
            showError = showSnackbarError,
            onDismiss = { updateShowSnackbarError(false) },
            modifier = Modifier.gravity(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun CoopContentBody(contract: Contract) {
    Stack(modifier = Modifier.fillMaxSize()) {
        VerticalScroller(modifier = Modifier.fillMaxSize()) {
            CoopTitle(contract)
            CoopGoals(contract)
            CoopFooter(contract)
        }
    }
}

@Composable
private fun CoopTitle(contract: Contract) {
    val icon = imageResource(contract.egg.resource)

    ConstraintLayout(modifier = Modifier.fillMaxWidth()){
        val (egg, title, description) = createRefs()

        Image(icon, modifier = Modifier
            .preferredSize(56.dp, 56.dp)
            .constrainAs(egg) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        )
        Text(contract.name,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
            .constrainAs(title) {
                top.linkTo(egg.top)
                start.linkTo(egg.end, margin = 5.dp)
            }
        )
        Text(contract.description,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(description) {
                top.linkTo(title.bottom)
                start.linkTo(egg.end, margin = 5.dp)
            }
        )
    }
}

@Composable
private fun CoopFooter(contract: Contract) {
    val coopText = if (contract.coopAllowed) "Max ${contract.maxCoopSize} members" else "Solo contract"

    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)){
        val (coop, tokens, timing, expires) = createRefs()

        Text(coopText,
            modifier = Modifier
                .constrainAs(coop) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )
        Text("${contract.lengthSeconds.formatSeconds()} per Token",
            modifier = Modifier
                .constrainAs(tokens) {
                    top.linkTo(coop.bottom)
                    end.linkTo(parent.end)
                }
        )
        Text("Duration: ${contract.lengthSeconds.formatSeconds()}",
            modifier = Modifier
                .constrainAs(timing) {
                    top.linkTo(coop.bottom)
                    start.linkTo(parent.start)
                }
        )
        Text("Expires in ${contract.expirationTime.toDateTime().fromNow()}",
            modifier = Modifier
                .constrainAs(expires) {
                    top.linkTo(timing.bottom)
                    start.linkTo(parent.start)
                }
        )
    }
}

@Composable
private fun CoopGoals(contract: Contract) {
    Column(modifier = Modifier.fillMaxWidth()) {
        contract.goals.forEach { goal ->
            CoopGoal(goal)
        }
    }
}

@Composable
private fun CoopGoal(goal: ContractGoal) {
    val missionResource = imageResource(R.drawable.icon_mission_complete_big)
    val rewardResource = imageResource(goal.rewardType.resource)

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ){
        val (mission, text, reward) = createRefs()

        Image(missionResource,
            modifier = Modifier
            .preferredSize(24.dp, 24.dp)
            .constrainAs(mission) {
                centerVerticallyTo(parent)
                start.linkTo(parent.start)
                width = Dimension.wrapContent
            }
        )
        Text("Deliver ${goal.targetAmount.format(0)} eggs",
            modifier = Modifier
                .constrainAs(text) {
                    start.linkTo(mission.end, margin = 5.dp)
                    end.linkTo(reward.start, margin = 15.dp)
                    top.linkTo(mission.top)
                    width = Dimension.fillToConstraints
                }
        )
        Image(rewardResource, modifier = Modifier
            .preferredSize(24.dp, 24.dp)
            .constrainAs(reward) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                width = Dimension.wrapContent
            }
        )
    }
}

@Preview("Title", group = "Parts",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun CoopTitlePreview() {
    EggIncCompanionTheme {
        CoopTitle(fakeContract1)
    }
}

@Preview("Footer", group = "Parts",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun CoopFooterPreview() {
    EggIncCompanionTheme {
        CoopFooter(fakeContract1)
    }
}

@Preview("Goal", group = "Parts",
    widthDp = 411,
    heightDp = 42,
    showBackground = true)
@Composable
private fun CoopGoalPreview() {
    EggIncCompanionTheme {
        CoopGoal(fakeContract1.goals.first())
    }
}

@Preview("Goals", group = "Parts",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun CoopGoalsPreview() {
    EggIncCompanionTheme {
        CoopGoals(fakeContract1)
    }
}


@Preview("Full UI Non-Refreshing", group = "UI",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun CoopFullUiNonRefreshingPreview() {
    EggIncCompanionTheme {
        CoopContentBody(fakeContract1)
    }
}
