package com.healthtunnel.ui.ecom

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.healthtunnel.R
import com.healthtunnel.data.model.*
import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.ui.utility.AppController
import com.healthtunnel.ui.utility.ClsGeneral
import com.healthtunnel.ui.utility.Constant

class EcomViewModel(private val app: Application) : AndroidViewModel(app) {

    val filterProd = MutableLiveData<Array<String>>()
    val distance = MutableLiveData<Double>()
    val tite = MutableLiveData<String>()
    var showProgress = MutableLiveData<Boolean>()
    var noItemInCart = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()
    var successMessage = MutableLiveData<String>()
    var businessSalesresponse = MutableLiveData<ArrayList<BusinessSalesResult>>()
    var businessAboutResponse = MutableLiveData<ArrayList<BusinessAboutResult>>()
    var shopCategoryResponse = MutableLiveData<ArrayList<BusinessAboutCategories>>()
    var shopCategoryListResponse = MutableLiveData<ArrayList<BusinessShopResult>>()
    var businessShopDetailsResponse = MutableLiveData<BusinessShopDetailsResult>()
    var filterCatResult = MutableLiveData<ArrayList<FilterCatResult>>()
    var filterProdResult = MutableLiveData<ArrayList<FilterProdResult>>()
    var cartDetailsResponse = MutableLiveData<ArrayList<CartDetailsResult>>()
    var clickedPosition = MutableLiveData<Int>(1)
    var selectedValue = MutableLiveData<String>()


    fun onAboutClicked(view: View) {
        clickedPosition.value = 1
    }

    fun onShopNowClicked(view: View) {
        clickedPosition.value = 2
    }

    fun onButtonClicked(view: View) {
        clickedPosition.value = 2
    }

