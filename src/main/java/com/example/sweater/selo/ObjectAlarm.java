package com.example.sweater.selo;

import java.util.Date;

public class ObjectAlarm {

    private Date data;
    private String selo;

    public ObjectAlarm(String selo, Date date) {
        this.selo = selo;
        this.data = date;
        ObjectAlarm(selo,date);
    }

    public synchronized void ObjectAlarm(String selo, Date date) {
        this.selo = selo;
        this.data = date;
    }

    public Date getData() {
        return data;
    }

    public String getSelo() {
        return selo;
    }

}
