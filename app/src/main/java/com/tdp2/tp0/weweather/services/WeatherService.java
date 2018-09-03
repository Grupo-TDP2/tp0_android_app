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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class WeatherService {

    private String API_URI = "https://fiuba-tdp2-tp0.herokuapp.com";
    private Integer utcTimeZoneDifference = -3;

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

            if (cityID != -1 && cityID != -2) {
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Argentina/Buenos_Aires"));
                Integer hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                isDayInCity = (hourOfDay >= 6 && hourOfDay <= 20);
            }

            ArrayList<DayTemperature> dayTemperatures = new ArrayList<DayTemperature>();

            for (int i = 1; i <= 5; i++) {
                JSONObject itemObject = jsonObject.getJSONObject("day_" + i);

                JSONObject midday = itemObject.getJSONObject("midday");
                JSONObject midnight = itemObject.getJSONObject("midnight");

                String middayTemp, middayWeather, midnightTemp, midnightWeather;

                if (midday.getString("temperature") == "null") {
                    middayTemp = "-";
                    middayWeather = "";
                } else {
                    middayTemp = midday.getString("temperature");
                    middayWeather = midday.getString("weather");
                }

                if (midnight.getString("temperature") == "null") {
                    midnightTemp = "-";
                    midnightWeather = "";
                } else {
                    midnightTemp = midnight.getString("temperature");
                    midnightWeather = midnight.getString("weather");
                }

                DayTemperature dayTemperature = new DayTemperature(DateTransform.transfrom(i),
                        middayTemp,
                        midnightTemp,
                        WeatherTransfrom.transform(middayWeather),
                        WeatherTransfrom.transform(midnightWeather));

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
