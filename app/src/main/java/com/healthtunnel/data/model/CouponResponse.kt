package com.healthtunnel.data.model

data class CouponResponse(
    var statusCode: Int,
    var message: String,
    var result: ArrayList<CouponResult>
)

data class CouponResult(

    var _id: String,
    var description: String,
    var couponCode: String
)
