package br.com.alexandremarcondes.egginc.companion.data.impl

import br.com.alexandremarcondes.egginc.companion.data.EggIncService
import br.com.alexandremarcondes.egginc.companion.data.EggIncServiceApi
import com.google.protobuf.GeneratedMessageLite
import ei.Ei
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InvalidClassException
import java.util.*

class EggIncServiceImpl(private val api: EggIncServiceApi) : EggIncService {

    override fun firstContact(firstContactRequest: Ei.EggIncFirstContactRequest): Ei.EggIncFirstContactResponse? {
        return doRequest<Ei.EggIncFirstContactRequest,Ei.EggIncFirstContactResponse >(firstContactRequest) { base64Value -> api.firstContact(base64Value!!) }
    }

    override fun firstContact(
        firstContactRequest: Ei.EggIncFirstContactRequest,
        callback: (success: Boolean, response: Ei.EggIncFirstContactResponse?) -> Unit
    ) {
        doRequest<Ei.EggIncFirstContactRequest,Ei.EggIncFirstContactResponse >(firstContactRequest, { base64Value -> api.firstContact(base64Value!!) }, callback)
    }

    override fun userDataInfo(userDataInfoRequest: Ei.UserDataInfoRequest): Ei.UserDataInfoResponse? {
        return doRequest<Ei.UserDataInfoRequest,Ei.UserDataInfoResponse >(userDataInfoRequest) { base64Value -> api.userDataInfo(base64Value!!) }
    }

    override fun userDataInfo(
        userDataInfoRequest: Ei.UserDataInfoRequest,
        callback: (success: Boolean, response: Ei.UserDataInfoResponse?) -> Unit
    ) {
        doRequest<Ei.UserDataInfoRequest,Ei.UserDataInfoResponse >(userDataInfoRequest, { base64Value -> api.userDataInfo(base64Value!!) }, callback)
    }

    override fun contractCoopStatus(contractCoopStatusRequest: Ei.ContractCoopStatusRequest): Ei.ContractCoopStatusResponse? {
        return doRequest<Ei.ContractCoopStatusRequest,Ei.ContractCoopStatusResponse >(contractCoopStatusRequest) { base64Value -> api.contractCoopStatus(base64Value!!) }
    }

    override fun contractCoopStatus(
        contractCoopStatusRequest: Ei.ContractCoopStatusRequest,
        callback: (success: Boolean, response: Ei.ContractCoopStatusResponse?) -> Unit
    ) {
        doRequest<Ei.ContractCoopStatusRequest,Ei.ContractCoopStatusResponse >(contractCoopStatusRequest, { base64Value -> api.contractCoopStatus(base64Value!!) }, callback)
    }

    override fun getContracts(): Ei.ContractsResponse? {
        return doRequest<Ei.QueryCoopRequest,Ei.ContractsResponse >(null) { api.getContracts() }
    }

    override fun getContracts(callback: (success: Boolean, response: Ei.ContractsResponse?) -> Unit) {
        doRequest<Ei.QueryCoopRequest,Ei.ContractsResponse >(null, { api.getContracts() }, callback)
    }

    override fun getSales(salesInfoRequest: Ei.SalesInfoRequest): Ei.SalesInfo? {
        return doRequest<Ei.SalesInfoRequest,Ei.SalesInfo >(salesInfoRequest) { base64Value -> api.getSales(base64Value!!) }
    }

    override fun getSales(
        salesInfoRequest: Ei.SalesInfoRequest,
        callback: (success: Boolean, response: Ei.SalesInfo?) -> Unit
    ) {
        doRequest<Ei.SalesInfoRequest,Ei.SalesInfo >(salesInfoRequest, { base64Value -> api.getSales(base64Value!!) }, callback)
    }

    override fun getDailyGiftInfo(userDataInfoRequest: Ei.UserDataInfoRequest): Ei.DailyGiftInfo? {
        return doRequest<Ei.UserDataInfoRequest,Ei.DailyGiftInfo >(userDataInfoRequest) { base64Value -> api.getDailyGiftInfo(base64Value!!) }
    }

    override fun getDailyGiftInfo(
        userDataInfoRequest: Ei.UserDataInfoRequest,
        callback: (success: Boolean, response: Ei.DailyGiftInfo?) -> Unit
    ) {
        doRequest<Ei.UserDataInfoRequest,Ei.DailyGiftInfo >(userDataInfoRequest, { base64Value -> api.getDailyGiftInfo(base64Value!!) }, callback)
    }

