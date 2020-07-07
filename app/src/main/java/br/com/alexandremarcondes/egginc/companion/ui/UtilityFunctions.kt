package br.com.alexandremarcondes.egginc.companion.ui

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.layout.Stack
import androidx.ui.layout.fillMaxSize

@Composable
fun Loading(message: String = "") {
    Stack(modifier = Modifier.fillMaxSize()) {
        Text(
            "Loading $message...",
            modifier = Modifier.gravity(Alignment.Center)
        )
    }
}