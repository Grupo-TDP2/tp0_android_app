package com.tdp2.tp0.weweather.tasks;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.tdp2.tp0.weweather.R;
import com.tdp2.tp0.weweather.model.DayTemperature;
import com.tdp2.tp0.weweather.model.Forecast;
import com.tdp2.tp0.weweather.model.responses.ServiceResponse;
import com.tdp2.tp0.weweather.services.WeatherService;

import java.util.List;

public class GetForecastTask extends AsyncTask<Integer, Void, ServiceResponse<Forecast>>
{
    public interface Listener
    {
        void onForecastGotten(boolean success, boolean isDay, List<DayTemperature> dayTemperatureList);
        View getView();
    }

    private Snackbar snackbar;
    private Listener listener;

    public GetForecastTask( Listener listener)
    {
        View view = listener.getView();
        if( view != null )
        {
            this.snackbar = Snackbar.make(view,
                    R.string.loading_temperatures, Snackbar.LENGTH_INDEFINITE);
        }

        this.listener = listener;
    }

    protected void onPreExecute() {
        if( snackbar != null ) this.snackbar.show();
    }

    protected ServiceResponse<Forecast> doInBackground(Integer... params)
    {
        return new WeatherService().getForecast(params[0]);
    }

    protected void onPostExecute(ServiceResponse<Forecast> response) {
        if( snackbar != null ) this.snackbar.dismiss();

        if (response.getStatusCode() == ServiceResponse.ServiceStatusCode.SUCCESS) {
            listener.onForecastGotten(true, response.getServiceResponse().isDayInCity(),
                    response.getServiceResponse().getDayTemperatureList());
        } else {
            listener.onForecastGotten(false, false, null);
        }
        listener = null;
    }
}
