package com.develogical;

import com.weather.Forecast;

public class CachedForecast {

    public   Forecast forecast;
    private  MyClock clock;

    public CachedForecast(Forecast forecast, MyClock clock) {
        this.forecast = forecast;
        this.clock = clock;
    }
}
