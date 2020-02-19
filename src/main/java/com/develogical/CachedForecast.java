package com.develogical;

import com.weather.Forecast;

class CachedForecast implements ForecastAddapter {

    private Forecast forecast;
    private  MyClock clock;

    public CachedForecast(Forecast forecast, MyClock clock) {
        this.forecast = forecast;
        this.clock = clock;
    }
    public long getTimeStamp(){
        return clock.now();
    }

    @Override
    public Forecast getForecast() {
        return forecast;
    }
}
