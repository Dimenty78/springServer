package com.example.sweater.controllers;

import com.example.sweater.SchedulerService;
import com.example.sweater.overs.ReadWrite;
import com.example.sweater.selo.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Controller
public class SeloControllers {

    String[] sort , readFil;
    String alarmData = "";
    String alarmList = "";
    long diffInMillies, diff;
    Date startDataTime, stopDataTime;
    String readFileObjectList, readFileObjectRebut, readFileObjectTeamwiewer, readFileObjectMishka, dateFormat1, dateFormat2, dateFormat3, dateFormat4, dateFormat5;

    @Value("${path1}")
    String path1;

    ArrayList<ObjectAlarm> objectAlarms = new ArrayList<>();
    ArrayList<ObjectAlarm> objectAlarmsDelete = new ArrayList<>();

    ArrayList<ObjectList> objectLists = ListObjectsStart();

    ArrayList<ObjectRebut> objectRebut = ObjectRebutStart();
    ArrayList<ObjectTeamwiewer> objectTeamwiewer = ObjectTeamwiewerStart();
    ArrayList<ObjectMishka> objectMishkas = ObjectMishkaStart();

    ArrayList<ObjectList> objectLists0 = new ArrayList<>();
    ArrayList<ObjectRebut> objectRebut0 = new ArrayList<>();
    ArrayList<ObjectTeamwiewer> objectTeamwiewer0 = new ArrayList<>();
    ArrayList<ObjectMishka> objectMishkas0 = new ArrayList<>();

    ArrayList<ObjectList> objectListsData = new ArrayList<>();
    ArrayList<ObjectRebut> objectRebutData = new ArrayList<>();
    ArrayList<ObjectTeamwiewer> objectTeamwiewerData = new ArrayList<>();
    ArrayList<ObjectMishka> objectMishkasData = new ArrayList<>();

