package br.com.alexandremarcondes.egginc.companion.data.model

import br.com.alexandremarcondes.egginc.companion.R
import ei.Ei

enum class RewardType(val resource: Int, val rewardType: Ei.RewardType) {
    CASH(R.drawable.icon_cash_stack, Ei.RewardType.CASH),
    GOLD(R.drawable.golden_egg_box, Ei.RewardType.GOLD),
    SOUL_EGGS(R.drawable.egg_soul, Ei.RewardType.SOUL_EGGS),
    EGGS_OF_PROPHECY(R.drawable.egg_of_prophecy, Ei.RewardType.EGGS_OF_PROPHECY),
    EPIC_RESEARCH_ITEM(R.drawable.icon_wb_package, Ei.RewardType.EPIC_RESEARCH_ITEM),
    PIGGY_FILL(R.drawable.piggy_bank1, Ei.RewardType.PIGGY_FILL),
    PIGGY_MULTIPLIER(R.drawable.piggy_bank2, Ei.RewardType.PIGGY_MULTIPLIER),
    PIGGY_LEVEL_BUMP(R.drawable.piggy_bank7, Ei.RewardType.PIGGY_LEVEL_BUMP),
    BOOST(R.drawable.r_icon_hold_to_research, Ei.RewardType.BOOST),
    UNKNOWN_REWARD(R.drawable.egg_unknown, Ei.RewardType.UNKNOWN_REWARD);

    companion object {
        fun convert(value: Ei.RewardType) = values().lastOrNull { it.rewardType == value } ?: UNKNOWN_REWARD
    }
}