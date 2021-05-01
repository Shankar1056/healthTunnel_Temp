package com.healthtunnel.ui.blood

import android.app.*
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.view.View
import android.widget.TimePicker
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.healthtunnel.R
import com.healthtunnel.data.model.BloodDonateModel
import com.healthtunnel.data.model.BloodRequestModel
import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.ui.utility.AppController
import com.healthtunnel.ui.utility.ClsGeneral
import com.healthtunnel.ui.utility.Constant
import com.healthtunnel.ui.utility.Utility
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.util.*


class BloodViewModel(val app: Application) : AndroidViewModel(app) {
    private var context: Context? = null
    var selectedYear = MutableLiveData<String>(app.resources.getString(R.string.titl_select))
    var selectedBloodType = MutableLiveData<String>(app.resources.getString(R.string.titl_select))
    var selectedBloodDonate = MutableLiveData<String>(app.resources.getString(R.string.titl_select))
    var selectedCovid = MutableLiveData<String>(app.resources.getString(R.string.titl_select))
    var selectedPlasma = MutableLiveData<String>(app.resources.getString(R.string.titl_select))
    var fullName: String? = null
    var selectedGender = MutableLiveData<String>(app.resources.getString(R.string.titl_select))
    var selectedBloodRequestReason =
        MutableLiveData<String>(app.resources.getString(R.string.titl_select))
    var selectedBloodRequestedFor =
        MutableLiveData<String>(app.resources.getString(R.string.titl_select))
    var selectedScheduleDate =
        MutableLiveData<String>(app.resources.getString(R.string.titl_select_req_date))
    var selectedScheduleTime =
        MutableLiveData<String>(app.resources.getString(R.string.titl_select_req_time))
    var selectedBloodTypeRequired =
        MutableLiveData<String>(app.resources.getString(R.string.titl_select))
    var selectedBloodReceiver =
        MutableLiveData<String>(app.resources.getString(R.string.titl_select))
    var enteredAge: String? = null
    var locationAreaTown: String? = null
    var relativeMobileNumber: String? = null
    var detailsOfUrgency: String? = null
    var showProgressDialog = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String>()
    var successMessage = MutableLiveData<String>()
    var isChecked = MutableLiveData<Boolean>(false)


    fun onYearClicked(view: View) {

        getYearList(
            Utility.getYearList(),
            app.resources.getString(R.string.title_select_birth_year),
            1
        )

    }


    fun onBloodTypeClicked(view: View) {
        getYearList(
            Utility.getBloodGroup(),
            app.resources.getString(R.string.title_select_blood_type),
            2
        )
    }

    fun onBloodDonatedClicked(view: View) {
        getYearList(
            Utility.getTrueFalse(),
            app.resources.getString(R.string.titl_blood_donate_message),
            3
        )
    }

    fun onBloodCovidClicked(view: View) {
        getYearList(
            Utility.getTrueFalse(),
            app.resources.getString(R.string.titl_covid_survivour),
            4
        )
    }

    fun onPlasmaBloodClicked(view: View) {
        getYearList(
            Utility.getTrueFalse(),
            app.resources.getString(R.string.titl_blood_donate_plasma),
            5
        )
    }


    fun onGenderClicked(view: View) {
        getYearList(
            Utility.getGender(),
            app.resources.getString(R.string.title_select_gender),
            6
        )
    }


    fun onBloodRequiredClicked(view: View) {
        getYearList(
            Utility.getBloodGroup(),
            app.resources.getString(R.string.titl_blood_type_required),
            7
        )
    }

    fun onScheduleClicked(view: View) {
        val c = Calendar.getInstance()
        val month: Int = c.get(Calendar.MONTH)
        val day: Int = c.get(Calendar.DAY_OF_MONTH)
        val year: Int = c.get(Calendar.YEAR)
        val datePickerDialog = DatePickerDialog(
            context!!,
            { view, year, month, dayOfMonth ->
                selectedScheduleDate.value = "$dayOfMonth-${month + 1}-$year"

            },
            year,
            month,
            day
        )
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.show()
    }

