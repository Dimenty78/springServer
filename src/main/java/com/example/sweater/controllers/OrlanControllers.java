package com.example.sweater.controllers;

import com.example.sweater.orlanobject.ObjectVolt;
import com.example.sweater.overs.ReadWrite;
import com.example.sweater.orlanobject.ObjectVolts;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Controller
public class OrlanControllers {

    public static ArrayList<ObjectVolts> objectVolts = new ListObjectVolts().OrlanControllers();
    ArrayList<ObjectVolt> objectVolt = new ArrayList<>();

    float voltKorrect, objectVoltUsr;

    @GetMapping("/ptica")
    public String ptica(
            @RequestParam(name = "volt", required = false, defaultValue = "volt") String volt,
            Map<String, Object> model
    ) {
        if (volt.equals("data")) {

        } else {
            if (volt.equals("dataUsr")) {
                model.put("volt", new SimpleDateFormat("dd.MM.yyyy HH:mm").format(objectVolts.get(OrlanControllers.objectVolts.size() - 1).getData()) + "       " + objectVolts.get(OrlanControllers.objectVolts.size() - 1).getVolt() + "v");
            } else {
                model.put("volt", "OK");
                voltKorrect = (float) (Float.parseFloat(volt.replace("_", ".")) * 0.8498023715415019 * 1.005436041834272);
                objectVolt.add(new ObjectVolt(voltKorrect));
                if (objectVolt.size() == 30) {
                    objectVoltUsr = 0;
                    for (ObjectVolt objectVolt : objectVolt) {
                        objectVoltUsr = objectVoltUsr + objectVolt.getVolt();
                    }
                    objectVoltUsr = objectVoltUsr / 30;
                    objectVolts.add(new ObjectVolts(String.valueOf(objectVoltUsr), new Date()));
                    ReadWrite.writeFile("\\\\MULTICAST\\(G) Arch\\ЗАПИСЬ ЭФИРОВ\\Download\\YandexDisk-dimenty-pokupka\\Logs\\Orlanul\\voltArh.log", new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()) + "\t" + objectVoltUsr + System.lineSeparator() + ReadWrite.readfile("\\\\MULTICAST\\(G) Arch\\ЗАПИСЬ ЭФИРОВ\\Download\\YandexDisk-dimenty-pokupka\\Logs\\Orlanul\\voltArh.log"));
                    //System.out.println(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()) + " > " + objectVoltUsr);
                    objectVolt = new ArrayList<>();
                }
            }
        }
        return "ptica";
    }
}

// Восстанавливаем объекты при старте сервера
class ListObjectVolts {
    ArrayList<ObjectVolts> objectVolts = new ArrayList<>();
    String readFile = ReadWrite.readfile("\\\\MULTICAST\\(G) Arch\\ЗАПИСЬ ЭФИРОВ\\Download\\YandexDisk-dimenty-pokupka\\Logs\\Orlanul\\voltArh.log");
    public ArrayList<ObjectVolts> OrlanControllers() {
        if (readFile.length() != 0) {
            for (String fileVolt : readFile.split(System.lineSeparator())) {
                try {
                    if (objectVolts.size() < 30) {
                        objectVolts.add(new ObjectVolts(fileVolt.split("\t")[1], new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(fileVolt.split("\t")[0])));
                    }
                } catch (ParseException e) {
                    System.out.println("Ошибка создания объекта objectVolts");
                }
            }
        }
        return objectVolts;
    }
}