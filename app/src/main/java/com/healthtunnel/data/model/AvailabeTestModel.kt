package com.healthtunnel.data.model

import android.os.Parcelable
import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.data.network.BackoffCallback
import com.healthtunnel.data.repository.ApiServices
import com.healthtunnel.data.repository.CustomUrlRetrofitClient
import com.healthtunnel.data.repository.RetroClient
import com.healthtunnel.ui.utility.Constant
import kotlinx.android.parcel.Parcelize
import retrofit2.Response

data class AvailabeTestModel(
    var tests: ArrayList<AvailableTests>
)

@Parcelize
data class AvailableTests(
    var name: String ?= null,
    var is_not_medlife: Int ?= null,
    var test_id: Int ?= null,
    var lab_id: Int ?= null,
    var description: String ?= null,
    var lab_name: String ?= null,
    var lab_address: String ?= null,
    var code: String ?= null,
    var isSelected: Boolean = false,
    var isView: Boolean = false
) : Parcelable

class AvailableTestAPI(

) {
    fun getAvailableTest(token : String, pincode: String?, district: String?, apiCallback: ApiCallback) {
        val myCallback = object : BackoffCallback<AvailabeTestModel>(apiCallback) {
            override fun onSuccess(response: Response<AvailabeTestModel>?) {
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
        CustomUrlRetrofitClient.getRetrofit("http://rest-labs.medlife.com/api/")?.create(ApiServices::class.java)
            ?.availableTeste(Constant.BEARER+token, pincode.toString(), district.toString())
            ?.enqueue(myCallback)
    }
}