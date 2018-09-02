package com.tdp2.tp0.weweather.persistance;

import android.content.Context;
import android.content.SharedPreferences;

import com.tdp2.tp0.weweather.model.AppModel;

public class Persistance
{
    private String sharedPrefencesName =  "com.tdp2.tp0.weweather.SHARED_PREFERENCES";
    private String CITY_KEY = "com.tdp2.tp0.weweather.CITY_KEY";
    private String COUNTRY_KEY = "com.tdp2.tp0.weweather.COUNTRY_KEY";

    public void loadInto(Context context, AppModel model)
    {
        SharedPreferences preferences = context
                .getSharedPreferences(sharedPrefencesName, Context.MODE_PRIVATE);
        String city = preferences.getString(CITY_KEY, null);
        String selected = preferences.getString(COUNTRY_KEY, null);
        model.setCity(city, selected);
    }

    public void saveFrom(Context context, AppModel model)
    {
        SharedPreferences preferences = context
                .getSharedPreferences(sharedPrefencesName, Context.MODE_PRIVATE);
        preferences.edit()
                .putString(CITY_KEY, model.getCitySelected())
                .putString(COUNTRY_KEY, model.getCountryCode()).apply();
    }
}
