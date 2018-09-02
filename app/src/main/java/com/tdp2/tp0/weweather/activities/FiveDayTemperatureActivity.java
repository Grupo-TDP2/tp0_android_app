package com.tdp2.tp0.weweather.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
        NO_CONNECTIVITY(R.style.AppTheme_NoConnectivity),
        NIGHT_MODE(R.style.AppTheme_NightMode),
        DAY_MODE(R.style.AppTheme_DayMode);
        int theme;
        DisplayState(int theme)
        {
            this.theme = theme;
        }
    }

    private FloatingActionButton actionButton;
    private DisplayState displayState = DisplayState.NO_CONNECTIVITY;
    private DayListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setTheme(displayState.theme);
        setContentView(R.layout.activity_pantalla_de_inicio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionButton= findViewById(R.id.fab);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO make actualization of temperatures
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ListView listView = findViewById(R.id.temperature_list_view);
        adapter = new DayListAdapter(this, R.layout.temperature_list_item, getDateList());
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pantalla_de_inicio, menu);
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
        displayRefresh(displayTemperatures);
        adapter.changeDisplayTemperature(displayTemperatures);
        recreate();
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
