package com.tdp2.tp0.weweather.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.tdp2.tp0.weweather.ClimateState;
import com.tdp2.tp0.weweather.R;
import com.tdp2.tp0.weweather.adapters.DayListAdapter;
import com.tdp2.tp0.weweather.viewModel.DayTemperatureViewModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    private FloatingActionButton actionButton;
    private static DisplayState displayState = DisplayState.NO_CONNECTIVITY;
    private static boolean temperatureVisible = false;
    private DayListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(displayState.theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_de_inicio);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionButton= findViewById(R.id.fab);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO make actualization of temperatures
                if(displayState == DisplayState.NO_CONNECTIVITY)
                {
                    setNightMode();
                } else if( displayState == DisplayState.NIGHT_MODE )
                {
                    setDayMode();
                } else {
                    setNoConnectivityMode();
                }
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
            }
        });

        //displayRefresh(temperatureVisible);

        ListView listView = findViewById(R.id.temperature_list_view);
        adapter = new DayListAdapter(this, R.layout.temperature_list_item, getDateList());
        adapter.changeDisplayTemperature(temperatureVisible);
        listView.setAdapter(adapter);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
        Intent intent = new Intent(this, FiveDayTemperatureActivity.class);
        startActivity(intent);
        finish();
    }

    private void displayRefresh(boolean display)
    {
        int visibility = display ? View.VISIBLE : View.GONE;
        actionButton.setVisibility(visibility);
    }

    private List<DayTemperatureViewModel> getDateList()
    {
        List<DayTemperatureViewModel> list = new ArrayList<>();
        Date date = new Date();
        //TODO get from model with country selected!!!
        for( int i = 0; i < 5; i++)
        {
            list.add(new DayTemperatureViewModel(date, 25,25, ClimateState.CLEAR_DAY, ClimateState.CLEAR_NIGHT ));
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, 1);
            date = c.getTime();
        }
        return list;
    }
}
