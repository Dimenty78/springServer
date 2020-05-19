package com.example.sweater.controllers;

import com.example.sweater.overs.ReadWrite;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class GreetingController {

    //-----------------------------------------------------------------------------------
    @GetMapping("/text")   //Получение строчки с устройства
    public String text(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name,
            Map<String, Object> model
    ) {
        model.put("name", "*LineOK*"); // Обязательно 8 символов !!!!!!!!!!!!!! Иначе не сработает проверка
        Date curTime = new Date();
        DateFormat dtfrm = DateFormat.getDateTimeInstance();
        String dateTime = dtfrm.format(curTime);
        ReadWrite.writeFile("\\\\Police-wave-pc\\d\\!!!\\orlanul.log", dateTime + "   > " + name + "\r" + ReadWrite.readfile("\\\\Police-wave-pc\\d\\!!!\\orlanul.log"));
        System.out.println("GPS: " + name);
        return "text";
    }

    //-----------------------------------------------------------------------------------
    @GetMapping("/site")   //Запрос крайней строки с мобильной проги
    public String site(
            @RequestParam(name = "pack", required = false, defaultValue = "pack") String pack,
            Map<String, Object> model
    ) {
        model.put("pack", ReadWrite.readfile("\\\\MULTICAST\\(G) Arch\\ЗАПИСЬ ЭФИРОВ\\Download\\YandexDisk-dimenty-pokupka\\Logs\\Orlanul\\log_mobil.log"));
        //System.out.println("site: "  + pack);
        return "site";
    }

    //----------------------------------------------------------------------------------- НАЧАЛЬНАЯ СТРАНИЦА
    @GetMapping
    public String main(Map<String, Object> model) {
        //String voltOtver = ReadWrite.readfile("\\\\MULTICAST\\(G) Arch\\ЗАПИСЬ ЭФИРОВ\\Download\\YandexDisk-dimenty-pokupka\\Logs\\Orlanul\\voltArh.log").split("\n")[0];
        System.out.println("Заход на основную страницу");
        model.put("some",
                "---------------------- АККУМУЛЯТОР: "
                        + " (данные от 2020.05.16 13:51"
                        + ")</br>Напряжение: ~12.3"
                        //+ ((float) ((int) (OrlanControllers.vUsr * OrlanControllers.voltMnojitel * 10)) / 10)
                        + "в</br></br>---------------------- ОНЛАЙН-КАМЕРА:"
                        + " (данные от 2020.05.17 11:47"
                        //+ OrlanControllers.dataTemp
                        + ")</br>Заряд: 20"
                        //+ OrlanControllers.zarjad
                        + "%</br>Температура: 33.0"
                        //+ OrlanControllers.temperatura
                        + "гр</br>Статус/Тип заряда: NOT-CHARGE"
                        //+ OrlanControllers.statusBattery
                        + "/NO-CHARGE"
                        //+ OrlanControllers.typeBattery
                        + "</br>Напряжение: 2.959"
                        //+ OrlanControllers.voltag
                        + "в");
//https://drive.google.com/folderview?id=15Pk98XKQdzldddPfs3ID7t81pQyUs5XP

        /*"---------------------- АККУМУЛЯТОР: "
                + " (данные от " + new SimpleDateFormat("yyyy.MM.dd HH:mm").format(OrlanControllers.objectVolts.get(OrlanControllers.objectVolts.size() - 1).getData())
                + ")</br>Напряжение: ~"
                + ((float) ((int) (OrlanControllers.vUsr * OrlanControllers.voltMnojitel * 10)) / 10)
                + "в</br></br>---------------------- ОНЛАЙН-КАМЕРА:"
                + " (данные от "
                + OrlanControllers.dataTemp
                + ")</br>Заряд: "
                + OrlanControllers.zarjad
                + "%</br>Температура: "
                + OrlanControllers.temperatura
                + "гр</br>Статус/Тип заряда: "
                + OrlanControllers.statusBattery
                + "/"
                + OrlanControllers.typeBattery
                + "</br>Напряжение: "
                + OrlanControllers.voltag
                + "в");*/

        // График температуры
        String temperaturaForGraphik = ReadWrite.readfile("z:\\!_LOGS\\Temperatura\\" + new SimpleDateFormat("yyyy.MM.dd").format(new Date()) + ".txt");
        String labelsTempForGraphik = "";
        String dataTempForGraphik = "";
        String dataTempForGraphikNool = "";

        /*if (temperaturaForGraphik.length() != 0) {
            for (int i = 0; i < temperaturaForGraphik.split(System.lineSeparator()).length; i++) {
                if (temperaturaForGraphik.split(System.lineSeparator())[i].split(" ").length == 13) {

                    if (temperaturaForGraphik.split(System.lineSeparator())[i].split("Темп: ")[1].split(".0гр, ")[0].equals(dataTempForGraphikNool)) {
                    } else {
                        dataTempForGraphik = temperaturaForGraphik.split(System.lineSeparator())[i].split("Темп: ")[1].split(".0гр, ")[0] + "," + dataTempForGraphik;
                        dataTempForGraphikNool = temperaturaForGraphik.split(System.lineSeparator())[i].split("Темп: ")[1].split(".0гр, ")[0];
                        labelsTempForGraphik = "`" + temperaturaForGraphik.split(System.lineSeparator())[i].split(" ")[1] + "`," + labelsTempForGraphik;
                    }
                }

            }
        }*/
        model.put("datasTempForGraphik", new SimpleDateFormat("yyyy.MM.dd").format(new Date()));
        model.put("labelsTempForGraphik", labelsTempForGraphik);
        model.put("dataTempForGraphik", dataTempForGraphik);

/*        // График Вольтажа
        String voltForGraphik = ReadWrite.readfile("\\\\MULTICAST\\(G) Arch\\ЗАПИСЬ ЭФИРОВ\\Download\\YandexDisk-dimenty-pokupka\\Logs\\Orlanul\\voltArh.log");
        String labelsVolt = "";
        String dataVolt = "";
        for (int i = 0; i < voltForGraphik.split(System.lineSeparator()).length; i++) {
            labelsVolt = "`" + voltForGraphik.split(System.lineSeparator())[i].split("\t")[0].split(" ")[0] + "`," + labelsVolt;
            dataVolt = voltForGraphik.split(System.lineSeparator())[i].split("\t")[1] + "," + dataVolt;
        }
        model.put("labelsVolt", labelsVolt);
        model.put("dataVolt", dataVolt);*/

        return "main";
    }
}