    override fun getAdConfig(userDataInfoRequest: Ei.UserDataInfoRequest): Ei.AdAttributionInfo? {
        return doRequest<Ei.UserDataInfoRequest,Ei.AdAttributionInfo >(userDataInfoRequest) { base64Value -> api.getAdConfig(base64Value!!) }
    }

    override fun getAdConfig(
        userDataInfoRequest: Ei.UserDataInfoRequest,
        callback: (success: Boolean, response: Ei.AdAttributionInfo?) -> Unit
    ) {
        doRequest<Ei.UserDataInfoRequest,Ei.AdAttributionInfo >(userDataInfoRequest, { base64Value -> api.getAdConfig(base64Value!!) }, callback)
    }

    override fun getEvents(): Ei.EggIncCurrentEvents? {
        return doRequest<Ei.QueryCoopRequest,Ei.EggIncCurrentEvents >(null) { api.getEvents() }
    }

    override fun getEvents(callback: (success: Boolean, response: Ei.EggIncCurrentEvents?) -> Unit) {
        doRequest<Ei.QueryCoopRequest,Ei.EggIncCurrentEvents >(null, { api.getEvents() }, callback)
    }

    override fun queryCoop(queryCoopRequest: Ei.QueryCoopRequest): Ei.QueryCoopResponse? {
        return doRequest<Ei.QueryCoopRequest,Ei.QueryCoopResponse >(queryCoopRequest) { base64Value -> api.queryCoop(base64Value!!) }
    }

    override fun queryCoop(
        queryCoopRequest: Ei.QueryCoopRequest,
        callback: (success: Boolean, response: Ei.QueryCoopResponse?) -> Unit
    ) {
        doRequest<Ei.QueryCoopRequest,Ei.QueryCoopResponse >(queryCoopRequest, { base64Value -> api.queryCoop(base64Value!!) }, callback)
    }

    private inline fun <reified RequestType, reified ResponseType> doRequest(request: RequestType?, action: (String?) -> Call<String>?, crossinline callback: (success: Boolean, response: ResponseType?) -> Unit) {
        if (!GeneratedMessageLite::class.java.isAssignableFrom(RequestType::class.java)) throw InvalidClassException("Request is not a ProtoBuf object or it is null")
        if (!GeneratedMessageLite::class.java.isAssignableFrom(ResponseType::class.java)) throw InvalidClassException("Request is not a ProtoBuf object or it is null")

        val base64Value = if (request == null) null else toBase64(request)
        val responseCall = action(base64Value)
        var responseObj: ResponseType?

        responseCall?.enqueue (object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val string =  response.body()
                    responseObj = fromBase64<ResponseType>(string!!)
                    callback(true, responseObj)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callback(false, null)
            }
        })
    }

    @Suppress("UnnecessaryVariable")
    private inline fun <reified RequestType, reified ResponseType> doRequest(request: RequestType?, action: (String?) -> Call<String>?): ResponseType? {
        if (!GeneratedMessageLite::class.java.isAssignableFrom(RequestType::class.java)) throw InvalidClassException("Request is not a ProtoBuf object or it is null")
        if (!GeneratedMessageLite::class.java.isAssignableFrom(ResponseType::class.java)) throw InvalidClassException("Request is not a ProtoBuf object or it is null")

        val base64Value = if (request == null) null else toBase64(request)
        val responseCall = action(base64Value)

        val response = responseCall?.execute()!!
        val responseObj = fromBase64<ResponseType>(response.body()!!)

        return responseObj
    }

    @Suppress("UnnecessaryVariable")
    private inline fun <reified T> toBase64(obj: T) : String {
        if (!GeneratedMessageLite::class.java.isAssignableFrom(T::class.java)) throw InvalidClassException("Class is not a ProtoBuf object or it is null")

        val toByteArray = T::class.java.methods.firstOrNull { it.name == "toByteArray" && it.parameterCount == 0 }

        val decodedBytes = toByteArray?.invoke(obj) as ByteArray
        val encodedString = Base64.getEncoder().encodeToString(decodedBytes)

        return  encodedString
    }

    @Suppress("UnnecessaryVariable")
    private inline fun <reified T> fromBase64(value: String) : T? {
        if (!GeneratedMessageLite::class.java.isAssignableFrom(T::class.java)) throw InvalidClassException("Class is not a ProtoBuf object or it is null")

        val decodedBytes = Base64.getDecoder().decode(value)

        val parseFrom = T::class.java.getMethod("parseFrom", ByteArray::class.java)

        return parseFrom.invoke(null, decodedBytes) as T
    }
}
