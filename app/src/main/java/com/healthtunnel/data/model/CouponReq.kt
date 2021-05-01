package com.healthtunnel.data.model

import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.data.network.BackoffCallback
import com.healthtunnel.data.repository.ApiServices
import com.healthtunnel.data.repository.CustomUrlRetrofitClient
import com.healthtunnel.data.repository.RetroClient
import com.healthtunnel.ui.utility.Constant
import retrofit2.Response

data class CouponReq(
    var page: Int? = null,
    var limit: Int? = null

) {
    fun getCoupon(token: String, id : String,  model: CouponReq, apiCallback: ApiCallback) {
        val myCallback = object : BackoffCallback<CouponResponse>(apiCallback) {
            override fun onSuccess(response: Response<CouponResponse>?) {
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
        RetroClient.getRetrofit()
            ?.create(ApiServices::class.java)
            ?.getCoupons(Constant.BEARER + token, id, model)
            ?.enqueue(myCallback)
    }
}