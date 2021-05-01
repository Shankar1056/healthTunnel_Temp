package com.healthtunnel.data.model

data class BusinessAboutModel(
    var statusCode: Int,
    var message: String,
    var result: ArrayList<BusinessAboutResult>
)

data class BusinessAboutResult(
    var _id: String,
    var email: String,
    var businessName: String,
    var description: String,
    var mobileNumber: String,
    var address: BusinessAboutAddress,
    var descImages: ArrayList<BusinessAboutDescImages>,
    var businessCategories: ArrayList<BusinessAboutCategories>
)

data class BusinessAboutCategories(
    var _id: String,
    var name: String
)

data class BusinessAboutDescImages(
    var _id: String,
    var activeStatus: Int,
    var userType: Int,
    var path: String
)

data class BusinessAboutAddress(
    var address: String,
    var address2: String,
    var city: String,
    var country: String,
    var latitude: String,
    var longitude: String,
    var pincode: String,
    var state: String,
    var type: String
)