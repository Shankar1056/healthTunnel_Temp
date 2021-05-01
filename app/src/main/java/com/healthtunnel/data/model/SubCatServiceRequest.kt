package com.healthtunnel.data.model

import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.data.network.BackoffCallback
import com.healthtunnel.data.repository.ApiServices
import com.healthtunnel.data.repository.RetroClient
import com.healthtunnel.ui.utility.Constant
import retrofit2.Response

data class SubCatServiceRequest(
    var page: Int? = null,
    var serviceCategory: String? = null,
    var serviceSubCategory: String? = null,
    var limit: Int? = null
) {
    fun getSubCategory(
        token: String?,
        model: SubCatServiceRequest,
        apiCallback: ApiCallback
    ) {
        val myCallback = object : BackoffCallback<SubCatServices>(apiCallback) {
            override fun onSuccess(response: Response<SubCatServices>?) {
                if (response != null) {
                    val region = response.body()
                    if (region != null) {
                        apiCallback.onSuccess(region)
                        return
                    }
                }
            }
        }
        RetroClient.getRetrofit()?.create(ApiServices::class.java)?.listAllServiceAPI(Constant.BEARER+token, model)
            ?.enqueue(myCallback)
    }
}