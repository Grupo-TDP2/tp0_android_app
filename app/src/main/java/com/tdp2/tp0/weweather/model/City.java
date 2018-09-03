package com.tdp2.tp0.weweather.model;

public class City
{
    private int id;
    private String name;
    private String country;

    public City( int id, String name, String country)
    {
        this.name = name;
        this.country = country;
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getCountry()
    {
        return country;
    }
}
