package com.gds.swe.challenge.Service;


import com.gds.swe.challenge.model.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {


     boolean save(MultipartFile file);

     List<Employee> getAllEmployeeInfo();

}
