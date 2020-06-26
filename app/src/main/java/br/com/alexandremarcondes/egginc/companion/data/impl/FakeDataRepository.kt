package br.com.alexandremarcondes.egginc.companion.data.impl

import android.content.res.Resources
import android.os.Handler
import br.com.alexandremarcondes.egginc.companion.data.DataRepository
import br.com.alexandremarcondes.egginc.companion.data.Result
import ei.Ei
import java.util.concurrent.ExecutorService
import kotlin.random.Random

class FakeDataRepository(
    private val executorService: ExecutorService,
    private val resultThreadHandler: Handler,
    private val resources: Resources
) : DataRepository {

    override fun getUser(deviceId: String, callback: (Result<Ei.DeviceInfo?>) -> Unit) {
        executeInBackground(callback) {
            resultThreadHandler.post {
                callback(Result.Success(
                    fakeUsers.find { it.deviceId == deviceId }
                ))
            }
        }
    }

    override fun getUsers(callback: (Result<List<Ei.DeviceInfo>>) -> Unit) {
        executeInBackground(callback) {
            simulateNetworkRequest()
            Thread.sleep(1500L)
            if (shouldRandomlyFail()) {
                throw IllegalStateException()
            }
            resultThreadHandler.post { callback(Result.Success(fakeUsers)) }
        }
    }

    /**
     * Executes a block of code in the past and returns an error in the [callback]
     * if [block] throws an exception.
     */
    private fun executeInBackground(callback: (Result<Nothing>) -> Unit, block: () -> Unit) {
        executorService.execute {
            try {
                block()
            } catch (e: Exception) {
                resultThreadHandler.post { callback(Result.Error(e)) }
            }
        }
    }

    /**
     * Simulates network request
     */
    private var networkRequestDone = false
    private fun simulateNetworkRequest() {
        if (!networkRequestDone) {
            Thread.sleep(2000L)
            networkRequestDone = true
        }
    }

    /**
     * 1/3 requests should fail loading
     */
    private fun shouldRandomlyFail(): Boolean = Random.nextFloat() < 0.33f
}