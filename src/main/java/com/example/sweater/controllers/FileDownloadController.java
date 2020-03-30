package com.example.sweater.controllers;

import com.example.sweater.overs.ReadWrite;
import com.example.sweater.selo.ObjectFileDownloadNameTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class FileDownloadController {

    String fileName;
    static String nameSeloFiles;
    String filNam;
    File dir, uploadedFile;
    BufferedOutputStream stream;
    Date filDat;

    String downloadFolderPath = "\\\\2x2_obrabotka\\ЗАПИСИ МВ СЕТЕВЫХ_Java\\";

    public static ArrayList<ObjectFileDownloadNameTime> objectFileDownloadNameTimes = objectFileDownloadNameTimesStart();

    ArrayList<ObjectFileDownloadNameTime> objectFileDownloadNameTimesAdd = new ArrayList<>();

    @RequestMapping(value = "/uploadMultipleFiles", method = RequestMethod.POST)
    @ResponseBody
    public void uploadFile(@RequestParam("files") MultipartFile[] files) {
        fileName = null;
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    fileName = file.getOriginalFilename();

                    //Проверка имени приходимого файла на наличие 3х частей разделенных _
                    if (fileName.split("_").length == 4) {
                        dir = new File(downloadFolderPath + fileName.split("_")[2] + "\\" + fileName.split("_")[0] + "\\");

                    } else {
                        dir = new File(downloadFolderPath + "OverFiles\\");
                    }

                    if (!dir.exists()) {dir.mkdirs();}

                    byte[] bytes = file.getBytes();
                    uploadedFile = new File(dir.getAbsolutePath() + File.separator + fileName);
                    stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
                    stream.write(bytes);
                    stream.flush();
                    stream.close();

                    // Создание списка последних принятых объектов по колличеству деревень
                    if (objectFileDownloadNameTimes.size() == 0) {
                        nameSeloFiles = ReadWrite.readfile("nameSeloFiles.txt");
                    }
                    filNam = fileName.split("_")[2];
                    filDat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fileName.split("_")[0] + " " + fileName.split("_")[1].substring(0, 2) + ":" + fileName.split("_")[1].substring(2, 4) + ":" + fileName.split("_")[1].substring(4, 6));

                    if (nameSeloFiles.contains(filNam)) {
                        if (objectFileDownloadNameTimes.size() == 0) {
                            objectFileDownloadNameTimes.add(new ObjectFileDownloadNameTime(filNam, filDat));
                        } else {
                            int triggerDownloadNameTimesAdd = 1;
                            objectFileDownloadNameTimesAdd = new ArrayList();
                            for (ObjectFileDownloadNameTime objectFileDownloadNameTime : objectFileDownloadNameTimes) {
                                if (objectFileDownloadNameTime.getSelo().contains(filNam)) {         // если в списке объектов есть объект с именем села пришедшего файла то
                                    triggerDownloadNameTimesAdd = -1;
                                    objectFileDownloadNameTimesAdd.add(objectFileDownloadNameTime);
                                }
                            }
                            if (triggerDownloadNameTimesAdd == 1) {
                                objectFileDownloadNameTimes.add(new ObjectFileDownloadNameTime(filNam, filDat));
                            } else {
                                for (ObjectFileDownloadNameTime objectFileDownloadNameTimeDelete : objectFileDownloadNameTimesAdd) {
                                    objectFileDownloadNameTimes.remove(objectFileDownloadNameTimeDelete);
                                }
                                objectFileDownloadNameTimes.add(new ObjectFileDownloadNameTime(filNam, filDat));
                            }
                        }
                    } else {
                        ReadWrite.writeFile("nameSeloFiles.txt", filNam + System.lineSeparator() + nameSeloFiles);
                        nameSeloFiles = ReadWrite.readfile("nameSeloFiles.txt");
                        objectFileDownloadNameTimes.add(new ObjectFileDownloadNameTime(filNam, filDat));
                    }

                    //System.out.println("Успешно принят файл: " + fileName);

                } catch (Exception e) {
                    System.out.println("Не принят файл " + fileName + " => " + e.getMessage());
                }
            } else {
                System.out.println("Вы не смогли передать файл  " + fileName + " потому что файл был пуст");
            }
        }
    }

    private static ArrayList<ObjectFileDownloadNameTime> objectFileDownloadNameTimesStart() {
        ArrayList<ObjectFileDownloadNameTime> objectFileDownloadNameTimes = new ArrayList<>();
        String readFileObjectFileDownloadNameTime = ReadWrite.readfile("nameSeloFiles.txt");
        if (readFileObjectFileDownloadNameTime.length() != 0) {
            for (String fileRead : readFileObjectFileDownloadNameTime.split(System.lineSeparator())) {
                objectFileDownloadNameTimes.add(new ObjectFileDownloadNameTime(fileRead, new Date()));
            }
        }
        nameSeloFiles = ReadWrite.readfile("nameSeloFiles.txt");
        return objectFileDownloadNameTimes;
    }
}