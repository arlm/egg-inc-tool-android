package br.com.alexandremarcondes.egginc.companion.ui

import androidx.compose.getValue
import androidx.compose.mutableStateOf
import androidx.compose.setValue

class SnackbarManager :  SnackbarState {
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