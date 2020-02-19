package com.develogical;

import com.weather.Forecast;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CachedForecastTest {

    @Test
    public void getTimeStamp() {
        MyClock fakeDate = mock(MyClock.class);

        CachedForecast forecast = new CachedForecast(new Forecast("Sunny", 22), fakeDate);
        forecast.getTimeStamp();
        verify(fakeDate, times(1)).now();
    }

    @Test
    public void getForecast() {
        CachedForecast forecastAdapted = new CachedForecast(new Forecast("Sunny", 22), new RealClock());
        Forecast forecast = forecastAdapted.getForecast();

        assertThat(forecast.summary(), equalTo("Sunny"));    }
}