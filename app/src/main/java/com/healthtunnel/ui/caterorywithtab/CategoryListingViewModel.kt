package com.healthtunnel.ui.caterorywithtab

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.healthtunnel.R
import com.healthtunnel.data.model.*
import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.ui.utility.AppController
import com.healthtunnel.ui.utility.ClsGeneral
import com.healthtunnel.ui.utility.Constant

class CategoryListingViewModel(private val app: Application) : AndroidViewModel(app) {
    var showProgressDialog = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()
    var homeCategoryResponse = MutableLiveData<ArrayList<CatResult>>()
    var subCatService = MutableLiveData<ArrayList<SubCatResult>>()
    var wellnessCornerResponse = MutableLiveData<java.util.ArrayList<WellnessResult>>()
    var wellnessArticleResponse = MutableLiveData<WellnessArticleResult>()
    var wellnessCornerListResponse =
        MutableLiveData<java.util.ArrayList<WellnessCornerListResult>>()
    var websiteLink = MutableLiveData<String>()
    var wellnessTitle = MutableLiveData<String>()
    var header = MutableLiveData<String>()
    var headerImage = MutableLiveData<ArrayList<String>>()
    var featureProductResponse = MutableLiveData<java.util.ArrayList<DataResult>>()

    fun getServiceList(parentId: String) {
        var model = HomCategoryRequest()
        model.levels = 1
        model.parentId = parentId
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

    fun getSubCat(id: String, parentId: String) {

        var model = SubCatServiceRequest()
        model.limit = Constant.DATA_LIMIT
        model.page = 1
        model.serviceCategory = parentId
        model.serviceSubCategory = id

        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgressDialog.value = true
            model.getSubCategory(
                ClsGeneral.getPreferences(Constant.TOKEN),
                model,
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as SubCatServices

                        if (response.statusCode == 200) {
                            subCatService.value = response.result
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

    fun getAllWellnessArticles(id: String) {
        var model = HomCategoryRequest()
        model.page = 1
        model.limit = Constant.DATA_LIMIT
        model.articleType = id

        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgressDialog.value = true
            model.getWellnessArticles(
                ClsGeneral.getPreferences(Constant.TOKEN),
                model,
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as WellnessCornerListModel

                        if (response.statusCode == 200) {
                            wellnessCornerListResponse.value = response.result
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

    fun getParticularWellnessArticle(stringExtra: String?) {
        var model = HomCategoryRequest()
        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgressDialog.value = true
            model.getParticularWellnessArticle(
                ClsGeneral.getPreferences(Constant.TOKEN),
                stringExtra,
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as WellnessCornerArticle

                        if (response.statusCode == 200) {
                            wellnessArticleResponse.value = response.result
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

    fun listParticularServiceAPI(_id: String) {
        var model = HomCategoryRequest()
        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgressDialog.value = true
            model.listParticularServiceAPI(
                ClsGeneral.getPreferences(Constant.TOKEN),
                _id,
                ClsGeneral.getPreferences(Constant.NAME),
                ClsGeneral.getPreferences(Constant.EMAIL),
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        val response = obj as WebsiteRedirectLinkodel

                       websiteLink.value = response.url

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


    fun getBanners(id: String, parentId: String) {
        var model = HomCategoryRequest()
        model.limit = Constant.DATA_LIMIT
        model.page = 1
        model.serviceCategory = parentId
        model.serviceSubCategory = id

        if (AppController.getInstance().networkStateMonitor.isUp) {
            showProgressDialog.value = true
            model.getFeaturedProduct(
                ClsGeneral.getPreferences(Constant.TOKEN),
                model,
                object : ApiCallback() {
                    override fun onSuccess(obj: Any?) {
                        if (obj == null) {
                            featureProductResponse.value = ArrayList<DataResult>()
                        } else {
                            val response = obj as FeaturedProductModel

                            if (response.statusCode == 200) {
                                featureProductResponse.value = response.result
                            } else {
                                featureProductResponse.value = ArrayList<DataResult>()
                                message.value = response.message
                            }


                            showProgressDialog.value = false
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





}