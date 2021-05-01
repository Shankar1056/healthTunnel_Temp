package com.healthtunnel.data.model

import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.data.network.BackoffCallback
import com.healthtunnel.data.repository.ApiServices
import com.healthtunnel.data.repository.RetroClient
import com.healthtunnel.ui.utility.Constant
import retrofit2.Response

data class CommunityModelRequest(
    var myRequests: Int? = null,
    var page: Int? = null,
    var limit: Int? = null,
    var assistanceDeleivered: Boolean? = null

) {
    fun getCommunityRequest(
        token: String?,
        model: CommunityModelRequest,
        apiCallback: ApiCallback
    ) {
        val myCallback = object : BackoffCallback<CommunityModel>(apiCallback) {
            override fun onSuccess(response: Response<CommunityModel>?) {
                if (response != null) {
                    val region = response.body()
                    if (region != null) {
                        apiCallback.onSuccess(region)
                        return
                    }
                }
            }
        }
        RetroClient.getRetrofit()?.create(ApiServices::class.java)
            ?.getBloodRequests(Constant.BEARER + token,
               model)
            ?.enqueue(myCallback)
    }

    fun getCommunityDetails(token: String?, id: String?, apiCallback: ApiCallback) {

        val myCallback = object : BackoffCallback<CommunityDetailsModel>(apiCallback) {
            override fun onSuccess(response: Response<CommunityDetailsModel>?) {
                if (response != null) {
                    val region = response.body()
                    if (region != null) {
                        apiCallback.onSuccess(region)
                        return
                    }
                }
            }
        }
        if (id != null) {
            RetroClient.getRetrofit()?.create(ApiServices::class.java)
                ?.getParticularRequest(Constant.BEARER + token,
                    id)
                ?.enqueue(myCallback)
        }
    }

    fun getupdateRequest(token: String?, id: String?, apiCallback: ApiCallback) {

        val myCallback = object : BackoffCallback<CommonResponse>(apiCallback) {
            override fun onSuccess(response: Response<CommonResponse>?) {
                if (response != null) {
                    val region = response.body()
                    if (region != null) {
                        apiCallback.onSuccess(region)
                        return
                    }
                }
            }
        }
        if (id != null) {
            RetroClient.getRetrofit()?.create(ApiServices::class.java)
                ?.updateRequestStatus(Constant.BEARER + token,
                    id)
                ?.enqueue(myCallback)
        }
    }
}