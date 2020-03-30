package com.example.sweater.selo;

import java.util.Date;

public class ObjectFileDownloadAlarmTime {

    private Date data;
    private String selo;
    private Long second;

    public ObjectFileDownloadAlarmTime(String selo, Date date, Long second) {
        this.selo = selo;
        this.second = second;
        this.data = date;
        ObjectFileDownloadAlarmTime(selo, date, second);
    }

    public synchronized void ObjectFileDownloadAlarmTime(String selo, Date date, Long second) {
        this.selo = selo;
        this.data = date;
        this.second = second;
    }

    public Date getData() {
        return data;
    }
    public String getSelo() {
        return selo;
    }
    public Long getSecond() {
        return second;
    }

}
