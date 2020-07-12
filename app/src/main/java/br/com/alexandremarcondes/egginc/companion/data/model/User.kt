package br.com.alexandremarcondes.egginc.companion.data.model

import kotlin.math.floor
import kotlin.math.pow

data class User (
    var stats: ei.Ei.Backup.Stats? = null,
    var game: ei.Ei.Backup.Game? = null,
    var homeFarm: ei.Ei.Backup.Simulation? = null,
    var coopFarms: List<ei.Ei.Backup.Simulation> = emptyList(),
    var contracts: ei.Ei.MyContracts? = null,
    var userName: String = "",
    var userId: String = ""
) {
    constructor (backup: ei.Ei.Backup): this() {
        apply {
            if (backup.hasStats()) this.stats = backup.stats
            if (backup.hasGame()) this.game = backup.game
            if (backup.hasSim()) this.homeFarm = backup.sim
            if (backup.farmsCount > 0) this.coopFarms = backup.farmsList
            if (backup.hasContracts()) this.contracts = backup.contracts
            if (backup.hasUserName()) this.userName = backup.userName
            if (backup.hasUserId()) this.userId = backup.userId
        }
    }

    val bonusPerSoulEgg: Double
        get() {
            val soulFood = game?.epicResearchList?.firstOrNull { it.id == "soul_eggs" }?.level ?: 0
            val prophecyBonus = game?.epicResearchList?.firstOrNull { it.id == "prophecy_bonus" }?.level ?: 0
            val prophecyEggs = game?.eggsOfProphecy?.toDouble() ?: 0.0
            val soulEggPercent = 10 + soulFood
            val prophecyEggPercent = (1.05 + (prophecyBonus / 100.0)).pow(prophecyEggs)

            return floor(soulEggPercent * prophecyEggPercent)
        }

    val earningsBonus: Double
        get() {
            val soulEggs = game?.soulEggsD ?: 0.0

            return floor(soulEggs * bonusPerSoulEgg)
        }

    val ranking: Magnitude
        get () = Magnitude.fromDouble(earningsBonus) ?: Magnitude.Farmer_1
}