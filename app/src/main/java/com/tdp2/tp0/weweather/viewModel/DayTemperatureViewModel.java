package com.tdp2.tp0.weweather.viewModel;

import com.tdp2.tp0.weweather.ClimateState;

import java.util.Date;

public class DayTemperatureViewModel
{
    private Date date;
    private int dayTemperature;
    private int nightTemperature;
    private ClimateState dayIcon;
    private ClimateState nightIcon;

    public DayTemperatureViewModel(
            Date date,
            int dayTemperature, int nightTemperature,
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

    public int getDayTemperature() {
        return dayTemperature;
    }

    public int getNightTemperature() {
        return nightTemperature;
    }

    public ClimateState getDayIcon() {
        return dayIcon;
    }

    public ClimateState getNightIcon() {
        return nightIcon;
    }
}
