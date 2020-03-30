package com.example.sweater.overs;

import java.text.SimpleDateFormat;
import java.util.*;

public class RadioPoints {

    static Map <String,String> vilagis = new HashMap<String, String>();

    public static void Add (String name, Date date){

        String dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_S").format(date);
        vilagis.put(name,dateFormat);


        for (Map.Entry<String, String> kay : vilagis.entrySet()){

            System.out.println(kay.getKey() + " " + kay.getValue());

        }


    }
}
