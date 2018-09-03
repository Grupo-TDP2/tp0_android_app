package com.tdp2.tp0.weweather.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdp2.tp0.weweather.model.AppModel;
import com.tdp2.tp0.weweather.model.ClimateState;
import com.tdp2.tp0.weweather.R;
import com.tdp2.tp0.weweather.utils.StringUtils;

import java.util.Date;

class DayTemperatureViewHolder
{
    private TextView dayName;
    private TextView dayTemperatue;
    private TextView nightTemperature;
    private ImageView dayIcon;
    private ImageView nightIcon;
    private View temperatureBlock;

    DayTemperatureViewHolder(View view)
    {
        dayName = view.findViewById(R.id.day_name);
        temperatureBlock = view.findViewById(R.id.temperature_container);
        dayTemperatue = view.findViewById(R.id.day_temperature);
        dayIcon = view.findViewById(R.id.day_icon);
        nightIcon = view.findViewById(R.id.night_icon);
        nightTemperature = view.findViewById(R.id.night_temperature);
    }

    public void setDayTemperatue(int temperatue)
    {
        setTemperature(temperatue, dayTemperatue);
    }

    public void setDayIcon(Context context, ClimateState state)
    {
        setDrawable(context, state, dayIcon, true);
    }

    public void setNightTemperature(int temperatue)
    {
        setTemperature(temperatue, nightTemperature);
    }

    public void setNightIcon(Context context, ClimateState state)
    {
       setDrawable(context, state, nightIcon, false);
    }

    public void setDayName(int position, Date day)
    {
        if( position == 0 )
        {
            dayName.setText(R.string.today);
        } else if( position == 1 )
        {
            dayName.setText(R.string.tomorrow);
        } else
        {
            String formated = DateFormat.format("EEEE, dd/MM",day).toString();
            dayName.setText(StringUtils.asUpperCaseFirstChar(formated));
        }
    }

    public void displayTemperatureBlock(boolean display)
    {
        boolean isVisible = temperatureBlock.getVisibility() == View.VISIBLE;
        if( isVisible && !display )
        {
            temperatureBlock.setVisibility(View.GONE);
        } else if( !isVisible && display )
        {
            temperatureBlock.setVisibility(View.VISIBLE);
        }
    }

    private void setDrawable(Context context, ClimateState state, ImageView view, boolean isDay)
    {
        Drawable drawable = VectorDrawableCompat.create(context.getResources(), getIcon(state, isDay), null );
        view.setImageDrawable(drawable);
    }

    private int getIcon(ClimateState state, boolean isDay)
    {
        boolean dayMode = AppModel.getInstance().isDayInCity();
        switch (state)
        {
            case MIST:
                if( AppModel.getInstance().isDayInCity() )
                {
                    return R.drawable.ic_mist;
                } else
                {
                    return R.drawable.ic_mist_white;
                }
            case SNOW:
                if( dayMode )
                {
                    return R.drawable.ic_snow_day_mode;
                }
                return R.drawable.ic_snow;
            case STORM:
                return R.drawable.ic_storm;
            case CLEAR:
                if( isDay )
                {
                    return R.drawable.ic_clear_day;
                }
                return R.drawable.ic_clear_night;
            case RAINY:
                if( isDay )
                {
                    if( dayMode )
                    {
                        return R.drawable.ic_rainy_day_day_mode;
                    }
                    return R.drawable.ic_rainy_day;
                }
                if( dayMode )
                {
                    return R.drawable.ic_rainy_night_day_mode;
                }
                return R.drawable.ic_rainy_night;
            case CLOUDY:
                if( isDay )
                {
                    return R.drawable.ic_cloudy_day;
                }
                return R.drawable.ic_cloudy_night;
            case CLOUDY_LOW:
                return R.drawable.ic_cloudy_low;
            case HEAVY_RAIN:
                if( dayMode )
                {
                    return R.drawable.ic_heavy_rain_day_mode;
                }
                return R.drawable.ic_heavy_rain;
            case CLOUDY_HIGH:
                return R.drawable.ic_cloudy_high;
            case CLOUDY_MEDIUM:
                return R.drawable.ic_cloudy_medium;
        }
        return 0;
    }

    private void setTemperature(int value, TextView view)
    {
        view.setText(String.valueOf(value));
    }
}
