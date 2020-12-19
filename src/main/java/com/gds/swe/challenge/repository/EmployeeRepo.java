package com.gds.swe.challenge.repository;

import com.gds.swe.challenge.model.Employee;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, String> {

    Employee findBylogin(String login);

    List<Employee> findAllBySalaryGreaterThanEqualAndSalaryLessThanEqual(double minSalary, double maxSalary, Pageable pageable);

}
