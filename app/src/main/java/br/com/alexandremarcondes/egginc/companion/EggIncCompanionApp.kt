package br.com.alexandremarcondes.egginc.companion

import android.app.Application
import br.com.alexandremarcondes.egginc.companion.data.IAppContainer
import br.com.alexandremarcondes.egginc.companion.data.AppDatabase
import br.com.alexandremarcondes.egginc.companion.data.impl.AppContainerImpl

class EggIncCompanionApp : Application() {

    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var container: IAppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}