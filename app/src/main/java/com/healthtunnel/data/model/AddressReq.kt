package com.healthtunnel.data.model

import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.data.network.BackoffCallback
import com.healthtunnel.data.repository.ApiServices
import com.healthtunnel.data.repository.RetroClient
import com.healthtunnel.ui.utility.Constant
import retrofit2.Response

data class AddressReq(
    var flat: String? = null,
    var address: String? = null,
    var city: String? = null,
    var state: String? = null,
    var pincode: String? = null
) {
    fun addAddress(token: String?, model: AddressReq, apiCallback: ApiCallback) {

        val myCallback = object : BackoffCallback<CommonResponse>(apiCallback) {
            override fun onSuccess(response: Response<CommonResponse>?) {
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
        RetroClient.getRetrofit()?.create(ApiServices::class.java)
            ?.addShippingAddress(Constant.BEARER + token, model)
            ?.enqueue(myCallback)
    }

    fun getAddress(token: String?, apiCallback: ApiCallback) {
        val myCallback = object : BackoffCallback<AddressResponse>(apiCallback) {
            override fun onSuccess(response: Response<AddressResponse>?) {
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
        RetroClient.getRetrofit()?.create(ApiServices::class.java)
            ?.getAllShippingAddress(Constant.BEARER + token)
            ?.enqueue(myCallback)
    }

    fun updateAddress(
        token: String?,
        addressId: String?,
        model: AddressReq,
        apiCallback: ApiCallback
    ) {

        val myCallback = object : BackoffCallback<CommonResponse>(apiCallback) {
            override fun onSuccess(response: Response<CommonResponse>?) {
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
        RetroClient.getRetrofit()?.create(ApiServices::class.java)
            ?.updateParticularShippingAddress(Constant.BEARER + token, addressId.toString(), model)
            ?.enqueue(myCallback)
    }
}