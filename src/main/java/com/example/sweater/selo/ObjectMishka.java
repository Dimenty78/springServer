package com.example.sweater.selo;

import java.util.Date;

public class ObjectMishka {

    private Date data;
    private String selo;

    public ObjectMishka(String selo, Date date) {
        this.selo = selo;
        this.data = date;
        ObjectMishka(selo,date);
    }

    public synchronized void ObjectMishka(String selo, Date date) {
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
