package com.dwarsoftgames.dwarsoft_lynk_hackathon.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class user_table {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "user_id")
    private int user_id;

    @ColumnInfo(name = "phoneNo")
    private String phoneNo;

    @ColumnInfo(name = "volunteer_id")
    private int volunteer_id = 0;

    @ColumnInfo(name = "victim_id")
    private int victim_id = 0;

    @ColumnInfo(name = "org_id")
    private int org_id = 0;

    @ColumnInfo(name = "latitude")
    private String latitude = "";

    @ColumnInfo(name = "longitude")
    private String longitude = "";

    @ColumnInfo(name = "areaID")
    private String areaID;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public int getVolunteer_id() {
        return volunteer_id;
    }

    public void setVolunteer_id(int volunteer_id) {
        this.volunteer_id = volunteer_id;
    }

    public int getVictim_id() {
        return victim_id;
    }

    public void setVictim_id(int victim_id) {
        this.victim_id = victim_id;
    }

    public int getOrg_id() {
        return org_id;
    }

    public void setOrg_id(int org_id) {
        this.org_id = org_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAreaID() {
        return areaID;
    }

    public void setAreaID(String areaID) {
        this.areaID = areaID;
    }
}