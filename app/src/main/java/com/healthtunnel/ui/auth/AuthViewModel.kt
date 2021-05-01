package com.healthtunnel.ui.auth

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.healthtunnel.R
import com.healthtunnel.data.model.AuthModel
import com.healthtunnel.data.model.GetOtpModelReq
import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.ui.utility.AppController
import com.healthtunnel.ui.utility.ClsGeneral
import com.healthtunnel.ui.utility.Constant
import com.healthtunnel.ui.utility.Utility
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.util.regex.Pattern

class AuthViewModel(private val app: Application) : AndroidViewModel(app) {
    var authModel: GetOtpModelReq = GetOtpModelReq()
    private var model: AuthModel = AuthModel()
    var message = MutableLiveData<String>()
    var showProgressDialog = MutableLiveData<Boolean>()
    var otpReceived = MutableLiveData<String>()
    var newUser = MutableLiveData<Boolean>()
    private var name: String? = null
    private var email: String? = null
    private var gender: String = "Male"
    private var context: Context? = null
    var selectedGender = MutableLiveData<String>("Male")
    private var otp = ""
    private var isNewUser: Boolean = false
    private var doesMobileExists: Boolean = false

    var buttonClickedOperation = MutableLiveData<ButtonClickedOperation>()

    fun onIntroMobileClicked(view: View) {
        buttonClickedOperation.value = ButtonClickedOperation.LOGIN

    }

    fun getOtpButtonClicked(view: View) {
        if (authModel.mobileNumber.isNullOrEmpty()) {
            message.value = app.resources.getString(R.string.invalid_mobile)
            return
        }
        authModel.userType = 5

        getOtp()

    }

    fun submitOtpButtonClicked(view: View) {
        checkAndVerifyUser()

    }

    private fun checkAndVerifyUser() {
        if (authModel.otp.isNullOrEmpty()) {
            message.value = app.resources.getString(R.string.invalid_mobile)
            return
        }

        if (!isNewUser) {
            verifyOtp()
        } else {
            if (otp.equals(authModel.otp)) {
                if (doesMobileExists) {
                    buttonClickedOperation.value = ButtonClickedOperation.SIGNP
                } else {
                    createUser()
                }

            } else {
                message.value = app.resources.getString(R.string.error_invalid_otp)
            }
        }

    }

