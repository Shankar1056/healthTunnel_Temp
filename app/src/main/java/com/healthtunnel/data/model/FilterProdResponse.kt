package com.healthtunnel.data.model

data class FilterProdResponse(
    var statusCode: Int? = null,
    var message: String? = null,
    var result: ArrayList<FilterProdResult>? = null
)

data class FilterProdResult(
    var _id: String? = null,
    var name: String? = null,
    var filterImage: String? = null,
    var isChecked: Boolean = false
)