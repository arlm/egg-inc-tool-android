package br.com.alexandremarcondes.egginc.companion.screens

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.animation.TargetAnimation
import androidx.animation.fling
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.stateFor
import androidx.ui.animation.animatedFloat
import androidx.ui.core.Alignment
import androidx.ui.core.DensityAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.*
import androidx.ui.foundation.gestures.DragDirection
import androidx.ui.foundation.gestures.draggable
import androidx.ui.foundation.shape.corner.CutCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.Divider
import androidx.ui.material.ListItem
import androidx.ui.material.MaterialTheme
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Delete
import androidx.ui.material.icons.filled.PersonAdd
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import br.com.alexandremarcondes.egginc.companion.EggIncCompanionApp
import br.com.alexandremarcondes.egginc.companion.data.IDataRepository
import br.com.alexandremarcondes.egginc.companion.data.impl.BlockingFakeDataRepository
import br.com.alexandremarcondes.egginc.companion.data.model.Egg
import br.com.alexandremarcondes.egginc.companion.data.model.User
import br.com.alexandremarcondes.egginc.companion.ui.*
import br.com.alexandremarcondes.egginc.companion.util.getDimensions
import ei.Ei

class CoopListActivity : AppCompatActivity() {
    private val navigationViewModel by viewModels<NavigationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (application as EggIncCompanionApp).container

        val windowSize = getDimensions(windowManager)

        setContent {
            EggIncCompanionTheme {
                CoopList(
                    repository =  appContainer.dataRepository,
                    refreshingState =  false,
                    widthPx = windowSize.width,
                    heightPx = windowSize.height,
                    navigateTo = navigationViewModel::navigateTo
                )
            }
        }
    }
}

@Composable
fun CoopList(
    repository: IDataRepository,
    refreshingState: Boolean,
    widthPx: Int,
    heightPx: Int,
    navigateTo: (Screen) -> Unit
) {
    if (refreshingState) {
        Loading("coop list")
    } else {
        val (state, refreshUsers) = refreshableUiStateFrom(repository::getUsers)

        SwipeToRefreshLayout(
            refreshingState = refreshingState,
            onRefresh = { refreshUsers() },
            refreshIndicator = { RefreshIndicator() }
        ) {
            CoopListContent(state, widthPx, heightPx, navigateTo)
        }
    }
}

