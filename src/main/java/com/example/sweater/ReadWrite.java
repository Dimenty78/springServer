package com.example.sweater;

import java.io.*;

public class ReadWrite {

    //Чтение текста из файла
    public static String readfile(String a){

        String text="";
        try{
            FileInputStream fstream = new FileInputStream(a);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream, "Windows-1251"));
            String strLine;
            while ((strLine = br.readLine()) != null){
                text = text.concat(strLine).concat(System.lineSeparator());
            }
            br.close();
        }catch (IOException e){
            return "error";
        }
        return text;
    }

    //Запись текста в файла
    public static void writeFile(String a , String b ){

        try
        {
            BufferedWriter bw =  new BufferedWriter(new OutputStreamWriter(new FileOutputStream(a), "Windows-1251"));
            bw.write(b);
            bw.flush();
            bw.close();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    //Удаление файлов через ентер
    public static void delDirFile(String a){

        String delFile [] = a.split(System.lineSeparator());
        for (int d = 0; d < delFile.length; d++) {

            final File file = new File(delFile[d]);
            if(file.delete()) {

            } else {
                System.out.println("Файл удалить не получилось");
            }
        }
    }
}
