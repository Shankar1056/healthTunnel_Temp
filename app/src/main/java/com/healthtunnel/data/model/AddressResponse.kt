package com.healthtunnel.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class AddressResponse(
    var statusCode: Int,
    var message: String,
    var result: ArrayList<AddressResult>
)

@Parcelize
data class AddressResult(
    var flat: String,
    var address: String,
    var city: String,
    var state: String,
    var pincode: String,
    var userId: String,
    var id: String
) : Parcelable