package com.dwarsoftgames.dwarsoft_lynk_hackathon.Models;

public class OrganizationListModel {

    private int OrgID;
    private String Name;
    private String Description;
    private String PhoneNo;
    private String GSTIn;

    public int getOrgID() {
        return OrgID;
    }

    public void setOrgID(int orgID) {
        OrgID = orgID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getGSTIn() {
        return GSTIn;
    }

    public void setGSTIn(String GSTIn) {
        this.GSTIn = GSTIn;
    }
}
