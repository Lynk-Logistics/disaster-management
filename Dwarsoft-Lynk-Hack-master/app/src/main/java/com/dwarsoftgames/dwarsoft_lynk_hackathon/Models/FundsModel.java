package com.dwarsoftgames.dwarsoft_lynk_hackathon.Models;

public class FundsModel {

    private int OFMapID;
    private String Name;
    private String Description;
    private String Sponsor;
    private String Link;

    public int getOFMapID() {
        return OFMapID;
    }

    public void setOFMapID(int OFMapID) {
        this.OFMapID = OFMapID;
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

    public String getSponsor() {
        return Sponsor;
    }

    public void setSponsor(String sponsor) {
        Sponsor = sponsor;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }
}