    private fun createUser() {
        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgressDialog.value = true
            model.createUser(authModel, object : ApiCallback() {
                override fun onSuccess(obj: Any?) {
                    val response = obj as AuthModel

                    if (response.statusCode == 201) {
                        message.value = response.message
                        isNewUser = response.result?.newUser!!
                        ClsGeneral.setPreferences(Constant.TOKEN, response.result?.token)
                        ClsGeneral.setPreferences(Constant.NAME, response.result?.name)
                        ClsGeneral.setPreferences(Constant.EMAIL, response.result?.email)
                        ClsGeneral.setPreferences(Constant.GENDER, response.result?.gender)
                        ClsGeneral.setPreferences(Constant.USERID, response.result?._id)
                        buttonClickedOperation.value = ButtonClickedOperation.SIGNP
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

    private fun verifyOtp() {
        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgressDialog.value = true
            model.verifyOtp(authModel, object : ApiCallback() {
                override fun onSuccess(obj: Any?) {
                    val response = obj as AuthModel

                    if (response.statusCode == 200) {
                        message.value = response.message
                        ClsGeneral.setPreferences(Constant.TOKEN, response.result?.token)
                        ClsGeneral.setPreferences(Constant.EMAIL, response.result?.email)
                        ClsGeneral.setPreferences(Constant.NAME, response.result?.name)
                        ClsGeneral.setPreferences(Constant.GENDER, response.result?.gender)
                        ClsGeneral.setPreferences(Constant.USERID, response.result?._id)
                        newUser.value = response.result?.newUser
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

    private fun getOtp() {
        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgressDialog.value = true
            model.getOtp(authModel, object : ApiCallback() {
                override fun onSuccess(obj: Any?) {
                    val response = obj as AuthModel

                    if (response.statusCode == 200) {
                        otp = response.result?.otp.toString()
                        isNewUser = response.result?.newUser!!
                        doesMobileExists = response.result?.doesMobileExists!!
                        ClsGeneral.setPreferences(Constant.TOKEN, response.result?.token!!)
                        ClsGeneral.setPreferences(Constant.MOBILE, authModel.mobileNumber)
                        buttonClickedOperation.value = ButtonClickedOperation.OTP
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

    fun getMobileNumber(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                authModel.mobileNumber = editable.toString()
            }
        }
    }

    fun getName(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                name = editable.toString()
            }
        }
    }

    fun getEmail(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                email = editable.toString()
            }
        }
    }

    fun getGender(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                gender = editable.toString()
            }
        }
    }

    fun receivedOtp(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                authModel.otp = editable.toString()
                if (authModel.otp!!.length == 4){
                    checkAndVerifyUser()
                }
            }
        }
    }

    fun selectGender(view: View) {
        showGenderDialog()
    }

    private fun showGenderDialog() {
        val genderList = Utility.genderList()
        val builder = AlertDialog.Builder(context)
        builder.setTitle(app.resources.getString(R.string.title_select_gender)).setCancelable(false)

        val checkedItem = 0 //this will checked the item when user open the dialog

        builder.setSingleChoiceItems(
            genderList, checkedItem
        ) { dialog, which ->
            gender = genderList[which]
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


    fun doSkip(view : View){
        buttonClickedOperation.value = ButtonClickedOperation.HOME
    }

    fun doSignUp(view: View) {
        if (name.isNullOrEmpty()) {
            message.value = app.resources.getString(R.string.required_name)
        } else if (email.isNullOrEmpty()) {
            message.value = app.resources.getString(R.string.required_email)
        } else if ((!Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            message.value = app.resources.getString(R.string.required_correct_name)
        } else if (gender.isNullOrEmpty()) {
            message.value = app.resources.getString(R.string.required_gender)
        } else {
            updateUser()
        }
    }

    private fun updateUser() {
        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgressDialog.value = true
            val name : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), name.toString())
            val email : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), email.toString())
            val gender : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), gender.toString())
            model.updateUser(
                ClsGeneral.getPreferences(Constant.TOKEN),
                name,
                email,
                gender,
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        /*val response = obj as CommonResponse

                        if (response.statusCode == 200) {
                            message.value = response.message
                            buttonClickedOperation.value = ButtonClickedOperation.HOME
                        } else {
                            message.value = response.message
                        }


                        showProgressDialog.value = false*/

                        val response = obj as AuthModel

                        if (response.statusCode == 200) {
                            message.value = response.message
                            ClsGeneral.setPreferences(Constant.EMAIL, response.result?.email)
                            ClsGeneral.setPreferences(Constant.NAME, response.result?.name)
                            ClsGeneral.setPreferences(Constant.GENDER, response.result?.gender)
                            ClsGeneral.setPreferences(Constant.USERID, response.result?._id)
                            buttonClickedOperation.value = ButtonClickedOperation.HOME
                        } else {
                            message.value = response.message
                        }

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


    fun parseCode(message: String?) {
        var code = ""
        val p = Pattern.compile("\\b\\d{4}\\b")
        val m = p.matcher(message)
        while (m.find()) {
            code = m.group(0)
        }
        otpReceived.value = code

    }

    fun switchHomeOrSignup(it: Boolean?) {
        if (it!!) {
            buttonClickedOperation.value = ButtonClickedOperation.SIGNP
        } else {
            buttonClickedOperation.value = ButtonClickedOperation.HOME
        }
    }

    fun setContext(activity: FragmentActivity?) {
        context = activity
    }


    enum class ButtonClickedOperation {
        LOGIN,
        OTP,
        SIGNP,
        HOME
    }

}