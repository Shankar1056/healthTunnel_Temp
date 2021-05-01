package com.healthtunnel.data.model

import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.data.network.BackoffCallback
import com.healthtunnel.data.repository.ApiServices
import com.healthtunnel.data.repository.RetroClient
import com.healthtunnel.ui.utility.Constant
import retrofit2.Response

data class BusinessSalesListReq(
    var serviceCategory: String? = null,
    var filterCategory: String? = null,
    var filterProducts: Array<String>? = null,
    var longitude: Double? = null,
    var latitude: Double? = null,
    var page: Int? = null,
    var limit: Int? = null
) {
    fun getBusinessSalesList(
        token: String?,
        model: BusinessSalesListReq,
        apiCallback: ApiCallback
    ) {
        val myCallback = object : BackoffCallback<BusinessSalesModel>(apiCallback) {
            override fun onSuccess(response: Response<BusinessSalesModel>?) {
                if (response != null) {
                    val region = response.body()
                    if (region != null) {
                        apiCallback.onSuccess(region)
                        return
                    } else{
                        apiCallback.onHandledError()
                    }
                }
            }
        }
        RetroClient.getRetrofit()?.create(ApiServices::class.java)
            ?.listBusinessSales(Constant.BEARER + token, model)
            ?.enqueue(myCallback)
    }

    fun listParticularBusinessLead(token: String?, id: String?, apiCallback: ApiCallback) {

        val myCallback = object : BackoffCallback<BusinessAboutModel>(apiCallback) {
            override fun onSuccess(response: Response<BusinessAboutModel>?) {
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
            ?.listParticularBusinessLead(Constant.BEARER + token, id.toString())
            ?.enqueue(myCallback)

    }
}