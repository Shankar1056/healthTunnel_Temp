package com.healthtunnel.ui.staticform

import android.app.*
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.healthtunnel.R
import com.healthtunnel.data.model.*
import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.ui.utility.AppController
import com.healthtunnel.ui.utility.ClsGeneral
import com.healthtunnel.ui.utility.Constant
import com.healthtunnel.ui.utility.Utility
import java.util.*

class StaticFormViewModel(private val app: Application) : AndroidViewModel(app) {
    var setlabcode = MutableLiveData<Array<String>>()
    private var context: Context? = null
    var selectedGender = MutableLiveData<String>()
    var selectedHomeDateTime = MutableLiveData<String>()
    var selectedMedicalServices = MutableLiveData<String>()
    var isChecked = MutableLiveData<Boolean>(false)
    var showProgressDialog = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()
    var formMessage = MutableLiveData<String>()
    var availableTestResponse = MutableLiveData<ArrayList<AvailableTests>>()
    var couponResultResponse = MutableLiveData<ArrayList<CouponResult>>()
    private var medlifeToen: String = ""
    private var address1: String = ""
    private var address2: String = ""
    private var date: String = ""
    private var model = MedlifeOrderFormReq()

    fun setContext(activity: Activity) {
        context = activity
    }

    fun getFullNme(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                model.name = editable.toString()
            }
        }
    }

    fun getAge(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                model.age = editable.toString().toInt()
            }
        }
    }


    fun getpincode(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                model.pincode = editable.toString()
            }
        }
    }

    fun getAddess1(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                address1 = editable.toString()
                model.address = address1 + "," + address2
            }
        }
    }

    fun getAddess2(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                address2 = editable.toString()
                model.address = address1 + "," + address2
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

    fun getinstruction(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                model.instruction = editable.toString()
            }
        }
    }

    fun onGenderClicked(view: View) {
        popupDialog(
            Utility.getGender(),
            app.resources.getString(R.string.title_select_gender),
            1
        )
    }

    fun onMedicalServicesClicked(view: View) {
        if (model.pincode.isNullOrEmpty()) {
            Toast.makeText(app, app.resources.getString(R.string.error_pincode), Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (medlifeToen.isNullOrEmpty()) {
            getToken()
        } else {
            getAvailableTest()
        }
    }

    private fun getToken() {
        var model = MedlifeTokenReq()
        model.username = "health_tunnel@partners.medlife.com"
        model.password = "health_tunnel@123"
        model.application = "labs-partner"
        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgressDialog.value = true
            model.getAvailableTest(
                model,
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as MedlifeTokenResponse

                        medlifeToen = response.token
                        if (medlifeToen.isNullOrEmpty()) {
                            message.value =
                                app.resources.getString(R.string.title_unable_to_fetch_token)
                        } else {
                            getAvailableTest()
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

    private fun getAvailableTest() {
        var model1 = AvailableTestAPI()
        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgressDialog.value = true
            model1.getAvailableTest(
                medlifeToen,
                /*ClsGeneral.getPreferences(Constant.PINCODE),
                ClsGeneral.getPreferences(Constant.DISTRICT),*/
                model.pincode,
                model.city,
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as AvailabeTestModel

                        availableTestResponse.value = response.tests


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

    fun onHomeDateTimeClicked(view: View) {
        showDateTimeDialog()
    }

    private fun showDateTimeDialog() {
        val c = Calendar.getInstance()
        val mYearParam: Int = c.get(Calendar.YEAR)
        val mMonthParam: Int = c.get(Calendar.MONTH)
        val mDayParam: Int = c.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(context!!,
            { view, year, monthOfYear, dayOfMonth ->

                date = "$year-${monthOfYear + 1}-$dayOfMonth"
                openTimeDialog()

            }, mYearParam, mMonthParam, mDayParam)
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()


    }

    private fun openTimeDialog() {
        val c = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(context,
            { view, pHour, pMinute ->
                model.collection_date = Utility.getDatetime("$date $pHour:$pMinute:00")
                selectedHomeDateTime.value = "$date $pHour:$pMinute:00"
            }, c.get(Calendar.HOUR), c.get(Calendar.MINUTE), true)

        timePickerDialog.show()
    }

    fun onRequestSubmitClicked(view: View) {

        model.courier_req = isChecked.value
        model.collection_type = "home"
        model.email = ClsGeneral.getPreferences(Constant.EMAIL)
        model.lat = ClsGeneral.getPreferences(Constant.LATITUTE)
        model.lng = ClsGeneral.getPreferences(Constant.LONGITUTE)
        model.sex = selectedGender.value
        model.order_time = model.collection_date
        model.source = "via Partner HealthTunnel"
        model.phone = ClsGeneral.getPreferences(Constant.MOBILE)
        model.test_code = setlabcode.value
        if (model.name.isNullOrEmpty()) {
            message.value = context?.resources?.getString(R.string.title_patient_name)
            return
        }
        if (model.sex.isNullOrEmpty()) {
            message.value = context?.resources?.getString(R.string.title_select_gender)
            return
        }
      /*  if (model.age!! > 0) {
            message.value = context?.resources?.getString(R.string.title_age)
        }*/
        if (model.pincode.isNullOrEmpty()) {
            message.value = context?.resources?.getString(R.string.title_pincode)
            return
        }
        /*if (model.test_code.array.i) {
            message.value = context?.resources?.getString(R.string.title_select_test_code)
        }*/
        /*if (model.collection_date.isNullOrEmpty()) {
            message.value = context?.resources?.getString(R.string.title_select_date_time)
        }*/
        if (address1.isNullOrEmpty()) {
            message.value = context?.resources?.getString(R.string.title_address_1)
            return
        }
        if (address2.isNullOrEmpty()) {
            message.value = context?.resources?.getString(R.string.title_address_2)
            return
        }
        if (model.city.isNullOrEmpty()) {
            message.value = context?.resources?.getString(R.string.title_select_city)
            return
        }

        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgressDialog.value = true
            model.placeFormOrder(
                medlifeToen,
                model,
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as FormOrderResponse

                        formMessage.value = response.message


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

    fun termsCondition(isChecked: Boolean) {
        this.isChecked.value = isChecked
    }

    private fun popupDialog(yearList: Array<String>, title: String, position: Int) {
        var selectedVllue = ""
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title).setCancelable(false)

        val checkedItem = 0 //this will checked the item when user open the dialog

        builder.setSingleChoiceItems(
            yearList, checkedItem
        ) { dialog, which ->
            selectedVllue = yearList[which]
            setValue(position, selectedVllue)

        }

        builder.setPositiveButton(
            "Done"
        ) { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()

    }

    private fun setValue(position: Int, selectedVllue: String) {

        when (position) {
            1 -> selectedGender.value = selectedVllue.toLowerCase()
        }

    }

    fun getCouponCode(id: String?) {
        var model = CouponReq()
        model.limit = Constant.DATA_LIMIT
        model.page = 1
        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgressDialog.value = true
            model.getCoupon(
                ClsGeneral.getPreferences(Constant.TOKEN),
                id.toString(),
                model,
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as CouponResponse

                        couponResultResponse.value = response.result


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


}