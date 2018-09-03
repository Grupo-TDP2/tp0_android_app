package com.tdp2.tp0.weweather.utils;

import java.util.Calendar;
import java.util.Date;

public class DateTransform
{
    /** @param dayNumber The day index starting from 1 onwards */
    public static Date transfrom(int dayNumber)
    {
        Date date = new Date();
        if( dayNumber > 1 ) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, dayNumber - 1);
            date = c.getTime();
        }
        return date;
    }
}
