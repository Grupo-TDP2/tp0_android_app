package com.tdp2.tp0.weweather.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.tdp2.tp0.weweather.R;
import com.tdp2.tp0.weweather.adapters.DayListAdapter;
import com.tdp2.tp0.weweather.model.AppModel;
import com.tdp2.tp0.weweather.model.City;
import com.tdp2.tp0.weweather.model.DayTemperature;
import com.tdp2.tp0.weweather.tasks.GetForecastTask;

import java.util.List;

public class FiveDayTemperatureActivity extends AppCompatActivity
    implements GetForecastTask.Listener
{
    enum DisplayState
    {
        NO_CONNECTIVITY(R.style.AppTheme_NoActionBar_NoConnectivity, false),
        NIGHT_MODE(R.style.AppTheme_NoActionBar_NightMode, true),
        DAY_MODE(R.style.AppTheme_NoActionBar_DayMode, true);
        int theme;
        boolean displayTemperature;
        DisplayState(int theme, boolean displayTemperature)
        {
            this.theme = theme;
            this.displayTemperature = displayTemperature;
        }
    }

    private static DisplayState displayState = DisplayState.NO_CONNECTIVITY;
    private DayListAdapter adapter;
    private List<DayTemperature> temperatures;
    private City lastCity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        displayState = determineActualStartingTheme();
        setTheme(displayState.theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_de_inicio);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setCityName();

        FloatingActionButton actionButton = findViewById(R.id.fab);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetForecastTask(FiveDayTemperatureActivity.this)
                        .execute(AppModel.getInstance().getCitySelected().getId());
            }
        });

        ListView listView = findViewById(R.id.temperature_list_view);
        adapter = new DayListAdapter(this, R.layout.temperature_list_item, getDateList());
        adapter.changeDisplayTemperature(displayState.displayTemperature);
        listView.setAdapter(adapter);

        lastCity = AppModel.getInstance().getCitySelected();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        DisplayState newState = determineActualStartingTheme();
        if( newState != displayState )
        {
            displayState = newState;
            restartActivity();
            return;
        } else if( lastCity.getId() != AppModel.getInstance().getCitySelected().getId() )
        {
            setCityName();
            setTemperature(AppModel.getInstance().getTemperatures());
        }

        if( displayState == DisplayState.NO_CONNECTIVITY )
        {
            Toast.makeText(this, R.string.no_connectivity_refresh, Toast.LENGTH_SHORT).show();
        }
    }

    private void setCityName()
    {
        City city = AppModel.getInstance().getCitySelected();
        getSupportActionBar().setTitle(getString(R.string.city_name, city.getName(), city.getCountry()));
    }

    private DisplayState determineActualStartingTheme()
    {
        AppModel model = AppModel.getInstance();
        if( !model.hasConnectivity() )
        {
            return DisplayState.NO_CONNECTIVITY;
        } else if( model.isDayInCity() )
        {
            return DisplayState.DAY_MODE;
        } else
        {
            return DisplayState.NIGHT_MODE;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pantalla_de_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id)
        {
            case R.id.action_search:
                Intent intent = new Intent(this, SearchCitiyActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<DayTemperature> getDateList()
    {
        temperatures = AppModel.getInstance().getTemperatures();
        return temperatures;
    }

    /** To be called after actualize is done */
    public void onForecastGotten(boolean hasConnectivity,
                                     boolean isDay,
                                     List<DayTemperature> dateList)
    {
        AppModel.getInstance().setHasConnectivity(hasConnectivity);
        if( hasConnectivity )
        {
            AppModel.getInstance().setCityData(dateList, isDay);
            if( isDay && displayState != DisplayState.DAY_MODE ||
                !isDay && displayState != DisplayState.NIGHT_MODE )
            {
                restartActivity();
            } else
            {
                setTemperature(dateList);
            }
        } else
        {
            Toast.makeText(this, R.string.no_connectivity_refresh, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View getView()
    {
        return findViewById(R.id.activity_temperature_days);
    }

    private void setTemperature(List<DayTemperature> temperature)
    {
        temperatures.clear();
        temperatures.addAll(temperature);
        adapter.notifyDataSetChanged();
    }

    private void restartActivity()
    {
        Intent intent = new Intent(this, FiveDayTemperatureActivity.class);
        startActivity(intent);
        finish();
    }
}
