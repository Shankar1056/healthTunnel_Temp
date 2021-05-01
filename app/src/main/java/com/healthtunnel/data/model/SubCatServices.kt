package com.healthtunnel.data.model

data class SubCatServices(
    var statusCode: Int,
    var message: String,
    var result: ArrayList<SubCatResult>
)

data class SubCatResult(
    var _id: String,
    var apiKey: String,
    var url: String,
    var description: String,
    var serviceName: String,
    var apiLogo: String

)