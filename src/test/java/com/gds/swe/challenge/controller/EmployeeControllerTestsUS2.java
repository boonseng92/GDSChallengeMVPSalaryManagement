package com.gds.swe.challenge.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gds.swe.challenge.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
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
        Double maxSalary = 1000.0;
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
        Double maxSalary = 1000.00;
        Integer offset = 0;
        Integer limit = 30;
        String sort = "+idname";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isBadRequest());

    }


    @Test
    @Order(16)
    /*
     * Test Case 16 Description: To test Salary range for minSalary equals maxSalary
     * Return 200 OK Request.
     */
    public void getEmployeeMinSalaryEqualsMaxSalary() throws Exception {

        Double minSalary = 1234.0;
        Double maxSalary = 1234.0;
        Integer offset = 0;
        Integer limit = 30;
        String sort = "+id";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));

    }

    @Test
    @Order(17)
    /*
     * Test Case 17 Description: To test Salary range for maxSalary greater than minSalary
     * Return 200 OK Request.
     */
    public void getEmployeeMaxSalaryGTMinSalary() throws Exception {

        Double minSalary = 100.00;
        Double maxSalary = 1000.00;
        Integer offset = 0;
        Integer limit = 30;
        String sort = "+id";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));

    }


    @Test
    @Order(18)
    /*
     * Test Case 18 Description: To set offset to 0 and limit to 30. To retrieve employess from 1st row to 30 row.
     * Return 200 OK Request.
     */
    public void getEmployeeOffsetSetTo1LimitTo30() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        Double minSalary = 100.00;
        Double maxSalary = 40000.00;
        Integer offset = 0;
        Integer limit = 30;
        String sort = "+id";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<Employee> employeeList = objectMapper.readValue(contentAsString, new TypeReference<List<Employee>>(){});

        assertTrue(employeeList.size()==30);
        assertTrue(employeeList.get(0).getId().equals("e0001"));
        assertTrue(employeeList.get(employeeList.size()-1).getId().equals("e0030"));

    }

    @Test
    @Order(19)
    /*
     * Test Case 19 Description: To change offset to 1. To retrieve employess from 2nd row to 31 row.
     * Return 200 OK Request.
     */
    public void getEmployeeOffsetSetTo1() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        Double minSalary = 100.00;
        Double maxSalary = 40000.00;
        Integer offset = 1;
        Integer limit = 30;
        String sort = "+id";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<Employee> employeeList = objectMapper.readValue(contentAsString, new TypeReference<List<Employee>>(){});

        assertTrue(employeeList.size()==30);
        assertTrue(employeeList.get(0).getId().equals("e0002"));
        assertTrue(employeeList.get(employeeList.size()-1).getId().equals("e0031"));

    }

    @Test
    @Order(20)
    /*
     * Test Case 20 Description: To change limit to 5. To retrieve employess from 1st row to 5 row.
     * Return 200 OK Request.
     */
    public void getEmployeeLimitSetTo5() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        Double minSalary = 100.00;
        Double maxSalary = 40000.00;
        Integer offset = 0;
        Integer limit = 5;
        String sort = "+id";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<Employee> employeeList = objectMapper.readValue(contentAsString, new TypeReference<List<Employee>>(){});

        assertTrue(employeeList.size()==5);
        assertTrue(employeeList.get(0).getId().equals("e0001"));
        assertTrue(employeeList.get(employeeList.size()-1).getId().equals("e0005"));

    }

    @Test
    @Order(21)
    /*
     * Test Case 21 Description: To retrieve by Id in Ascending order with limit 30 and offset 0
     * Return 200 OK Request.
     */
    public void getEmployeeByIdAsc() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        Double minSalary = 0.0;
        Double maxSalary = 40000.00;
        Integer offset = 0;
        Integer limit = 30;
        String sort = "+id";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<Employee> employeeList = objectMapper.readValue(contentAsString, new TypeReference<List<Employee>>(){});

        assertTrue(employeeList.size()==30);
        assertTrue(employeeList.get(0).getId().equals("e0001"));
        assertTrue(employeeList.get(employeeList.size()-1).getId().equals("e0030"));

    }

    @Test
    @Order(22)
    /*
     * Test Case 22 Description: To retrieve by Id in Descending order with limit 30 and offset 0
     * Return 200 OK Request.
     */
    public void getEmployeeByIdDes() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        Double minSalary = 0.0;
        Double maxSalary = 40000.00;
        Integer offset = 0;
        Integer limit = 30;
        String sort = "-id";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<Employee> employeeList = objectMapper.readValue(contentAsString, new TypeReference<List<Employee>>(){});

        assertTrue(employeeList.size()==30);
        assertTrue(employeeList.get(0).getId().equals("e0065"));
        assertTrue(employeeList.get(employeeList.size()-1).getId().equals("e0036"));

    }

    @Test
    @Order(23)
    /*
     * Test Case 23 Description: To retrieve by Name in Ascending order with limit 30 and offset 0
     * Return 200 OK Request.
     */
    public void getEmployeeByNameAsc() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        Double minSalary = 0.0;
        Double maxSalary = 40000.00;
        Integer offset = 0;
        Integer limit = 30;
        String sort = "+name";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<Employee> employeeList = objectMapper.readValue(contentAsString, new TypeReference<List<Employee>>(){});

        assertTrue(employeeList.size()==30);
        assertTrue(employeeList.get(0).getName().equals("Albus Dumbledore"));
        assertTrue(employeeList.get(employeeList.size()-1).getName().equals("Harry Potter"));

    }

    @Test
    @Order(24)
    /*
     * Test Case 24 Description: To retrieve by Name in Descending order with limit 30 and offset 0
     * Return 200 OK Request.
     */
    public void getEmployeeByNameDes() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        Double minSalary = 0.0;
        Double maxSalary = 40000.00;
        Integer offset = 0;
        Integer limit = 30;
        String sort = "-name";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<Employee> employeeList = objectMapper.readValue(contentAsString, new TypeReference<List<Employee>>(){});

        assertTrue(employeeList.size()==30);
        assertTrue(employeeList.get(0).getName().equals("Severus Snape"));
        assertTrue(employeeList.get(employeeList.size()-1).getName().equals("Hermione Granger"));

    }

    @Test
    @Order(25)
    /*
     * Test Case 25 Description: To retrieve by login in Ascending order with limit 30 and offset 0
     * Return 200 OK Request.
     */
    public void getEmployeeByLoginAsc() throws Exception {
        setupDatabase();
        ObjectMapper objectMapper = new ObjectMapper();
        Double minSalary = 0.0;
        Double maxSalary = 40000.00;
        Integer offset = 0;
        Integer limit = 30;
        String sort = "+login";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<Employee> employeeList = objectMapper.readValue(contentAsString, new TypeReference<List<Employee>>(){});

        assertTrue(employeeList.size()==30);
        assertTrue(employeeList.get(0).getLogin().equals("adumbledore51"));
        assertTrue(employeeList.get(employeeList.size()-1).getLogin().equals("hpotter10"));

    }

    @Test
    @Order(26)
    /*
     * Test Case 26 Description: To retrieve by login in Descending order with limit 30 and offset 0
     * Return 200 OK Request.
     */
    public void getEmployeeByLoginDes() throws Exception {
        setupDatabase();
        ObjectMapper objectMapper = new ObjectMapper();
        Double minSalary = 0.0;
        Double maxSalary = 40000.00;
        Integer offset = 0;
        Integer limit = 30;
        String sort = "-login";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<Employee> employeeList = objectMapper.readValue(contentAsString, new TypeReference<List<Employee>>(){});

        assertTrue(employeeList.size()==30);
        assertTrue(employeeList.get(0).getLogin().equals("voldemort8"));
        assertTrue(employeeList.get(employeeList.size()-1).getLogin().equals("hpotter35"));

    }

    @Test
    @Order(27)
    /*
     * Test Case 27 Description: To retrieve by salary in Ascending order with limit 30 and offset 0
     * Return 200 OK Request.
     */
    public void getEmployeeBySalaryAsc() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        Double minSalary = 0.0;
        Double maxSalary = 40000.00;
        Integer offset = 0;
        Integer limit = 30;
        String sort = "+salary";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<Employee> employeeList = objectMapper.readValue(contentAsString, new TypeReference<List<Employee>>(){});

        assertTrue(employeeList.size()==30);
        assertTrue(employeeList.get(0).getSalary() == 0.0);
        assertTrue(employeeList.get(employeeList.size()-1).getSalary() == 523.4);

    }

    @Test
    @Order(28)
    /*
     * Test Case 28 Description: To retrieve by salary in Descending order with limit 30 and offset 0
     * Return 200 OK Request.
     */
    public void getEmployeeBySalaryDes() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        Double minSalary = 0.0;
        Double maxSalary = 40000.00;
        Integer offset = 0;
        Integer limit = 30;
        String sort = "-salary";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<Employee> employeeList = objectMapper.readValue(contentAsString, new TypeReference<List<Employee>>(){});

        assertTrue(employeeList.size()==30);
        assertTrue(employeeList.get(0).getSalary() == 34234.5);
        assertTrue(employeeList.get(employeeList.size()-1).getSalary() == 1234.0);

    }

    @Test
    @Order(29)
    /*
     * Test Case 29 Description: To test more than 5 parameters. Pass in 6 parameters
     * Return 400 Bad Request.
     */
    public void getEmployeePass6Parameters() throws Exception {

        Double minSalary = 0.0;
        Double maxSalary = 40000.00;
        Integer offset = 0;
        Integer limit = 30;
        String sort = "-salary";

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit + "&sort=" + sort + "&test=test"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(30)
    /*
     * Test Case 30 Description: To test less than 5 parameters. Pass in 4 parameters
     * Return 400 Bad Request.
     */
    public void getEmployeePass4Parameters() throws Exception {

        Double minSalary = 0.0;
        Double maxSalary = 40000.00;
        Integer offset = 0;
        Integer limit = 30;

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(employeeController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/users?minSalary=" + minSalary + "&maxSalary=" + maxSalary + "&offset=" + offset + "&limit=" + limit))
                .andExpect(status().isBadRequest());
    }
}
