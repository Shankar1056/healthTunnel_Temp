package com.healthtunnel.data.model

import com.healthtunnel.data.network.ApiCallback
import com.healthtunnel.data.network.BackoffCallback
import com.healthtunnel.data.repository.ApiServices
import com.healthtunnel.data.repository.RetroClient
import com.healthtunnel.ui.utility.Constant
import retrofit2.Response

data class HomCategoryRequest(
    var levels: Int? = null,
    var parentId: String? = null,
    var page: Int? = null,
    var limit: Int? = null,
    var displayType: Int? = null,
    var serviceCategory: String? = null,
    var serviceSubCategory: String? = null,
    var articleType: String? = null
) {
    fun getCategory(token : String, model: HomCategoryRequest, apiCallback: ApiCallback) {
        val myCallback = object : BackoffCallback<HomeCategory>(apiCallback) {
            override fun onSuccess(response: Response<HomeCategory>?) {
                if (response != null) {
                    val region = response.body()
                    if (region != null) {
                        apiCallback.onSuccess(region)
                        return
                    }
                }
            }
        }
        RetroClient.getRetrofit()?.create(ApiServices::class.java)?.getAllCategories(Constant.BEARER+token, model)
            ?.enqueue(myCallback)
    }

    fun getWellnessCategory(token: String?, model: HomCategoryRequest, apiCallback: ApiCallback) {
        val myCallback = object : BackoffCallback<WellnessCorner>(apiCallback) {
            override fun onSuccess(response: Response<WellnessCorner>?) {
                if (response != null) {
                    val region = response.body()
                    if (region != null) {
                        apiCallback.onSuccess(region)
                        return
                    }
                }
            }
        }
        RetroClient.getRetrofit()?.create(ApiServices::class.java)?.getWellnessCategory(Constant.BEARER+token, model)
            ?.enqueue(myCallback)
    }

    fun getFeaturedProduct(
        token: String?,
        model: HomCategoryRequest,
        apiCallback: ApiCallback
    ) {
        val myCallback = object : BackoffCallback<FeaturedProductModel>(apiCallback) {
            override fun onSuccess(response: Response<FeaturedProductModel>?) {
                if (response != null) {
                    val region = response.body()
                    if (region != null) {
                        apiCallback.onSuccess(region)
                        return
                    } else{
                        apiCallback.onSuccess(region)
                    }
                }
            }
        }
        RetroClient.getRetrofit()?.create(ApiServices::class.java)?.getFeaturedProduct(Constant.BEARER+token, model)
            ?.enqueue(myCallback)
    }

    fun getWellnessArticles(
        token: String?,
        model: HomCategoryRequest,
        apiCallback: ApiCallback
    ) {
        val myCallback = object : BackoffCallback<WellnessCornerListModel>(apiCallback) {
            override fun onSuccess(response: Response<WellnessCornerListModel>?) {
                if (response != null) {
                    val region = response.body()
                    if (region != null) {
                        apiCallback.onSuccess(region)
                        return
                    }
                }
            }
        }
        RetroClient.getRetrofit()?.create(ApiServices::class.java)?.getAllWellnessArticles(Constant.BEARER+token, model)
            ?.enqueue(myCallback)
    }

    fun getParticularWellnessArticle(
        token: String?,
        stringExtra: String?,
        apiCallback: ApiCallback
    ) {
        val myCallback = object : BackoffCallback<WellnessCornerArticle>(apiCallback) {
            override fun onSuccess(response: Response<WellnessCornerArticle>?) {
                if (response != null) {
                    val region = response.body()
                    if (region != null) {
                        apiCallback.onSuccess(region)
                        return
                    }
                }
            }
        }
        if (stringExtra != null) {
            RetroClient.getRetrofit()?.create(ApiServices::class.java)?.getParticularWellnessArticle(Constant.BEARER+token, stringExtra)
                ?.enqueue(myCallback)
        }
    }

    fun listParticularServiceAPI(token: String?, _id: String, name : String, email: String, apiCallback: ApiCallback) {
        val myCallback = object : BackoffCallback<WebsiteRedirectLinkodel>(apiCallback) {
            override fun onSuccess(response: Response<WebsiteRedirectLinkodel>?) {
                if (response != null) {
                    val region = response.body()
                    if (region != null) {
                        apiCallback.onSuccess(region)
                        return
                    }
                }
            }
        }
        if (_id != null) {
            RetroClient.getRetrofit()?.create(ApiServices::class.java)?.listParticularServiceAPI(Constant.BEARER+token, _id, name, email)
                ?.enqueue(myCallback)
        }
    }

    fun getStandAloneImage(token: String?, apiCallback: ApiCallback) {
        val myCallback = object : BackoffCallback<StandAloneImageModel>(apiCallback) {
            override fun onSuccess(response: Response<StandAloneImageModel>?) {
                if (response != null) {
                    val region = response.body()
                    if (region != null) {
                        apiCallback.onSuccess(region)
                        return
                    }
                }
            }
        }
            RetroClient.getRetrofit()?.create(ApiServices::class.java)?.standAloneImage(Constant.BEARER+token)
                ?.enqueue(myCallback)
    }
}