package com.tdp2.tp0.weweather.model;

import java.util.List;

public class Forecast {

    private boolean isDayInCity;
    private List<DayTemperature> dayTemperatureList;

    public Forecast(boolean isDayInCity, List<DayTemperature> list)
    {
        this.isDayInCity = isDayInCity;
        this.dayTemperatureList = list;
    }

    public boolean isDayInCity()
    {
        return isDayInCity;
    }

    public List<DayTemperature> getDayTemperatureList()
    {
        return dayTemperatureList;
    }
}
