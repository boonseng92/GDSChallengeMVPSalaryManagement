package com.gds.swe.challenge.repository;

import com.gds.swe.challenge.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, String> {

    Employee findBylogin(String login);
}
