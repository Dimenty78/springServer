package com.example.sweater.selo;

import java.util.Date;

public class ObjectFileDownloadNameTime {

    private Date data;
    private String selo;

    public ObjectFileDownloadNameTime(String selo, Date date) {
        this.selo = selo;
        this.data = date;
        ObjectFileDownloadNameTime(selo,date);
    }

    public synchronized void ObjectFileDownloadNameTime(String selo, Date date) {
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
