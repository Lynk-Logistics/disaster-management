package com.example.myapplication.BridegfyVictim.Dao.food

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FoodServiceDao {

    @Query("SELECT * FROM FoodServiceEntity")
    fun getSavedFoodServices(): List<FoodServiceEntity>

    @Insert
    fun insertFoodService(foodServiceEntity: FoodServiceEntity)
}