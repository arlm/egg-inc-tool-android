package br.com.alexandremarcondes.egginc.companion.data.model

import br.com.alexandremarcondes.egginc.companion.R
import ei.Ei

enum class Egg(val resource: Int, val egg: Ei.Egg) {
    UNKNOWN(R.drawable.egg_unknown, Ei.Egg.UNKNOWN),
    EDIBLE(R.drawable.egg_edible, Ei.Egg.EDIBLE),
    SUPERFOOD(R.drawable.egg_superfood, Ei.Egg.SUPERFOOD),
    MEDICAL(R.drawable.egg_medical, Ei.Egg.MEDICAL),
    ROCKET_FUEL(R.drawable.egg_rocketfuel, Ei.Egg.ROCKET_FUEL),
    SUPER_MATERIAL(R.drawable.egg_supermaterial, Ei.Egg.SUPER_MATERIAL),
    FUSION(R.drawable.egg_fusion, Ei.Egg.FUSION),
    QUANTUM(R.drawable.egg_quantum, Ei.Egg.QUANTUM),
    IMMORTALITY(R.drawable.egg_immortality, Ei.Egg.IMMORTALITY),
    TACHYON(R.drawable.egg_tachyon, Ei.Egg.TACHYON),
    GRAVITON(R.drawable.egg_graviton, Ei.Egg.GRAVITON),
    DILITHIUM(R.drawable.egg_dilithium, Ei.Egg.DILITHIUM),
    PRODIGY(R.drawable.egg_prodigy, Ei.Egg.PRODIGY),
    TERRAFORM(R.drawable.egg_terraform, Ei.Egg.TERRAFORM),
    ANTIMATTER(R.drawable.egg_antimatter, Ei.Egg.ANTIMATTER),
    DARK_MATTER(R.drawable.egg_darkmatter, Ei.Egg.DARK_MATTER),
    AI(R.drawable.egg_ai, Ei.Egg.AI),
    NEBULA(R.drawable.egg_vision, Ei.Egg.NEBULA),
    UNIVERSE(R.drawable.egg_universe, Ei.Egg.UNIVERSE),
    ENLIGHTENMENT(R.drawable.egg_enlightenment, Ei.Egg.ENLIGHTENMENT),
    CHOCOLATE(R.drawable.egg_chocolate, Ei.Egg.CHOCOLATE),
    EASTER(R.drawable.egg_easter, Ei.Egg.EASTER),
    WATERBALLOON(R.drawable.egg_waterballoon, Ei.Egg.WATERBALLOON),
    FIREWORK(R.drawable.egg_firework, Ei.Egg.FIREWORK),
    PUMPKIN(R.drawable.egg_pumpkin, Ei.Egg.PUMPKIN);

    companion object {
        fun getByEgg(value: Ei.Egg) = values().lastOrNull() { it.egg == value }
    }
}