package com.gds.swe.challenge.controller;


import com.gds.swe.challenge.Service.FileStorageService;
import com.gds.swe.challenge.model.Employee;
import com.gds.swe.challenge.repository.EmployeeRepo;
import com.gds.swe.challenge.validator.ResponseMessage;
import helper.CSVvalidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;


@Controller
@CrossOrigin("http://localhost:4200")
@RequestMapping("/users")
@Validated
public class EmployeeController {

    @Autowired
    FileStorageService fileService;

    @Autowired
    EmployeeRepo employeeRepo;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@Valid @RequestBody @RequestParam("file") MultipartFile file) {
        String message;
        if (CSVvalidator.hasCSVFormat(file)) {
            try {
                System.out.println("Start Upload");
                boolean result = fileService.save(file);
                if(result) {
                    message = "Uploaded the file successfully: " + file.getOriginalFilename();
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
                } else {
                    message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
                }


            } catch (Exception e) {

                    message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @ResponseBody
    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public Optional<Employee> getUser(@NotBlank @RequestParam("id") String id){
        return employeeRepo.findById(id);
    }

    @GetMapping("/files")
    public ResponseEntity<List<Employee>> getAllEmployeeInfo() {
        try {
            List<Employee> employees = fileService.getAllEmployeeInfo();

            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
