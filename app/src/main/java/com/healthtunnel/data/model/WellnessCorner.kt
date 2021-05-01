package com.healthtunnel.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class WellnessCorner (
    var statusCode: Int,
    var message: String,
    var result: ArrayList<WellnessResult>
)

@Parcelize
data class WellnessResult (
    var activeStatus: Int,
    var _id: String,
    var name: String,
    var articleTypeImage: String
) : Parcelable