@Composable
private fun CoopListContent(
    state: RefreshableUiState<List<User>>,
    widthPx: Int, heightPx: Int,
    navigateTo: (Screen) -> Unit
) {
    val (showSnackbarError, updateShowSnackbarError) = stateFor(state) {
        state is RefreshableUiState.Error
    }

    Stack(modifier = Modifier.fillMaxSize()) {
        state.currentData?.let { users ->
            CoopListContentBody(
                users.first().contracts!!,
                widthPx, heightPx,
                navigateTo
            )
        }

        ErrorSnackbar(
            text = "Could not refresh the coop list!",
            showError = showSnackbarError,
            onDismiss = { updateShowSnackbarError(false) },
            modifier = Modifier.gravity(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun CoopListContentBody(
    myContracts: Ei.MyContracts,
    widthPx: Int, heightPx: Int,
    navigateTo: (Screen) -> Unit
) {
    Stack(modifier = Modifier.fillMaxSize()) {
        VerticalScroller(modifier = Modifier.fillMaxSize()) {
            myContracts.contractsList.forEach { contract ->
                CoopListItem(contract, widthPx, heightPx, navigateTo)
                Divider(color = if (MaterialTheme.colors.isLight) Color.LightGray else Color.DarkGray )
            }
        }
    }
}

@Composable
private fun CoopListItem(
    localContract: Ei.LocalContract,
    widthPx: Int, heightPx: Int,
    navigateTo: (Screen) -> Unit
) {
    val egg = Egg.convert(localContract.contract.egg)
    val icon = imageResource(egg.resource)
    val size = widthPx * 2 / 3
    val draggingLimit = size / 2
    val buttonWidth = widthPx / 3 * 0.75
    val (minPx, maxPx) = with(DensityAmbient.current) { (-size).toFloat() to size.toFloat() }
    val position = animatedFloat(0f)
    var oldPosition = 0f

    Stack (modifier = Modifier
            .fillMaxWidth()
            .preferredHeight(88.dp)
            .draggable(
                dragDirection = DragDirection.Horizontal,
                onDragStarted = { oldPosition = position.value },
                onDragStopped = {
                    var target = when {
                        position.targetValue < 0 && position.targetValue > -draggingLimit -> 0f
                        position.targetValue <= -draggingLimit -> if (oldPosition == (-size).toFloat()) 0f else (-size).toFloat()
                        position.targetValue > 0 && position.targetValue < draggingLimit -> 0f
                        else -> if (oldPosition == size.toFloat()) 0f else (size).toFloat()
                    }

                    position.fling(1f,
                        adjustTarget = { TargetAnimation(target) }
                    )
                }
            ) { delta ->
                    // consume only delta that needed if we hit bounds
                    val old = position.value
                    position.snapTo((position.value + delta).coerceIn(minPx, maxPx))
                    position.value - old
            }
    ) {
        val text = if (localContract.contract.coopAllowed) {
            if (localContract.accepted) "Coop: ${localContract.coopIdentifier}" else "Contract not accepted"
        } else {
            "Solo contract"
        }

        val xOffset =  with(DensityAmbient.current) { position.value.toDp() }

        ConstraintLayout(modifier = Modifier.fillMaxSize().drawBackground(Color.Yellow)) {
            val (leftButton, rightButton) = createRefs()
            Button(
                modifier = Modifier
                    .preferredWidth(buttonWidth.dp)
                    .constrainAs(leftButton) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        height = Dimension.fillToConstraints
                    },
                backgroundColor = Color.Red,
                shape = CutCornerShape(0.dp),
                onClick = {}
            ) {
                Column {
                    Icon(Icons.Filled.Delete,
                        tint = Color.White,
                        modifier = Modifier.gravity(Alignment.CenterHorizontally))
                    Text("Remove",
                        color = Color.White,
                        modifier = Modifier.gravity(Alignment.CenterHorizontally))
                }
            }
            Spacer(modifier = Modifier)
            Button(
                modifier = Modifier
                    .width(buttonWidth.dp)
                    .fillMaxHeight()
                    .constrainAs(rightButton) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
                backgroundColor = Color(0xFF2b9348),
                shape = CutCornerShape(0.dp),
                onClick = {}
                ) {
                Column {
                    Icon(Icons.Filled.PersonAdd,
                        tint = Color.White,
                        modifier = Modifier.gravity(Alignment.CenterHorizontally))
                    Text("Create\nGroups",
                        color = Color.White,
                        modifier = Modifier.gravity(Alignment.CenterHorizontally))
                }
            }
        }
        ListItem(
            modifier = Modifier
                .offset(x = xOffset, y = 0.dp)
                .drawBackground(MaterialTheme.colors.background)
                .clickable(onClick = { navigateTo(Screen.Coop(localContract.coopIdentifier, localContract.contract.identifier)) }),
            text = { Text(localContract.contract.name,
                color = MaterialTheme.colors.onBackground) },
            secondaryText = { Text(localContract.contract.description,
                color = MaterialTheme.colors.onBackground) },
            singleLineSecondaryText = true,
            overlineText = { Text(text,
                color = MaterialTheme.colors.onBackground) },
            icon = { Image(icon, modifier = Modifier.preferredSize(56.dp, 56.dp)) }
        )
    }
}

@Preview("Card", group = "Elements")
@Composable
private fun  CoopListItemPreview() {
    EggIncCompanionTheme {
        CoopListItem(
            loadFakeUsers().first().contracts!!.contractsList.first(),
            411,
            731
        ) {}
    }
}

@Preview("Content", group = "Elements",
    widthDp = 411,
    heightDp = 731,
    showBackground = true)
@Composable
private fun  CoopListContentPreview() {
    EggIncCompanionTheme {
        CoopListContentBody(
            loadFakeUsers().first().contracts!!,
            411,
            731
        ) {}
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
        CoopListContentBody(
            loadFakeUsers().first().contracts!!,
            411,
            731
        ) {}
    }
}
