package br.com.alexandremarcondes.egginc.companion.data.impl

import android.content.Context
import br.com.alexandremarcondes.egginc.companion.data.DataRepository
import br.com.alexandremarcondes.egginc.companion.data.Result
import br.com.alexandremarcondes.egginc.companion.data.model.User

class BlockingFakeDataRepository(private val context: Context) : DataRepository {
    override fun getUser(deviceId: String, callback: (Result<User?>) -> Unit) {
        callback(Result.Success(fakeUsers.find { it.deviceInfo?.deviceId ?: "" == deviceId }))
    }

    override fun getUsers(callback: (Result<List<User>>) -> Unit) {
        callback(Result.Success(fakeUsers))
    }
}