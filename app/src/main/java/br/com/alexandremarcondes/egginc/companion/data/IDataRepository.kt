package br.com.alexandremarcondes.egginc.companion.data

import br.com.alexandremarcondes.egginc.companion.data.model.Contract
import br.com.alexandremarcondes.egginc.companion.data.model.Coop
import br.com.alexandremarcondes.egginc.companion.data.model.User

interface IDataRepository {

    fun getUser(deviceId: String, callback: (Result<User?>) -> Unit)

    fun getUsers(callback: (Result<List<User>>) -> Unit)

    fun getContracts(callback: (Result<List<Contract>>) -> Unit)

    fun getContract(contractId: String, callback: (Result<Contract?>) -> Unit)

    fun getCoops(callback: (Result<List<Coop>>) -> Unit)

    fun getCoop(coopId: String, contractId: String, callback: (Result<Coop?>) -> Unit)
}