package com.healthtunnel.data.model

data class FormOrderResponse(
    var success: Boolean,
    var message: String,
    var code: Int,
    var data: FormData
)

data class FormData(
    var order_encrypted_id: String
)