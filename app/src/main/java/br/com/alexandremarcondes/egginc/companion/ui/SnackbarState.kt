package br.com.alexandremarcondes.egginc.companion.ui

interface SnackbarState {
    val currentSnack: String?
    fun dismissCurrentSnack()
}