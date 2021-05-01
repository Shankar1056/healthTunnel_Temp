package com.healthtunnel.data.model

import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.data.network.BackoffCallback
import com.healthtunnel.data.repository.ApiServices
import com.healthtunnel.data.repository.RetroClient
import com.healthtunnel.ui.utility.Constant
import retrofit2.Response

data class CartDetails(
    var statusCode: Int,
    var message: String,
    var result: ArrayList<CartDetailsResult>
)

data class CartDetailsResult(

    var quantity: Int,
    var id: String,
    var businessId: CartBusiness,
    var planId: CartPlan,
    var productId: CartProduct
)

data class CartProduct(
    var _id: String,
    var name: String,
    var productImage: String
)

data class CartPlan(
    var _id: String,
    var name: String,
    var price: Int
)

data class CartBusiness(
    var businessName: String,
    var profileImage: String,
    var id: String
)

class CartDetailsReq(

) {
    fun cartDetails(token: String?, apiCallback: ApiCallback) {
        val myCallback = object : BackoffCallback<CartDetails>(apiCallback) {
            override fun onSuccess(response: Response<CartDetails>?) {
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
            ?.displayCart(Constant.BEARER + token)
            ?.enqueue(myCallback)
    }
}