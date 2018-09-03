package com.tdp2.tp0.weweather.model.responses;

import com.tdp2.tp0.weweather.model.ClimateState;

public class WeatherTransfrom
{
    public static ClimateState transform(int id)
    {
        String value = String.valueOf(id);
        if( value.startsWith("2") )
        {
            return ClimateState.STORM;
        } else if( value.startsWith("3")
                || (value.startsWith("5") && id <= 501))
        {
            return ClimateState.RAINY;
        } else if( value.startsWith("5") )
        {
            return ClimateState.HEAVY_RAIN;
        } else if( value.startsWith("6") )
        {
            return ClimateState.SNOW;
        } else if( value.startsWith("7") )
        {
            return ClimateState.MIST;
        } else if( value.startsWith("8") )
        {
            switch (id)
            {
                case 801:
                    return ClimateState.CLOUDY;
                case 802:
                    return ClimateState.CLOUDY_LOW;
                case 803:
                    return ClimateState.CLOUDY_MEDIUM;
                case 804:
                    return ClimateState.CLOUDY_HIGH;
            }
        }
        return ClimateState.CLEAR;
    }
}
