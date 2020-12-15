package com.gds.swe.challenge.Service;


import com.gds.swe.challenge.model.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {


     void save(MultipartFile file);

     List<FileInfo> getAllEmployeeInfo();

}
