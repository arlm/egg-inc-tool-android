package br.com.alexandremarcondes.egginc.companion.data

import br.com.alexandremarcondes.egginc.companion.data.model.User

interface DataRepository {

    fun getUser(deviceId: String, callback: (Result<User?>) -> Unit)

    fun getUsers(callback: (Result<List<User>>) -> Unit)
}