package com.healthtunnel.data.model

import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.data.network.BackoffCallback
import com.healthtunnel.data.repository.ApiServices
import com.healthtunnel.data.repository.RetroClient
import com.healthtunnel.ui.utility.Constant
import retrofit2.Response

data class FilterReq(
    var page: Int? = null,
    var limit: Int? = null,
    var serviceCategory: String? = null,
    var salesCategory: String? = null
) {
    fun getFilterCat(token: String?, model: FilterReq, apiCallback: ApiCallback) {
        val myCallback = object : BackoffCallback<FilterCatResponse>(apiCallback) {
            override fun onSuccess(response: Response<FilterCatResponse>?) {
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
            ?.getAllSalesCategory(Constant.BEARER + token, model)
            ?.enqueue(myCallback)
    }

    fun getFilterProd(token: String?, model: FilterReq, apiCallback: ApiCallback) {
        val myCallback = object : BackoffCallback<FilterProdResponse>(apiCallback) {
            override fun onSuccess(response: Response<FilterProdResponse>?) {
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
            ?.getAllSalesFilterProducts(Constant.BEARER + token, model)
            ?.enqueue(myCallback)
    }
}