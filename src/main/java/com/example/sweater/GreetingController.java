package com.example.sweater;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

@Controller
public class GreetingController {

//-----------------------------------------------------------------------------------
    @GetMapping("/text")
    public String text(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);

        Date curTime = new Date();
        DateFormat dtfrm = DateFormat.getDateTimeInstance();
        String dateTime = dtfrm.format(curTime);
        ReadWrite.writeFile ("\\\\Police-wave-pc\\d\\!!!\\orlanul.log", dateTime + "   > " + name + "\r" + ReadWrite.readfile ("\\\\Police-wave-pc\\d\\!!!\\orlanul.log"));

        System.out.println(">>>>>>>>>>>>>>>>>>> "  + name);
        return "text";
    }
    //-----------------------------------------------------------------------------------
    @GetMapping("/site")
    public String site(
            @RequestParam(name="pack", required=false, defaultValue="---") String pack,
            Map<String, Object> model
    ) {
        model.put("pack", ReadWrite.readfile ("\\\\MULTICAST\\(G) Arch\\ЗАПИСЬ ЭФИРОВ\\Download\\YandexDisk-dimenty-pokupka\\Logs\\Orlanul\\log_mobil.log"));
        System.out.println("<<<<<<<<<<<<<<>>>>>>>> "  + pack);
        return "site";
    }
    //-----------------------------------------------------------------------------------
    @GetMapping("/rd")
            public String rd(
            @RequestParam(name="zapros", required=false, defaultValue="---") String zapros,
            Map<String, Object> model
    ) {

        String otvet = RD.rdgo(zapros);
        model.put("zapros", otvet);
        System.out.println(otvet);
        return "rd";
    }






//-----------------------------------------------------------------------------------
    @GetMapping
    public String main(Map<String, Object> model) {
        model.put("some", "Сдесь скоро будет сайт... а может и не будет... а может и не скоро... а может и не сайт...");
        return "main";
    }





}