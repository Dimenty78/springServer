package com.example.sweater.controllers;

import com.example.sweater.radioclass.Avtorization;
import com.example.sweater.overs.RD;
import com.example.sweater.selo.XStarterRazbor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class RadioController {

    //----------------------------------------------------------------------------------- РАДИО СЕРВИС
    @GetMapping("/rd")                      // Запросы от программы
    public String rd(
            @RequestParam(name = "zapros", required = false, defaultValue = "zapros") String zapros,
            Map<String, Object> model
    ) {
        System.out.println(">>> Запрос: " + zapros);
        String otvet = RD.rdgo(zapros);
        model.put("zapros", otvet);
        System.out.println("<<< Ответ: " + otvet);
        return "rd";
    }

    @GetMapping("/pusto")                   // Пустая страничка
    public String pusto(
            @RequestParam(name = "nool", required = false, defaultValue = "nool") String nool,
            Map<String, Object> model
    ) {
        System.out.println("nool: " + nool);
        model.put("nool", nool);
        return "pusto";
    }

    @GetMapping("/rdid")                    // АВТОРИЗАЦИЯ
    public String rdid(
            @RequestParam(name = "rnd", required = false, defaultValue = "rnd") String rnd,
            Map<String, Object> model
    ) {
        model.put("rnd", Avtorization.vhod(rnd));
        return "rdid";
    }

    @GetMapping("/selo")
    // Посылы от сёл xStarter "xStarter_Inza_DataTime_ночное откл (1-0)_Тимвивер (1-0)_Мыш (1-0)_Перезагрузка (1-0)"
    public String selo1(
            @RequestParam(name = "data", required = false, defaultValue = "nool") String data,
            Map<String, Object> model
    ) {
        XStarterRazbor.XStarter(data);
        model.put("xStart", "OK");
        return "selo";
    }
}