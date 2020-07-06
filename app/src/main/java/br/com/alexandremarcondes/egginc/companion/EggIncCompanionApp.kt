package br.com.alexandremarcondes.egginc.companion

import android.app.Application
import androidx.room.Room
import br.com.alexandremarcondes.egginc.companion.data.AppContainer
import br.com.alexandremarcondes.egginc.companion.data.AppDatabase
import br.com.alexandremarcondes.egginc.companion.data.impl.AppContainerImpl

class EggIncCompanionApp : Application() {

    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var container: AppContainer

    // Rooms Database instance used by the rest of classes to obtain dependencies
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)

        //database = AppDatabase.getInMemoryDatabase(applicationContext)
        database = AppDatabase.getDatabase(applicationContext)
    }
}