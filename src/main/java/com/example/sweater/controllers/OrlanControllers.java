package com.example.sweater.controllers;

import com.example.sweater.orlanobject.ObjectVolt;
import com.example.sweater.overs.ReadWrite;
import com.example.sweater.orlanobject.ObjectVolts;
import com.example.sweater.overs.VoltUsrednenie;
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

    static String temperaturaTextForNool = temperaturaTextForNool();

    public static String dataTemp = temperaturaTextForNool.split(System.lineSeparator())[0].split(" >>> ")[0];
    public static int zarjad = Integer.parseInt(temperaturaTextForNool.split(System.lineSeparator())[0].split("Заряд: ")[1].split("%, ")[0]);
    public static float temperatura = Float.parseFloat(temperaturaTextForNool.split(System.lineSeparator())[0].split("Темп: ")[1].split(".0гр, ")[0]);
    public static String typeBattery = temperaturaTextForNool.split(System.lineSeparator())[0].split("Тип: ")[1];
    public static String statusBattery = temperaturaTextForNool.split(System.lineSeparator())[0].split("Статус: ")[1].split(" Тип: ")[0];
    public static String voltag = temperaturaTextForNool.split(System.lineSeparator())[0].split("Вольт: ")[1].split("в, ")[0];

    static String config = ReadWrite.readfile("configVoltUsr.txt");
    static int buferSize = Integer.parseInt(config.split("buferSize=")[1].split(System.lineSeparator())[0]);
    static int buferMinus = Integer.parseInt(config.split("buferMinus=")[1].split(System.lineSeparator())[0]);
    int vUsrTrr = buferSize;

    public static float voltMnojitel = Float.parseFloat(ReadWrite.readfile("voltMnojitel.txt").split(System.lineSeparator())[0].split(" ")[2]);

    public static String voltArhiv = ReadWrite.readfile("\\\\MULTICAST\\(G) Arch\\ЗАПИСЬ ЭФИРОВ\\Download\\YandexDisk-dimenty-pokupka\\Logs\\Orlanul\\voltArh.log");
    static float voltAddUsr[] = getVoltAddUsr(voltArhiv);
    public static float vUsr = VoltUsrednenie.dataText(voltAddUsr, buferSize, buferMinus);

    public static String temperaturaTextForNool() {
        String readFileTemp = ReadWrite.readfile("z:\\!_LOGS\\Temperatura\\" + new SimpleDateFormat("yyyy.MM.dd").format(new Date()) + ".txt");
        if (readFileTemp.length() != 0) {
            return readFileTemp;
        } else {
            return ReadWrite.readfile("z:\\!_LOGS\\Temperatura\\" + new SimpleDateFormat("yyyy.MM.dd").format(new Date().getTime() + (long) -1 * 1000 * 60 * 60 * 24) + ".txt");
        }
    }

    public static float[] getVoltAddUsr(String voltArhiv) {
        voltAddUsr = new float[buferSize];
        for (int i = 0; i < buferSize; i++) {
            voltAddUsr[i] = Float.parseFloat(voltArhiv.split(System.lineSeparator())[i].split("\t")[1]);
        }
        return voltAddUsr;
    }


    @GetMapping("/ptica")
    public String ptica(
            @RequestParam(name = "volt", required = false, defaultValue = "volt") String volt,
            Map<String, Object> model
    ) {
        if (volt.split("_")[0].equals("voltMnojitel")) { //voltMnojitel_1-0031
            //System.out.println(volt);
            if (Float.parseFloat(volt.split("_")[1].replace("-", ".")) != voltMnojitel) {
                voltMnojitel = Float.parseFloat(volt.split("_")[1].replace("-", "."));
                ReadWrite.writeFile("voltMnojitel.txt", new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Date()) + " " + voltMnojitel + System.lineSeparator() + ReadWrite.readfile("voltMnojitel.txt"));
                System.out.println(voltMnojitel);
            }
            model.put("volt", "OK");
        } else {
            if (volt.equals("dataUsr")) {
                model.put("volt", new SimpleDateFormat("dd.MM.yyyy HH:mm").format(objectVolts.get(OrlanControllers.objectVolts.size() - 1).getData()) + "       " + objectVolts.get(OrlanControllers.objectVolts.size() - 1).getVolt() + "v");
            } else {
                if (volt.equals("voltMnojitelGet")) {
                    System.out.println("GetMnoj " + voltMnojitel);
                    model.put("volt", voltMnojitel);
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
                        voltArhiv = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()) + "\t" + objectVoltUsr + System.lineSeparator() + voltArhiv;
                        System.out.println("АККУМА: " + objectVoltUsr + "v");
                        objectVolt = new ArrayList<>();

                        for (int i = 1; i < voltAddUsr.length; i++) {
                            voltAddUsr[voltAddUsr.length - i] = voltAddUsr[voltAddUsr.length - i - 1];
                        }
                        voltAddUsr[0] = objectVoltUsr;
                        vUsrTrr++;

                        if (vUsrTrr >= buferSize) {
                            vUsrTrr--;
                            vUsr = VoltUsrednenie.dataText(voltAddUsr, buferSize, buferMinus);
                        }
                    }
                }
            }
        }
        return "ptica";
    }

    //-----------------------------------------------------------------------------------
    @GetMapping("/oorlans")   //Запрос крайней строки с мобильной проги
    public String site(
            @RequestParam(name = "zartemp", required = false, defaultValue = "zartemp") String zartemp,
            Map<String, Object> model
    ) {
        if (zartemp.equals("data")) {
            model.put("zartemp", "" + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(objectVolts.get(OrlanControllers.objectVolts.size() - 1).getData()) + "       " + vUsr + "v  " + dataTemp + " >>> Заряд: " + zarjad + "%, Темп: " + temperatura + "гр, Вольт: " + voltag + "в, Статус: " + statusBattery + " Тип: " + typeBattery);
        } else {
            if (zartemp.equals("massivTemp")) { // Для мобильного графика температуры
                String massivTextTemper = ReadWrite.readfile("z:\\!_LOGS\\Temperatura\\" + new SimpleDateFormat("yyyy.MM.dd").format(new Date()) + ".txt");
                String masssMobilGraphTemp = "";
                if (massivTextTemper.length() > 0) {
                    //int num = 0;
                    String nuum = "";
                    for (int i = 0; i < massivTextTemper.split(System.lineSeparator()).length; i++) {
                        if (massivTextTemper.split(System.lineSeparator())[i].split("Темп: ")[1].split(".0гр,")[0].equals(nuum)) {
                        } else {
                            masssMobilGraphTemp = "_" + massivTextTemper.split(System.lineSeparator())[i].split("Темп: ")[1].split(".0гр,")[0] + "_" + ";" + masssMobilGraphTemp;
                            nuum = massivTextTemper.split(System.lineSeparator())[i].split("Темп: ")[1].split(".0гр,")[0];
                        }
                    }
                    massivTextTemper = "";
                    for (int i = 0; i < masssMobilGraphTemp.split(";").length; i++) {
                        massivTextTemper = massivTextTemper + i + masssMobilGraphTemp.split(";")[i] + ";";
                    }
                }else {
                    massivTextTemper = "0_0_;";
                }
                //System.out.println(massivTextTemper);
                model.put("zartemp", massivTextTemper);
            } else {
                model.put("zartemp", "OK");
                zarjad = Integer.parseInt(zartemp.split("_")[0]);
                temperatura = (Float.parseFloat(zartemp.split("_")[1])) / 10;
                typeBattery = zartemp.split("_")[2];
                statusBattery = zartemp.split("_")[3];
                voltag = zartemp.split("_")[4].replace("-", ".");
                dataTemp = new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Date());
                ReadWrite.writeFile("z:\\!_LOGS\\Temperatura\\" + new SimpleDateFormat("yyyy.MM.dd").format(new Date()) + ".txt", dataTemp + " >>> Заряд: " + zarjad + "%, Темп: " + temperatura + "гр, Вольт: " + voltag + "в, Статус: " + statusBattery + " Тип: " + typeBattery + System.lineSeparator() + ReadWrite.readfile("z:\\!_LOGS\\Temperatura\\" + new SimpleDateFormat("yyyy.MM.dd").format(new Date()) + ".txt"));
                System.out.println("КАМЕРА:  " + zarjad + "%, " + temperatura + "гр, " + voltag + "в, " + statusBattery + " " + typeBattery);
            }
        }
        return "oorlans";
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