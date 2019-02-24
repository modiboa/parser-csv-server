package com.fabre.parsecsv.controller;

import com.fabre.parsecsv.service.DecisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
public class FileUploadController {


    @Autowired
    private DecisionService decisionService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File parameter is empty");
        }

        try {

            byte[] bytes = file.getBytes();
            return decisionService.prepare(bytes);

        } catch (IOException e) {
            throw new IllegalArgumentException("Error with process CSV file");
        }
    }

}
