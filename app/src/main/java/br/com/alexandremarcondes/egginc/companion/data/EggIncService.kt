package br.com.alexandremarcondes.egginc.companion.data

import br.com.alexandremarcondes.egginc.companion.data.impl.EggIncServiceImpl
import ei.Ei

interface EggIncService {
    fun firstContact(firstContactRequest: Ei.EggIncFirstContactRequest): Ei.EggIncFirstContactResponse?
    fun firstContact(firstContactRequest: Ei.EggIncFirstContactRequest, callback: (success: Boolean, response: Ei.EggIncFirstContactResponse?) -> Unit)

    fun userDataInfo(userDataInfoRequest: Ei.UserDataInfoRequest): Ei.UserDataInfoResponse?
    fun userDataInfo(userDataInfoRequest: Ei.UserDataInfoRequest, callback: (success: Boolean, response: Ei.UserDataInfoResponse?) -> Unit)

    fun contractCoopStatus(contractCoopStatusRequest: Ei.ContractCoopStatusRequest): Ei.ContractCoopStatusResponse?
    fun contractCoopStatus(contractCoopStatusRequest: Ei.ContractCoopStatusRequest, callback: (success: Boolean, response: Ei.ContractCoopStatusResponse?) -> Unit)

    fun getContracts(): Ei.ContractsResponse?
    fun getContracts(callback: (success: Boolean, response: Ei.ContractsResponse?) -> Unit)

    fun getSales(salesInfoRequest: Ei.SalesInfoRequest): Ei.SalesInfo?
    fun getSales(salesInfoRequest: Ei.SalesInfoRequest, callback: (success: Boolean, response: Ei.SalesInfo?) -> Unit)

    fun getDailyGiftInfo(userDataInfoRequest: Ei.UserDataInfoRequest): Ei.DailyGiftInfo?
    fun getDailyGiftInfo(userDataInfoRequest: Ei.UserDataInfoRequest, callback: (success: Boolean, response: Ei.DailyGiftInfo?) -> Unit)

    fun getAdConfig(userDataInfoRequest: Ei.UserDataInfoRequest): Ei.AdAttributionInfo?
    fun getAdConfig(userDataInfoRequest: Ei.UserDataInfoRequest, callback: (success: Boolean, response: Ei.AdAttributionInfo?) -> Unit)

    fun getEvents(): Ei.EggIncCurrentEvents?
    fun getEvents(callback: (success: Boolean, response: Ei.EggIncCurrentEvents?) -> Unit)

    fun queryCoop(queryCoopRequest: Ei.QueryCoopRequest): Ei.QueryCoopResponse?
    fun queryCoop(queryCoopRequest: Ei.QueryCoopRequest, callback: (success: Boolean, response: Ei.QueryCoopResponse?) -> Unit)

    companion object {
        fun build(): EggIncService {
            val api = EggIncServiceApi.build();

            return EggIncServiceImpl(api);
        }
    }
}