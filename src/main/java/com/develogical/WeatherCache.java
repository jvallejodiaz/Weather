package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;

import java.util.HashMap;

public class WeatherCache implements WeatherService {
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
        CachedForecast result = getForecastFromCache(region, day);
        if (result == null) {
            result = new CachedForecast(this.service.forecastFor(region, day), new MyTimeStamp());
            updateCache(region, day, result);
        }
        return result.getForecast();

    }

    private CachedForecast getForecastFromCache(Region region, Day day) {
            if (forecastCache.containsKey(region)) {
                HashMap<Day, CachedForecast> regionMap = forecastCache.get(region);
                if (regionMap.containsKey(day)) {
                    CachedForecast forecastDay = regionMap.get(day);
                    if(forecastDay.getTimeStamp() > clock.now()){
                        regionMap.remove(day);
                    }else{
                        return forecastDay;
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
