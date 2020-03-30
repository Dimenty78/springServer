package com.example.sweater.selo;

import java.util.Date;

public class ObjectTeamwiewer {

    private Date data;
    private String selo;

    public ObjectTeamwiewer(String selo, Date date) {
        this.selo = selo;
        this.data = date;
        ObjectTeamwiewer(selo,date);
    }

    public synchronized void ObjectTeamwiewer(String selo, Date date) {
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
