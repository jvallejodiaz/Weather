package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;

import java.util.HashMap;

public class WeatherCache {
    public Forecaster service;
    private HashMap<Region,HashMap<Day, Forecast>> forecastCache = new HashMap<>();
    private int limit;
    private MyClock clock;


    public WeatherCache(Forecaster service, int i, MyClock clock){
        this.service = service;
        this.limit = i;
        this.clock = clock;

    }
    public Forecast getWeather(Region region, Day day) {
        Forecast forecast = getForecastFromCache(region, day);

        if (forecast != null) return forecast;

        forecast = this.service.forecastFor(region, day);

        updateCache(region, day, forecast);

        return forecast;

    }

    private Forecast getForecastFromCache(Region region, Day day) {
        Forecast forecast = null;
        if(whatever-time-the-thing-was-cached + 1< clock.now()) {
            if (forecastCache.containsKey(region)) {
                HashMap<Day, Forecast> dailyForecast = forecastCache.get(region);
                if (dailyForecast.containsKey(day)) {
                    return dailyForecast.get(day);

                }
            }
        }
        return null;
    }

    private void updateCache(Region region, Day day, Forecast forecast) {
        if(forecastCache.size() == limit){
            forecastCache.clear();
        }
        forecastCache.put(region, new HashMap<Day, Forecast>());
        forecastCache.get(region).put(day, forecast);
    }
}
