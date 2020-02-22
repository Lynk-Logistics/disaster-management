package com.example.myapplication.BridegfyVictim.Dao.user

import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM UserDetail WHERE _userId=0")
    suspend fun getCurrentUser(): UserDetail?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(userDetail: UserDetail)

}