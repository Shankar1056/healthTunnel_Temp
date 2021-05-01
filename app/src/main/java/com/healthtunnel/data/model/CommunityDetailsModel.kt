package com.healthtunnel.data.model

data class CommunityDetailsModel (
    var statusCode : Int,
    var message: String,
    var result: CommunityDetailsResult
)

data class CommunityDetailsResult (
    var _id: String,
    var bloodGroup: String,
    var age: String,
    var patientContactNumber: String,
    var scheduledDate: String,
    var scheduledTime: String,
    var locationName: String,
    var latitude: String,
    var longitude: String,
    var statusOfPatient: String,
    var reasonForBloodRequest: String,
    var bloodRequestedFor: String,
    var detailsOfUrgency: String,
    var patientImage: String,
    var userId: User
)

data class User (
    var name: String,
    var mobileNumber: String,
    var gender: String,
    var id: String
)