package com.tdp2.tp0.weweather.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.tdp2.tp0.weweather.R;

import java.util.ArrayList;
import java.util.List;

public class SearchCitiyActivity extends AppCompatActivity
{
    private SearchView searchView;
    private ArrayAdapter<String> cityAdapter;
    private List<String> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                if( query.length() >= 3 )
                {
                    //TODO make request for cities
                    return true;
                }
                Toast.makeText(SearchCitiyActivity.this,
                        R.string.not_enough_letters, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });

        cityList = new ArrayList<>();
        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 ,cityList);
        ListView listView = findViewById(R.id.cities_list);
        listView.setAdapter(cityAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if( position <= cityList.size() )
                {
                    String selected = cityList.get(position);
                    //TODO set city selected..
                }
            }
        });

        //onCitiesLoaded();
    }


    public void onCitiesLoaded(List<String> cities)
    {
        cityList.clear();
        cities.addAll(cities);
        cityAdapter.notifyDataSetChanged();
    }
}
