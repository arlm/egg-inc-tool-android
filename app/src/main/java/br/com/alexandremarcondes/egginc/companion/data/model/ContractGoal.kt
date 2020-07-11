package br.com.alexandremarcondes.egginc.companion.data.model

data class ContractGoal (
    var type: GoalType = GoalType.UNKNOWN_GOAL,
    var targetAmount: Double = 0.0,
    var rewardType: RewardType = RewardType.UNKNOWN_REWARD,
    var rewardSubType: String = "",
    var rewardAmount :  Double = 0.0,
    var targetSoulEggs: Double = 0.0
) {
    constructor(goal: ei.Ei.Contract.Goal) : this() {
        apply {
            if (goal.hasType()) this.type = GoalType.convert(goal.type)
            if (goal.hasTargetAmount()) this.targetAmount = goal.targetAmount
            if (goal.hasRewardType()) this.rewardType = RewardType.convert(goal.rewardType)
            if (goal.hasRewardSubType()) this.rewardSubType = goal.rewardSubType
            if (goal.hasRewardAmount()) this.rewardAmount = goal.rewardAmount
            if (goal.hasTargetSoulEggs()) this.targetSoulEggs = goal.targetSoulEggs
        }
    }
}