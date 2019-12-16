package com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils;

public class Constants {

    public static final String SHAREDPREF = "SharedPreference";

    private static final String ENDPOINT = "http://172.18.13.187:3004/api/";

    public static final String AUTH = ENDPOINT + "volunteerauth";

    public static final String AUTH_DATA = ENDPOINT + "volunteerinsert";

    public static final String STATES = ENDPOINT + "getstates";

    public static final String CITY = ENDPOINT + "getcities";

    public static final String AREA = ENDPOINT + "getareas";

    public static final String GET_GROUP_DETAILS = ENDPOINT + "getgroupdetails";

    public static final String CREATE_GROUP = ENDPOINT + "creategroup";

    public static final String GET_HELP_TYPES = ENDPOINT + "gethelpertypes";

    public static final String SHARE_RESOURCES = ENDPOINT + "helpsomeone";

    public static final String GET_VICTIM_DETAILS = ENDPOINT + "getvictimdetails";

    public static final String GET_VOLUNTEER_DETAILS = ENDPOINT + "getvolunteerdetails";

    public static final String REQUEST_HELP = ENDPOINT + "posthelpvictim";

    public static final String GET_PROFILE_DETAILS = ENDPOINT + "getprofiledetails";

    public static final String UPDATE_PROFILE_DETAILS = ENDPOINT + "updateprofile";

    public static final String UPDATE_VHMAP_ISCOMPLETE = ENDPOINT + "iscompletevictim";

    public static final String UPDATE_VRMAP_ISCOMPLETE = ENDPOINT + "iscompletevolunteer";

//    public static final String GET_VHMAP = ENDPOINT + "getvictimshelpmap"; //VictimID

    public static final String GET_VRMAP = ENDPOINT + "getvolunteerhelpmap"; //VolunteerID

    public static final String ORGANIZATION_AUTH = ENDPOINT + "ngoauth";

    public static final String ORGANIZATION_AUTH_DATA = ENDPOINT + "ngoinsert";

    public static final String ORGANIZATION_FUND_CAMPAIGN = ENDPOINT + "ngofundinsert";

    public static final String ORGANIZATION_FUNDS_LIST = ENDPOINT + "getngofunddetails";

    public static final String ORGANIZATION_LIST = ENDPOINT + "getngodetails";

    public static final String TEXT_SMS = "http://api.msg91.com/api/sendhttp.php?country=91&sender=TESTIN&route=4&mobiles=";

    public static final String AUTH_KEY = "&authkey=235086AuBUHp6g5b8a8abc&message=";
}