    fun getBusinessSalesList(stringExtra: String?, filterCat: String?) {

        var model = BusinessSalesListReq()

        model.latitude = ClsGeneral.getPreferences(Constant.LATITUTE).toDouble()
        model.longitude = ClsGeneral.getPreferences(Constant.LONGITUTE).toDouble()
        model.serviceCategory = stringExtra
        model.filterCategory = filterCat
        model.filterProducts = filterProd.value
        model.page = 1
        model.limit = Constant.DATA_LIMIT

        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgress.value = true
            model.getBusinessSalesList(
                ClsGeneral.getPreferences(Constant.TOKEN),
                model,
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as BusinessSalesModel

                        if (response.statusCode == 200) {
                            businessSalesresponse.value = response.result
                        } else {
                            message.value = response.message
                        }


                        showProgress.value = false
                    }

                    override fun onHandledError() {
                        showProgress.value = false
                    }
                })
        } else {
            showProgress.value = false
            message.value = app.resources.getString(R.string.no_internet_connection)
        }

    }

    fun listParticularBusinessLead(id: String?) {
        var model = BusinessSalesListReq()


        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgress.value = true
            model.listParticularBusinessLead(
                ClsGeneral.getPreferences(Constant.TOKEN),
                id,
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as BusinessAboutModel

                        if (response.statusCode == 200) {
                            businessAboutResponse.value = response.result
                        } else {
                            message.value = response.message
                        }


                        showProgress.value = false
                    }

                    override fun onHandledError() {
                        showProgress.value = false
                    }
                })
        } else {
            showProgress.value = false
            message.value = app.resources.getString(R.string.no_internet_connection)
        }
    }

    fun getAllBusinessCategories(id: String?) {
        var model = BusinessShopReq()
        model.businessId = id
        model.limit = Constant.DATA_LIMIT
        model.page = 1


        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgress.value = true
            model.getAllBusinessCategories(
                ClsGeneral.getPreferences(Constant.TOKEN),
                model,
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as BusinessCategoryModel

                        if (response.statusCode == 200) {
                            shopCategoryResponse.value = response.result
                        } else {
                            message.value = response.message
                        }


                        showProgress.value = false
                    }

                    override fun onHandledError() {
                        showProgress.value = false
                    }
                })
        } else {
            showProgress.value = false
            message.value = app.resources.getString(R.string.no_internet_connection)
        }
    }

    fun getAllBusinessProducts(businessCategoryId: String, _id: String) {
        var model = BusinessShopReq()
        model.businessId = businessCategoryId
        model.businessCategory = _id
        model.limit = Constant.DATA_LIMIT
        model.page = 1


        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgress.value = true
            model.getAllBusinessProducts(
                ClsGeneral.getPreferences(Constant.TOKEN),
                model,
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as BusinessShopListModel

                        if (response.statusCode == 200) {
                            shopCategoryListResponse.value = response.result
                        } else {
                            message.value = response.message
                        }


                        showProgress.value = false
                    }

                    override fun onHandledError() {
                        showProgress.value = false

                    }
                })
        } else {
            showProgress.value = false
            message.value = app.resources.getString(R.string.no_internet_connection)
        }
    }

    fun getParticularBusinessProduct(id: String?) {
        var model = BusinessShopReq()

        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgress.value = true
            model.getParticularBusinessProduct(
                ClsGeneral.getPreferences(Constant.TOKEN),
                id.toString(),
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as BusinessShopDetailsModel

                        if (response.statusCode == 200) {
                            businessShopDetailsResponse.value = response.result
                        } else {
                            message.value = response.message
                        }


                        showProgress.value = false
                    }

                    override fun onHandledError() {
                        showProgress.value = false

                    }
                })
        } else {
            showProgress.value = false
            message.value = app.resources.getString(R.string.no_internet_connection)
        }
    }

    fun addTocard(businessId: String?, productId: String, planId: String, quantity: Int) {

        var model = AddToCardReq()
        model.businessId = businessId
        model.productId = productId
        model.planId = planId
        model.quantity = quantity

        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgress.value = true
            model.addToCard(
                ClsGeneral.getPreferences(Constant.TOKEN),
                model,
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as AddToCartModel

                        if (response.statusCode == 200) {
                            message.value = response.message
                        } else {
                            message.value = response.message
                        }


                        showProgress.value = false
                    }

                    override fun onHandledError() {
                        showProgress.value = false

                    }
                })
        } else {
            showProgress.value = false
            message.value = app.resources.getString(R.string.no_internet_connection)
        }

    }


    fun showDialog(
        context: Context,
        yearList: Array<String?>,
        title: String
    ) {
        var selectedVllue = ""
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title).setCancelable(false)

        val checkedItem = 0 //this will checked the item when user open the dialog

        builder.setSingleChoiceItems(
            yearList, checkedItem
        ) { dialog, which ->
            selectedVllue = yearList[which].toString()
            setValue(selectedVllue)

        }

        builder.setPositiveButton(
            "Done"
        ) { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()

    }

    private fun setValue(value: String) {
        selectedValue.value = value
    }

    fun showAssociatedPlan(
        context: BusinessShopDetails,
        associatedPlanList: ArrayList<usinessShopAssociatePlan>
    ) {
        var stringList = ArrayList<String>()

        for (data in associatedPlanList) {
            stringList.add("Rs. " + data.price + "(" + data.name + ")")
        }

        showDialog(context,
            GetStringArray(stringList),
            context.resources.getString(R.string.title_select_associated_paln))
    }

    private fun GetStringArray(stringList: java.util.ArrayList<String>): Array<String?> {
        val str = arrayOfNulls<String>(stringList.size)

        // ArrayList to Array Conversion

        // ArrayList to Array Conversion
        for (j in 0 until stringList.size) {

            // Assign each value to String array
            str[j] = stringList.get(j)
        }

        return str
    }

    fun getCartDetails() {
        var model = CartDetailsReq()

        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgress.value = true
            model.cartDetails(
                ClsGeneral.getPreferences(Constant.TOKEN),
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as CartDetails

                        if (response.statusCode == 200) {
                            cartDetailsResponse.value = response.result
                        } else {
                            message.value = response.message
                        }


                        showProgress.value = false
                    }


                    override fun onHandledError() {
                        showProgress.value = false
                        cartDetailsResponse.value = ArrayList<CartDetailsResult>()

                    }
                })
        } else {
            showProgress.value = false
            message.value = app.resources.getString(R.string.no_internet_connection)
        }

    }

    fun deleteCard(productId: String, quantity: Int, totalItemSize: Int) {
        var model = AddToCardReq()
        model.productId = productId
        model.remove = 1

        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgress.value = true
            model.remoeCard(
                ClsGeneral.getPreferences(Constant.TOKEN),
                model,
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as DeleteCardResponse

                        if (response.statusCode == 200) {
                            if (quantity == 1 && totalItemSize == 1) {
                                cartDetailsResponse.value = ArrayList<CartDetailsResult>()
                                noItemInCart.value = true
                            } else {
                                successMessage.value = response.message
                            }
                        } else {
                            message.value = response.message
                        }


                        showProgress.value = false
                    }

                    override fun onHandledError() {
                        showProgress.value = false

                    }
                })
        } else {
            showProgress.value = false
            message.value = app.resources.getString(R.string.no_internet_connection)
        }
    }

    fun getFilterCat(serviceCat: String?) {
        var model = FilterReq()
        model.limit = Constant.DATA_LIMIT
        model.page = 1
        model.serviceCategory = serviceCat
        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgress.value = true
            model.getFilterCat(
                ClsGeneral.getPreferences(Constant.TOKEN),
                model,
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as FilterCatResponse

                        if (response.statusCode == 200) {
                            filterCatResult.value = response.result
                        } else {
                            message.value = response.message
                        }


                        showProgress.value = false
                    }

                    override fun onHandledError() {
                        showProgress.value = false

                    }
                })
        } else {
            showProgress.value = false
            message.value = app.resources.getString(R.string.no_internet_connection)
        }
    }

    fun getFilterProd(salesCat: String?) {
        var model = FilterReq()
        model.limit = Constant.DATA_LIMIT
        model.page = 1
        model.salesCategory = salesCat
        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgress.value = true
            model.getFilterProd(
                ClsGeneral.getPreferences(Constant.TOKEN),
                model,
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as FilterProdResponse

                        if (response.statusCode == 200) {
                            filterProdResult.value = response.result
                        } else {
                            message.value = response.message
                        }


                        showProgress.value = false
                    }

                    override fun onHandledError() {
                        showProgress.value = false

                    }
                })
        } else {
            showProgress.value = false
            message.value = app.resources.getString(R.string.no_internet_connection)
        }
    }


}