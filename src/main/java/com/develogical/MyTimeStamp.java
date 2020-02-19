package com.develogical;

public class MyTimeStamp implements MyClock {
    public long timeStamp = System.currentTimeMillis();

    @Override
    public long now() {
        return timeStamp;
    }
}
