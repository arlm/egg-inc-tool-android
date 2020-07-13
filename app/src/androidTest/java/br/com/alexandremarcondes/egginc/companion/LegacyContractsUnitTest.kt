package br.com.alexandremarcondes.egginc.companion

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import br.com.alexandremarcondes.egginc.companion.data.impl.fakeContracts
import br.com.alexandremarcondes.egginc.companion.data.model.LegacyContracts
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LegacyContractsUnitTest {
    @Test
    fun loading() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val contracts = LegacyContracts(appContext)

        Assert.assertEquals(115, contracts.size)

        Assert.assertEquals(113, contracts.missingContracts(fakeContracts).count())
    }
}