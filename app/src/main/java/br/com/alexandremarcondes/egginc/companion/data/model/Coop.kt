package br.com.alexandremarcondes.egginc.companion.data.model

data class Coop(
    var identifier: String = "",
    var contract: Contract = Contract(),
    var accepted: Boolean = false,
    var boostsUsed: Int = 0,
    var cancelled: Boolean = false,
    var coopContributionFinalized: Boolean = false,
    var coopGracePeriodEndTime: Double = 0.0,
    var coopLastUploadedContribution: Double = 0.0,
    var coopSharedEndTime: Double = 0.0,
    var lastAmountWhenRewardGiven: Double = 0.0,
    var new: Boolean = false,
    var timeAccepted: Double = 0.0
) {
    fun applyFrom (localContract: ei.Ei.LocalContract): Coop  {
        apply {
            if (localContract.hasCoopIdentifier()) this.identifier = localContract.coopIdentifier
            if (localContract.hasContract()) this.contract = Contract().applyFrom(localContract.contract)
            if (localContract.hasAccepted()) this.accepted = localContract.accepted
            if (localContract.hasBoostsUsed()) this.boostsUsed = localContract.boostsUsed
            if (localContract.hasCancelled()) this.cancelled = localContract.cancelled
            if (localContract.hasCoopContributionFinalized()) this.coopContributionFinalized = localContract.coopContributionFinalized
            if (localContract.hasCoopGracePeriodEndTime()) this.coopGracePeriodEndTime = localContract.coopGracePeriodEndTime
            if (localContract.hasCoopLastUploadedContribution()) this.coopLastUploadedContribution = localContract.coopLastUploadedContribution
            if (localContract.hasCoopSharedEndTime()) this.coopSharedEndTime = localContract.coopSharedEndTime
            if (localContract.hasLastAmountWhenRewardGiven()) this.lastAmountWhenRewardGiven = localContract.lastAmountWhenRewardGiven
            if (localContract.hasNew()) this.new = localContract.new
            if (localContract.hasTimeAccepted()) this.timeAccepted = localContract.timeAccepted
        }

        return this
    }
}