package com.dwarsoftgames.dwarsoft_lynk_hackathon.Models;

public class GroupDetailsModel {

    private int GroupsID;
    private int VolunteerID;
    private int AreaID;
    private String Name;
    private String Description;
    private String Members;
    private String link;
    private String createdOn;
    private String updatedOn;
    private int isCompleted;
    private int isActive;
    private String distance;
    private String latitude;
    private String longitude;

    public int getGroupsID() {
        return GroupsID;
    }

    public int getVolunteerID() {
        return VolunteerID;
    }

    public int getAreaID() {
        return AreaID;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public String getMembers() {
        return Members;
    }

    public String getLink() {
        return link;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public int getIsActive() {
        return isActive;
    }

    public int getIsCompleted() {
        return isCompleted;
    }

    public String getDistance() {
        return distance;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setGroupsID(int groupsID) {
        GroupsID = groupsID;
    }

    public void setVolunteerID(int volunteerID) {
        VolunteerID = volunteerID;
    }

    public void setAreaID(int areaID) {
        AreaID = areaID;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setMembers(String members) {
        Members = members;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public void setIsCompleted(int isCompleted) {
        this.isCompleted = isCompleted;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
