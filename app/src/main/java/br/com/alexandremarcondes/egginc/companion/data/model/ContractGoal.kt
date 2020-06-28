package br.com.alexandremarcondes.egginc.companion.data.model

data class ContractGoal (
    var type: ei.Ei.GoalType = ei.Ei.GoalType.UNKNOWN_GOAL,
    var targetAmount: Double = 0.0,
    var rewardType: ei.Ei.RewardType = ei.Ei.RewardType.UNKNOWN_REWARD,
    var rewardSubType: String = "",
    var rewardAmount :  Double = 0.0,
    var targetSoulEggs: Double = 0.0
) {
    constructor(goal: ei.Ei.Contract.Goal) : this() {
        apply {
            if (goal.hasType()) this.type = goal.type
            if (goal.hasTargetAmount()) this.targetAmount = goal.targetAmount
            if (goal.hasRewardType()) this.rewardType = goal.rewardType
            if (goal.hasRewardSubType()) this.rewardSubType = goal.rewardSubType
            if (goal.hasRewardAmount()) this.rewardAmount = goal.rewardAmount
            if (goal.hasTargetSoulEggs()) this.targetSoulEggs = goal.targetSoulEggs
        }
    }
}