    @GetMapping("/selojava")                    //
    public String selojava(
            @RequestParam(name = "data", required = false, defaultValue = "nool") String data,
            Map<String, Object> model
    ) {
        if (data.contains("alarmListData")) {
            System.out.println(">" + path1 + "АРХИВ\\ЛОГИ\\!_JavaClient\\Tishina\\<");
            objectListsData = new ArrayList<>();
            readFil = ReadWrite.readfile("\\\\Multicast\\(Z) ARHIV\\АРХИВ\\ЛОГИ\\!_JavaClient\\Tishina\\" + data.split("_")[1].replace("-", "_") + ".log").split(System.lineSeparator());
            if (readFil.length > 1) {
                for (String readFilLine : readFil) {
                    try {
                        objectListsData.add(new ObjectList(readFilLine.split(" > ")[2], new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").parse(readFilLine.split(" > ")[0]), Long.parseLong(readFilLine.split(" > ")[1].split("sec")[0])));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            objectRebutData = new ArrayList<>();
            readFil = ReadWrite.readfile("\\\\Multicast\\(Z) ARHIV\\АРХИВ\\ЛОГИ\\!_JavaClient\\Rebut\\" + data.split("_")[1].replace("-", "_") + ".log").split(System.lineSeparator());
            if (readFil.length > 1) {
                for (String readFilLine : readFil) {
                    try {
                        objectRebutData.add(new ObjectRebut(readFilLine.split(" > ")[1], new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").parse(readFilLine.split(" > ")[0])));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            objectTeamwiewerData = new ArrayList<>();
            readFil = ReadWrite.readfile("\\\\Multicast\\(Z) ARHIV\\АРХИВ\\ЛОГИ\\!_JavaClient\\Teamwiewer\\" + data.split("_")[1].replace("-", "_") + ".log").split(System.lineSeparator());
            if (readFil.length > 1) {
                for (String readFilLine : readFil) {
                    try {
                        objectTeamwiewerData.add(new ObjectTeamwiewer(readFilLine.split(" > ")[1], new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").parse(readFilLine.split(" > ")[0])));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            objectMishkasData = new ArrayList<>();
            readFil = ReadWrite.readfile("\\\\Multicast\\(Z) ARHIV\\АРХИВ\\ЛОГИ\\!_JavaClient\\Mishka\\" + data.split("_")[1].replace("-", "_") + ".log").split(System.lineSeparator());
            if (readFil.length > 1) {
                for (String readFilLine : readFil) {
                    try {
                        objectMishkasData.add(new ObjectMishka(readFilLine.split(" > ")[1], new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").parse(readFilLine.split(" > ")[0])));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            model.put("selojava", AlarmPrint(objectListsData, objectRebutData, objectTeamwiewerData, objectMishkasData, objectAlarms, SchedulerService.objectFileDownloadAlarmTimes));

        } else {
            switch (data) {
                case "alarmData":
                    // Отрисовка незакрытых событий     AlarmData
                    alarmData = "";
                    for (ObjectAlarm objectAlarms : objectAlarms) {
                        alarmData = alarmData + new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(objectAlarms.getData()) + " > Tishina Seichas 0sec > " + objectAlarms.getSelo() + "</br>";
                    }
                    model.put("selojava", alarmData);
                    break;
                case "alarmList":
                    model.put("selojava", AlarmPrint(objectLists, objectRebut, objectTeamwiewer, objectMishkas, objectAlarms, SchedulerService.objectFileDownloadAlarmTimes));
                    break;
                case "alarmList0":
                    model.put("selojava", AlarmPrint(objectLists0, objectRebut0, objectTeamwiewer0, objectMishkas0, objectAlarms, SchedulerService.objectFileDownloadAlarmTimes));
                    break;
                case "alarmNool":
                    objectAlarms = new ArrayList<>();
                    objectAlarmsDelete = new ArrayList<>();
                    objectLists = new ArrayList<>();
                    objectRebut = new ArrayList<>();
                    objectTeamwiewer = new ArrayList<>();
                    objectMishkas = new ArrayList<>();
                    objectLists = ListObjectsStart();
                    objectRebut = ObjectRebutStart();
                    objectTeamwiewer = ObjectTeamwiewerStart();
                    objectMishkas = ObjectMishkaStart();
                    System.out.println(">" + path1 + "АРХИВ\\ЛОГИ\\!_JavaClient\\Tishina\\<");
                    model.put("selojava", "OK");
                    break;
                case "alarmNoolDel":
                    objectLists0 = new ArrayList<>();
                    objectRebut0 = new ArrayList<>();
                    objectTeamwiewer0 = new ArrayList<>();
                    objectMishkas0 = new ArrayList<>();
                    SchedulerService.objectFileDownloadAlarmTimes = new ArrayList<>();
                    model.put("selojava", "OK");
                    break;
                default:
                    model.put("selojava", "OK");
                    dateFormat1 = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_S").format(new Date());
                    // не записывать тишину сел которые на ночь отключаются
                    if (dateFormat1.split("_")[3].equals("00") || dateFormat1.split("_")[3].equals("01") || dateFormat1.split("_")[3].equals("02") || dateFormat1.split("_")[3].equals("03") || dateFormat1.split("_")[3].equals("04") || dateFormat1.split("_")[3].equals("05")) {
                        if (data.contains("Kuzovatovo") || data.contains("Veshkaima") || data.contains("Novospaskoe") || data.contains("Inza") || data.contains("Pavlovka")) {
                            System.out.println(data + " - не записываем");
                        } else {
                            GetTishinaAnaliz(data);
                        }
                    } else {
                        GetTishinaAnaliz(data);
                    }
                    break;
            }
        }
        return "selojava";
    }

    @GetMapping("/selorebut")                    //
    public String selorebut(
            @RequestParam(name = "data", required = false, defaultValue = "nool") String data,
            Map<String, Object> model
    ) {
        objectRebut.add(new ObjectRebut(data, new Date()));
        objectRebut0.add(new ObjectRebut(data, new Date()));
        //Запись лога
        dateFormat2 = new SimpleDateFormat("yyyy_MM_dd").format(new Date());
        ReadWrite.writeFile("\\\\Multicast\\(Z) ARHIV\\АРХИВ\\ЛОГИ\\!_JavaClient\\Rebut\\" + dateFormat2 + ".log", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date()) + " > " + data + System.lineSeparator() + ReadWrite.readfile("\\\\Multicast\\(Z) ARHIV\\АРХИВ\\ЛОГИ\\!_JavaClient\\Rebut\\" + dateFormat2 + ".log"));
        model.put("selorebut", "OK");
        return "selorebut";
    }

    @GetMapping("/seloteamwiewer")                    //
    public String seloteamwiewer(
            @RequestParam(name = "data", required = false, defaultValue = "nool") String data,
            Map<String, Object> model
    ) {
        objectTeamwiewer.add(new ObjectTeamwiewer(data, new Date()));
        objectTeamwiewer0.add(new ObjectTeamwiewer(data, new Date()));
        //Запись лога
        dateFormat3 = new SimpleDateFormat("yyyy_MM_dd").format(new Date());
        ReadWrite.writeFile("\\\\Multicast\\(Z) ARHIV\\АРХИВ\\ЛОГИ\\!_JavaClient\\Teamwiewer\\" + dateFormat3 + ".log", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date()) + " > " + data + System.lineSeparator() + ReadWrite.readfile("\\\\Multicast\\(Z) ARHIV\\АРХИВ\\ЛОГИ\\!_JavaClient\\Teamwiewer\\" + dateFormat3 + ".log"));
        model.put("seloteamwiewer", "OK");
        return "seloteamwiewer";
    }

    @GetMapping("/selomishka")                    //
    public String selomishka(
            @RequestParam(name = "data", required = false, defaultValue = "nool") String data,
            Map<String, Object> model
    ) {
        objectMishkas.add(new ObjectMishka(data, new Date()));
        objectMishkas0.add(new ObjectMishka(data, new Date()));
        //Запись лога
        dateFormat4 = new SimpleDateFormat("yyyy_MM_dd").format(new Date());
        ReadWrite.writeFile("\\\\Multicast\\(Z) ARHIV\\АРХИВ\\ЛОГИ\\!_JavaClient\\Mishka\\" + dateFormat4 + ".log", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date()) + " > " + data + System.lineSeparator() + ReadWrite.readfile("\\\\Multicast\\(Z) ARHIV\\АРХИВ\\ЛОГИ\\!_JavaClient\\Mishka\\" + dateFormat4 + ".log"));
        model.put("selomishka", "OK");
        return "selomishka";
    }

    private void GetTishinaAnaliz(String data) {
        if (data.split("_")[1].equals("ALARM")) {
            startDataTime = new Date();
            objectAlarms.add(new ObjectAlarm(data.split("_")[0], startDataTime));
        } else {
            stopDataTime = new Date();
            objectAlarmsDelete = new ArrayList<>();
            for (ObjectAlarm objectAlarm : objectAlarms) {
                if (objectAlarm.getSelo().equals(data.split("_")[0])) {
                    diffInMillies = Math.abs(objectAlarm.getData().getTime() - stopDataTime.getTime());
                    diff = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    if (diff == 0) diff = 1;
                    objectLists.add(new ObjectList(data.split("_")[0], objectAlarm.getData(), diff));
                    objectLists0.add(new ObjectList(data.split("_")[0], objectAlarm.getData(), diff));
                    objectAlarmsDelete.add(objectAlarm);
                    //Запись лога
                    dateFormat5 = new SimpleDateFormat("yyyy_MM_dd").format(new Date());
                    ReadWrite.writeFile("\\\\Multicast\\(z) arhiv\\АРХИВ\\ЛОГИ\\!_JavaClient\\Tishina\\" + dateFormat5 + ".log", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(objectAlarm.getData()) + " > " + diff + "sec > " + data.split("_")[0] + System.lineSeparator() + ReadWrite.readfile("\\\\Multicast\\(z) arhiv\\АРХИВ\\ЛОГИ\\!_JavaClient\\Tishina\\" + dateFormat5 + ".log"));
                }
            }
            // Удаление закрывшихся событий
            for (ObjectAlarm objectAlarmDelete : objectAlarmsDelete) {
                objectAlarms.remove(objectAlarmDelete);
            }
        }
    }

    // Восстанавливаем объекты при старте сервера
    public ArrayList<ObjectList> ListObjectsStart() {
        System.out.println(path1);
        ArrayList<ObjectList> objectLists = new ArrayList<>();
        readFileObjectList = ReadWrite.readfile("\\\\Multicast\\(z) arhiv\\АРХИВ\\ЛОГИ\\!_JavaClient\\Tishina\\" + new SimpleDateFormat("yyyy_MM_dd").format(new Date()) + ".log");
        if (readFileObjectList.length() != 0) {
            for (String fileRead : readFileObjectList.split(System.lineSeparator())) {
                try {
                    objectLists.add(new ObjectList(fileRead.split(" > ")[2], new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").parse(fileRead.split(" > ")[0]), Long.parseLong(fileRead.split(" > ")[1].split("sec")[0])));
                } catch (ParseException e) {
                    System.out.println("Ошибка создания объекта objectLists");
                }
            }
        }
        return objectLists;
    }

    public ArrayList<ObjectRebut> ObjectRebutStart() {
        ArrayList<ObjectRebut> objectRebuts = new ArrayList<>();
        readFileObjectRebut = ReadWrite.readfile("\\\\Multicast\\(z) arhiv\\АРХИВ\\ЛОГИ\\!_JavaClient\\Rebut\\" + new SimpleDateFormat("yyyy_MM_dd").format(new Date()) + ".log");
        if (readFileObjectRebut.length() != 0) {
            for (String fileRead : readFileObjectRebut.split(System.lineSeparator())) {
                try {
                    objectRebuts.add(new ObjectRebut(fileRead.split(" > ")[1], new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").parse(fileRead.split(" > ")[0])));
                } catch (ParseException e) {
                    System.out.println("Ошибка создания объекта objectLists");
                }
            }
        }
        return objectRebuts;
    }

    public ArrayList<ObjectTeamwiewer> ObjectTeamwiewerStart() {
        ArrayList<ObjectTeamwiewer> objectTeamwiewers = new ArrayList<>();
        readFileObjectTeamwiewer = ReadWrite.readfile("\\\\Multicast\\(z) arhiv\\АРХИВ\\ЛОГИ\\!_JavaClient\\Teamwiewer\\" + new SimpleDateFormat("yyyy_MM_dd").format(new Date()) + ".log");
        if (readFileObjectTeamwiewer.length() != 0) {
            for (String fileRead : readFileObjectTeamwiewer.split(System.lineSeparator())) {
                try {
                    objectTeamwiewers.add(new ObjectTeamwiewer(fileRead.split(" > ")[1], new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").parse(fileRead.split(" > ")[0])));
                } catch (ParseException e) {
                    System.out.println("Ошибка создания объекта objectLists");
                }
            }
        }
        return objectTeamwiewers;
    }

    public ArrayList<ObjectMishka> ObjectMishkaStart() {
        ArrayList<ObjectMishka> objectMishkas = new ArrayList<>();
        readFileObjectMishka = ReadWrite.readfile("\\\\Multicast\\(z) arhiv\\АРХИВ\\ЛОГИ\\!_JavaClient\\Mishka\\" + new SimpleDateFormat("yyyy_MM_dd").format(new Date()) + ".log");
        if (readFileObjectMishka.length() != 0) {
            for (String fileRead : readFileObjectMishka.split(System.lineSeparator())) {
                try {
                    objectMishkas.add(new ObjectMishka(fileRead.split(" > ")[1], new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").parse(fileRead.split(" > ")[0])));
                } catch (ParseException e) {
                    System.out.println("Ошибка создания объекта objectLists");
                }
            }
        }
        return objectMishkas;
    }


    public String AlarmPrint(ArrayList<ObjectList> objectLists, ArrayList<ObjectRebut> objectRebut, ArrayList<ObjectTeamwiewer> objectTeamwiewer, ArrayList<ObjectMishka> objectMishkas, ArrayList<ObjectAlarm> objectAlarms, ArrayList<ObjectFileDownloadAlarmTime> objectFileDownloadAlarmTimes) {
        alarmList = "";
        // Добавляем в ЛИСТ закрытую тишину если она больше определенного значения
        for (ObjectList objectList1 : objectLists) {
            if (objectList1.getTimeSec() > 2) {
                alarmList = alarmList + new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(objectList1.getData()) + " > Была тишина " + objectList1.getTimeSec() + "сек > " + objectList1.getSelo() + "</br>";
            }
        }
        // Добавляем в ЛИСТ перезагрузку
        for (ObjectRebut objectRebut1 : objectRebut) {
            alarmList = alarmList + new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(objectRebut1.getData()) + " > Перезагрузка > " + objectRebut1.getSelo() + "</br>";
        }
        // Добавляем в ЛИСТ тимвивера
        for (ObjectTeamwiewer objectTeamwiewer1 : objectTeamwiewer) {
            alarmList = alarmList + new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(objectTeamwiewer1.getData()) + " > Тимвивер > " + objectTeamwiewer1.getSelo() + "</br>";
        }
        // Добавляем в ЛИСТ мышки
        for (ObjectMishka objectMishka1 : objectMishkas) {
            alarmList = alarmList + new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(objectMishka1.getData()) + " > Мышка > " + objectMishka1.getSelo() + "</br>";
        }
        // Сортировка ЛИСТА
        sort = alarmList.split("</br>");
        alarmList = "";
        Arrays.sort(sort, Collections.reverseOrder());
        for (String objectList : sort) {
            alarmList = alarmList + objectList + "</br>";
        }
        // Добавляем в ЛИСТ события пропадения записей эфира
        for (ObjectFileDownloadAlarmTime objectFileDownloadAlarmTime1 : objectFileDownloadAlarmTimes) {
            alarmList = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(objectFileDownloadAlarmTime1.getData()) + " > ЗАПИСЕЙ ДАВНО НЕ БЫЛО - " + objectFileDownloadAlarmTime1.getSecond() + "сек > " + objectFileDownloadAlarmTime1.getSelo() + "</br>" + alarmList;
        }
        // Добавляем в ЛИСТ незакрытую тишину
        for (ObjectAlarm objectAlarms1 : objectAlarms) {
            alarmList = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(objectAlarms1.getData()) + " > Тишина сейчас!! > " + objectAlarms1.getSelo() + "</br>" + alarmList;
        }

        return alarmList;
    }
}