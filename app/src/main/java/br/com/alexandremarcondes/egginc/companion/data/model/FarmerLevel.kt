package br.com.alexandremarcondes.egginc.companion.data.model

enum class FarmerLevel(val value: Int) {
    NoobFarmer(0),
    Farmer_1(1),
    Farmer_2(4),
    Farmer_3(5),
    KiloFarmer_1(6),
    KiloFarmer_2(7),
    KiloFarmer_3(8),
    MegaFarmer_1(9),
    MegaFarmer_2(10),
    MegaFarmer_3(11),
    GigaFarmer_1(12),
    GigaFarmer_2(13),
    GigaFarmer_3(14),
    TeraFarmer_1(15),
    TeraFarmer_2(16),
    TeraFarmer_3(17),
    PetaFarmer_1(18),
    PetaFarmer_2(19),
    PetaFarmer_3(20),
    ExaFarmer_1(21),
    ExaFarmer_2(22),
    ExaFarmer_3(23),
    ZetaFarmer_1(24),
    ZetaFarmer_2(25),
    ZetaFarmer_3(26),
    YottaFarmer_1(27),
    YottaFarmer_2(28),
    YottaFarmer_3(29),
    XennaFarmer_1(30),
    XennaFarmer_2(31),
    XennaFarmer_3(32),
    WeccaFarmer_1(33),
    WeccaFarmer_2(34),
    WeccaFarmer_3(35);

    companion object {
        private val values = values();
        fun getByValue(value: Int) = values.lastOrNull() { it.value <= value }
        fun fromDouble(value: Double) = getByValue(Math.log10(value).toInt())
    }
}