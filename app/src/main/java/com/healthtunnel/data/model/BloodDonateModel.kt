package com.healthtunnel.data.model

import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.data.network.BackoffCallback
import com.healthtunnel.data.repository.ApiServices
import com.healthtunnel.data.repository.RetroClient
import com.healthtunnel.ui.utility.Constant
import okhttp3.RequestBody
import retrofit2.Response

data class BloodDonateModel(
    var statusCode: Int? = null,
    var message: String? = null,
    var result: BloodDonateResult? = null
) {
    fun saveBloodDonateRecord(
        token: String?,
        bloodGroup: RequestBody,
        age: RequestBody,
        isCovidSurvivor: RequestBody,
        willingToDonatePlasma: RequestBody,
        donatedBloodInNinetyDays: RequestBody,
        latitude: RequestBody,
        longitude: RequestBody,
        apiCallback: ApiCallback
    ) {
        val myCallback = object : BackoffCallback<BloodDonateModel>(apiCallback) {
            override fun onSuccess(response: Response<BloodDonateModel>?) {
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
            ?.registerDonar(Constant.BEARER + token,
                bloodGroup,
                age,
                isCovidSurvivor,
                willingToDonatePlasma,
                donatedBloodInNinetyDays,
                latitude,
                longitude)
            ?.enqueue(myCallback)
    }

}

data class BloodDonateResult(
    var deleteStatus: Int,
    var _id: String,
    var bloodGroup: String,
    var age: String,
    var isCovidSurvivor: String,
    var willingToDonatePlasma: String,
    var donatedBloodInNinetyDays: String,
    var latitude: Double,
    var longitude: Double,
    var userId: String
)