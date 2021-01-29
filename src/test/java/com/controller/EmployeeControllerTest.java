package com.controller;

import com.model.Employee;
import com.service.EmployeeService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integrations tests of {@link EmployeeController}.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void givenEmployee_whenGetOneEmployeeById_thenReturnJson() throws Exception {
        Employee employee = Employee.builder()
                .id(1L)
                .fullName("Harry Potter")
                .dateOfBirth(new Date())
                .phoneNumber("9-3/4")
                .emailAddress("harryPotter@gmail.com")
                .position("magician")
                .dateOfEmployment(new Date())
                .departmentId(1L)
                .build();

        when(employeeService.getOneEmployeeById(1L)).thenReturn(employee);

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(employee.getId().intValue())))
                .andExpect(jsonPath("$.fullName", Matchers.is(employee.getFullName())))
                .andExpect(jsonPath("$.dateOfBirth", Matchers.is(new SimpleDateFormat("yyyy-MM-dd")
                        .format(employee.getDateOfBirth()))))
                .andExpect(jsonPath("$.phoneNumber", Matchers.is(employee.getPhoneNumber())))
                .andExpect(jsonPath("$.emailAddress", Matchers.is(employee.getEmailAddress())))
                .andExpect(jsonPath("$.position", Matchers.is(employee.getPosition())))
                .andExpect(jsonPath("$.dateOfEmployment", Matchers.is(new SimpleDateFormat("yyyy-MM-dd")
                        .format(employee.getDateOfEmployment()))))
                .andExpect(jsonPath("$.departmentId", Matchers.is(employee.getDepartmentId().intValue())));
    }

    @Test
    public void givenConstraintViolationException_whenGetOneEmployeeById_thenReturnJson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", Matchers.is("Constraint Violation")))
                .andExpect(jsonPath("$.errors[0]", Matchers.is("getOneEmployeeById.id: must be greater than or equal to 1")));
    }
}