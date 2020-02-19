package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;

import java.util.HashMap;

public class WeatherCache {
    public Forecaster service;
    private HashMap<Region,HashMap<Day, CachedForecast>> forecastCache = new HashMap<>();
    private int limit;
    private MyClock clock;


    public WeatherCache(Forecaster service, int i, MyClock clock){
        this.service = service;
        this.limit = i;
        this.clock = clock;

    }
    public Forecast getWeather(Region region, Day day) {
        CachedForecast cachedForecast = getForecastFromCache(region, day);

        if (cachedForecast != null) return cachedForecast.forecast;

        Forecast forecast = this.service.forecastFor(region, day);

        CachedForecast myForecast = new CachedForecast(forecast, clock);

        updateCache(region, day, myForecast);

        return myForecast.forecast;

    }

    private CachedForecast getForecastFromCache(Region region, Day day) {
        CachedForecast forecast = null;
        if(2L + 1< clock.now()) {
            if (forecastCache.containsKey(region)) {
                HashMap<Day, CachedForecast> dailyForecast = forecastCache.get(region);
                if (dailyForecast.containsKey(day)) {
                    return dailyForecast.get(day);

                }
            }
        }
        return null;
    }

    private void updateCache(Region region, Day day, CachedForecast forecast) {
        if(forecastCache.size() == limit){
            forecastCache.clear();
        }
        forecastCache.put(region, new HashMap<Day, CachedForecast>());
        forecastCache.get(region).put(day, forecast);
    }
}
