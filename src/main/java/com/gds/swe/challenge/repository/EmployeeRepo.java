package com.gds.swe.challenge.repository;

import com.gds.swe.challenge.model.FileInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends CrudRepository<FileInfo, Long> {

    FileInfo findBylogin(String Id);
}
