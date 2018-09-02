package com.tdp2.tp0.weweather.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

public class FiveDayTemperatureActivity extends AppCompatActivity {

    private DayListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_de_inicio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        return super.onOptionsItemSelected(item);
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
