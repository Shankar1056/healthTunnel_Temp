package com.healthtunnel.data.model

data class FilterCatResponse(
    var statusCode: Int? = null,
    var message: String? = null,
    var result: ArrayList<FilterCatResult>? = null
)

data class FilterCatResult(
    var _id: String? = null,
    var name: String? = null,
    var isChecked: Boolean = false
)



