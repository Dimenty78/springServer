package com.example.sweater;

import java.util.ArrayList;

public class RD {

    public static String rdgo(String zapros){

        String otvet = "";

        //Проверка id запроса
        String id = zapros.split("_")[0];
        if(ReadWrite.readfile ("\\\\MULTICAST\\(G) Arch\\ЗАПИСЬ ЭФИРОВ\\Download\\YandexDisk-dimenty-pokupka\\Logs\\Доступ\\users.dat").contains(id)){

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

// Сообщение ведущим
        if (deistvie.contains("messag")){
            String messag = zapros.split("_")[2];

            //if(id.contains("id2")) {
                //ReadWrite.writeFile("\\\\Radio2x2\\d\\message.txt", messag);
                ReadWrite.writeFile("\\\\Radio2x2\\d\\message.txt", messag);
            //}
        }

// Схема музлиста
        if (deistvie.contains("shema")){
            String parametrMuzL = ReadWrite.readfile("\\\\Radio2x2\\d\\Архив\\МузЛисты для 2х2\\Параметры.txt").replace("+++", "6545893249");
            String foldersMuzL = parametrMuzL.split("6545893249")[0].replace("||", "&ensp;&ensp;").replace(System.lineSeparator(), "<br/>");
            String shemaMuzL = parametrMuzL.split("6545893249")[1].replace("*","33556890545433").split("33556890545433")[0];
            String povtorMuzL = parametrMuzL.split("6545893249")[1].replace("*","33556890545433").split("33556890545433")[1];

            otvet = "Номера папок: " + "<br/>" + foldersMuzL + "<br/>" + "Последовательность: " +  "<br/>" + shemaMuzL + "<br/>" + "<br/>" + "Повторяемость исполнителя через " + povtorMuzL + " песен";

                    //otvet = ReadWrite.readfile("\\\\Radio2x2\\d\\Архив\\МузЛисты для 2х2\\Параметры.txt").replace("+++", "6545893249");
        }

// Раскидать анонсы
        if (deistvie.contains("anons")){
            ReadWrite.writeFile("\\\\Radio2x2\\d\\Архив\\МузЛисты для 2х2\\РаскидатьАнонсы.txt","anonsGOmen");
            int a = 0;
            String anonsOtvet = "";
            while(a < 1) {
                try {
                    Thread.sleep(900);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                anonsOtvet = ReadWrite.readfile("\\\\Radio2x2\\d\\Архив\\МузЛисты для 2х2\\РаскидатьАнонсы.txt");
                if (anonsOtvet.contains("anonsGOmen")){
                }else {
                    if (anonsOtvet.contains("error")){
                    }else{
                        a=1;
                    }
                }
            }
            otvet = anonsOtvet;
        }

// Наши МП имена
        if (deistvie.contains("mpAll")){
                otvet = ReadWrite.filesInFolderName("c:\\Program Files (x86)\\RADIO Studio Pro\\RADIO AdsMan Pro\\!_БАЗЫ\\!2х2\\Ads\\");
        }

// Чтение конкретного МП
        if (deistvie.contains("itsMP")){

            String mpName = zapros.split("_")[2].replace("71324565", "_"); //71324565

            String listText = ReadWrite.readfile("c:\\Program Files (x86)\\RADIO Studio Pro\\RADIO AdsMan Pro\\!_БАЗЫ\\!2х2\\Ads\\" + mpName + ".card");

            String title = "Название: " + listText.split("Title=")[1].split(System.lineSeparator())[0];
            //String patch = "Путь: " + listText.split("Path=")[1].split(System.lineSeparator())[0];
            String startDate = "Дата старта: " + listText.split("StartDate=")[1].split(System.lineSeparator())[0];
            String stopDate = "Дата финиша: " + listText.split("StopDate=")[1].split(System.lineSeparator())[0];
            String filesTime = "Длительность: " + listText.split("FilesTime=")[1].split(System.lineSeparator())[0];
            String maskTimes = listText.split("MaskTimes]" + System.lineSeparator())[1].replace(System.lineSeparator() + System.lineSeparator(), System.lineSeparator()).replace(System.lineSeparator() + "Date=","Date=").replace(":00Date=","Date=");

            String timeData [] = maskTimes.split(System.lineSeparator());
            String data = timeData[0].split("Date=")[1];
            String timeDataOut = "Дата: " + data + System.lineSeparator();

            for (int i = 0; i < timeData.length; i++) {

                if(timeData[i].split("Date=")[1].contains(data)){
                    timeDataOut = timeDataOut + timeData[i].split("Date=")[0].split("Time=")[1] + ", ";
                }else {
                    data = timeData[i].split("Date=")[1];
                    timeDataOut = timeDataOut + System.lineSeparator() + System.lineSeparator() + "Дата: " + data + System.lineSeparator() + timeData[i].split("Date=")[0].split("Time=")[1] + ", ";
                }
            }
            timeDataOut = timeDataOut.replace(System.lineSeparator() ,"<br/>");
            otvet = title + "<br/>" + "<br/>" + startDate + "<br/>" + stopDate + "<br/>" + "<br/>" + filesTime + "<br/>" + "<br/>" + "<br/>" + "<br/>" + timeDataOut;
        }

// ОШИБКИ
        if (deistvie.contains("errorEfir")){
            otvet = ReadWrite.readfile("\\\\Multicast\\(z) arhiv\\АРХИВ\\ЛОГИ\\ОШИБКИ ЭФИРА\\errorEfir.log");
        }
            return otvet;
        }else {
            return "Нет авторизации";
        }
    }
}