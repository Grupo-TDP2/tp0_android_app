package com.tdp2.tp0.weweather.model;

import com.tdp2.tp0.weweather.model.demos.Paris1Demo;
import com.tdp2.tp0.weweather.model.demos.Paris2Demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Demos
{
    private static List<Demo> demos = Arrays.asList(new Paris1Demo(), new Paris2Demo());

    public static boolean isDemo(City city)
    {
        for( Demo demo : demos )
        {
            if(demo.hasCity(city))
            {
                return true;
            }
        }
        return false;
    }

    public static List<City> getDemoCities(String condition)
    {
        List<City> filtered = new ArrayList<>();
        for( Demo demo : demos )
        {
            if(demo.canBeFound(condition))
            {
                filtered.add(demo.getCity());
            }
        }
        return filtered;
    }

    public static void getNewData(City demo)
    {
        //TODO DETERMINE HOW TO OBTAIN
    }
}
