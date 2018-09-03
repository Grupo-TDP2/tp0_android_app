package com.tdp2.tp0.weweather.services;

import com.tdp2.tp0.weweather.model.City;
import com.tdp2.tp0.weweather.model.responses.ServiceResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class CitySearchService {

    private String API_URI = "https://fiuba-tdp2-tp0.herokuapp.com";

    public ServiceResponse<ArrayList<City>> getCities(String partialCityName) {
        HttpURLConnection client = null;

        try {
            URL url = new URL(API_URI + "/weather/cities?name=" + partialCityName);
            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("GET");

            client.connect();

            BufferedReader br;

            int statusCode = client.getResponseCode();

            if (400 <= statusCode && statusCode <= 500){
                return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.ERROR);
            }

            if (200 <= statusCode && statusCode <= 299) {
                br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(client.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            String result = sb.toString();

            JSONArray jsonArray = new JSONArray(result);

            ArrayList<City> cities = new ArrayList<City>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject itemObject = jsonArray.getJSONObject(i);
                String newCityID = itemObject.getString("owm_id");
                String newCityName = itemObject.getString("name");
                City newCity = new City(Integer.parseInt(newCityID), newCityName, "AR");
                // Pulling items from the array
                cities.add(newCity);
            }

            return new ServiceResponse<ArrayList<City>>(ServiceResponse.ServiceStatusCode.SUCCESS, cities);

        } catch(Exception error) {
            //Handles an incorrectly entered URL
            return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.ERROR);
        }
        finally {
            if(client != null) {
                client.disconnect();
            }
        }
    }

}

