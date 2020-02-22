package com.example.myapplication.BridegfyVictim.Dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.BridegfyVictim.Dao.food.FoodServiceDao
import com.example.myapplication.BridegfyVictim.Dao.food.FoodServiceEntity
import com.example.myapplication.BridegfyVictim.Dao.user.UserDao
import com.example.myapplication.BridegfyVictim.Dao.user.UserDetail

object AppDatabaseInstance {
    fun getDb(context: Context) : AppDatabase {
        return Room.databaseBuilder(
                context,
                AppDatabase::class.java, "database-name"
        )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    }
}

@Database(entities = [FoodServiceEntity::class, UserDetail::class], version = 1)
@TypeConverters(UserDetail.EnumConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodServiceDao(): FoodServiceDao
    abstract fun userDao(): UserDao
}