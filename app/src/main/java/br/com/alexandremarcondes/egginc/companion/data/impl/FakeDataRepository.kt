package br.com.alexandremarcondes.egginc.companion.data.impl

import android.content.res.Resources
import android.os.Handler
import br.com.alexandremarcondes.egginc.companion.data.IDataRepository
import br.com.alexandremarcondes.egginc.companion.data.Result
import br.com.alexandremarcondes.egginc.companion.data.model.Contract
import br.com.alexandremarcondes.egginc.companion.data.model.User
import java.util.concurrent.ExecutorService
import kotlin.random.Random

class FakeDataRepository(
    private val executorService: ExecutorService,
    private val resultThreadHandler: Handler,
    private val resources: Resources
) : IDataRepository {

    override fun getUser(userId: String, callback: (Result<User?>) -> Unit) {
        executeInBackground(callback) {
            resultThreadHandler.post {
                callback(Result.Success(
                    fakeUsers.find { it.userId == userId }
                ))
            }
        }
    }

    override fun getUsers(callback: (Result<List<User>>) -> Unit) {
        executeInBackground(callback) {
            simulateNetworkRequest()
            Thread.sleep(1500L)
            if (shouldRandomlyFail()) {
                throw IllegalStateException()
            }
            resultThreadHandler.post { callback(Result.Success(fakeUsers)) }
        }
    }

    override fun getContract(contractId: String, callback: (Result<Contract?>) -> Unit) {
        executeInBackground(callback) {
            resultThreadHandler.post {
                callback(Result.Success(
                    fakeContracts.find { it.identifier == contractId }
                ))
            }
        }
    }

    override fun getContracts(callback: (Result<List<Contract>>) -> Unit) {
        executeInBackground(callback) {
            simulateNetworkRequest()
            Thread.sleep(1500L)
            if (shouldRandomlyFail()) {
                throw IllegalStateException()
            }
            resultThreadHandler.post { callback(Result.Success(fakeContracts)) }
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