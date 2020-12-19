package com.gds.swe.challenge.Service;

import java.util.ArrayList;
import java.util.List;

import com.gds.swe.challenge.model.Employee;
import com.gds.swe.challenge.repository.EmployeeRepo;
import com.gds.swe.challenge.validator.CSVvalidator;
import com.gds.swe.challenge.validator.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@Transactional
public class FileServiceImpl implements FileStorageService {


    @Autowired
    EmployeeRepo repository;

    @Override
    public synchronized boolean save(MultipartFile file) {

        try {
            try {
                System.out.println("FileStorageService Start");
                List<Employee> employees = CSVvalidator.csvToString(file.getInputStream(), repository);
                if(employees.isEmpty())
                {
                    return false;
                }

                repository.saveAll(employees);
                System.out.println("FileStorageService save");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("fail to store csv data: " + e.getMessage());
            }

        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }



    public List<Employee> getAllEmployeeInfo() {
        return (List<Employee>) repository.findAll();
    }

    @Override
    public List<Employee> getEmployeeInfo(Double minSalary, Double maxSalary, Integer offset, Integer limit, String sortSymbol, String sortColumn) {

        List<Employee> employees;

        if(sortSymbol.matches("[+]")){
            employees = repository.findAllBySalaryGreaterThanEqualAndSalaryLessThanEqual(minSalary, maxSalary, new Pagination(offset, limit, Sort.by(sortColumn).ascending()));
        }else{
            employees = repository.findAllBySalaryGreaterThanEqualAndSalaryLessThanEqual(minSalary, maxSalary, new Pagination(offset, limit, Sort.by(sortColumn).descending()));
        }

        return employees;
    }

}
