package com.example.sweater;

import java.io.*;

public class ReadWrite {

    //Чтение текста из файла
    public static String readfile(String patchFileRead){

        String text="";
        try{
            FileInputStream fstream = new FileInputStream(patchFileRead);
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
    public static void writeFile(String patchFileRec , String textForRec ){
        try
        {
            BufferedWriter bw =  new BufferedWriter(new OutputStreamWriter(new FileOutputStream(patchFileRec), "Windows-1251"));
            bw.write(textForRec);
            bw.flush();
            bw.close();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    //Файлы в папке с путями
    public static String filesInFolder(String pathFolder) {
        File f = new File(pathFolder);
        String[] list = f.list();
        String listFile = "";
        for (String file : list) {
            listFile = listFile + pathFolder + file + System.lineSeparator();
        }
        return  listFile;
    }
    //Файлы в папке только имя
    public static String filesInFolderName(String pathFolder) {
        File f = new File(pathFolder);
        String[] list = f.list();
        String listFile = "";
        for (String file : list) {
            if (file.contains(".card")){
                listFile = listFile + file + "|||";
            }
        }
        return  listFile;
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
