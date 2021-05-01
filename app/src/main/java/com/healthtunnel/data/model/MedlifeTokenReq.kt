package com.healthtunnel.data.model

import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.data.network.BackoffCallback
import com.healthtunnel.data.repository.ApiServices
import com.healthtunnel.data.repository.CustomUrlRetrofitClient
import com.healthtunnel.data.repository.RetroClient
import retrofit2.Response

data class MedlifeTokenReq(
    var username: String? = null,
    var password: String? = null,
    var application: String? = null
) {
    fun getAvailableTest(model: MedlifeTokenReq, apiCallback: ApiCallback) {
        val myCallback = object : BackoffCallback<MedlifeTokenResponse>(apiCallback) {
            override fun onSuccess(response: Response<MedlifeTokenResponse>?) {
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
        CustomUrlRetrofitClient.getRetrofit("http://auth-labs.medlife.com/api/auth/")?.create(ApiServices::class.java)
            ?.getMedlifeToken(model)
            ?.enqueue(myCallback)
    }
}