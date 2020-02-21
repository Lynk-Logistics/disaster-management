package com.example.myapplication.BridegfyVictim.Dao

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDetail (
        @PrimaryKey(autoGenerate = false) val _userId: Int = 0,
        val userName: String,
        val phoneNumber: Long,
        val gender: Gender = Gender.NONE,
        @Embedded val emergencyContact: EmergencyContact
        ) {
    enum class Gender {
        MALE, FEMALE, NONE
    }

    data class EmergencyContact(
            val contact1: Long,
            val contact2: Long,
            val contact3: Long
    )
}