package com.tdp2.tp0.weweather.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.tdp2.tp0.weweather.R;
import com.tdp2.tp0.weweather.model.AppModel;
import com.tdp2.tp0.weweather.model.City;

import com.tdp2.tp0.weweather.model.DayTemperature;
import com.tdp2.tp0.weweather.persistance.Persistance;

import com.tdp2.tp0.weweather.model.responses.ServiceResponse;
import com.tdp2.tp0.weweather.services.CitySearchService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchCitiyActivity extends AppCompatActivity
{
    private SearchView searchView;
    private ArrayAdapter<String> cityAdapter;
    private List<City> cityList;
    private List<String> viewList;
    private Persistance persistance = new Persistance();

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
                    new GetCitiesTask().execute(query);
                    searchView.clearFocus();
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
        viewList = new ArrayList<>();
        cityAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1
                , viewList);
        ListView listView = findViewById(R.id.cities_list);
        listView.setAdapter(cityAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if( position <= cityList.size() )
                {
                    onCitySelected(cityList.get(position));
                }
            }
        });
    }

    public void onCitySelected(City city)
    {
        AppModel.getInstance().setCity(city);
        persistance.saveFrom(this, AppModel.getInstance());
        //TODO DO CORRECT CALL
    }

    public void onCitiesLoaded(boolean success, List<City> cities)
    {
        AppModel.getInstance().setHasConnectivity(success);
        if( success )
        {
            loadModelCities(cities);
            loadViewCities();
        } else
        {
            Toast.makeText(this, R.string.no_connectivity_refresh, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadViewCities()
    {
        viewList.clear();
        for( City city : cityList )
        {
            viewList.add(city.getName());
        }
        cityAdapter.notifyDataSetChanged();
    }

    private void loadModelCities(List<City> cities)
    {
        cityList.clear();
        cityList.addAll(cities);
        Collections.sort(cityList, new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                return o1.getCountry().compareTo(o2.getCountry());
            }
        });
    }

    private void onRefreshedDateList(boolean hasConnectivity,
                                     boolean isDay,
                                     List<DayTemperature> dateList)
    {
        AppModel.getInstance().setHasConnectivity(hasConnectivity);
        if( hasConnectivity )
        {
            AppModel.getInstance().setCityData(dateList, isDay);
        } else
        {
            Toast.makeText(this, R.string.no_connectivity_refresh, Toast.LENGTH_LONG).show();
        }
    }

    protected class GetCitiesTask extends AsyncTask<String, Void, ServiceResponse<ArrayList<City>>> {

        protected void onPreExecute() {

        }

        protected ServiceResponse<ArrayList<City>> doInBackground(String... params) {
            return new CitySearchService().getCities(params[0]);
        }

        protected void onPostExecute(ServiceResponse<ArrayList<City>> response) {
            if (response.getStatusCode() == ServiceResponse.ServiceStatusCode.SUCCESS) {
                onCitiesLoaded(true, response.getServiceResponse());
            } else {
                onCitiesLoaded(false, response.getServiceResponse());
            }
        }
    }
}
