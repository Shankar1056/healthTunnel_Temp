package com.healthtunnel.ui.address

import android.app.Application
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.healthtunnel.R
import com.healthtunnel.data.model.AddressReq
import com.healthtunnel.data.model.AddressResponse
import com.healthtunnel.data.model.AddressResult
import com.healthtunnel.data.model.CommonResponse
import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.ui.utility.AppController
import com.healthtunnel.ui.utility.ClsGeneral
import com.healthtunnel.ui.utility.Constant

class AddressViewModel(val app : Application) : AndroidViewModel(app) {
    var operation: String ? = null
    var addressId : String ? = null
    var model = AddressReq()
    var message = MutableLiveData<String>()
    var successMessage = MutableLiveData<String>()
    var progress = MutableLiveData<Boolean>()
    var addressResult = MutableLiveData<ArrayList<AddressResult>>()

    init {
        operation = "add"
    }

    fun getFullNme(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                model.flat = editable.toString()
            }
        }
    }

    fun getAddress(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                model.address = editable.toString()
            }
        }
    }

    fun getCity(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                model.city = editable.toString()
            }
        }
    }

    fun getPincode(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                model.pincode = editable.toString()
            }
        }
    }

    fun getState(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                model.state = editable.toString()
            }
        }
    }

    fun onContinueClicked(view: View) {

        if (model.flat.isNullOrEmpty()) {
            message.value = app.resources.getString(R.string.error_flat_no)
            return
        }
        if (model.address.isNullOrEmpty()) {
            message.value = app.resources.getString(R.string.error_address)
            return
        }

        if (model.state.isNullOrEmpty()) {
            message.value = app.resources.getString(R.string.error_state)
            return
        }
        if (model.city.isNullOrEmpty()) {
            message.value = app.resources.getString(R.string.error_city)
            return
        }

        if (model.pincode.isNullOrEmpty()) {
            message.value = app.resources.getString(R.string.error_pincode)
            return
        }

        if (operation == "add") {
            addAddress()
        } else {
            updateAdress()
        }

    }

    private fun addAddress() {
        if (AppController.getInstance().networkStateMonitor.isUp) {
            progress.value = true
            model.addAddress(
                ClsGeneral.getPreferences(Constant.TOKEN),
                model,
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as CommonResponse

                        if (response.statusCode == 201) {
                            successMessage.value = response.message
                        } else {
                            message.value = response.message
                        }


                        progress.value = false
                    }


                    override fun onHandledError() {
                        progress.value = false

                    }
                })
        } else {
            progress.value = false
            message.value = app.resources.getString(R.string.no_internet_connection)
        }
    }

    private fun updateAdress() {
        if (AppController.getInstance().networkStateMonitor.isUp) {
            progress.value = true
            model.updateAddress(
                ClsGeneral.getPreferences(Constant.TOKEN),
                addressId,
                model,
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as CommonResponse

                        if (response.statusCode == 201) {
                            successMessage.value = response.message
                        } else {
                            message.value = response.message
                        }


                        progress.value = false
                    }


                    override fun onHandledError() {
                        progress.value = false

                    }
                })
        } else {
            progress.value = false
            message.value = app.resources.getString(R.string.no_internet_connection)
        }
    }

    fun getAllAddress() {
        model = AddressReq()
        if (AppController.getInstance().networkStateMonitor.isUp) {
            progress.value = true
            model.getAddress(
                ClsGeneral.getPreferences(Constant.TOKEN),
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as AddressResponse

                        if (response.statusCode == 200) {
                            addressResult.value = response.result
                        } else {
                            message.value = response.message
                        }


                        progress.value = false
                    }


                    override fun onHandledError() {
                        progress.value = false

                    }
                })
        } else {
            progress.value = false
            message.value = app.resources.getString(R.string.no_internet_connection)
        }
    }


    fun updateParticularShippingAddress() {
        model = AddressReq()
        if (AppController.getInstance().networkStateMonitor.isUp) {
            progress.value = true
            model.getAddress(
                ClsGeneral.getPreferences(Constant.TOKEN),
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as AddressResponse

                        if (response.statusCode == 200) {
                            addressResult.value = response.result
                        } else {
                            message.value = response.message
                        }


                        progress.value = false
                    }


                    override fun onHandledError() {
                        progress.value = false

                    }
                })
        } else {
            progress.value = false
            message.value = app.resources.getString(R.string.no_internet_connection)
        }
    }


}