package com.healthtunnel.data.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.healthtunnel.ui.utility.Utility
import com.bumptech.glide.Glide

data class BusinessSalesModel(
    var statusCode: Int,
    var message: String,
    var result: ArrayList<BusinessSalesResult>
)

data class BusinessSalesResult(
    var businessName: String,
    var profileImage: String,
    var distance: Double,
    var explanatory_image: String,
    var id: String,
    var address: ArrayList<BusinessAddress>



)

@BindingAdapter("bind:imageUrl")
fun loadImagee(view: ImageView, imageUrl: String) {
    Glide.with(view.context)
        .load(imageUrl)
        .into(view)
}

data class BusinessAddress(
    var city: String
)