package com.example.sweater.orlanobject;

import java.util.Date;

public class ObjectVolt {

    private float volt;

    public ObjectVolt(float volt) {
        this.volt = volt;
        ObjectVolt(volt);
    }

    public synchronized void ObjectVolt(float volt) {
        this.volt = volt;
    }

    public float getVolt() {
        return volt;
    }
}
