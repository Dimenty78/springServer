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

    String downloadFolderPath = "d:\\Temp\\+\\";

    @RequestMapping(value = "/uploadMultipleFiles", method = RequestMethod.POST)
    @ResponseBody
    public void uploadFile(@RequestParam("files") MultipartFile[] files) {
        fileName = null;
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {

                    dir = new File(downloadFolderPath);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    byte[] bytes = file.getBytes();
                    fileName = file.getOriginalFilename();
                    uploadedFile = new File(dir.getAbsolutePath() + File.separator + fileName);
                    stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
                    stream.write(bytes);
                    stream.flush();
                    stream.close();

                    System.out.println("Успешно передан файл: " + fileName);

                } catch (Exception e) {
                    System.out.println("Не передан файл " + fileName + " => " + e.getMessage());
                }
            } else {
                System.out.println("Вы не смогли передать файл  " + fileName + " потому что файл был пуст");
            }
        }
    }
}