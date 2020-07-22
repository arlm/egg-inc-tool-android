package br.com.alexandremarcondes.egginc.companion.data.composable


import androidx.compose.*
import br.com.alexandremarcondes.egginc.companion.data.IDataRepository
import br.com.alexandremarcondes.egginc.companion.data.Result
import br.com.alexandremarcondes.egginc.companion.data.model.Coop
import br.com.alexandremarcondes.egginc.companion.ui.RefreshableUiState
import br.com.alexandremarcondes.egginc.companion.ui.currentData

/**
 * Effect that interacts with the repository to obtain a coop with coopId and contractId to display on the screen.
 *
 * To load asynchronous data, effects are better pattern than using @Model classes since
 * effects are Compose lifecycle aware.
 *
 * FIXME: Is it possible to reuse uiStateFrom for this use case?
 */
@Composable
fun fetchCoop(coopId: String, contractId: String, repository: IDataRepository): RefreshableUiState<Coop> {
    var state: RefreshableUiState<Coop> by state<RefreshableUiState<Coop>> { RefreshableUiState.Success(data = null, loading = true) }

    // Whenever this effect is used in a composable function, it'll load data from the repository
    // when the first composition is applied
    onCommit(contractId, repository) {
        repository.getCoop(coopId, contractId) { result ->
            state = when (result) {
                is Result.Success -> {
                    if (result.data != null) {
                        RefreshableUiState.Success(result.data, loading = false)
                    } else {
                        RefreshableUiState.Error(Exception("coopId and matching contractId doesn't exist"), previousData = state.currentData)
                    }
                }
                is Result.Error -> RefreshableUiState.Error(result.exception, previousData = state.currentData)
            }
        }
    }

    return state
}

fun refreshCoop(coopId: String, contractId: String, repository: IDataRepository): RefreshableUiState<Coop> {
    var state: RefreshableUiState<Coop> = RefreshableUiState.Success(data = null, loading = true)

    repository.getCoop(coopId, contractId) { result ->
        state = when (result) {
            is Result.Success -> {
                if (result.data != null) {
                    RefreshableUiState.Success(result.data, loading = false)
                } else {
                    RefreshableUiState.Error(Exception("coopId and matching contractId doesn't exist"), previousData = state.currentData)
                }
            }
            is Result.Error -> RefreshableUiState.Error(result.exception, previousData = state.currentData)
        }
    }

    return state
}