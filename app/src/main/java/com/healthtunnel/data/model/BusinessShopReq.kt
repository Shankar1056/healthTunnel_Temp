package com.healthtunnel.data.model

import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.data.network.BackoffCallback
import com.healthtunnel.data.repository.ApiServices
import com.healthtunnel.data.repository.RetroClient
import com.healthtunnel.ui.utility.Constant
import retrofit2.Response

data class BusinessShopReq(
    var businessId: String? = null,
    var page: Int? = null,
    var limit: Int? = null,
    var businessCategory: String? = null
) {
    fun getAllBusinessCategories(
        token: String?,
        model: BusinessShopReq,
        apiCallback: ApiCallback
    ) {
        val myCallback = object : BackoffCallback<BusinessCategoryModel>(apiCallback) {
            override fun onSuccess(response: Response<BusinessCategoryModel>?) {
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
            ?.getAllBusinessCategories(Constant.BEARER + token, model)
            ?.enqueue(myCallback)


    }

    fun getAllBusinessProducts(
        token: String?,
        model: BusinessShopReq,
        apiCallback: ApiCallback
    ) {
        val myCallback = object : BackoffCallback<BusinessShopListModel>(apiCallback) {
            override fun onSuccess(response: Response<BusinessShopListModel>?) {
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
            ?.getAllBusinessProducts(Constant.BEARER + token, model)
            ?.enqueue(myCallback)

    }

    fun getParticularBusinessProduct(
        token: String?,
        id: String,
        apiCallback: ApiCallback
    ) {
        val myCallback = object : BackoffCallback<BusinessShopDetailsModel>(apiCallback) {
            override fun onSuccess(response: Response<BusinessShopDetailsModel>?) {
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
            ?.getParticularBusinessProduct(Constant.BEARER + token, id)
            ?.enqueue(myCallback)
    }


}