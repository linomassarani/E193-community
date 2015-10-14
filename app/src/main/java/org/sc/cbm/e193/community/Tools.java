package org.sc.cbm.e193.community;

import java.sql.Date;

public class Tools {
    public static String toString(Date date) {
        int month = date.getMonth();
        int day = date.getDay();
        int year = date.getYear();
        int hour = date.getHours();
        int minute = date.getMinutes();

        String month_zero = "";
        String day_zero = "";
        String minute_zero = "";
        String hour_zero = "";

        if(month < 10)
            month_zero = "0";
        if(day < 10)
            day_zero = "0";
        if(hour < 10)
            hour_zero = "0";
        if(minute < 10)
            minute_zero = "0";

        return day_zero + String.valueOf(day)
                + "/" + month_zero
                + String.valueOf(month)
                + "/"
                + String.valueOf(year)
                + " às "
                + hour_zero + String.valueOf(hour)
                + ":"
                + minute_zero + String.valueOf(minute);
    }
}
