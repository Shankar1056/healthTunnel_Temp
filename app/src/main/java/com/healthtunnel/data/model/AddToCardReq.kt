package com.healthtunnel.data.model

import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.data.network.BackoffCallback
import com.healthtunnel.data.repository.ApiServices
import com.healthtunnel.data.repository.RetroClient
import com.healthtunnel.ui.utility.Constant
import retrofit2.Response

data class AddToCardReq(
    var businessId: String? = null,
    var productId: String? = null,
    var planId: String? = null,
    var quantity: Int? = null,
    var remove: Int? = null

) {
    fun addToCard(token: String?, model: AddToCardReq, apiCallback: ApiCallback) {
        val myCallback = object : BackoffCallback<AddToCartModel>(apiCallback) {
            override fun onSuccess(response: Response<AddToCartModel>?) {
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
            ?.addToCart(Constant.BEARER + token, model)
            ?.enqueue(myCallback)

    }

    fun remoeCard(token: String?, model: AddToCardReq, apiCallback: ApiCallback) {
        val myCallback = object : BackoffCallback<DeleteCardResponse>(apiCallback) {
            override fun onSuccess(response: Response<DeleteCardResponse>?) {
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
            ?.removeItemFromCart(Constant.BEARER + token, model)
            ?.enqueue(myCallback)
    }
}

