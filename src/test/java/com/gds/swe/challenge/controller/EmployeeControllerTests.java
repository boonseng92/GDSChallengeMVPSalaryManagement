package com.gds.swe.challenge.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

@SpringBootTest
class EmployeeControllerTests {

    // Sample files to upload for test cases
    //final String fileDir = this.getClass().getResource(".").getPath() + "/";
    File currentDir = new File (".");
    String basePath = currentDir.getCanonicalPath() + "/src/test/resources/test-files/";
    @Autowired
    private WebApplicationContext employeeController;

    EmployeeControllerTests() throws IOException {
    }


    @Test
    @Order(1)
    /*
     * Test Case 01 Description: To test on uploading an empty file. No record will be inserted with an error message "Could not upload the file: " + filename
     */
    public void uploadEmptyfile() throws Exception {
        //upload file
        String fileName = "testfile1.csv";
        File file = new File(basePath + fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", file.getName(), "text/csv", fileInputStream);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();

        mockMvc.perform(MockMvcRequestBuilders.multipart("/users/upload").file(mockMultipartFile)).andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Could not upload the file: "+fileName+"!\"}"));
    }



    @Test
    @Order(2)
    /*
     * Test Case 02 Description: To test on uploading non CSV file. No record will be inserted with an error message "Please upload a csv file!"
     */
    public void uploadnonCSVfile() throws Exception {
        //upload file
        String fileName = "testfile2.txt";
        File file = new File(basePath + fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", file.getName(), "text/plain", fileInputStream);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/users/upload").file(mockMultipartFile)).andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Please upload a csv file!\"}"));
    }

    @Test
    @Order(3)
    /*
     * Test Case 03 Description: To test on uploading good test file and retrieving good test data e0001, e0002
     */
    public void uploadEmployeeGoodTestData() throws Exception {


        //upload file
        String fileName = "testfile3.csv";
        File file = new File(basePath + fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", file.getName(), "text/csv", fileInputStream);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/users/upload").file(mockMultipartFile)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Uploaded the file successfully: "+fileName+"\"}"));

        // get id e0001
        String getId = "e0001";
        mockMvc.perform(MockMvcRequestBuilders.get("/users?id=" + getId))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(getId)));

        // get id e0002
        getId = "e0002";
        mockMvc.perform(MockMvcRequestBuilders.get("/users?id=" + getId))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(getId)));

    }


    @Test
    @Order(4)
    /*
     * Test Case 04 Description: To test on uploading bad test file, e0001 has missing name. No record will be inserted with an error: "Could not upload the file:" + fileName
     */
    public void uploadEmployeewithMissingName() throws Exception {
        //upload file
        String fileName = "testfile4.csv";
        File file = new File(basePath + fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", file.getName(), "text/csv", fileInputStream);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/users/upload").file(mockMultipartFile)).andExpect(status().isExpectationFailed())
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Could not upload the file: "+fileName+"!\"}"));
    }

    @Test
    @Order(5)
    /*
     * Test Case 05 Description: To test on uploading bad test file, e0001 has extra column. No record will be inserted with an error: "Could not upload the file:" + fileName
     */
    public void uploadEmployeewithmorethan4column() throws Exception {
        //upload file
        String fileName = "testfile5.csv";
        File file = new File(basePath + fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", file.getName(), "text/csv", fileInputStream);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/users/upload").file(mockMultipartFile)).andExpect(status().isExpectationFailed())
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Could not upload the file: "+fileName+"!\"}"));
    }


    @Test
    @Order(6)
    /*
     * Test Case 06 Description: To test on uploading bad test file with salary more than 2 decimal places. No record will be inserted with an error: "Could not upload the file:" + fileName
     */
    public void uploadEmployeeSalaryGT2Decimal() throws Exception {


        //upload file
        String fileName = "testfile6.csv";
        File file = new File(basePath + fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", file.getName(), "text/csv", fileInputStream);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/users/upload").file(mockMultipartFile)).andExpect(status().isExpectationFailed())
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Could not upload the file: " + fileName + "!\"}"));
    }


    @Test
    @Order(7)
    /*
     * Test Case 07 Description: To test on uploading bad test file with salary 0 and 1, 2 decimal places. Record will be save successfully with a success message "Uploaded the file successfully:" + fileName
     */
    public void uploadEmployeeSalary012Decimal() throws Exception {


        //upload file
        String fileName = "testfile7.csv";
        File file = new File(basePath + fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", file.getName(), "text/csv", fileInputStream);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/users/upload").file(mockMultipartFile)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Uploaded the file successfully: "+fileName+"\"}"));
    }

    @Test
    @Order(8)
    /*
     * Test Case 08 Description: To test on uploading bad test file with negative salary value. No record will be inserted with an error: "Could not upload the file:" + fileName
     */
    public void uploadEmployeeNegativeSalary() throws Exception {


        //upload file
        String fileName = "testfile8.csv";
        File file = new File(basePath + fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", file.getName(), "text/csv", fileInputStream);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/users/upload").file(mockMultipartFile)).andExpect(status().isExpectationFailed())
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Could not upload the file: " + fileName + "!\"}"));
    }

    @Test
    @Order(9)
    /*
     * Test Case 09 Description: To test on uploading bad test file with non-numeric salary value. No record will be inserted with an error: "Could not upload the file:" + fileName
     */
    public void uploadEmployeeNonnumericSalary() throws Exception {


        //upload file
        String fileName = "testfile9.csv";
        File file = new File(basePath + fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", file.getName(), "text/csv", fileInputStream);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/users/upload").file(mockMultipartFile)).andExpect(status().isExpectationFailed())
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Could not upload the file: " + fileName + "!\"}"));
    }

    @Test
    @Order(10)
    /*
     * Test Case 10 Description: To test on uploading test file with #. Ensure that records that has # as first char are not skipped. Record will be save successfully with a success message "Uploaded the file successfully:" + fileName
     * To retrieve e0001 and e0002 where e0002 has # as first char
     */
    public void uploadEmployeeLogicWithHex() throws Exception {


        //upload file
        String fileName = "testfile10.csv";
        File file = new File(basePath + fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", file.getName(), "text/csv", fileInputStream);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/users/upload").file(mockMultipartFile)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Uploaded the file successfully: "+fileName+"\"}"));

        // get id e0001
        String getId = "e1001";
        mockMvc.perform(MockMvcRequestBuilders.get("/users?id=" + getId))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(getId)));

        // get id e0002
        getId = "e1002";
        mockMvc.perform(MockMvcRequestBuilders.get("/users?id=" + getId))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("null"));
    }

    @Test
    @Order(11)
    /*
     * Test Case 10 Description: To test on updating records from the previous successful upload. Record will be save successfully with a success message "Uploaded the file successfully:" + fileName
     * To retrieve e0001 and e0003 where the values will be updated.
     * e0001 salary: "1234.00" to "999.0" and name: "Harry Potter" to "Pot Potter"
     * e0003 salary: "4000.00" to "888.88" and name: "Severus Snape" to "Severus"
     */
    public void uploadEmployeeUpdateNameSalary() throws Exception {


        //upload file
        String fileName = "testfile11.csv";
        File file = new File(basePath + fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", file.getName(), "text/csv", fileInputStream);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/users/upload").file(mockMultipartFile)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Uploaded the file successfully: "+fileName+"\"}"));

        // get id e1001
        String getId = "e1001";
        mockMvc.perform(MockMvcRequestBuilders.get("/users?id=" + getId))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.salary", is(999.0)))
                .andExpect(jsonPath("$.name", is("Pot Potter")));

        // get id e1003
        getId = "e1003";
        mockMvc.perform(MockMvcRequestBuilders.get("/users?id=" + getId))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.salary", is(888.88)))
                .andExpect(jsonPath("$.name", is("Severus")));
    }

    @Test
    @Order(12)
    /*
     * Test Case 12 Description: To test on uploading bad test file with non-english characters. No record will be inserted with an error: "Could not upload the file:" + fileName
     */
    public void uploadEmployeeNonEnglish() throws Exception {


        //upload file
        String fileName = "testfile12.csv";
        File file = new File(basePath + fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", file.getName(), "text/csv", fileInputStream);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/users/upload").file(mockMultipartFile)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Uploaded the file successfully: "+fileName+"\"}"));

        // get id e0001
        String getId = "e0001";
        mockMvc.perform(MockMvcRequestBuilders.get("/users?id=" + getId))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.name", is("哈利波特")));
    }

    @Test
    @Order(13)
    /*
     * Test Case 13 Description: To test on uploading good test file testfile13 and bad test file testfile13-1 where there is duplicate loginid on another id
     */
    public void uploadEmployeeDuplicateLoginid() throws Exception {

        //upload file
        String fileName = "testfile13.csv";
        File file = new File(basePath + fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", file.getName(), "text/csv", fileInputStream);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/users/upload").file(mockMultipartFile)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Uploaded the file successfully: "+fileName+"\"}"));



        //upload file
        fileName = "testfile13-1.csv";
        file = new File(basePath + fileName);
        fileInputStream = new FileInputStream(file);
        mockMultipartFile = new MockMultipartFile("file", file.getName(), "text/csv", fileInputStream);
        mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/users/upload").file(mockMultipartFile)).andExpect(status().isExpectationFailed())
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Could not upload the file: " + fileName + "!\"}"));
    }

    @Test
    @Order(14)
    /*
     * Test Case 14 Description: To test on uploading bad test file where there is duplicate id in the same file upload
     */
    public void uploadEmployeeComplexUsecase() throws Exception {


    }

}
