package com.tdp2.tp0.weweather.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdp2.tp0.weweather.ClimateState;
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

    public DayTemperatureViewHolder(View view)
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
        setDrawable(context, state, dayIcon);
    }

    public void setNightTemperature(int temperatue)
    {
        setTemperature(temperatue, nightTemperature);
    }

    public void setNightIcon(Context context, ClimateState state)
    {
       setDrawable(context, state, nightIcon);
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
            DateFormat df = new DateFormat();
            String formated = df.format("EEEE, dd/MM",day).toString();
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

    private void setDrawable(Context context, ClimateState state, ImageView view)
    {
        Drawable drawable = VectorDrawableCompat.create(context.getResources(), getIcon(state), null );
        view.setImageDrawable(drawable);
    }

    private int getIcon(ClimateState state)
    {
        switch (state)
        {
            case MIST:
                return R.drawable.ic_mist;
            case SNOW:
                return R.drawable.ic_snow;
            case STORM:
                return R.drawable.ic_storm;
            case CLEAR_DAY:
                return R.drawable.ic_clear_day;
            case RAINY_DAY:
                return R.drawable.ic_rainy_day;
            case CLOUDY_DAY:
                return R.drawable.ic_cloudy_day;
            case CLOUDY_LOW:
                return R.drawable.ic_cloudy_low;
            case HEAVY_RAIN:
                return R.drawable.ic_heavy_rain;
            case CLOUDY_HIGH:
                return R.drawable.ic_cloudy_high;
            case RAINY_NIGHT:
                return R.drawable.ic_rainy_night;
            case CLOUDY_NIGHT:
                return R.drawable.ic_cloudy_night;
            case CLEAR_NIGHT:
                return R.drawable.ic_clear_night;
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
