package com.healthtunnel.data.model

data class FeaturedProductModel (
    var statusCode : Int,
    var message: String,
    var result : ArrayList<DataResult>

)

data class DataResult (
    var activeStatus: String,
    var hyperLink: String,
    var bannerImage: String,
    var updatedAt: String,
    var id: String
)