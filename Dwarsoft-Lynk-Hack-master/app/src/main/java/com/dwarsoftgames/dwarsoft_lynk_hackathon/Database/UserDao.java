package com.dwarsoftgames.dwarsoft_lynk_hackathon.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user_table")
    List<user_table> getAll();

    @Query("SELECT * FROM user_table ORDER BY uid DESC LIMIT 1")
    user_table getUserData();

    @Query("SELECT volunteer_id FROM user_table WHERE user_id = 1")
    int getVolunteerID();

    @Query("SELECT victim_id FROM user_table WHERE user_id = 1")
    int getVictimID();

    @Query("SELECT phoneNo FROM user_table WHERE user_id = 1")
    String getPhoneNumber();

    @Query("SELECT latitude FROM user_table WHERE user_id = 1")
    String getLatitude();

    @Query("SELECT longitude FROM user_table WHERE user_id = 1")
    String getLongitude();

    @Query("SELECT areaID FROM user_table WHERE user_id = 1")
    String getAreaID();

    @Query("SELECT org_id FROM user_table WHERE user_id = 1")
    int getOrgID();

    @Query("UPDATE user_table SET volunteer_id = :volunteerID WHERE user_id = 1")
    void updateVolunteerID(int volunteerID);

    @Query("UPDATE user_table SET phoneNo = :phoneNo WHERE user_id = 1")
    void updatePhoneNo(String phoneNo);

    @Query("UPDATE user_table SET latitude = :latitude WHERE user_id = 1")
    void updateLatitude(String latitude);

    @Query("UPDATE user_table SET longitude = :longitude WHERE user_id = 1")
    void updateLongitude(String longitude);

    @Query("UPDATE user_table SET areaID = :areaID WHERE user_id = 1")
    void updateAreaID(String areaID);

    @Query("UPDATE user_table SET victim_id = :victimID WHERE user_id = 1")
    void updateVictimID(int victimID);

    @Query("UPDATE user_table SET org_id = :OrgID WHERE user_id = 1")
    void updateOrgID(int OrgID);

    @Insert
    void insertAll(user_table... user_tables);
}