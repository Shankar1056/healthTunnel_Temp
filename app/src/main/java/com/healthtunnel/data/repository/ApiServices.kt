package com.healthtunnel.data.repository

import com.healthtunnel.data.model.*
import com.healthtunnel.ui.utility.Constant
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiServices {
    @Headers("x-api-key:" + Constant.API_KEY)
    @POST("sendOtp")
    fun sendOTP(
        @Body request: GetOtpModelReq
    ): Call<AuthModel>

    @Headers("x-api-key:" + Constant.API_KEY)
    @POST("verifyOtp")
    fun verifyOtp(
        @Body request: GetOtpModelReq
    ): Call<AuthModel>

    @Headers("x-api-key:" + Constant.API_KEY)
    @PUT("updateUser")
    @Multipart
    fun updateUser(
        @Header("Authorization") auth: String,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("gender") gender: RequestBody
    ): Call<AuthModel>

    @Headers("x-api-key:" + Constant.API_KEY)
    @POST("createUser")
    fun createUser(
        @Body request: GetOtpModelReq
    ): Call<AuthModel>

    @Headers("x-api-key:" + Constant.API_KEY)
    @POST("getAllCategories")
    fun getAllCategories(
        @Header("Authorization") auth: String,
        @Body request: HomCategoryRequest
    ): Call<HomeCategory>


    @Headers("x-api-key:" + Constant.API_KEY)
    @POST("getAllWellnessArticlesType")
    fun getWellnessCategory(
        @Header("Authorization") auth: String,
        @Body request: HomCategoryRequest
    ): Call<WellnessCorner>

    @Headers("x-api-key:" + Constant.API_KEY)
    @POST("listAllBanners")
    fun getFeaturedProduct(
        @Header("Authorization") auth: String,
        @Body request: HomCategoryRequest
    ): Call<FeaturedProductModel>

    @Headers("x-api-key:" + Constant.API_KEY)
    @POST("getAllWellnessArticles")
    fun getAllWellnessArticles(
        @Header("Authorization") auth: String,
        @Body request: HomCategoryRequest
    ): Call<WellnessCornerListModel>

    @Headers("x-api-key:" + Constant.API_KEY)
    @POST("registerDonor")
    @Multipart
    fun registerDonar(
        @Header("Authorization") auth: String,
        @Part("bloodGroup") bloodGroup: RequestBody,
        @Part("age") age: RequestBody,
        @Part("isCovidSurvivor") isCovidSurvivor: RequestBody,
        @Part("willingToDonatePlasma") willingToDonatePlasma: RequestBody,
        @Part("donatedBloodInNinetyDays") donatedBloodInNinetyDays: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody
    ): Call<BloodDonateModel>

    @Headers("x-api-key:" + Constant.API_KEY)
    @POST("registerRequester")
    @Multipart
    fun registerRequester(
        @Header("Authorization") auth: String,
        @Part("bloodGroup") bloodGroup: RequestBody,
        @Part("age") age: RequestBody,
        @Part("patientContactNumber") patientContactNumber: RequestBody,
        @Part("scheduledDate") scheduledDate: RequestBody,
        @Part("scheduledTime") scheduledTime: RequestBody,
        @Part("locationName") locationName: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part("file") file: RequestBody,
        @Part("statusOfPatient") statusOfPatient: RequestBody,
        @Part("reasonForBloodRequest") reasonForBloodRequest: RequestBody,
        @Part("bloodRequestedFor") bloodRequestedFor: RequestBody,
        @Part("detailsOfUrgency") detailsOfUrgency: RequestBody
    ): Call<BloodRequestModel>

    @Headers("x-api-key:" + Constant.API_KEY)
    @POST("listAllServiceAPI")
    fun listAllServiceAPI(
        @Header("Authorization") auth: String,
        @Body request: SubCatServiceRequest
    ): Call<SubCatServices>

    @Headers("x-api-key:" + Constant.API_KEY)
    @POST("getBloodRequests")
    fun getBloodRequests(
        @Header("Authorization") auth: String,
        @Body request: CommunityModelRequest
    ): Call<CommunityModel>

    @Headers("x-api-key:" + Constant.API_KEY)
    @GET("getParticularRequest/{id}")
    fun getParticularRequest(
        @Header("Authorization") auth: String,
        @Path("id") id: String
    ): Call<CommunityDetailsModel>

    @Headers("x-api-key:" + Constant.API_KEY)
    @PUT("updateRequestStatus/{id}")
    fun updateRequestStatus(
        @Header("Authorization") auth: String,
        @Path("id") id: String
    ): Call<CommonResponse>

    @Headers("x-api-key:" + Constant.API_KEY)
    @GET("getParticularWellnessArticle/{id}")
    fun getParticularWellnessArticle(
        @Header("Authorization") auth: String,
        @Path("id") id: String
    ): Call<WellnessCornerArticle>

    @Headers("x-api-key:" + Constant.API_KEY)
    @GET("listParticularServiceAPI/{id}")
    fun listParticularServiceAPI(
        @Header("Authorization") auth: String,
        @Path("id") id: String,
        @Query("name") name: String,
        @Query("email") email: String
    ): Call<WebsiteRedirectLinkodel>

    @Headers("x-api-key:" + Constant.API_KEY)
    @POST("listBusinessSales")
    fun listBusinessSales(
        @Header("Authorization") auth: String,
        @Body request: BusinessSalesListReq
    ): Call<BusinessSalesModel>

    @Headers("x-api-key:" + Constant.API_KEY)
    @POST("getAllBusinessCategories")
    fun getAllBusinessCategories(
        @Header("Authorization") auth: String,
        @Body request: BusinessShopReq
    ): Call<BusinessCategoryModel>

    @Headers("x-api-key:" + Constant.API_KEY)
    @POST("getAllBusinessProducts")
    fun getAllBusinessProducts(
        @Header("Authorization") auth: String,
        @Body request: BusinessShopReq
    ): Call<BusinessShopListModel>

    @Headers("x-api-key:" + Constant.API_KEY)
    @POST("addToCart")
    fun addToCart(
        @Header("Authorization") auth: String,
        @Body request: AddToCardReq
    ): Call<AddToCartModel>


    @Headers("x-api-key:" + Constant.API_KEY)
    @GET("listParticularBusinessLead/{id}")
    fun listParticularBusinessLead(
        @Header("Authorization") auth: String,
        @Path("id") id: String
    ): Call<BusinessAboutModel>

    @Headers("x-api-key:" + Constant.API_KEY)
    @GET("getParticularBusinessProduct/{id}")
    fun getParticularBusinessProduct(
        @Header("Authorization") auth: String,
        @Path("id") id: String
    ): Call<BusinessShopDetailsModel>


    @Headers("x-api-key:" + Constant.API_KEY)
    @GET("tests/")
    fun availableTeste(
        @Header("Authorization") auth: String,
        @Query("pincode") name: String,
        @Query("city") email: String
    ): Call<AvailabeTestModel>

    @Headers("x-api-key:" + Constant.API_KEY)
    @GET("getAllShippingAddress")
    fun getAllShippingAddress(
        @Header("Authorization") auth: String
    ): Call<AddressResponse>

    @Headers("x-api-key:" + Constant.API_KEY)
    @POST("login")
    fun getMedlifeToken(
        @Body request: MedlifeTokenReq
    ): Call<MedlifeTokenResponse>

    @Headers("x-api-key:" + Constant.API_KEY)
    @POST("order/")
    fun formOrder(
        @Header("Authorization") auth: String,
        @Body request: MedlifeOrderFormReq
    ): Call<FormOrderResponse>

    @Headers("x-api-key:" + Constant.API_KEY)
    @POST("displayCart")
    fun displayCart(
        @Header("Authorization") auth: String
    ): Call<CartDetails>


    @Headers("x-api-key:" + Constant.API_KEY)
    @POST("getCoupons/{id}")
    fun getCoupons(
        @Header("Authorization") auth: String,
        @Path("id") id: String,
        @Body request: CouponReq
    ): Call<CouponResponse>

    @Headers("x-api-key:" + Constant.API_KEY)
    @POST("addShippingAddress")
    fun addShippingAddress(
        @Header("Authorization") auth: String,
        @Body request: AddressReq
    ): Call<CommonResponse>

    @Headers("x-api-key:" + Constant.API_KEY)
    @POST("getAllSalesCategory")
    fun getAllSalesCategory(
        @Header("Authorization") auth: String,
        @Body request: FilterReq
    ): Call<FilterCatResponse>

    @Headers("x-api-key:" + Constant.API_KEY)
    @POST("getAllSalesFilterProducts")
    fun getAllSalesFilterProducts(
        @Header("Authorization") auth: String,
        @Body request: FilterReq
    ): Call<FilterProdResponse>

    @Headers("x-api-key:" + Constant.API_KEY)
    @PUT("updateParticularShippingAddress/{id}")
    fun updateParticularShippingAddress(
        @Header("Authorization") auth: String,
        @Path("id") id: String,
        @Body request: AddressReq
    ): Call<CommonResponse>


    @Headers("x-api-key:" + Constant.API_KEY)
    @HTTP(method = "DELETE", path = "removeItemFromCart", hasBody = true)
    fun removeItemFromCart(
        @Header("Authorization") auth: String,
        @Body request: AddToCardReq
    ): Call<DeleteCardResponse>


    @Headers("x-api-key:" + Constant.API_KEY)
    @GET("getStandAloneCategoriesImage")
    fun standAloneImage(
        @Header("Authorization") auth: String
    ): Call<StandAloneImageModel>


}