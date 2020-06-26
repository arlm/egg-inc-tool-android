package br.com.alexandremarcondes.egginc.companion.ui

import androidx.compose.*
import androidx.ui.animation.animatedFloat
import androidx.ui.core.Alignment
import androidx.ui.core.DensityAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.drawLayer
import androidx.ui.foundation.Text
import androidx.ui.layout.Row
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.layout.preferredHeight
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.unit.dp
import kotlinx.coroutines.delay


@Composable
fun Snackbar(state: SnackbarState, modifier: Modifier = Modifier) {
    val height = 48.dp
    val heightPx = with(DensityAmbient.current) { height.toIntPx().value.toFloat() }
    val offset = animatedFloat(heightPx)
    var dismissing by state { false }

    onCommit(dismissing, state.currentSnack) {
        state.currentSnack ?: return@onCommit
        if (dismissing) {
            offset.animateTo(heightPx) { _, _ ->
                dismissing = false
                state.dismissCurrentSnack()
            }
        } else {
            offset.animateTo(0f)
        }
    }

    if (state.currentSnack != null) {
        // this is a prototype coroutine + compose API (see appendix for code listing)
        // It will launch a new coroutine any time `snack` changes, cancelling the previous (if any)
        launchInComposition(state.currentSnack) {
            delay(1000)
            dismissing = true
        }
        Surface(
            color = MaterialTheme.colors.secondary,
            contentColor = MaterialTheme.colors.onSecondary,
            modifier = modifier
                .fillMaxWidth()
                .preferredHeight(48.dp)
                .drawLayer(translationY = offset.value)
        ) {
            Row(verticalGravity = Alignment.CenterVertically) {
                Text(
                    state.currentSnack!!,
                    Modifier.padding(start = 16.dp, end = 16.dp)
                )
            }
        }
    }
}

interface SnackbarState {
    val currentSnack: String?
    fun dismissCurrentSnack()
}

@Composable
fun NonAnimatingSnackbar(state: SnackbarState, modifier: Modifier = Modifier) {
    state.currentSnack?.let { snack ->
        // this is a prototype coroutine + compose API (see appendix for code listing)
        // It will launch a new coroutine any time `snack` changes, cancelling the previous (if any)
        launchInComposition(snack) {
            delay(1000)
            state.dismissCurrentSnack()
        }
        Surface(
            color = MaterialTheme.colors.secondary,
            contentColor = MaterialTheme.colors.onSecondary,
            modifier = modifier
                .fillMaxWidth()
                .preferredHeight(48.dp)
        ) {
            Row(verticalGravity = Alignment.CenterVertically) {
                Text(snack, Modifier.padding(start = 16.dp, end = 16.dp))
            }
        }
    }
}

class SnackbarManager : SnackbarState {
    private var snacks: List<String> by mutableStateOf(listOf())
    override val currentSnack: String?
        get() = snacks.getOrNull(0)

    fun addSnack(snack: String) {
        snacks = snacks + listOf(snack)
    }

    override fun dismissCurrentSnack() {
        snacks = if (snacks.isNotEmpty()) snacks.drop(1) else snacks
    }
}