    fun onScheduleTimeClicked(view: View) {
        val calendar: Calendar = Calendar.getInstance()
        var hour: Int = 0
        var minute: Int = 0

        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog =
            TimePickerDialog(
                context, object : TimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                        selectedScheduleTime.value = "$hourOfDay:$minute"
                    }

                }, hour, minute,
                DateFormat.is24HourFormat(context)
            )
        timePickerDialog.show()
    }

    fun onBloodChangeReasonClicked(view: View) {
        getYearList(
            Utility.getBloodRequestReason(),
            app.resources.getString(R.string.titl_blood_change_reason),
            8
        )
    }

    fun onBloodRequestedClicked(view: View) {
        getYearList(
            Utility.getBloodRequestedFor(),
            app.resources.getString(R.string.titl_blood_requested_for),
            9
        )
    }

    fun onBloodReceiverClicked(view: View) {
        getYearList(
            Utility.getBloodReceiverStatus(),
            app.resources.getString(R.string.titl_blood_receiver_status),
            10
        )
    }

    fun getFullNme(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                fullName = editable.toString()
            }
        }
    }

    fun getAge(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                enteredAge = editable.toString()
            }
        }
    }

    fun getAreaTown(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                locationAreaTown = editable.toString()
            }
        }
    }

    fun getRelativeContactNumber(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                relativeMobileNumber = editable.toString()
            }
        }
    }

    fun getDonalDetails(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                detailsOfUrgency = editable.toString()
            }
        }
    }


    private fun getYearList(yearList: Array<String>, title: String, position: Int) {
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
            1 -> selectedYear.value = "${Utility.getYearInYears(selectedVllue.toInt())}"
            2 -> selectedBloodType.value = selectedVllue
            3 -> selectedBloodDonate.value = selectedVllue
            4 -> selectedCovid.value = selectedVllue
            5 -> selectedPlasma.value = selectedVllue
            6 -> selectedGender.value = selectedVllue
            7 -> selectedBloodTypeRequired.value = selectedVllue
            8 -> selectedBloodRequestReason.value = selectedVllue
            9 -> selectedBloodRequestedFor.value = selectedVllue
            10 -> selectedBloodReceiver.value = selectedVllue
        }
    }


    fun setContext(bloodDonationActivity: Activity) {
        context = bloodDonationActivity
    }

    fun onSubmitClicked(view: View) {

        if (selectedYear.value == app.resources.getString(R.string.titl_select) || selectedBloodType.value == app.resources.getString(
                R.string.titl_select
            ) || selectedBloodDonate.value == app.resources.getString(R.string.titl_select) || selectedCovid.value == app.resources.getString(
                R.string.titl_select
            ) || selectedPlasma.value == app.resources.getString(R.string.titl_select)
        ) {
            errorMessage.value = app.resources.getString(R.string.title_all_field_mendator)
        } else {

            saveDonateBloodInfo()
        }
    }

    fun onRequestSubmitClicked(view: View) {

        if (fullName.isNullOrEmpty() || selectedGender.value.isNullOrEmpty() || enteredAge.isNullOrEmpty() || selectedBloodType.value.isNullOrEmpty() || selectedScheduleDate.value.isNullOrEmpty()
            || selectedScheduleTime.value.isNullOrEmpty() || locationAreaTown.isNullOrEmpty() || selectedBloodRequestReason.value.isNullOrEmpty() ||
            relativeMobileNumber.isNullOrEmpty() || selectedBloodReceiver.value.isNullOrEmpty() || detailsOfUrgency.isNullOrEmpty()
        ) {
            errorMessage.value = app.resources.getString(R.string.title_all_field_mendator)
        } else {

            saveRequestBloodInfo()
        }
    }

    private fun saveRequestBloodInfo() {
        val model = BloodRequestModel()
        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgressDialog.value = true
            model.saveBloodDonateRecord(
                ClsGeneral.getPreferences(Constant.TOKEN),
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    selectedBloodTypeRequired.value!!.toString()
                ),
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    enteredAge.toString()
                ),
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    relativeMobileNumber.toString()
                ),
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    selectedScheduleDate.value!!.toString()
                ),
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    selectedScheduleTime.value!!.toString()
                ),
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    locationAreaTown.toString()
                ),
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    ClsGeneral.getPreferences(Constant.LATITUTE)
                ),
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    ClsGeneral.getPreferences(Constant.LONGITUTE)
                ),
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    ""
                ),
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    selectedBloodReceiver.value!!.toString()
                ),
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    selectedBloodRequestReason.value!!.toString()
                ),
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    selectedBloodRequestedFor.value!!.toString()
                ),
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    detailsOfUrgency.toString()
                ),

                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as BloodRequestModel

                        if (response.statusCode == 201) {
                            successMessage.value = response.message
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

    private fun saveDonateBloodInfo() {
        val model = BloodDonateModel()
        if (AppController.getInstance().networkStateMonitor.isUp) {
            var sCovid: String = ""
            var sBlood: String = ""
            var sPlasma: String = ""
            if (selectedCovid.value == "Yes") {
                sCovid = "true"
            } else if (selectedCovid.value == "No") {
                sCovid = "false"
            }
            if (selectedBloodDonate.value == "Yes") {
                sBlood = "true"
            } else if (selectedBloodDonate.value == "No") {
                sBlood = "false"
            }
            if (selectedPlasma.value == "Yes") {
                sPlasma = "true"
            } else if (selectedPlasma.value == "No") {
                sPlasma = "false"
            }

            showProgressDialog.value = true
            model.saveBloodDonateRecord(
                ClsGeneral.getPreferences(Constant.TOKEN),
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    selectedBloodType.value!!.toString()
                ),
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    selectedYear.value!!.toString()
                ),
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),  sCovid
                ),
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),sPlasma
                ),
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),sBlood
                ),
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    ClsGeneral.getPreferences(Constant.LATITUTE)
                ),
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    ClsGeneral.getPreferences(Constant.LONGITUTE)
                ),

                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as BloodDonateModel

                        if (response.statusCode == 201) {
                            successMessage.value = response.message
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

    fun termsCondition(isChecked: Boolean) {
        this.isChecked.value = isChecked
    }


}