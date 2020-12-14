package com.gds.swe.challenge.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.gds.swe.challenge.model.FileInfo;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {


    public void save(MultipartFile file);

    public ByteArrayInputStream load();

    public List<FileInfo> getAllEmployeeInfo();

}
