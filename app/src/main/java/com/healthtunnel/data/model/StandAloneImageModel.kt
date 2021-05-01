package com.healthtunnel.data.model

data class StandAloneImageModel(
    var statusCode: Int? = null,
    var message: String? = null,
    var result: ArrayList<StandAloneImageResult>? = null
)

data class StandAloneImageResult(
    var _id: String? = null,
    var serviceCategoryNumber: Int? = null,
    var path: String? = null,
    var description: String? = null
)