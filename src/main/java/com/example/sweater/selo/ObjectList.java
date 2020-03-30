package com.example.sweater.selo;

import java.util.Date;

public class ObjectList {

    private Date data;
    private String selo;
    private Long timeSec;

    public ObjectList(String selo, Date date, Long timeSec) {
        this.selo = selo;
        this.data = date;
        this.timeSec = timeSec;
        ObjectList(selo,date,timeSec);
    }

    public synchronized void ObjectList(String selo, Date date, Long timeSec) {
        this.selo = selo;
        this.data = date;
        this.timeSec = timeSec;
    }

    public Date getData() {
        return data;
    }

    public String getSelo() {
        return selo;
    }

    public Long getTimeSec() {
        return timeSec;
    }
}
