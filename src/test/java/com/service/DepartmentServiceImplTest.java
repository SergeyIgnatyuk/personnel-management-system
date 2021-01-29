package com.service;

import com.model.Department;
import com.model.Employee;
import com.repository.DepartmentRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

/**
 * Integrations tests of {@link DepartmentServiceImpl}.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@RunWith(SpringRunner.class)
public class DepartmentServiceImplTest {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private DepartmentService departmentService;

    @TestConfiguration
    static class DepartmentServiceImplTestContextConfiguration {
        @Bean
        public DepartmentRepository getDepartmentRepository() {
            return mock(DepartmentRepository.class);
        }

        @Bean
        public DepartmentService getDepartmentService() {
            return new DepartmentServiceImpl(getDepartmentRepository());
        }
    }

    @Before
    public void setUp() {
        Department departmentWithOneEmployee = Department.builder()
                .id(1L)
                .name("Magician Department")
                .description("Magician Department")
                .phoneNumber("111-111")
                .dateOfFormation(new GregorianCalendar(1812, Calendar.JANUARY, 1).getTime())
                .employees(
                        Stream.of(Employee.builder()
                                .id(1L)
                                .fullName("Harry Potter")
                                .dateOfBirth(new GregorianCalendar(1989, Calendar.AUGUST, 7).getTime())
                                .phoneNumber("9-3/4")
                                .emailAddress("harryPotter@gmail.com")
                                .position("magician")
                                .dateOfEmployment(new GregorianCalendar(2000, Calendar.SEPTEMBER, 1).getTime())
                                .departmentId(1L)
                                .build()).collect(Collectors.toSet()))
                .build();
        when(departmentRepository.findAll()).thenReturn(Stream.of(departmentWithOneEmployee).collect(Collectors.toList()));
        when(departmentRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(departmentWithOneEmployee));
    }

    @Test
    public void whenGetAllDepartmentsWithTheirEmployees_thenReturnDepartmentsList() {
        List<Department> departmentList = departmentRepository.findAll();
        Department firstDepartmentFromList = departmentList.get(0);
        Set<Employee> setEmployeesFromFirstDepartment = firstDepartmentFromList.getEmployees();

        verify(departmentRepository, times(1)).findAll();

        Assert.assertEquals(1, departmentList.size());
        Assert.assertEquals(1, firstDepartmentFromList.getId().intValue());
        Assert.assertEquals("Magician Department", firstDepartmentFromList.getName());
        Assert.assertEquals("Magician Department", firstDepartmentFromList.getDescription());
        Assert.assertEquals("111-111", firstDepartmentFromList.getPhoneNumber());
        Assert.assertEquals(new GregorianCalendar(1812, Calendar.JANUARY, 1).getTime(), firstDepartmentFromList.getDateOfFormation());
        Assert.assertEquals(1, setEmployeesFromFirstDepartment.size());

    }

    @Test
    public void whenGetOneDepartmentById_thenReturnDepartment() {
        Department department = departmentRepository.findById(1L).get();
        Set<Employee> setEmployeesFromDepartment = department.getEmployees();

        verify(departmentRepository, times(1)).findById(1L);

        Assert.assertEquals("Magician Department", department.getName());
        Assert.assertEquals("Magician Department", department.getDescription());
        Assert.assertEquals("111-111", department.getPhoneNumber());
        Assert.assertEquals(new GregorianCalendar(1812, Calendar.JANUARY, 1).getTime(), department.getDateOfFormation());
        Assert.assertEquals(1, setEmployeesFromDepartment.size());
    }
}