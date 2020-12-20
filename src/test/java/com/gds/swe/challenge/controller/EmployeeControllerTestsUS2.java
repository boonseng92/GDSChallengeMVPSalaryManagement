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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class EmployeeControllerTestsUS2 {

    //final String fileDir = this.getClass().getResource(".").getPath() + "/";
    File currentDir = new File (".");
    String basePath = currentDir.getCanonicalPath() + "/src/test/resources/test-files/";
    @Autowired
    private WebApplicationContext employeeController;

    EmployeeControllerTestsUS2() throws IOException {
    }


    @Test
    @Order(1)
    /*
     * Test Case 01 Description: To set up database for subsequent test cases
     */
    public void setupDatabase() throws Exception {
        //upload file
        String fileName = "testfileUS2-1.csv";
        File file = new File(basePath + fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", file.getName(), "text/csv", fileInputStream);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/users/upload").file(mockMultipartFile)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Uploaded the file successfully: "+fileName+"\"}"));

    }


    @Test
    @Order(2)
    /*
     * Test Case 02 Description: To test null value for minSalary.
     * Return 400 Bad Request.
     */
    public void getEmployeeNullOnMinSalary() throws Exception {

        Double minSalary = null;
        Double maxSalary = 100.00;
        Integer offset = 0;
        Integer limit = 30;
        String sort = "+name";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Order(3)
    /*
     * Test Case 03 Description: To test null value for maxSalary
     * Return 400 Bad Request.
     */
    public void getEmployeeNullOnMaxSalary() throws Exception {

        Double minSalary = 100.00;
        Double maxSalary = null;
        Integer offset = 0;
        Integer limit = 30;
        String sort = "+name";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Order(4)
    /*
     * Test Case 04 Description: To test null value for offset
     * Return 400 Bad Request.
     */
    public void getEmployeeNullOnOffset() throws Exception {

        Double minSalary = 100.00;
        Double maxSalary = 1000.00;
        Integer offset = null;
        Integer limit = 30;
        String sort = "+name";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Order(5)
    /*
     * Test Case 05 Description: To test null value for limit
     * Return 400 Bad Request.
     */
    public void getEmployeeNullOnLimit() throws Exception {

        Double minSalary = 100.00;
        Double maxSalary = 1000.00;
        Integer offset = 0;
        Integer limit = null;
        String sort = "+name";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Order(6)
    /*
     * Test Case 06 Description: To test null value for sort
     * Return 400 Bad Request.
     */
    public void getEmployeeNullOnSort() throws Exception {

        Double minSalary = 100.00;
        Double maxSalary = 1000.00;
        Integer offset = 0;
        Integer limit = 30;
        String sort = null;

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Order(7)
    /*
     * Test Case 07 Description: To test minSalary is greater than maxSalary
     * Return 400 Bad Request.
     */
    public void getEmployeeMinSalaryGTMaxSalary() throws Exception {

        Double minSalary = 1000.00;
        Double maxSalary = 100.00;
        Integer offset = 0;
        Integer limit = 30;
        String sort = "+name";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Order(8)
    /*
     * Test Case 08 Description: To test offset cannot be lesser than 0
     * Return 400 Bad Request.
     */
    public void getEmployeeOffsetLT0() throws Exception {

        Double minSalary = 100.00;
        Double maxSalary = 1000.00;
        Integer offset = -1;
        Integer limit = 30;
        String sort = "+name";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Order(9)
    /*
     * Test Case 09 Description: To test limit cannot be lesser than 1
     * Return 400 Bad Request.
     */
    public void getEmployeeLimitLT1() throws Exception {

        Double minSalary = 100.00;
        Double maxSalary = 1000.00;
        Integer offset = 0;
        Integer limit = 0;
        String sort = "+name";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Order(10)
    /*
     * Test Case 10 Description: To test minSalary on negative value
     * Return 400 Bad Request.
     */
    public void getEmployeeMinSalaryNegative() throws Exception {

        Double minSalary = -100.00;
        Double maxSalary = 1000.00;
        Integer offset = 0;
        Integer limit = 0;
        String sort = "+name";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Order(11)
    /*
     * Test Case 11 Description: To test minSalary on non-numeric value
     * Return 400 Bad Request.
     */
    public void getEmployeeMinSalaryNonNumeric() throws Exception {

        String minSalary = "abc";
        Double maxSalary = 100.00;
        Integer offset = 0;
        Integer limit = 0;
        String sort = "+name";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Order(12)
    /*
     * Test Case 12 Description: To test maxSalary on negative value
     * Return 400 Bad Request.
     */
    public void getEmployeeMaxSalaryNegative() throws Exception {

        Double minSalary = 100.00;
        Double maxSalary = -1000.00;
        Integer offset = 0;
        Integer limit = 0;
        String sort = "+name";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Order(13)
    /*
     * Test Case 13 Description: To test maxSalary on non-numeric value
     * Return 400 Bad Request.
     */
    public void getEmployeeMaxSalaryNonNumeric() throws Exception {

        Double minSalary = 100.00;
        String maxSalary = "abc";
        Integer offset = 0;
        Integer limit = 0;
        String sort = "+name";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Order(14)
    /*
     * Test Case 14 Description: To test sort does not accept other symbol other than +/-
     * Return 400 Bad Request.
     */
    public void getEmployeeSortWithSpecialSymbol() throws Exception {

        Double minSalary = 100.00;
        String maxSalary = "abc";
        Integer offset = 0;
        Integer limit = 0;
        String sort = "@name";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Order(15)
    /*
     * Test Case 15 Description: To test sort only accepts 1 column name
     * Return 400 Bad Request.
     */
    public void getEmployeeSortWithMoreThan1Column() throws Exception {

        Double minSalary = 100.00;
        String maxSalary = "abc";
        Integer offset = 0;
        Integer limit = 0;
        String sort = "+idname";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isBadRequest());

    }


}
