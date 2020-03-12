package com.example.sweater;

import java.util.ArrayList;

public class RD {

    public static String rdgo(String zapros){

        String otvet = "";

        String id = zapros.split("_")[0];
        String deistvie = zapros.split("_")[1];

        //Поиск по логу 2х2
        if (deistvie.contains("log2x2")){

            ArrayList<mp3LineLog> mp3Line = new ArrayList<mp3LineLog>();
            String mes = "";
            String file = "\\\\Radio2x2\\d\\!_Player\\History\\Files\\";

            String data = zapros.split("_")[2]; //27-01-2020

            file = file + data.split("-")[2] + "\\";
            if (data.split("-")[1].contains("01")){ mes = "Январь";}
            if (data.split("-")[1].contains("02")){ mes = "Февраль";}
            if (data.split("-")[1].contains("03")){ mes = "Март";}
            if (data.split("-")[1].contains("04")){ mes = "Апрель";}
            if (data.split("-")[1].contains("05")){ mes = "Май";}
            if (data.split("-")[1].contains("06")){ mes = "Июнь";}
            if (data.split("-")[1].contains("07")){ mes = "Июль";}
            if (data.split("-")[1].contains("08")){ mes = "Август";}
            if (data.split("-")[1].contains("09")){ mes = "Сентябрь";}
            if (data.split("-")[1].contains("10")){ mes = "Октябрь";}
            if (data.split("-")[1].contains("11")){ mes = "Ноябрь";}
            if (data.split("-")[1].contains("12")){ mes = "Декабрь";}
            file = file + mes + "\\";
            file = file + data.split("-")[0] + "_" + mes + "_" + data.split("-")[2] + ".log";

            //ЧИТАЕМ ФАЙЛ ЛОГА
            String textLog = ReadWrite.readfile (file);

            String Path[] = textLog.split("Path=");
            for (int i = 1; i < Path.length; i++) {
                mp3Line.add(new mp3LineLog(Path[i]));
            }

            for (int i = 0; i < mp3Line.size(); i++) {

                int startT = Integer.parseInt(zapros.split("_")[3].split("-")[0]) * 60 + Integer.parseInt(zapros.split("_")[3].split("-")[1]);
                int stopT = Integer.parseInt(zapros.split("_")[4].split("-")[0]) * 60 + Integer.parseInt(zapros.split("_")[4].split("-")[1]);
                int lineT = Integer.parseInt(mp3Line.get(i).getStartTime().split(" ")[1].split(":")[0]) * 60 + Integer.parseInt(mp3Line.get(i).getStartTime().split(" ")[1].split(":")[1]);

                if (lineT >= startT && lineT <= stopT) {
                    otvet = otvet + mp3Line.get(i).getStartTime().split(" ")[1] + "   " + mp3Line.get(i).getName() + "<br/>";
                }
            }
        }
        return otvet;
    }
}