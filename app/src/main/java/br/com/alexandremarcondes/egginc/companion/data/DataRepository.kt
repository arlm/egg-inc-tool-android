package br.com.alexandremarcondes.egginc.companion.data

interface DataRepository {

    fun getUser(deviceId: String, callback: (Result<ei.Ei.DeviceInfo?>) -> Unit)

    fun getUsers(callback: (Result<List<ei.Ei.DeviceInfo>>) -> Unit)
}