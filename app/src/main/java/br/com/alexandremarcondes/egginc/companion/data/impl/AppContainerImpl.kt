package br.com.alexandremarcondes.egginc.companion.data.impl

import android.content.Context
import android.os.Looper
import android.os.Handler
import br.com.alexandremarcondes.egginc.companion.data.AppContainer
import br.com.alexandremarcondes.egginc.companion.data.DataRepository
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class AppContainerImpl(private val applicationContext: Context) : AppContainer {
    private val executorService: ExecutorService by lazy {
        Executors.newFixedThreadPool(4)
    }

    private val mainThreadHandler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

    override val dataRepository: DataRepository by lazy {
        FakeDataRepository(
            executorService = executorService,
            resultThreadHandler = mainThreadHandler,
            resources = applicationContext.resources
        )
    }
}