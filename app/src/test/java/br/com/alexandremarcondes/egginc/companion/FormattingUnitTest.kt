package br.com.alexandremarcondes.egginc.companion

import aux.Aux
import br.com.alexandremarcondes.egginc.companion.data.EggIncService
import br.com.alexandremarcondes.egginc.companion.util.formatSeconds
import ei.Ei
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class FormattingUnitTest {

    @Test
    fun toSeconds() {
        assertEquals("7 days", 604800.0.formatSeconds())
        assertEquals("7 days 22 hours 34 minutes 55 seconds", 686095.0.formatSeconds())
        assertEquals("7 days 22 hours", 684000.0.formatSeconds())
        assertEquals("7 days 22 hours 34 minutes", 686040.0.formatSeconds())
        assertEquals("7 days 12 hours 35 seconds", 648035.0.formatSeconds())
    }
}