package br.com.alexandremarcondes.egginc.companion.data.model

import br.com.alexandremarcondes.egginc.companion.R
import ei.Ei

enum class GoalType(val resource: Int, val goalType: Ei.GoalType) {
    EGGS_LAID(R.drawable.egg_edible, Ei.GoalType.EGGS_LAID),
    UNKNOWN_GOAL(R.drawable.egg_unknown, Ei.GoalType.UNKNOWN_GOAL);

    companion object {
        fun convert(value: Ei.GoalType) = values().lastOrNull { it.goalType == value } ?: UNKNOWN_GOAL
    }
}