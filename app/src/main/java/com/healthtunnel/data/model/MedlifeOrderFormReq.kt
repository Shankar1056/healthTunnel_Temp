package com.healthtunnel.data.model

import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.data.network.BackoffCallback
import com.healthtunnel.data.repository.ApiServices
import com.healthtunnel.data.repository.CustomUrlRetrofitClient
import com.healthtunnel.ui.utility.Constant
import retrofit2.Response
import java.sql.Array
import java.util.*

data class MedlifeOrderFormReq(
    var name: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var age: Int? = null,
    var sex: String? = null,
    var address: String? = null,
    var order_time: String? = null,
    var city: String? = null,
    var pincode: String? = null,
    var test_code: kotlin.Array<String>? = null,
    var collection_date: String? = null,
    var collection_type: String? = null,
    var source: String? = null,
    var instruction: String? = null,
    var lat: String? = null,
    var lng: String? = null,
    var courier_req: Boolean? = null
) {

    fun placeFormOrder(token: String, model: MedlifeOrderFormReq, apiCallback: ApiCallback) {
        val myCallback = object : BackoffCallback<FormOrderResponse>(apiCallback) {
            override fun onSuccess(response: Response<FormOrderResponse>?) {
                if (response != null) {
                    val region = response.body()
                    if (region != null) {
                        apiCallback.onSuccess(region)
                        return
                    } else {
                        apiCallback.onError("")
                    }
                }
            }
        }
        CustomUrlRetrofitClient.getRetrofit("http://rest-labs.medlife.com/api/")
            ?.create(ApiServices::class.java)
            ?.formOrder(Constant.BEARER + token, model)
            ?.enqueue(myCallback)
    }
}