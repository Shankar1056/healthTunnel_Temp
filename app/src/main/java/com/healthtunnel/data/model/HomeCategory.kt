package com.healthtunnel.data.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

data class HomeCategory(
    var statusCode: Int,
    var message: String,
    var result: ArrayList<CatResult>
)

data class CatResult(
    var parentId: String,
    var activeStatus: Int,
    var _id: String,
    var workFlowType: String,
    var name: String,
    var formType: String,
    var description: String,
    var explanatory_image: String,
    var real_image: String? = null,
    var logoImages: ArrayList<LogoImages>

)

data class LogoImages(
    var activeStatus: Int,
    var _id: String,
    var serviceCategoryId: String,
    var path: String
)

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    Glide.with(view.context)
        .load(imageUrl).apply(RequestOptions().circleCrop())
        .into(view)
}
