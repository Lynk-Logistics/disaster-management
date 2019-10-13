package com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utilities {

    public static String UTCToIST(String dateUTC) {
        try {
            DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            DateFormat indianFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            utcFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
            Date timestamp;
            SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
            timestamp = utcFormat.parse(dateUTC);
            assert timestamp != null;
            Date ist = sdf.parse(indianFormat.format(timestamp));
            Calendar cal = Calendar.getInstance();
            assert ist != null;
            cal.setTime(ist);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int date = cal.get(Calendar.DATE);
            String mo = getMonthForInt(month);
            return mo + " " + date + " " + ", " + year;
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (AssertionError ignored) {
        }
        return "";
    }

    private static String getMonthForInt(int num) {
        String[] months = {"Jan","Feb","Mar","Apr","May","Jun","July","Aug","Sept","Oct","Nov","Dec"};
        return months[num];
    }
}
