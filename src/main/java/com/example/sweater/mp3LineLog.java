package com.example.sweater;

public class mp3LineLog {

    private String name;
    private String patchMp3;
    private String startTime;


    public mp3LineLog(){
    }

    public mp3LineLog(String textLog){

            patchMp3 = textLog.split(".mp3")[0];
            String kosieStrokiInt [] = patchMp3.split("\\\\");
            int a = kosieStrokiInt.length - 1;
            name = patchMp3.split("\\\\")[a];
            patchMp3 = patchMp3 + ".mp3";
            startTime = textLog.split("StartTime=")[1].split("\r")[0];
        }


    public String getName() {
        return name;
    }

    public String getPatchMp3() {
        return patchMp3;
    }

    public String getStartTime() {
        return startTime;
    }
}
