package br.com.alexandremarcondes.egginc.companion.data

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface EggIncServiceApi {
    @Headers(
        "User-Agent: Dalvik/2.1.0 (Linux; U; Android 9; SM-G960U1 Build/PPR1.180610.011)",
        "Accept-Encoding: gzip",
        "Connection: Keep-Alive"
    )
    @FormUrlEncoded
    @POST("first_contact")
    fun firstContact(@Field("data") firstContactRequest: String): Call<String>?

    @Headers(
        "User-Agent: Dalvik/2.1.0 (Linux; U; Android 9; SM-G960U1 Build/PPR1.180610.011)",
        "Accept-Encoding: gzip",
        "Connection: Keep-Alive"
    ) @FormUrlEncoded
    @POST("user_data_info")
    fun userDataInfo(@Field("data") userDataInfoRequest: String): Call<String>?

    @Headers(
        "User-Agent: Dalvik/2.1.0 (Linux; U; Android 9; SM-G960U1 Build/PPR1.180610.011)",
        "Accept-Encoding: gzip",
        "Connection: Keep-Alive"
    ) @FormUrlEncoded
    @POST("coop_status")
    fun contractCoopStatus(@Field("data") contractCoopStatusRequest: String): Call<String>?

    @Headers(
        "User-Agent: Dalvik/2.1.0 (Linux; U; Android 9; SM-G960U1 Build/PPR1.180610.011)",
        "Accept-Encoding: gzip",
        "Connection: Keep-Alive"
    )@GET("get_contracts")
    fun getContracts(): Call<String>?

    @Headers(
        "User-Agent: Dalvik/2.1.0 (Linux; U; Android 9; SM-G960U1 Build/PPR1.180610.011)",
        "Accept-Encoding: gzip",
        "Connection: Keep-Alive"
    )@FormUrlEncoded
    @POST("get_sales")
    fun getSales(@Field("data") salesInfoRequest: String): Call<String>?

    @Headers(
        "User-Agent: Dalvik/2.1.0 (Linux; U; Android 9; SM-G960U1 Build/PPR1.180610.011)",
        "Accept-Encoding: gzip",
        "Connection: Keep-Alive"
    )@FormUrlEncoded
    @POST("daily_gift_info")
    fun getDailyGiftInfo(@Field("data") userDataInfoRequest: String): Call<String>?

    @Headers(
        "User-Agent: Dalvik/2.1.0 (Linux; U; Android 9; SM-G960U1 Build/PPR1.180610.011)",
        "Accept-Encoding: gzip",
        "Connection: Keep-Alive"
    )@FormUrlEncoded
    @POST("get_ad_config")
    fun getAdConfig(@Field("data") userDataInfoRequest: String): Call<String>?

    @Headers(
        "User-Agent: Dalvik/2.1.0 (Linux; U; Android 9; SM-G960U1 Build/PPR1.180610.011)",
        "Accept-Encoding: gzip",
        "Connection: Keep-Alive"
    )@GET("get_events")
    fun getEvents(): Call<String>?

    @Headers(
        "User-Agent: Dalvik/2.1.0 (Linux; U; Android 9; SM-G960U1 Build/PPR1.180610.011)",
        "Accept-Encoding: gzip",
        "Connection: Keep-Alive"
    )@FormUrlEncoded
    @POST("query_coop")
    fun queryCoop(@Field("data") queryCoopRequest: String): Call<String>?

    companion object {
        fun build(): EggIncServiceApi {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://www.auxbrain.com/ei/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()

            val service = retrofit.create(EggIncServiceApi::class.java)

            return service
        }
    }
}