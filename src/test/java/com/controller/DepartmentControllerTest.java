package com.controller;


import com.model.Department;
import com.model.Employee;
import com.service.DepartmentService;
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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integrations tests of {@link DepartmentController}.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DepartmentService departmentService;

    @Test
    public void givenDepartments_whenGetAllDepartmentsWithTheirEmployees_thenReturnJsonArray() throws Exception {
        Department departmentWithOneEmployee = Department.builder()
                .id(1L)
                .name("Magician Department")
                .description("Magician Department")
                .phoneNumber("111-111")
                .dateOfFormation(new Date())
                .employees(
                        Stream.of(Employee.builder()
                                .id(1L)
                                .fullName("Harry Potter")
                                .dateOfBirth(new Date())
                                .phoneNumber("9-3/4")
                                .emailAddress("harryPotter@gmail.com")
                                .position("magician")
                                .dateOfEmployment(new Date())
                                .departmentId(1L)
                                .build()).collect(Collectors.toSet()))
                .build();

        List<Department> departmentList = Stream.of(departmentWithOneEmployee).collect(Collectors.toList());

        Department firstDepartmentFromList = departmentList.get(0);

        Employee firstEmployeeFromList = firstDepartmentFromList.getEmployees().stream().findFirst().get();

        when(departmentService.getAllDepartmentsWithTheirEmployees()).thenReturn(departmentList);

        mockMvc.perform(MockMvcRequestBuilders.get("/departments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(firstDepartmentFromList.getId().intValue())))
                .andExpect(jsonPath("$[0].name", Matchers.is(firstDepartmentFromList.getName())))
                .andExpect(jsonPath("$[0].description", Matchers.is(firstDepartmentFromList.getDescription())))
                .andExpect(jsonPath("$[0].phoneNumber", Matchers.is(firstDepartmentFromList.getPhoneNumber())))
                .andExpect(jsonPath("$[0].dateOfFormation", Matchers.is(new SimpleDateFormat("yyyy-MM-dd").format(firstDepartmentFromList.getDateOfFormation()))))
                .andExpect(jsonPath("$[0].employees[0].id", Matchers.is(firstEmployeeFromList.getId().intValue())))
                .andExpect(jsonPath("$[0].employees[0].fullName", Matchers.is(firstEmployeeFromList.getFullName())))
                .andExpect(jsonPath("$[0].employees[0].dateOfBirth", Matchers.is(new SimpleDateFormat("yyyy-MM-dd").format(firstEmployeeFromList.getDateOfBirth()))))
                .andExpect(jsonPath("$[0].employees[0].phoneNumber", Matchers.is(firstEmployeeFromList.getPhoneNumber())))
                .andExpect(jsonPath("$[0].employees[0].emailAddress", Matchers.is(firstEmployeeFromList.getEmailAddress())))
                .andExpect(jsonPath("$[0].employees[0].position", Matchers.is(firstEmployeeFromList.getPosition())))
                .andExpect(jsonPath("$[0].employees[0].dateOfEmployment", Matchers.is(new SimpleDateFormat("yyyy-MM-dd").format(firstEmployeeFromList.getDateOfEmployment()))))
                .andExpect(jsonPath("$[0].employees[0].departmentId", Matchers.is(firstEmployeeFromList.getDepartmentId().intValue())));
    }

    @Test
    public void givenDepartment_whenGetOneDepartmentById_thenReturnJson() throws Exception {
        Department departmentWithOneEmployee = Department.builder()
                .id(1L)
                .name("Magician Department")
                .description("Magician Department")
                .phoneNumber("111-111")
                .dateOfFormation(new Date())
                .employees(
                        Stream.of(Employee.builder()
                                .id(1L)
                                .fullName("Harry Potter")
                                .dateOfBirth(new Date())
                                .phoneNumber("9-3/4")
                                .emailAddress("harryPotter@gmail.com")
                                .position("magician")
                                .dateOfEmployment(new Date())
                                .departmentId(1L)
                                .build()).collect(Collectors.toSet()))
                .build();

        Employee firstEmployeeFromList = departmentWithOneEmployee.getEmployees().stream().findFirst().get();

        when(departmentService.getOneDepartmentById(1L)).thenReturn(departmentWithOneEmployee);

        mockMvc.perform(MockMvcRequestBuilders.get("/departments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(departmentWithOneEmployee.getId().intValue())))
                .andExpect(jsonPath("$.name", Matchers.is(departmentWithOneEmployee.getName())))
                .andExpect(jsonPath("$.description", Matchers.is(departmentWithOneEmployee.getDescription())))
                .andExpect(jsonPath("$.phoneNumber", Matchers.is(departmentWithOneEmployee.getPhoneNumber())))
                .andExpect(jsonPath("$.dateOfFormation", Matchers.is(new SimpleDateFormat("yyyy-MM-dd").format(departmentWithOneEmployee.getDateOfFormation()))))
                .andExpect(jsonPath("$.employees[0].id", Matchers.is(firstEmployeeFromList.getId().intValue())))
                .andExpect(jsonPath("$.employees[0].fullName", Matchers.is(firstEmployeeFromList.getFullName())))
                .andExpect(jsonPath("$.employees[0].dateOfBirth", Matchers.is(new SimpleDateFormat("yyyy-MM-dd").format(firstEmployeeFromList.getDateOfBirth()))))
                .andExpect(jsonPath("$.employees[0].phoneNumber", Matchers.is(firstEmployeeFromList.getPhoneNumber())))
                .andExpect(jsonPath("$.employees[0].emailAddress", Matchers.is(firstEmployeeFromList.getEmailAddress())))
                .andExpect(jsonPath("$.employees[0].position", Matchers.is(firstEmployeeFromList.getPosition())))
                .andExpect(jsonPath("$.employees[0].dateOfEmployment", Matchers.is(new SimpleDateFormat("yyyy-MM-dd").format(firstEmployeeFromList.getDateOfEmployment()))))
                .andExpect(jsonPath("$.employees[0].departmentId", Matchers.is(firstEmployeeFromList.getDepartmentId().intValue())));
    }

    @Test
    public void givenConstraintViolationException_whenGetOneDepartmentById_thenReturnJson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/departments/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", Matchers.is("Constraint Violation")))
                .andExpect(jsonPath("$.errors[0]", Matchers.is("getOneDepartmentById.id: must be greater than or equal to 1")));
    }
}