package com.healthtunnel.data.model

import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.data.network.BackoffCallback
import com.healthtunnel.data.repository.ApiServices
import com.healthtunnel.data.repository.RetroClient
import com.healthtunnel.ui.utility.Constant
import okhttp3.RequestBody
import retrofit2.Response

data class BloodRequestModel(
    var statusCode: Int? = null,
    var message: String? = null,
    var result: BloodRequestResult? = null
) {
    fun saveBloodDonateRecord(
        token: String?,
        bloodGroup: RequestBody,
        age: RequestBody,
        patientContactNumber: RequestBody,
        scheduledDate: RequestBody,
        scheduledTime: RequestBody,
        locationName: RequestBody,
        latitude: RequestBody,
        longitude: RequestBody,
        file: RequestBody,
        statusOfPatient: RequestBody,
        reasonForBloodRequest: RequestBody,
        bloodRequestedFor: RequestBody,
        detailsOfUrgency: RequestBody,
        apiCallback: ApiCallback
    ) {

        val myCallback = object : BackoffCallback<BloodRequestModel>(apiCallback) {
            override fun onSuccess(response: Response<BloodRequestModel>?) {
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
            ?.registerRequester(
                Constant.BEARER + token,
                bloodGroup,
                age,
                patientContactNumber,
                scheduledDate,
                scheduledTime,
                locationName,
                latitude,
                longitude,
                file,
                statusOfPatient,
                reasonForBloodRequest,
                bloodRequestedFor,
                detailsOfUrgency
            )
            ?.enqueue(myCallback)

    }
}

data class BloodRequestResult(
    var requestStatus: Int,
    var deleteStatus: Int,
    var _id: String,
    var bloodGroup: String,
    var age: Int,
    var patientContactNumber: String,
    var scheduledDate: String,
    var scheduledTime: String,
    var locationName: String,
    var latitude: String,
    var longitude: String,
    var statusOfPatient: String,
    var reasonForBloodRequest: String,
    var bloodRequestedFor: String,
    var detailsOfUrgency: String,
    var userId: String
)