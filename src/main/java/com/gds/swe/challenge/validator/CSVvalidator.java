package com.gds.swe.challenge.validator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.gds.swe.challenge.model.Employee;
import com.gds.swe.challenge.repository.EmployeeRepo;
import org.springframework.web.multipart.MultipartFile;

public class CSVvalidator {
    static String TYPE = "text/csv";
    static String[] HEADERs = { "Id", "Login", "Name", "Salary" };
    static String cvsSplitBy = ",";

    public static boolean hasCSVFormat(MultipartFile file) {

        boolean result = false;

        if(TYPE.equals(file.getContentType())){
            result = true;
        }
        else if (file.getOriginalFilename().substring(file.getOriginalFilename().length() - 3).equals("csv")) {
            result = true;
        }

        return result;
    }

    public static List<Employee> csvToString(InputStream is, EmployeeRepo repository) {
        List<Employee> employees = new ArrayList<>();
        try
        {
            String line;

            BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            //Skip first line
            line = fileReader.readLine();
            if(!line.isEmpty()) {
                while ((line = fileReader.readLine()) != null) {
                    char firstChar = line.charAt(0);
                    //Check for # first char in every line
                    if (firstChar != '#') {
                        if (validateRecord(line, repository)) {
                            //Split record into individual data
                            String[] data = line.split(cvsSplitBy);

                            Employee employee = new Employee(
                                    data[0],
                                    data[1],
                                    data[2],
                                    Double.parseDouble(data[3]));

                            employees.add(employee);
                        }else{
                            throw new RuntimeException("Failed to validate record.");
                        }
                    }
                }
            }
            return employees;

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return employees;
    }

    //Record Validation
    private static boolean validateRecord(String record, EmployeeRepo repository){
        boolean result = false;
        String[] data = record.split(cvsSplitBy);
        //Every field has to have values
        if(data.length == 4){
            //Salary Validation
            //Check Decimal places lesser than 2
            if(BigDecimal.valueOf( Double.parseDouble(data[3])).scale() < 3){

                //Check negative salary
                if(Double.compare(Double.parseDouble(data[3]), 0.0) > 0){
                    Employee employee = repository.findBylogin(data[1]);
                    //Check existing Login
                    if(employee == null){
                        result = true;
                    }
                    //Check existing ID if login is true
                    else if (employee.getId().equals(data[0])) {
                        result = true;
                    }

                }
            }
        }

        return result;
    }

}
