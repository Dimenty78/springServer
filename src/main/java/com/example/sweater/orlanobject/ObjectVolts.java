package com.example.sweater.orlanobject;

import java.util.Date;

public class ObjectVolts {

    private Date data;
    private String volt;

    public ObjectVolts(String volt, Date date) {
        this.volt = volt;
        this.data = date;
        ObjectVolts(volt,date);
    }

    public synchronized void ObjectVolts(String volt, Date date) {
        this.volt = volt;
        this.data = date;
    }

    public Date getData() {
        return data;
    }

    public String getVolt() {
        return volt;
    }

}
