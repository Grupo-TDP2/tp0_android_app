package com.tdp2.tp0.weweather.persistance;

import android.content.Context;
import android.content.SharedPreferences;

import com.tdp2.tp0.weweather.model.AppModel;
import com.tdp2.tp0.weweather.model.City;

public class Persistance
{
    private String sharedPrefencesName =  "com.tdp2.tp0.weweather.SHARED_PREFERENCES";
    private String CITY_KEY = "com.tdp2.tp0.weweather.CITY_KEY";
    private String COUNTRY_KEY = "com.tdp2.tp0.weweather.COUNTRY_KEY";
    private String CITY_ID = "com.tdp2.tp0.weweather.CITY_ID";

    public void loadInto(Context context, AppModel model)
    {
        SharedPreferences preferences = context
                .getSharedPreferences(sharedPrefencesName, Context.MODE_PRIVATE);
        String city = preferences.getString(CITY_KEY, null);
        String country = preferences.getString(COUNTRY_KEY, null);
        int id = preferences.getInt(CITY_ID, -1);
        if( city != null && country != null && id != -1 )
        {
            model.setCity(new City(id, city, country));
        }

    }

    public void saveFrom(Context context, AppModel model)
    {
        City city = model.getCitySelected();
        SharedPreferences preferences = context
                .getSharedPreferences(sharedPrefencesName, Context.MODE_PRIVATE);
        preferences.edit()
                .putString(CITY_KEY, city.getName())
                .putString(COUNTRY_KEY, city.getCountry())
                .putInt(CITY_ID, city.getId())
                .apply();
    }
}
