package br.com.alexandremarcondes.egginc.companion.data.impl

import android.content.Context
import android.os.Looper
import android.os.Handler
import br.com.alexandremarcondes.egginc.companion.data.AppDatabase
import br.com.alexandremarcondes.egginc.companion.data.IAppContainer
import br.com.alexandremarcondes.egginc.companion.data.IDataRepository
import br.com.alexandremarcondes.egginc.companion.data.model.LegacyContracts
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class AppContainerImpl(private val applicationContext: Context) : IAppContainer {
    private val executorService: ExecutorService by lazy {
        Executors.newFixedThreadPool(4)
    }

    private val mainThreadHandler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

     // Rooms Database instance used by the rest of classes to obtain dependencies
     override val database: AppDatabase by lazy {
         AppDatabase.getDatabase(applicationContext)
    }

    override val legacyContracts: LegacyContracts by lazy {
        LegacyContracts(applicationContext)
    }

    // Fake data repository
    override val dataRepository: IDataRepository by lazy {
        FakeDataRepository(
            executorService = executorService,
            resultThreadHandler = mainThreadHandler,
            resources = applicationContext.resources
        )
    }
}