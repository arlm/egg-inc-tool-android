package br.com.alexandremarcondes.egginc.companion.data.impl

import android.content.Context
import br.com.alexandremarcondes.egginc.companion.data.DataRepository
import br.com.alexandremarcondes.egginc.companion.data.Result
import ei.Ei

class BlockingFakeDataRepository(private val context: Context) : DataRepository {
    override fun getUser(deviceId: String, callback: (Result<Ei.DeviceInfo?>) -> Unit) {
        callback(Result.Success(fakeUsers.find { it.deviceId == deviceId }))
    }

    override fun getUsers(callback: (Result<List<Ei.DeviceInfo>>) -> Unit) {
        callback(Result.Success(fakeUsers))
    }
}