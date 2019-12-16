package com.dwarsoftgames.dwarsoft_lynk_hackathon.Models;

public class VictimsHelpModel {

    private int VHMapID;
    private int VictimID;
    private int HelpID;
    private int AreaID;
    private String Description;
    private String Members;
    private String Male;
    private String Female;
    private String Children;
    private String Latitude;
    private String Longitude;
    private String PhoneNo;
    private String createdOn;
    private String updatedOn;

    public int getVHMapID() {
        return VHMapID;
    }
    public int getVictimID() {
        return VictimID;
    }

    public int getHelpID() {
        return HelpID;
    }

    public int getAreaID() {
        return AreaID;
    }

    public String getDescription() {
        return Description;
    }

    public String getMembers() {
        return Members;
    }

    public String getMale() {
        return Male;
    }

    public String getFemale() {
        return Female;
    }

    public String getChildren() {
        return Children;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setVHMapID(int VHMapID) {
        this.VHMapID = VHMapID;
    }

    public void setVictimID(int victimID) {
        VictimID = victimID;
    }

    public void setHelpID(int helpID) {
        HelpID = helpID;
    }

    public void setAreaID(int areaID) {
        AreaID = areaID;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setMembers(String members) {
        Members = members;
    }


    public void setMale(String male) {
        Male = male;
    }

    public void setFemale(String female) {
        Female = female;
    }

    public void setChildren(String children) {
        Children = children;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
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
