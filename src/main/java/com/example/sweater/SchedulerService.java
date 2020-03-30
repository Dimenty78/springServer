package com.example.sweater;

import com.example.sweater.controllers.FileDownloadController;
import com.example.sweater.selo.ObjectFileDownloadAlarmTime;
import com.example.sweater.selo.ObjectFileDownloadNameTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class SchedulerService {

    @Value("${scheduler.secondlimit}")
    Long secondlimit;

    Long second;
    int triggerDelere;

    public static ArrayList<ObjectFileDownloadAlarmTime> objectFileDownloadAlarmTimes = new ArrayList<>();
    ArrayList<ObjectFileDownloadAlarmTime> objectFileDownloadAlarmTimesDelete = new ArrayList<>();

    @Scheduled(initialDelay = 10000, fixedDelayString = "${scheduler.delay}")
    public void doWork() {
        // Контроль по времени последнего файла записей эфира из сел
        for (ObjectFileDownloadNameTime objectFileDownloadNameTime : FileDownloadController.objectFileDownloadNameTimes) {
            second = TimeUnit.SECONDS.convert(Math.abs(objectFileDownloadNameTime.getData().getTime() - new Date().getTime()), TimeUnit.MILLISECONDS);
            if (second > secondlimit) {


                triggerDelere = 1;
                objectFileDownloadAlarmTimesDelete = new ArrayList<>();
                for (ObjectFileDownloadAlarmTime objectFileDownloadAlarmTime : objectFileDownloadAlarmTimes) {
                    if (objectFileDownloadAlarmTime.getSelo().contains(objectFileDownloadNameTime.getSelo())) {
                        triggerDelere = -1;
                        objectFileDownloadAlarmTimesDelete.add(objectFileDownloadAlarmTime);
                    }
                }
                if (triggerDelere == 1) {
                    objectFileDownloadAlarmTimes.add(new ObjectFileDownloadAlarmTime(objectFileDownloadNameTime.getSelo(), objectFileDownloadNameTime.getData(), second));
                } else {
                    for (ObjectFileDownloadAlarmTime objectFileDownloadAlarmTimeDelete : objectFileDownloadAlarmTimesDelete) {
                        objectFileDownloadAlarmTimes.remove(objectFileDownloadAlarmTimeDelete);
                    }
                    objectFileDownloadAlarmTimes.add(new ObjectFileDownloadAlarmTime(objectFileDownloadNameTime.getSelo(), objectFileDownloadNameTime.getData(), second));
                }
            } else {
                triggerDelere = 1;
                objectFileDownloadAlarmTimesDelete = new ArrayList<>();
                for (ObjectFileDownloadAlarmTime objectFileDownloadAlarmTime : objectFileDownloadAlarmTimes) {
                    if (objectFileDownloadAlarmTime.getSelo().contains(objectFileDownloadNameTime.getSelo())) {
                        triggerDelere = -1;
                        objectFileDownloadAlarmTimesDelete.add(objectFileDownloadAlarmTime);
                    }
                }
                if (triggerDelere == -1) {
                    for (ObjectFileDownloadAlarmTime objectFileDownloadAlarmTimeDelete : objectFileDownloadAlarmTimesDelete) {
                        objectFileDownloadAlarmTimes.remove(objectFileDownloadAlarmTimeDelete);
                    }
                }
            }
        }
        // ---------------------------------------------------------

    }
}