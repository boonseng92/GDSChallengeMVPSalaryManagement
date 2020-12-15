package com.gds.swe.challenge.repository;

import com.gds.swe.challenge.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<FileInfo, Long> {

    FileInfo findBylogin(String Id);
}
