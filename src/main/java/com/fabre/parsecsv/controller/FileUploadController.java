package com.fabre.parsecsv.controller;

import com.fabre.parsecsv.model.Decision;
import com.fabre.parsecsv.service.DecisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
public class FileUploadController {


    @Autowired
    private DecisionService decisionService;

    @PostMapping("/upload")
    public List<Decision> fileUpload(@RequestParam("file") MultipartFile file) {

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
