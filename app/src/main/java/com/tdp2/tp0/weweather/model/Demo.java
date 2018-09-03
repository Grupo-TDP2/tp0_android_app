package com.tdp2.tp0.weweather.model;

public abstract class Demo
{
    private City city;

    public Demo(City city)
    {
        this.city = city;
    }

    public boolean hasCity(City city)
    {
        return this.city.getId() == city.getId();
    }

    public City getCity()
    {
        return city;
    }

    public boolean canBeFound(String condition)
    {
        return city.getName().toLowerCase().startsWith(condition.toLowerCase());
    }
}
