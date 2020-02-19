package com.develogical;
import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;
import org.junit.Test;

import java.util.Date;

import static org.mockito.Mockito.*;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class WeatherCachingTests {

    @Test
    public void checkRequestsDataFromWeathersystem() throws Exception {
        Forecaster delegate = mock(Forecaster.class);
        when(delegate.forecastFor(Region.BIRMINGHAM, Day.FRIDAY))
                .thenReturn(new Forecast("Sunny", 22));
        WeatherCache cache = new WeatherCache(delegate, 3, new Date(System.currentTimeMillis()));

        Forecast data = cache.getWeather(Region.BIRMINGHAM, Day.FRIDAY);
        assertThat(data.summary(), equalTo("Sunny"));
        assertThat(data.temperature(), equalTo(22));
    }

    @Test
    public void checkRequestsDataFromWeatherSystemIsCalledOnce() throws Exception {
        Forecaster delegate = mock(Forecaster.class);
        when(delegate.forecastFor(Region.BIRMINGHAM, Day.FRIDAY))
                .thenReturn(new Forecast("Sunny", 22));
        WeatherCache cache = new WeatherCache(delegate, 3, new Date(System.currentTimeMillis()));

        cache.getWeather(Region.BIRMINGHAM, Day.FRIDAY);
        Forecast data = cache.getWeather(Region.BIRMINGHAM, Day.FRIDAY);
        assertThat(data.summary(), equalTo("Sunny"));
        assertThat(data.temperature(), equalTo(22));
        verify(delegate, times(1)).forecastFor(Region.BIRMINGHAM, Day.FRIDAY);

    }

    @Test
    public void checkRequestsDataFromWeatherSystemIsCalledOnceLondon() throws Exception {
        Forecaster delegate = mock(Forecaster.class);
        when(delegate.forecastFor(Region.BIRMINGHAM, Day.FRIDAY))
                .thenReturn(new Forecast("Sunny", 22));
        WeatherCache cache = new WeatherCache(delegate, 3, new Date(System.currentTimeMillis()));

        cache.getWeather(Region.LONDON, Day.FRIDAY);
        Forecast data = cache.getWeather(Region.BIRMINGHAM, Day.FRIDAY);
        assertThat(data.summary(), equalTo("Sunny"));
        assertThat(data.temperature(), equalTo(22));
        verify(delegate, times(1)).forecastFor(Region.BIRMINGHAM, Day.FRIDAY);

    }

    @Test
    public void checkRequestsDataFromWeatherSystemIsCalledOnceDifferentDays() throws Exception {
        Forecaster delegate = mock(Forecaster.class);
        when(delegate.forecastFor(Region.BIRMINGHAM, Day.FRIDAY))
                .thenReturn(new Forecast("Sunny", 22));

        WeatherCache cache = new WeatherCache(delegate, 3, new Date(System.currentTimeMillis()));

        cache.getWeather(Region.BIRMINGHAM, Day.SATURDAY);
        Forecast data = cache.getWeather(Region.BIRMINGHAM, Day.FRIDAY);
        assertThat(data.summary(), equalTo("Sunny"));
        assertThat(data.temperature(), equalTo(22));
        verify(delegate, times(1)).forecastFor(Region.BIRMINGHAM, Day.FRIDAY);

    }

    @Test
    public void checkThatTheCacheHasASizeLimit() throws Exception {
        Forecaster delegate = mock(Forecaster.class);
        when(delegate.forecastFor(any(Region.class), any(Day.class)))
                .thenReturn(new Forecast("Sunny", 22));
        WeatherCache cache = new WeatherCache(delegate, 2, new Date(System.currentTimeMillis()));

        cache.getWeather(Region.EDINBURGH, Day.FRIDAY);
        cache.getWeather(Region.LONDON, Day.SATURDAY);
        cache.getWeather(Region.MANCHESTER, Day.SATURDAY);
        Forecast data = cache.getWeather(Region.EDINBURGH, Day.FRIDAY);

        assertThat(data.summary(), equalTo("Sunny"));
        assertThat(data.temperature(), equalTo(22));
        verify(delegate, times(2)).forecastFor(Region.EDINBURGH, Day.FRIDAY);

    }
    @Test
    public void checkThatCacheClearsAfterAHour(){
        Forecaster delegate = mock(Forecaster.class);
        when(delegate.forecastFor(any(Region.class), any(Day.class)))
                .thenReturn(new Forecast("Sunny", 22));

        Date date1 = new Date(System.currentTimeMillis());

        MyClock fakeDate = mock(MyClock.class);
        when(fakeDate.now()).thenReturn(1L);


        WeatherCache cache = new WeatherCache(delegate, 2, fakeDate);

        cache.getWeather(Region.EDINBURGH, Day.FRIDAY);
        Forecast data = cache.getWeather(Region.EDINBURGH, Day.FRIDAY);
        assertThat(data.summary(), equalTo("Sunny"));
        assertThat(data.temperature(), equalTo(22));
        verify(delegate, times(2)).forecastFor(Region.EDINBURGH, Day.FRIDAY);



    }


}
