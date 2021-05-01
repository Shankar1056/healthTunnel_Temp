package com.healthtunnel.data.model

data class GetOtpModelReq (
    var mobileNumber: String? = null,
    var userType: Int? = null,
    var otp: String? = null
)