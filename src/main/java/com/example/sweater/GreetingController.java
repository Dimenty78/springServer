package com.example.sweater;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

@Controller
public class GreetingController {

    float voltag = 0;

    //-----------------------------------------------------------------------------------
    @GetMapping("/text")   //Получение строчки с устройства
    public String text(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String, Object> model
    ) {
        model.put("name", "*LineOK*"); // Обязательно 8 символов !!!!!!!!!!!!!! Иначе не сработает проверка
        Date curTime = new Date();
        DateFormat dtfrm = DateFormat.getDateTimeInstance();
        String dateTime = dtfrm.format(curTime);
        ReadWrite.writeFile ("\\\\Police-wave-pc\\d\\!!!\\orlanul.log", dateTime + "   > " + name + "\r" + ReadWrite.readfile ("\\\\Police-wave-pc\\d\\!!!\\orlanul.log"));
        System.out.println("GPS: " + name);
        return "text";
    }

    //-----------------------------------------------------------------------------------
    @GetMapping("/site")   //Запрос крайней строки с мобильной проги
    public String site(
            @RequestParam(name="pack", required=false, defaultValue="pack") String pack,
            Map<String, Object> model
    ) {
        model.put("pack", ReadWrite.readfile ("\\\\MULTICAST\\(G) Arch\\ЗАПИСЬ ЭФИРОВ\\Download\\YandexDisk-dimenty-pokupka\\Logs\\Orlanul\\log_mobil.log"));
        //System.out.println("site: "  + pack);
        return "site";
    }

    //-----------------------------------------------------------------------------------
    @GetMapping("/ptica")
    public String ptica(
            @RequestParam(name="volt", required=false, defaultValue="volt") String volt,
            Map<String, Object> model
    ) {
        if(volt.equals("data")){
            model.put("volt", "Напряжение " + voltag + "в");
            System.out.println("Запрос напряжения");
        }else{
            if(volt.equals("dataUsr")) {
                model.put("volt", ReadWrite.readfile ("\\\\MULTICAST\\(G) Arch\\ЗАПИСЬ ЭФИРОВ\\Download\\YandexDisk-dimenty-pokupka\\Logs\\Orlanul\\volt_arhiv.log").split("\n")[0]);
                System.out.println("Запрос напряжения");
            }else{
                model.put("volt", "OK");
                float voltKorrect = (float) (Float.parseFloat(volt.replace("_", ".")) * 0.8498023715415019 * 1.005436041834272);
                ReadWrite.writeFile ("\\\\MULTICAST\\(G) Arch\\ЗАПИСЬ ЭФИРОВ\\Download\\YandexDisk-dimenty-pokupka\\Logs\\Orlanul\\volt.log",voltKorrect + "\n" + ReadWrite.readfile ("\\\\MULTICAST\\(G) Arch\\ЗАПИСЬ ЭФИРОВ\\Download\\YandexDisk-dimenty-pokupka\\Logs\\Orlanul\\volt.log"));
                voltag = voltKorrect;
                System.out.println("Volt: " + voltag);
            }
        }
        return "ptica";
    }

    //----------------------------------------------------------------------------------- РАДИО СЕРВИС
    @GetMapping("/rd")                      // Запросы от программы
            public String rd(
            @RequestParam(name="zapros", required=false, defaultValue="zapros") String zapros,
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
            @RequestParam(name="nool", required=false, defaultValue="nool") String nool,
            Map<String, Object> model
    ) {
        System.out.println("nool: " + nool);
        model.put("nool", nool);
        return "pusto";
    }
    @GetMapping("/rdid")                    // АВТОРИЗАЦИЯ
    public String rdid(
            @RequestParam(name="rnd", required=false, defaultValue="rnd") String rnd,
            Map<String, Object> model
    ) {
        model.put("rnd", Avtorization.vhod(rnd));
        return "rdid";
    }
    @GetMapping("/selo")                    // Посылы от сёл xStarter "xStarter_Inza_DataTime_ночное откл (1-0)_Тимвивер (1-0)_Мыш (1-0)_Перезагрузка (1-0)"
    public String selo1(
            @RequestParam(name="data", required=false, defaultValue="nool") String data,
            Map<String, Object> model
    ) {
        XStarterRazbor.XStarter(data);
        model.put("xStart", "OK");
        return "selo";
    }

//----------------------------------------------------------------------------------- НАЧАЛЬНАЯ СТРАНИЦА
    @GetMapping
    public String main(Map<String, Object> model) {
        //model.put("some", "Сдесь скоро будет сайт... а может и не будет... а может и не скоро... а может и не сайт...");
        String voltOtver = ReadWrite.readfile ("\\\\MULTICAST\\(G) Arch\\ЗАПИСЬ ЭФИРОВ\\Download\\YandexDisk-dimenty-pokupka\\Logs\\Orlanul\\volt_arhiv.log").split("\n")[0];
        //model.put("some", "Напряжение аккумулятора ~" + voltOtver);
        System.out.println(voltOtver);
        model.put("some", "НАПРЯЖЕНИЕ АККУМУЛЯТОРА ~" + voltOtver.split("\t")[1] + " (данные от " + voltOtver.split("\t")[0] + ")");
        return "main";
    }
}