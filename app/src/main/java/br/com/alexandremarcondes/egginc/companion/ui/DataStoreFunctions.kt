package br.com.alexandremarcondes.egginc.companion.ui

import androidx.compose.Composable
import androidx.ui.core.ContextAmbient
import br.com.alexandremarcondes.egginc.companion.data.impl.BlockingFakeDataRepository
import br.com.alexandremarcondes.egginc.companion.data.model.User

@Composable
fun loadFakeUsers(): List<User> {
    return previewDataFrom(
        BlockingFakeDataRepository(
            ContextAmbient.current
        )::getUsers
    )
}