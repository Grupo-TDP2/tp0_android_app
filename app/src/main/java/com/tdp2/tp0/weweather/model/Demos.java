package com.tdp2.tp0.weweather.model;

import com.tdp2.tp0.weweather.model.demos.Paris2Demo;
import com.tdp2.tp0.weweather.model.demos.Paris1Demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Demos
{
    private static List<Demo> demos = Arrays.asList(new Paris2Demo(), new Paris1Demo());

    public static List<City> getDemoCities(String condition)
    {
        List<City> filtered = new ArrayList<>();
        for( Demo demo : demos )
        {
            if( demo.canBeFound(condition) )
            {
                filtered.add(demo.getCity());
            }
        }
        return filtered;
    }
}
