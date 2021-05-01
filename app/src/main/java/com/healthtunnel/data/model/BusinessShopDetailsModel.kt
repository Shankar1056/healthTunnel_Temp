package com.healthtunnel.data.model

data class BusinessShopDetailsModel(
    var statusCode: Int,
    var message: String,
    var result: BusinessShopDetailsResult
)

data class BusinessShopDetailsResult(
    var activeStatus: Int,
    var _id: String,
    var name: String,
    var businessId: String,
    var businessCategory: String,
    var description: String,
    var moreInfo: String,
    var quantityAvailable: Int,
    var productImage: String,
    var plans: ArrayList<usinessShopDetailsPlan>,
    var associatedPlans: ArrayList<usinessShopAssociatePlan>,
    var cartQuantity: CardQuantity
)

data class CardQuantity (
    var productId: String,
    var quantity: Int,
    var id: String,
    var planId: String
)
data class usinessShopAssociatePlan(
    var activeStatusL: Int,
    var _id: String,
    var name: String,
    var price: String,
    var businessId: String
)

data class usinessShopDetailsPlan(
    var _id: String,
    var name: String,
    var price: Int
)