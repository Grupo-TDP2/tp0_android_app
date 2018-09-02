package com.tdp2.tp0.weweather.persistance;

import android.content.Context;
import android.content.SharedPreferences;

import com.tdp2.tp0.weweather.model.AppModel;

public class Persistance
{
    private String sharedPrefencesName =  "com.tdp2.tp0.weweather.SHARED_PREFERENCES";
    private String NAME_KEY = "com.tdp2.tp0.weweather.NAME_KEY";

    public void loadInto(Context context, AppModel model)
    {
        SharedPreferences preferences = context
                .getSharedPreferences(sharedPrefencesName, Context.MODE_PRIVATE);
        String selected = preferences.getString(NAME_KEY, null);
        model.setCity(selected);
    }

    public void saveFrom(Context context, AppModel model)
    {
        SharedPreferences preferences = context
                .getSharedPreferences(sharedPrefencesName, Context.MODE_PRIVATE);
        preferences.edit().putString(NAME_KEY, model.getCitySelected()).apply();
    }
}
