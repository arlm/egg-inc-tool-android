package br.com.alexandremarcondes.egginc.companion.data.model

data class Contract(
    var identifier: String = "",
    var name: String = "",
    var description: String = "",
    var egg: ei.Ei.Egg = ei.Ei.Egg.UNKNOWN,
    var goals: List<ContractGoal> = listOf(),
    var coopAllowed: Boolean = false,
    var maxCoopSize: Int = 0,
    var maxBoosts: Int = 0,
    var expirationTime: Double = 0.0,
    var lengthSeconds: Double = 0.0,
    var maxSoulEggs: Double = 0.0,
    var minClientVersion: Int = 0
) {
    constructor (contract: ei.Ei.Contract) : this() {
        apply {
            if (contract.hasIdentifier()) this.identifier = contract.identifier
            if (contract.hasName()) this.name = contract.name
            if (contract.hasDescription()) this.description = contract.description
            if (contract.hasEgg()) this.egg = contract.egg
            if (contract.goalsCount > 0) this.goals = contract.goalsList.map { ContractGoal(it) }
            if (contract.hasCoopAllowed()) this.coopAllowed = contract.coopAllowed
            if (contract.hasMaxCoopSize()) this.maxCoopSize = contract.maxCoopSize
            if (contract.hasMaxBoosts()) this.maxBoosts = contract.maxBoosts
            if (contract.hasExpirationTime()) this.expirationTime = contract.expirationTime
            if (contract.hasLengthSeconds()) this.lengthSeconds = contract.lengthSeconds
            if (contract.hasMaxSoulEggs()) this.maxSoulEggs = contract.maxSoulEggs
            if (contract.hasMinClientVersion()) this.minClientVersion = contract.minClientVersion
        }
    }
}