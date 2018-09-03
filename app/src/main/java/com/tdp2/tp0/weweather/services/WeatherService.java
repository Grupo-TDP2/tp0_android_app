package com.tdp2.tp0.weweather.services;

import com.tdp2.tp0.weweather.model.DayTemperature;
import com.tdp2.tp0.weweather.model.Forecast;
import com.tdp2.tp0.weweather.model.responses.ServiceResponse;
import com.tdp2.tp0.weweather.utils.WeatherTransfrom;
import com.tdp2.tp0.weweather.utils.DateTransform;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WeatherService {

    private String API_URI = "https://fiuba-tdp2-tp0.herokuapp.com";

    public ServiceResponse<Forecast> getForecast(Integer cityID) {
        HttpURLConnection client = null;

        try {
            URL url = new URL(API_URI + "/weather?city_id=" + cityID);
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

            JSONObject jsonObject = new JSONObject(result);

            Boolean isDayInCity = jsonObject.getBoolean("isDayInCity");
            ArrayList<DayTemperature> dayTemperatures = new ArrayList<DayTemperature>();

            for (int i = 1; i <= 5; i++) {
                JSONObject itemObject = jsonObject.getJSONObject("day_" + i);

                JSONObject midday = itemObject.getJSONObject("midday");
                JSONObject midnight = itemObject.getJSONObject("midnight");

                DayTemperature dayTemperature = new DayTemperature(DateTransform.transfrom(i),
                        midday.getInt("temperature"),
                        midnight.getInt("temperature"),
                        WeatherTransfrom.transform(midday.getInt("weather")),
                        WeatherTransfrom.transform(midnight.getInt("weather")));

                dayTemperatures.add(dayTemperature);
            }

            Forecast forecast = new Forecast(isDayInCity, dayTemperatures);

            return new ServiceResponse<Forecast>(ServiceResponse.ServiceStatusCode.SUCCESS, forecast);

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
