package com.healthtunnel.data.model

data class CommunityModel(
    var statusCode: Int,
    var message: String,
    var result: ArrayList<CommunityResult>
)

data class CommunityResult(
    var requestStatus: Int,
    var deleteStatus: Int,
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
    var userId: UserDetails
)

data class UserDetails(
    var createdByAdmin: Int,
    var mobileNumber: String,
    var userType: String,
    var businessId: String,
    var registerId: String,
    var email: String,
    var gender: String,
    var name: String,
    var otpExpiry: String,
    var id: String

)
