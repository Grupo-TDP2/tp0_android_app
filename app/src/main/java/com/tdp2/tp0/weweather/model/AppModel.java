package com.tdp2.tp0.weweather.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AppModel
{
    private static AppModel instance;
    public static final City DEFAULT_CITY = new City(3435910, "Buenos Aires", "AR");
    public static AppModel getInstance()
    {
        if( instance == null )
        {
            instance = new AppModel();
        }
        return instance;
    }

    private boolean hasConnectivity = false;
    private City city = null;
    private List<DayTemperature> temperatures = new ArrayList<>();
    private boolean isDayInCity = false;

    private AppModel()
    {
        Date date = new Date();
        for( int i = 0; i < 5; i++)
        {
           temperatures.add(new DayTemperature(date,
                   25,25,
                   ClimateState.CLEAR, ClimateState.CLEAR ));
           Calendar c = Calendar.getInstance();
           c.setTime(date);
           c.add(Calendar.DATE, 1);
           date = c.getTime();
        }
    }

    public void setCity(City city)
    {
        this.city = city;
    }

    public void setCityData(List<DayTemperature> temperatures, boolean isDayInCity )
    {
        this.temperatures = temperatures;
        this.isDayInCity = isDayInCity;
    }

    public boolean hasCity()
    {
        return city != null;
    }

    public City getCitySelected()
    {
        return city;
    }

    public List<DayTemperature> getTemperatures()
    {
        return temperatures;
    }

    public boolean isDayInCity()
    {
        return isDayInCity;
    }

    public boolean hasConnectivity()
    {
        return hasConnectivity;
    }

    public void setHasConnectivity(boolean hasConnectivity)
    {
        this.hasConnectivity = hasConnectivity;
    }
}
