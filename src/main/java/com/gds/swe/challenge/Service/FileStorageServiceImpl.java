package com.gds.swe.challenge.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.gds.swe.challenge.model.FileInfo;
import com.gds.swe.challenge.repository.EmployeeRepo;
import helper.CSVvalidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Autowired
    EmployeeRepo repository;


    @Override
    public void save(MultipartFile file) {
        try {
            try {
                List<FileInfo> tutorials = CSVvalidator.csvToTutorials(file.getInputStream());
                repository.saveAll(tutorials);
            } catch (IOException e) {
                throw new RuntimeException("fail to store csv data: " + e.getMessage());
            }

        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public ByteArrayInputStream load() {
        List<FileInfo> fileInfos = (List<FileInfo>) repository.findAll();

        ByteArrayInputStream in = CSVvalidator.tutorialsToCSV(fileInfos);
        return in;
    }

    public List<FileInfo> getAllEmployeeInfo() {
        return (List<FileInfo>) repository.findAll();
    }
    }


