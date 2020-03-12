package com.example.sweater;

public class XStarterRazbor {

    public static void XStarter (String data){

        //xStarter
        if ((data.split("_")[0].equals("xStarter"))){
            if ((data.split("_")[2].equals("datatime"))){ //xStarter_Inza_datatime_2020-02-19-14-18-15 - проверка работы Стартера
                ReadWrite.writeFile ("\\\\Multicast\\(z) arhiv\\АРХИВ\\ЛОГИ\\РаботаАвтоматики_Проверка\\" + data.split("_")[1] + ".test",data.split("_")[3]);
            }
        }
        System.out.println(data);
    }
}