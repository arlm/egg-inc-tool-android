package br.com.alexandremarcondes.egginc.companion.data.impl

import android.content.Context
import br.com.alexandremarcondes.egginc.companion.data.IDataRepository
import br.com.alexandremarcondes.egginc.companion.data.Result
import br.com.alexandremarcondes.egginc.companion.data.model.Contract
import br.com.alexandremarcondes.egginc.companion.data.model.User

class BlockingFakeDataRepository {
    fun getUser(userId: String): User? =
        fakeUsers.find { it.userId == userId }

    fun getUsers(): List<User> = fakeUsers

    fun getContract(contractId: String): Contract? =
        fakeContracts.find { it.identifier == contractId }

    fun getContracts(): List<Contract> = fakeContracts

    companion object DataRepository : IDataRepository {
        override fun getUser(userId: String, callback: (Result<User?>) -> Unit) =
            callback(Result.Success(fakeUsers.find { it.userId == userId }))

        override fun getUsers(callback: (Result<List<User>>) -> Unit) =
            callback(Result.Success(fakeUsers))

        override fun getContract(contractId: String, callback: (Result<Contract?>) -> Unit) =
            callback(Result.Success(fakeContracts.find { it.identifier == contractId }))

        override fun getContracts(callback: (Result<List<Contract>>) -> Unit) =
            callback(Result.Success(fakeContracts))
    }
}