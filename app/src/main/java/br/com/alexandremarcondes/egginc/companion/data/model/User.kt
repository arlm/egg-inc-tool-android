package br.com.alexandremarcondes.egginc.companion.data.model

data class User (
    var stats: ei.Ei.Backup.Stats? = null,
    var game: ei.Ei.Backup.Game? = null,
    var deviceInfo: ei.Ei.DeviceInfo? = null,
    var homeFarm: ei.Ei.Backup.Simulation? = null,
    var coopFarms: List<ei.Ei.Backup.Simulation> = emptyList(),
    var contracts: ei.Ei.MyContracts? = null,
    var userName: String = "",
    var userId: String = ""
) {
    fun applyFrom (backup: ei.Ei.Backup): User {
        apply {
            if (backup.hasStats()) this.stats = backup.stats
            if (backup.hasGame()) this.game = backup.game
            if (backup.hasSim()) this.homeFarm = backup.sim
            if (backup.farmsCount > 0) this.coopFarms = backup.farmsList
            if (backup.hasContracts()) this.contracts = backup.contracts
            if (backup.hasUserName()) this.userName = backup.userName
            if (backup.hasUserId()) this.userId = backup.userId
        }

        return this
    }

    fun applyFrom (deviceInfo: ei.Ei.DeviceInfo): User {
        apply {
            if (deviceInfo.isInitialized) this.deviceInfo = deviceInfo
        }

        return this
    }
}