package com.example.myapplication.BridegfyVictim.Dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

object FoodServiceDatabase {
    fun getDb(context: Context) : AppDatabase{
        return Room.databaseBuilder(
                context,
                AppDatabase::class.java, "database-name"
        )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    }
}

@Database(entities = arrayOf(FoodServiceEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodServiceDao(): FoodServiceDao
}