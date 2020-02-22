package com.example.myapplication.BridegfyVictim.Dao.user

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

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
            val contact2: Long
    )

    class EnumConverters() {
        @TypeConverter
        fun fromGenderToInt(gender: Gender): Int {
            return when (gender) {
                Gender.MALE -> 1
                Gender.FEMALE -> 2
                Gender.NONE -> 3
            }
        }

        @TypeConverter
        fun fromIntToGender(int: Int): Gender{
            return when (int) {
                1 -> Gender.MALE
                2 ->Gender.FEMALE
                else -> Gender.NONE
            }
        }

    }
}