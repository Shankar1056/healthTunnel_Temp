package com.healthtunnel.ui.community_center

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.healthtunnel.R
import com.healthtunnel.data.model.*
import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.ui.utility.AppController
import com.healthtunnel.ui.utility.ClsGeneral
import com.healthtunnel.ui.utility.Constant

class CommunityViewModel(val app: Application) : AndroidViewModel(app) {
    var showProgressDialog = MutableLiveData<Boolean>()
    var closeActivity = MutableLiveData<Boolean>()
    var successMessage = MutableLiveData<String>()
    var errorMessage = MutableLiveData<String>()
    var responseModel = MutableLiveData<ArrayList<CommunityResult>>()
    var communityResponseModel = MutableLiveData<CommunityDetailsResult>()
    var patientMobileNuber: String = ""


    var clickedPosition = MutableLiveData<Int>()

    fun CommunityRequest(clickedPosition: Int) {
        when (clickedPosition) {
            1 -> getComunityRequest(0, 1, 10, false)
            2 -> getComunityRequest(1, 1, 10, false)
            3 -> getComunityRequest(0, 1, 10, true)
        }
    }

    fun helpRequiredClicked(view: View) {
        clickedPosition.value = 1
        getComunityRequest(0, 1, 10, false)
    }

    fun myRequestClicked(view: View) {
        clickedPosition.value = 2
        getComunityRequest(1, 1, 10, false)
    }

    fun assistanceClicked(view: View) {
        clickedPosition.value = 3
        getComunityRequest(0, 1, 10, true)
    }

    private fun getComunityRequest(myRequests: Int, page: Int, limit: Int, assistance : Boolean) {
        val model = CommunityModelRequest()
        model.myRequests = myRequests
        model.page = page
        model.limit = Constant.DATA_LIMIT
        model.assistanceDeleivered = assistance

        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgressDialog.value = true
            model.getCommunityRequest(
                ClsGeneral.getPreferences(Constant.TOKEN), model,


                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as CommunityModel

                        if (response.statusCode == 200) {
                            successMessage.value = response.message
                            responseModel.value = response.result
                        } else {
                            errorMessage.value = response.message
                        }


                        showProgressDialog.value = false
                    }

                    override fun onHandledError() {
                        showProgressDialog.value = false
                    }
                })
        } else {
            showProgressDialog.value = false
            errorMessage.value = app.resources.getString(R.string.no_internet_connection)
        }

    }

    fun getCommunityDetails(id: String?) {
        val model = CommunityModelRequest()
        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgressDialog.value = true
            model.getCommunityDetails(
                ClsGeneral.getPreferences(Constant.TOKEN), id,


                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as CommunityDetailsModel

                        if (response.statusCode == 200) {
                            successMessage.value = response.message
                            communityResponseModel.value = response.result
                            patientMobileNuber = response.result.patientContactNumber
                        } else {
                            errorMessage.value = response.message
                        }


                        showProgressDialog.value = false
                    }

                    override fun onHandledError() {
                        showProgressDialog.value = false
                    }
                })
        } else {
            showProgressDialog.value = false
            errorMessage.value = app.resources.getString(R.string.no_internet_connection)
        }
    }

    fun callPatient() {

    }

    fun closeRequest(id: String?) {
        val model = CommunityModelRequest()
        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgressDialog.value = true
            model.getupdateRequest(
                ClsGeneral.getPreferences(Constant.TOKEN), id,


                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as CommonResponse

                        if (response.statusCode == 200) {
                            successMessage.value = response.message
                            closeActivity.value = true
                        } else {
                            errorMessage.value = response.message
                        }


                        showProgressDialog.value = false
                    }

                    override fun onHandledError() {
                        showProgressDialog.value = false
                    }
                })
        } else {
            showProgressDialog.value = false
            errorMessage.value = app.resources.getString(R.string.no_internet_connection)
        }
    }


}