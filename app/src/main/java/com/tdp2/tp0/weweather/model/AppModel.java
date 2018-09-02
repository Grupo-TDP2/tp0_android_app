package com.tdp2.tp0.weweather.model;

import java.util.ArrayList;
import java.util.List;

public class AppModel
{
    private static AppModel instance;

    public static AppModel getInstance()
    {
        if( instance == null )
        {
            instance = new AppModel();
        }
        return instance;
    }

    private String citySelected = null;
    private List<DayTemperature> temperatures = new ArrayList();
    private boolean isDayInCity = false;

    public void setCity(String name, List<DayTemperature> temperatures, boolean isDayInCity )
    {
        this.citySelected = name;
        this.temperatures = temperatures;
        this.isDayInCity = isDayInCity;
    }

    public boolean hasCity()
    {
        return citySelected != null;
    }

    public String getCitySelected()
    {
        return citySelected;
    }

    public List<DayTemperature> getTemperatures()
    {
        return temperatures;
    }

    public boolean isDayInCity()
    {
        return isDayInCity;
    }

}
