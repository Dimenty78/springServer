package com.example.sweater;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Controller
public class FileController {

    String fileName;
    File dir, uploadedFile;
    BufferedOutputStream stream;

    String downloadFolderPath = "\\\\2x2_obrabotka\\ЗАПИСИ МВ СЕТЕВЫХ_Java\\";

    @RequestMapping(value = "/uploadMultipleFiles", method = RequestMethod.POST)
    @ResponseBody
    public void uploadFile(@RequestParam("files") MultipartFile[] files) {
        fileName = null;
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    fileName = file.getOriginalFilename();

                    //Проверка имени приходимого файла на наличие 3х частей разделенных _
                    if (fileName.split("_").length == 3) {
                        dir = new File(downloadFolderPath + fileName.split("_")[0] + "\\" + fileName.split("_")[1] + "\\");
                    } else {
                        dir = new File(downloadFolderPath + "OverFiles\\");
                    }

                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    byte[] bytes = file.getBytes();
                    uploadedFile = new File(dir.getAbsolutePath() + File.separator + fileName);
                    stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
                    stream.write(bytes);
                    stream.flush();
                    stream.close();
                    //System.out.println("Успешно принят файл: " + fileName);

                } catch (Exception e) {
                    System.out.println("Не ппринят файл " + fileName + " => " + e.getMessage());
                }
            } else {
                System.out.println("Вы не смогли передать файл  " + fileName + " потому что файл был пуст");
            }
        }
    }
}