package br.com.alexandremarcondes.egginc.companion

import br.com.alexandremarcondes.egginc.companion.data.impl.user1
import br.com.alexandremarcondes.egginc.companion.data.model.Magnitude
import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UserUnitTest {

    @Test
    fun calculatedProperties() {
        Assert.assertEquals(165_738_457.0, user1.bonusPerSoulEgg, 0.01 )
        //Assert.assertEquals(       77_874_642_050_652_639_829_426_176.0, user1.earningsBonus, 0.01)
        Assert.assertEquals(77_874_641_898_941_920_000_000_000.0, user1.earningsBonus, 0.01)
        Assert.assertEquals(Magnitude.ZetaFarmer_3, user1.ranking)

    }
}