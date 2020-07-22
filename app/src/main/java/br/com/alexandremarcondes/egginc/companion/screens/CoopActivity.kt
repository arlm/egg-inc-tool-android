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
import br.com.alexandremarcondes.egginc.companion.data.composable.fetchCoop
import br.com.alexandremarcondes.egginc.companion.data.composable.refreshCoop
import br.com.alexandremarcondes.egginc.companion.data.impl.coop1
import br.com.alexandremarcondes.egginc.companion.data.impl.fakeCoops
import br.com.alexandremarcondes.egginc.companion.data.model.ContractGoal
import br.com.alexandremarcondes.egginc.companion.data.model.Coop
import br.com.alexandremarcondes.egginc.companion.ui.*
import br.com.alexandremarcondes.egginc.companion.util.format
import br.com.alexandremarcondes.egginc.companion.util.formatSeconds
import br.com.alexandremarcondes.egginc.companion.util.fromNow
import br.com.alexandremarcondes.egginc.companion.util.toDateTime

class CoopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (application as EggIncCompanionApp).container
        val coop: Coop = fakeCoops.first()

        setContent {
            EggIncCompanionTheme {
                CoopData(
                    appContainer.dataRepository,
                    false,
                    coop.identifier,
                    coop.contract.identifier
                )
            }
        }
    }
}

@Composable
fun CoopData(repository: IDataRepository, refreshingState: Boolean,
             coopId: String, contractId: String) {
    if (refreshingState) {
        Loading("coop data")
    } else {
        var coopState = fetchCoop(coopId, contractId, repository)

        SwipeToRefreshLayout(
            refreshingState = refreshingState,
            onRefresh = { coopState = refreshCoop(coopId, contractId, repository) },
            refreshIndicator = { RefreshIndicator() }
        ) {
            CoopContent(coopState)
        }
    }
}

@Composable
private fun CoopContent(state: RefreshableUiState<Coop>) {
    val (showSnackbarError, updateShowSnackbarError) = stateFor(state) {
        state is RefreshableUiState.Error
    }

    Stack(modifier = Modifier.fillMaxSize()) {
        CoopContentBody(state.currentData)

        ErrorSnackbar(
            text = "Could not refresh the user list!",
            showError = showSnackbarError,
            onDismiss = { updateShowSnackbarError(false) },
            modifier = Modifier.gravity(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun CoopContentBody(coop: Coop?) {
    if (coop == null) {
        Loading("coop")
    } else {
        Stack(modifier = Modifier.fillMaxSize()) {
            VerticalScroller(modifier = Modifier.fillMaxSize()) {
                CoopTitle(coop)
                CoopGoals(coop)
                CoopFooter(coop)
            }
        }
    }
}

@Composable
private fun CoopTitle(coop: Coop) {
    val icon = imageResource(coop.contract.egg.resource)

    ConstraintLayout(modifier = Modifier.fillMaxWidth()){
        val (egg, title, description) = createRefs()

        Image(icon, modifier = Modifier
            .preferredSize(56.dp, 56.dp)
            .constrainAs(egg) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        )
        Text(coop.contract.name,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
            .constrainAs(title) {
                top.linkTo(egg.top)
                start.linkTo(egg.end, margin = 5.dp)
            }
        )
        Text(coop.contract.description,
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
private fun CoopFooter(coop: Coop) {
    val coopText = if (coop.contract.coopAllowed) "Max ${coop.contract.maxCoopSize} members" else "Solo contract"

    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)){
        val (coopHead, tokens, timing, expires) = createRefs()

        Text(coopText,
            modifier = Modifier
                .constrainAs(coopHead) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )
        Text("${coop.contract.lengthSeconds.formatSeconds()} per Token",
            modifier = Modifier
                .constrainAs(tokens) {
                    top.linkTo(coopHead.bottom)
                    end.linkTo(parent.end)
                }
        )
        Text("Duration: ${coop.contract.lengthSeconds.formatSeconds()}",
            modifier = Modifier
                .constrainAs(timing) {
                    top.linkTo(coopHead.bottom)
                    start.linkTo(parent.start)
                }
        )
        Text("Expires in ${coop.contract.expirationTime.toDateTime().fromNow()}",
            modifier = Modifier
                .constrainAs(expires) {
                    top.linkTo(timing.bottom)
                    start.linkTo(parent.start)
                }
        )
    }
}

@Composable
private fun CoopGoals(coop: Coop) {
    Column(modifier = Modifier.fillMaxWidth()) {
        coop.contract.goals.forEach { goal ->
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
        CoopTitle(coop1)
    }
}

@Preview("Footer", group = "Parts",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun CoopFooterPreview() {
    EggIncCompanionTheme {
        CoopFooter(coop1)
    }
}

@Preview("Goal", group = "Parts",
    widthDp = 411,
    heightDp = 42,
    showBackground = true)
@Composable
private fun CoopGoalPreview() {
    EggIncCompanionTheme {
        CoopGoal(coop1.contract.goals.first())
    }
}

@Preview("Goals", group = "Parts",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun CoopGoalsPreview() {
    EggIncCompanionTheme {
        CoopGoals(coop1)
    }
}


@Preview("Full UI Non-Refreshing", group = "UI",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun CoopFullUiNonRefreshingPreview() {
    EggIncCompanionTheme {
        CoopContentBody(coop1)
    }
}
