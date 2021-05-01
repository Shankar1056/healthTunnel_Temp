package com.healthtunnel.ui.profile

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.healthtunnel.R
import com.healthtunnel.data.model.AuthModel
import com.healthtunnel.data.model.CommonResponse
import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.ui.utility.AppController
import com.healthtunnel.ui.utility.ClsGeneral
import com.healthtunnel.ui.utility.Constant
import com.healthtunnel.ui.utility.Utility
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

class ProfileViewModel(val app: Application) : AndroidViewModel(app) {

    var showProgressDialog = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()
    var selectedGender = MutableLiveData<String>()


    fun updareProfile(
        emailId: String,
        userName: String,
        mobileNumber: String,
        selectedGender: String
    ) {
        var model = AuthModel()
        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgressDialog.value = true
            val name: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                userName
            )
            val email: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                emailId
            )
            val gender: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                selectedGender
            )
            model.updateUser(
                ClsGeneral.getPreferences(Constant.TOKEN),
                name,
                email,
                gender,
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as AuthModel

                        if (response.statusCode == 200) {
                            message.value = response.message
                            ClsGeneral.setPreferences(Constant.EMAIL, emailId)
                            ClsGeneral.setPreferences(Constant.NAME, userName)
                            ClsGeneral.setPreferences(Constant.MOBILE, mobileNumber)
                            ClsGeneral.setPreferences(Constant.GENDER, selectedGender)
                        } else {
                            message.value = response.message
                        }


                        showProgressDialog.value = false
                    }

                    override fun onHandledError() {
                        showProgressDialog.value = false
                    }
                })
        } else {
            showProgressDialog.value = false
            message.value = app.resources.getString(R.string.no_internet_connection)
        }
    }

    fun showGenderDialog(context: Context) {
        val genderList = Utility.genderList()
        val builder = AlertDialog.Builder(context)
        builder.setTitle(app.resources.getString(R.string.title_select_gender)).setCancelable(false)

        val checkedItem = 0 //this will checked the item when user open the dialog

        builder.setSingleChoiceItems(
            genderList, checkedItem
        ) { dialog, which ->
            selectedGender.value = genderList[which]
        }

        builder.setPositiveButton(
            "Done"
        ) { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()

    }

}