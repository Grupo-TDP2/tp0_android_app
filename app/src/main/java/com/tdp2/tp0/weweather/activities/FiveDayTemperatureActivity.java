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
import com.tdp2.tp0.weweather.model.DayTemperature;

import java.util.List;

public class FiveDayTemperatureActivity extends AppCompatActivity
{
    enum DisplayState
    {
        NO_CONNECTIVITY(R.style.AppTheme_NoActionBar_NoConnectivity),
        NIGHT_MODE(R.style.AppTheme_NoActionBar_NightMode),
        DAY_MODE(R.style.AppTheme_NoActionBar_DayMode);
        int theme;
        DisplayState(int theme)
        {
            this.theme = theme;
        }
    }

    private static DisplayState displayState = DisplayState.NO_CONNECTIVITY;
    private static boolean temperatureVisible = false;
    private DayListAdapter adapter;
    private List<DayTemperature> temperatures;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        determineStartingTheme();
        setTheme(displayState.theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_de_inicio);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(AppModel.getInstance().getCitySelected());

        FloatingActionButton actionButton = findViewById(R.id.fab);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO make actualization of temperatures
                onRefreshedDateList(false, false,
                        AppModel.getInstance().getTemperatures());
            }
        });

        ListView listView = findViewById(R.id.temperature_list_view);
        adapter = new DayListAdapter(this, R.layout.temperature_list_item, getDateList());
        adapter.changeDisplayTemperature(temperatureVisible);
        listView.setAdapter(adapter);

        if( displayState == DisplayState.NO_CONNECTIVITY )
        {
            Toast.makeText(this, R.string.no_connectivity_refresh, Toast.LENGTH_SHORT).show();
        }
    }

    private void determineStartingTheme()
    {
        AppModel model = AppModel.getInstance();
        if( !model.hasConnectivity() )
        {
            setNoConnectivityMode();
        } else if( model.isDayInCity() )
        {
            setDayMode();
        } else
        {
            setNightMode();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pantalla_de_inicio, menu);
        if (!temperatureVisible)
        {
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setVisible(false);
        }
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

    public void setNightMode()
    {
        changeDisplayMode(DisplayState.NIGHT_MODE, true);
    }

    public void setDayMode()
    {
        changeDisplayMode(DisplayState.DAY_MODE, true);
    }

    public void setNoConnectivityMode()
    {
       changeDisplayMode(DisplayState.NO_CONNECTIVITY, false);
    }

    private void changeDisplayMode(DisplayState state, boolean displayTemperatures)
    {
        displayState = state;
        temperatureVisible = displayTemperatures;
    }

    private List<DayTemperature> getDateList()
    {
        temperatures = AppModel.getInstance().getTemperatures();
        return temperatures;
    }

    /** To be called after actualize is done */
    private void onRefreshedDateList(boolean hasConnectivity,
                                     boolean isDay,
                                     List<DayTemperature> dateList)
    {
        if( hasConnectivity )
        {
            if( isDay && displayState != DisplayState.DAY_MODE ||
                !isDay && displayState != DisplayState.NIGHT_MODE )
            {
                restartActivity();
            } else
            {
                temperatures.clear();
                temperatures.addAll(dateList);
                adapter.notifyDataSetChanged();
            }
        } else
        {
            Toast.makeText(this, R.string.no_connectivity_refresh, Toast.LENGTH_SHORT).show();
        }
    }

    private void restartActivity()
    {
        Intent intent = new Intent(this, FiveDayTemperatureActivity.class);
        startActivity(intent);
        finish();
    }
}
