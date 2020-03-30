package com.example.sweater.radioclass;

import com.example.sweater.overs.ReadWrite;

public class Avtorization {

    public static String vhod (String rnd){

        int rndC;
        System.out.println("inID=" + rnd);

        if(rnd.equals("0")){                // Если новый пользователь
            String idText = ReadWrite.readfile ("\\\\MULTICAST\\(G) Arch\\ЗАПИСЬ ЭФИРОВ\\Download\\YandexDisk-dimenty-pokupka\\Logs\\Доступ\\users.dat");

            if(idText.contains("error")){
                ReadWrite.writeFile ("\\\\MULTICAST\\(G) Arch\\ЗАПИСЬ ЭФИРОВ\\Download\\YandexDisk-dimenty-pokupka\\Logs\\Доступ\\users.dat","");
                idText = "";
            }

            if(idText.length() == 0){
                rndC = 31232;
            } else {
                rndC = Integer.parseInt(idText.split(System.lineSeparator())[0].split(":::;:::")[0]) - 1;
            }
            System.out.println("NewUser >>> " + rndC);
            String outRec = rndC + ":::;:::noN-1 " + (rndC+1) + ":::;:::off" + System.lineSeparator() + idText;
            ReadWrite.writeFile ("\\\\MULTICAST\\(G) Arch\\ЗАПИСЬ ЭФИРОВ\\Download\\YandexDisk-dimenty-pokupka\\Logs\\Доступ\\users.dat", outRec);
            return "" + rndC;

        }else {                             // Если пользователь старый
            String idText = ReadWrite.readfile ("\\\\MULTICAST\\(G) Arch\\ЗАПИСЬ ЭФИРОВ\\Download\\YandexDisk-dimenty-pokupka\\Logs\\Доступ\\users.dat");
            if(idText.contains(rnd) && (idText.split(rnd)[1].split(System.lineSeparator())[0].split(":::;:::")[2].contains("on"))){
                return idText.split(rnd)[1].split(System.lineSeparator())[0].split(":::;:::")[1];
            }else {
                return "off";
            }
        }
    }
}