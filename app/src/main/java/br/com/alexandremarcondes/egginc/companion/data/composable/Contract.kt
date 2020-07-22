package br.com.alexandremarcondes.egginc.companion.data.composable

import androidx.compose.*
import br.com.alexandremarcondes.egginc.companion.data.IDataRepository
import br.com.alexandremarcondes.egginc.companion.data.model.Contract
import br.com.alexandremarcondes.egginc.companion.ui.UiState
import br.com.alexandremarcondes.egginc.companion.data.Result

/**
 * Effect that interacts with the repository to obtain a contract with contractId to display on the screen.
 *
 * To load asynchronous data, effects are better pattern than using @Model classes since
 * effects are Compose lifecycle aware.
 *
 * FIXME: Is it possible to reuse uiStateFrom for this use case?
 */
@Composable
fun fetchContract(contractId: String, repository: IDataRepository): UiState<Contract> {
    var state: UiState<Contract> by state<UiState<Contract>> { UiState.Loading }

    // Whenever this effect is used in a composable function, it'll load data from the repository
    // when the first composition is applied
    onCommit(contractId, repository) {
        repository.getContract(contractId) { result ->
            state = when (result) {
                is Result.Success -> {
                    if (result.data != null) {
                        UiState.Success(result.data)
                    } else {
                        UiState.Error(Exception("contractId doesn't exist"))
                    }
                }
                is Result.Error -> UiState.Error(result.exception)
            }
        }
    }

    return state
}
