package com.dwarsoftgames.dwarsoft_lynk_hackathon.Models;

public class VolunteerHelpModel {

    private int VRMapID;
    private int VolunteerID;
    private int HelpID;
    private int AreaID;
    private String Description;
    private String Latitude;
    private String Longitude;
    private String PhoneNo;
    private String createdOn;
    private String updatedOn;

    public int getVRMapID() {
        return VRMapID;
    }

    public void setVRMapID(int VRMapID) {
        this.VRMapID = VRMapID;
    }

    public int getVolunteerID() {
        return VolunteerID;
    }

    public void setVolunteerID(int volunteerID) {
        VolunteerID = volunteerID;
    }

    public int getHelpID() {
        return HelpID;
    }

    public void setHelpID(int helpID) {
        HelpID = helpID;
    }

    public int getAreaID() {
        return AreaID;
    }

    public void setAreaID(int areaID) {
        AreaID = areaID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }
}
