package com.example.myapplication.BridegfyVictim.Dao.food

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FoodServiceEntity(
        @PrimaryKey(autoGenerate = true) val _id: Int = 0,
        val longitude: Double,
        val latitude: Double,
        val message: String
) {
}