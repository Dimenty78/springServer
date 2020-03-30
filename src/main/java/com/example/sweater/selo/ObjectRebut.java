package com.example.sweater.selo;

import java.util.Date;

public class ObjectRebut {

    private Date data;
    private String selo;

    public ObjectRebut(String selo, Date date) {
        this.selo = selo;
        this.data = date;
        ObjectRebut(selo,date);
    }

    public synchronized void ObjectRebut(String selo, Date date) {
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
