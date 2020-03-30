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
        model.put("some", "НАПРЯЖЕНИЕ АККУМУЛЯТОРА ~" + OrlanControllers.objectVolts.get(OrlanControllers.objectVolts.size()-1).getVolt() + " (данные от " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(OrlanControllers.objectVolts.get(OrlanControllers.objectVolts.size()-1).getData()) + ")");
        //model.put("some", "НАПРЯЖЕНИЕ АККУМУЛЯТОРА ~" + voltOtver.split("\t")[1] + " (данные от " + voltOtver.split("\t")[0] + ")");
        return "main";
    }
}