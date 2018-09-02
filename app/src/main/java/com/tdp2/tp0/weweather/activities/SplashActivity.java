package com.tdp2.tp0.weweather.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tdp2.tp0.weweather.model.AppModel;
import com.tdp2.tp0.weweather.model.DayTemperature;
import com.tdp2.tp0.weweather.persistance.Persistance;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity
{
    private Persistance persistance = new Persistance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AppModel model = AppModel.getInstance();
        persistance.loadInto(this, model);
        if( !AppModel.getInstance().hasCity() )
        {
            //TODO check name
            AppModel.getInstance().setCity("Buenos Aires");
        }
        //TODO search city
        //TODO remove
        onSearchResult(false,
                false,
                new ArrayList<DayTemperature>());
    }

    private void onSearchResult(
            boolean hasConnectivity,
            boolean isDay,
            List<DayTemperature> dayTemperatureList)
    {
        AppModel model = AppModel.getInstance();
        model.setHasConnectivity(hasConnectivity);
        if( hasConnectivity )
        {
            model.setCityData( dayTemperatureList, isDay);
        }
        persistance.saveFrom(this, model);
        Intent intent = new Intent(this, FiveDayTemperatureActivity.class);
        startActivity(intent);
        finish();
    }
}
