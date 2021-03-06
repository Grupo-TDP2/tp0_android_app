package com.tdp2.tp0.weweather.model;

import java.util.Date;

public class DayTemperature
{
    private Date date;
    private String dayTemperature;
    private String nightTemperature;
    private ClimateState dayIcon;
    private ClimateState nightIcon;

    public DayTemperature(
            Date date,
            String dayTemperature, String nightTemperature,
            ClimateState dayIcon, ClimateState nightIcon) {
        this.date = date;
        this.dayTemperature = dayTemperature;
        this.nightTemperature = nightTemperature;
        this.dayIcon = dayIcon;
        this.nightIcon = nightIcon;
    }

    public Date getDate() {
        return date;
    }

    public String getDayTemperature() {
        return dayTemperature;
    }

    public String getNightTemperature() {
        return nightTemperature;
    }

    public ClimateState getDayIcon() {
        return dayIcon;
    }

    public ClimateState getNightIcon() {
        return nightIcon;
    }
}
