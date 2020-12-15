package com.gds.swe.challenge.controller;


import com.gds.swe.challenge.Service.FileStorageService;
import com.gds.swe.challenge.validator.ResponseMessage;
import com.gds.swe.challenge.model.FileInfo;
import helper.CSVvalidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@CrossOrigin("http://localhost:4200")
@RequestMapping("/users")
public class EmployeeController {

    @Autowired
    FileStorageService fileService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@Valid @RequestBody @RequestParam("file") MultipartFile file) {
        String message;

        if (CSVvalidator.hasCSVFormat(file)) {
            try {
                System.out.println("Start Upload");
                fileService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getAllEmployeeInfo() {
        try {
            List<FileInfo> fileInfos = fileService.getAllEmployeeInfo();

            if (fileInfos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(fileInfos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
