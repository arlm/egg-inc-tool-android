package br.com.alexandremarcondes.egginc.companion

import aux.Aux
import br.com.alexandremarcondes.egginc.companion.data.EggIncService
import br.com.alexandremarcondes.egginc.companion.util.formatMagnitude
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

    @Test
    fun magnitude() {
        assertEquals("77,875S", 77_874_641_898_941_920_000_000_000.0.formatMagnitude())
        assertEquals("74,642s", 74_641_898_941_920_000_000_000.0.formatMagnitude())
        assertEquals("41,899Q", 41_898_941_920_000_000_000.0.formatMagnitude())
        assertEquals("898,942q", 898_941_920_000_000_000.0.formatMagnitude())
        assertEquals("1,920T", 1_920_000_000_000.0.formatMagnitude())
        assertEquals("920,000B", 920_000_000_000.0.formatMagnitude())
        assertEquals("77,875B", 77_874_641_898.0.formatMagnitude())
        assertEquals("874,642M", 874_641_898.0.formatMagnitude())
        assertEquals("74,642M", 74_641_898.0.formatMagnitude())
        assertEquals("4,642M", 4_641_898.0.formatMagnitude())
        assertEquals("641898", 641_898.0.formatMagnitude())
        assertEquals("41898", 41_898.0.formatMagnitude())
        assertEquals("1898", 1_898.0.formatMagnitude())
        assertEquals("898", 898.0.formatMagnitude())
        assertEquals("98", 98.0.formatMagnitude())
        assertEquals("8", 8.0.formatMagnitude())
    }
}