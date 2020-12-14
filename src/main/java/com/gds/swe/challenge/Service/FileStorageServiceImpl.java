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
    public synchronized void save(MultipartFile file) {
        try {
            try {
                System.out.println("FileStorageService Start");
                List<FileInfo> fileinfos = CSVvalidator.csvToString(file.getInputStream(), repository);
                System.out.println("FileStorageService Validate");
                repository.saveAll(fileinfos);
                System.out.println("FileStorageService save");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new RuntimeException("fail to store csv data: " + e.getMessage());
            }

        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }



    public List<FileInfo> getAllEmployeeInfo() {
        return (List<FileInfo>) repository.findAll();
    }

}
