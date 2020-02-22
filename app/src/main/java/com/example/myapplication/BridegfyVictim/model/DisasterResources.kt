package com.example.myapplication.BridegfyVictim.model

import com.bridgefy.sdk.client.Bridgefy

sealed class DisasterResources(
        val userDeviceId: String
) {

    data class SendEmergencyMessage(
            val emergencyContact1: Long,
            val emergencyContact2: Long,
            val message: String,
            val deviceId: String,
            private val tag: String = EMERGENCY_CONTACT_TAG
    ): DisasterResources(deviceId)

    data class FoodRequest(
            val latitude: Double?,
            val longitude: Double?,
            val area: String,
            val numOfPeople: Int,
            val requiredFood: String,
            val deviceId: String,
            private val tag: String = FOOD_REQUEST
    ) : DisasterResources(deviceId)

    data class Medication(
            val latitude: Double?,
            val longitude: Double?,
            val area: String,
            val requiredMedication: String,
            val deviceId: String,
            private val tag: String = MEDICATION
    ) : DisasterResources(deviceId)

    data class FoodService(
            val latitude: Double,
            val longitude: Double,
            val numOfPeople: Int,
            val deviceId: String,
            private val tag: String = FOOD_SERVICE
    ) : DisasterResources(deviceId)

    companion object {
        const val EMERGENCY_CONTACT_TAG = "emergency_tag"
        const val FOOD_REQUEST = "food_request"
        const val MEDICATION = "MedicationFragment"
        const val FOOD_SERVICE = "food_service"
    }
}