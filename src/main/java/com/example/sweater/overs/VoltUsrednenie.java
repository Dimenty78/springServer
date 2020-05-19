package com.example.sweater.overs;

import java.util.Arrays;

public class VoltUsrednenie {

    public static float dataText(float[] fileRead, int buferSize, int buferMinus) {

        //System.out.println();

        float voltAddUsrr[] = new float[buferSize];
        for (int f = 0; f < fileRead.length; f++) {
            voltAddUsrr[f] = fileRead[f];
            //System.out.println(">> " + fileRead[f]);
        }

        //System.out.println();
        Arrays.sort(voltAddUsrr);

        /*for (int f = 0; f < voltAddUsrr.length; f++) {
            System.out.println("> " + voltAddUsrr[f]);
        }*/

        float rez = 0;
        for (int f = 0; f < voltAddUsrr.length; f++) {
            //System.out.println("!=" + f + ">>" + fileRead[f]);
            if (f >= buferMinus && f <= buferSize-buferMinus-1) {
                //System.out.println("" + f + "__" + voltAddUsrr[f]);
                rez = rez + voltAddUsrr[f];
            }
        }
        //System.out.println("_______________________" + rez + " del " + (buferSize-buferMinus*2));
        rez = rez/(buferSize-buferMinus*2);
        return rez;
    }
}