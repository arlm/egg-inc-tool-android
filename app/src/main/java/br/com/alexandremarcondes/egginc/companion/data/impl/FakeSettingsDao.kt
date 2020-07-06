package br.com.alexandremarcondes.egginc.companion.data.impl

import br.com.alexandremarcondes.egginc.companion.data.model.Settings
import br.com.alexandremarcondes.egginc.companion.data.model.SettingsDao

class FakeSettingsDao : SettingsDao {
    override fun getAll(): List<Settings> = listOf(Settings(1, "102371659776481580429"))

    override fun loadAllByIds(userIds: IntArray): List<Settings> {
        val result = mutableListOf<Settings>()

        userIds.forEach { result.add(Settings(it, "102371659776481580429")) }

        return  result
    }

    override fun findByEggIncId(eggIncId: String): Settings = Settings(1, "102371659776481580429")

    override fun insertAll(vararg settings: Settings) { }

    override fun insert(settings: Settings) { }

    override fun delete(settings: Settings) { }

}