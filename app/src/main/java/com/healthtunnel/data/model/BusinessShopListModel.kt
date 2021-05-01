package com.healthtunnel.data.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.util.*

data class BusinessShopListModel(
    var statusCode: Int,
    var message: String,
    var result: ArrayList<BusinessShopResult>
)

data class BusinessShopResult(
    var _id: String,
    var name: String,
    var description: String,
    var productImage: String,
    var plans: ArrayList<BusinessShopPlans>
)

@BindingAdapter("bind:shopimageUrl")
fun loadImage(view: ImageView, shopimageUrl: String) {
    Glide.with(view.context)
        .load(shopimageUrl)
        .into(view)
}

data class BusinessShopPlans(
    var _id: String,
    var name: String,
    var price: Int
)

/*
@BindingAdapter("bind:imageUrl")
fun loadImage(view: ImageView, imageUrl: String) {
    Glide.with(view.context)
        .load(imageUrl)
        .into(view)
}*/
