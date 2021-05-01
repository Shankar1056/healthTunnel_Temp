package com.healthtunnel.ui.home

import android.app.Application
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.healthtunnel.R
import com.healthtunnel.data.model.*
import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.ui.utility.AppController
import com.healthtunnel.ui.utility.ClsGeneral
import com.healthtunnel.ui.utility.Constant
import com.waheed.location.updates.livedata.LocationLiveData
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class HomeViewModel(private val app: Application) : AndroidViewModel(app) {

    var address = MutableLiveData<String>()
    var showProgressDialog = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()
    var homeCategoryResponse = MutableLiveData<ArrayList<CatResult>>()
    var wellnessCornerResponse = MutableLiveData<ArrayList<WellnessResult>>()
    var featureProductResponse = MutableLiveData<ArrayList<DataResult>>()
    var standAloneImageResult = MutableLiveData<ArrayList<StandAloneImageResult>>()
    var performedClickOperation = MutableLiveData<HomeClickListener>()
    var wellnessResult = ArrayList<WellnessResult>()

    /**
     * MutableLiveData private field to get/save location updated values
     */
    private val locationData =
        LocationLiveData(app)

    /**
     * LiveData a public field to observe the changes of location
     */
    val getLocationData: LiveData<Location> = locationData


    fun getAddressFromLatLon(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(getApplication(), Locale.getDefault())

        try {
            val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null && addresses.size > 0) {
                val obj: Address = addresses[0]
                var add: String = obj.getAddressLine(0)
                add = """
                $add
                ${obj.getCountryName()}
                """.trimIndent()
                add = """
                $add
                ${obj.getCountryCode()}
                """.trimIndent()
                add = """
                $add
                ${obj.getAdminArea()}
                """.trimIndent()
                add = """
                $add
                ${obj.getPostalCode()}
                """.trimIndent()
                add = """
                $add
                ${obj.getSubAdminArea()}
                """.trimIndent()
                add = """
                $add
                ${obj.getLocality()}
                """.trimIndent()
                add = """
                $add
                ${obj.getSubThoroughfare()}
                """.trimIndent()
                ClsGeneral.setPreferences(Constant.DISTRICT, obj.subAdminArea)
                ClsGeneral.setPreferences(Constant.PINCODE, obj.postalCode)
                if (obj.subLocality.isNullOrEmpty()) {
                    address.value = obj.locality
                } else {
                    address.value = obj.subLocality
                }
            } else {
                address.value = "Unknown"
            }

        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getPopularCat() {
        var model = HomCategoryRequest()
        model.levels = 0
        model.parentId = ""
        model.page = 1
        model.limit = Constant.DATA_LIMIT
        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgressDialog.value = true
            model.getCategory(
                ClsGeneral.getPreferences(Constant.TOKEN),
                model,
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as HomeCategory

                        if (response.statusCode == 200) {
                            homeCategoryResponse.value = response.result
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

    fun getWellnessArticle() {
        var model = HomCategoryRequest()
        model.page = 1
        model.limit = Constant.DATA_LIMIT

        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgressDialog.value = true
            model.getWellnessCategory(
                ClsGeneral.getPreferences(Constant.TOKEN),
                model,
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as WellnessCorner

                        if (response.statusCode == 200) {
                            wellnessCornerResponse.value = response.result
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

    fun getBanners(displayType: Int) {
        var model = HomCategoryRequest()
        model.page = 1
        model.limit = Constant.DATA_LIMIT
        model.displayType = displayType

        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgressDialog.value = true
            model.getFeaturedProduct(
                ClsGeneral.getPreferences(Constant.TOKEN),
                model,
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as FeaturedProductModel

                        if (response.statusCode == 200) {
                            featureProductResponse.value = response.result
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

    fun onDonateBloodClicked(view: View) {

        performedClickOperation.value = HomeClickListener.DONATE_BLOOD
    }

    fun onBloodRequestClicked(view: View) {

        performedClickOperation.value = HomeClickListener.REQUEST_BLOOD
    }

    fun onPillRemainderClicked(view: View) {

        //performedClickOperation.value = HomeClickListener.PILL_REMAINDER
    }

    fun onCommuniCenterClicked(view: View) {

        performedClickOperation.value = HomeClickListener.COMMUNI_CENTER
    }

    fun getStandAloneImage() {
        if (AppController.getInstance().networkStateMonitor.isUp) {
            var model = HomCategoryRequest()
            showProgressDialog.value = true
            model.getStandAloneImage(
                ClsGeneral.getPreferences(Constant.TOKEN),
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as StandAloneImageModel

                        if (response.statusCode == 200) {
                            standAloneImageResult.value = response.result
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


    enum class HomeClickListener {
        DONATE_BLOOD,
        REQUEST_BLOOD,
        PILL_REMAINDER,
        COMMUNI_CENTER
    }

}