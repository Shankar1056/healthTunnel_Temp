package com.healthtunnel.data.model

data class WellnessCornerListModel(
    var statusCode: Int,
    var message: String,
    var result: ArrayList<WellnessCornerListResult>
)

data class WellnessCornerListResult(
    var _id: String,
    var articleSubject: String,
    var articleBannerLogo: String
)

data class WellnessCornerArticle(
    var statusCode: Int,
    var message: String,
    var result: WellnessArticleResult
)

data class WellnessArticleResult(
    var _id: String,
    var articleSubject: String,
    var description: String,
    var articleLogo: String,
    var createdAt: String
)