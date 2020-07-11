package br.com.alexandremarcondes.egginc.companion

import aux.Aux
import br.com.alexandremarcondes.egginc.companion.data.EggIncService
import ei.Ei
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ServiceUnitTest {

    @Test
    fun firstContact() {
        val service = EggIncService.build()

        val request = Ei.EggIncFirstContactRequest.newBuilder()
            .setUserId("102371659776481580429")
            .setClientVersion(17)
            .setPlatform(Aux.Platform.ANDROID)
            .build()

        val response = service.firstContact(request)

        assertNotNull(response)
        assertNotNull(response!!.backup)
    }
}