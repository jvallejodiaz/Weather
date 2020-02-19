package com.develogical;

public class RealClock implements MyClock {
    @Override
    public long now() {
        return System.currentTimeMillis();
    }
}
