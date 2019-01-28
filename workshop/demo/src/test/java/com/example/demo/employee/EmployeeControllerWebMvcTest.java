package com.example.demo.employee;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerWebMvcTest {
    
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private EmployeeService employeeService;
    
    private JacksonTester<EmployeeResponse> json;
  
    
    @Test
    public void shouldError() {
        
    }
    
    private List<Employee> mockData() {
        Employee e1 = new Employee(1, "name 1");
        Employee e2 = new Employee(2, "name 2");
        List<Employee> employees = new ArrayList<>(); 
        employees.add(e1);
        employees.add(e2);
        return employees;
    }

    @Test
    public void test() throws Exception {
        // Stub
        when(employeeService.listAll())
          .thenReturn(mockData());
        
        // Call controller
        MockHttpServletResponse response =
                mvc.perform(get("/employee"))
                   .andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), 
                response.getStatus());
        
        // Working with response
        JacksonTester.initFields(this, new ObjectMapper());
        EmployeeResponse employeeResponse =
                json.parseObject(response.getContentAsString());
        assertEquals(200, employeeResponse.getCode());
        assertEquals(2, employeeResponse.getEmployees().size());    
        
    }

}
