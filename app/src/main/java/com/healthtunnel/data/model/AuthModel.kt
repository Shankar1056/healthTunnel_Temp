package com.healthtunnel.data.model

import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.data.network.BackoffCallback
import com.healthtunnel.data.repository.ApiServices
import com.healthtunnel.data.repository.RetroClient
import com.healthtunnel.ui.utility.Constant
import okhttp3.RequestBody
import retrofit2.Response

data class AuthModel(

    var statusCode: Int? = null,
    var message: String? = null,
    var result: AuthResult? = null
) {
    fun getOtp(request: GetOtpModelReq, apiCallback: ApiCallback) {

        val myCallback = object : BackoffCallback<AuthModel>(apiCallback) {
            override fun onSuccess(response: Response<AuthModel>?) {
                if (response != null) {
                    val region = response.body()
                    if (region != null) {
                        apiCallback.onSuccess(region)
                        return
                    }
                }
            }
        }
        RetroClient.getRetrofit()?.create(ApiServices::class.java)?.sendOTP(request)
            ?.enqueue(myCallback)

    }

    fun verifyOtp(request: GetOtpModelReq, apiCallback: ApiCallback) {

        val myCallback = object : BackoffCallback<AuthModel>(apiCallback) {
            override fun onSuccess(response: Response<AuthModel>?) {
                if (response != null) {
                    val region = response.body()
                    if (region != null) {
                        apiCallback.onSuccess(region)
                        return
                    }
                }
            }
        }
        RetroClient.getRetrofit()?.create(ApiServices::class.java)?.verifyOtp(request)
            ?.enqueue(myCallback)

    }

    fun updateUser(
        token: String,
        name: RequestBody,
        email: RequestBody,
        gender: RequestBody,
        apiCallback: ApiCallback
    ) {
        val myCallback = object : BackoffCallback<AuthModel>(apiCallback) {
            override fun onSuccess(response: Response<AuthModel>?) {
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
            ?.updateUser(Constant.BEARER+token, name, email, gender)
            ?.enqueue(myCallback)

    }

    fun createUser(authModel: GetOtpModelReq, apiCallback: ApiCallback) {
        val myCallback = object : BackoffCallback<AuthModel>(apiCallback) {
            override fun onSuccess(response: Response<AuthModel>?) {
                if (response != null) {
                    val region = response.body()
                    if (region != null) {
                        apiCallback.onSuccess(region)
                        return
                    }
                }
            }
        }
        RetroClient.getRetrofit()?.create(ApiServices::class.java)?.createUser(authModel)
            ?.enqueue(myCallback)
    }
}

data class AuthResult(
    var otp: String,
    var newUser: Boolean,
    var doesMobileExists: Boolean,
    var token: String,
    var name : String,
    var email: String,
    var mobileNumber: String,
    var gender: String,
    var _id: String

)


