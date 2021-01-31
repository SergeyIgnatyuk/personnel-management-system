package com.controller;

import com.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
                        .format(employee.getDateOfEmployment()))));
    }

    @Test
    public void givenConstraintViolationException_whenGetOneEmployeeById_thenReturnJson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", Matchers.is("Constraint Violation")))
                .andExpect(jsonPath("$.errors[0]", Matchers.is("getOneEmployeeById.id: must be greater than or equal to 1")));
    }

    @Test
    public void givenResourceNotFoundException_whenGetEmployeeById_thenReturnJson() throws Exception {
        when(employeeService.getOneEmployeeById(1L)).thenThrow(new ResourceNotFoundException("Employee with ID: 1 Not Found!"));

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", Matchers.is("Resource Not Found")))
                .andExpect(jsonPath("$.errors[0]", Matchers.is("Employee with ID: 1 Not Found!")));
    }

    @Test
    public void whenCreateEmployee_thenReturnJson() throws Exception {
        Employee employee = Employee.builder()
                .fullName("Petya Petrov")
                .dateOfBirth(new GregorianCalendar(1989, Calendar.AUGUST, 7).getTime())
                .phoneNumber("+37529-532-65-31")
                .emailAddress("ppetrov89@gmail.com")
                .position("Java Developer")
                .dateOfEmployment(new GregorianCalendar(2016, Calendar.OCTOBER, 14).getTime())
                .departmentId(2L)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(employee)))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenMethodArgumentNotValidException_whenCreateEmployee_thenReturnJson() throws Exception {
        Employee employee = Employee.builder()
                .fullName("")
                .dateOfBirth(new GregorianCalendar(1989, Calendar.AUGUST, 7).getTime())
                .phoneNumber("+37529-532-65-31")
                .emailAddress("ppetrov89@gmail.com")
                .position("Java Developer")
                .dateOfEmployment(new GregorianCalendar(2016, Calendar.OCTOBER, 14).getTime())
                .departmentId(2L)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]", Matchers.is("Full name must be not empty")));
    }

    @Test
    public void whenDeleteEmployeeById_thenReturnJson() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", String.valueOf(1L));

        mockMvc.perform(delete("/employees/1")
                .contentType(MediaType.APPLICATION_JSON).params(params))
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenConstraintViolationException_whenDeleteEmployeeById_thenReturnJson() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", String.valueOf(0L));

        mockMvc.perform(delete("/employees/0")
                .contentType(MediaType.APPLICATION_JSON).params(params))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", Matchers.is("Constraint Violation")))
                .andExpect(jsonPath("$.errors[0]", Matchers.is("deleteEmployeeById.id: must be greater than or equal to 1")));
    }

    @Test
    public void givenResourceNotFoundException_whenDeleteEmployeeById_thenReturnJson() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", String.valueOf(1L));

        doThrow(new ResourceNotFoundException("Employee with ID: 1 Not Found!")).when(employeeService).deleteEmployeeById(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/1")
                .contentType(MediaType.APPLICATION_JSON).params(params))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", Matchers.is("Resource Not Found")))
                .andExpect(jsonPath("$.errors[0]", Matchers.is("Employee with ID: 1 Not Found!")));
    }
}