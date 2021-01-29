package com.service;

import com.model.Employee;
import com.repository.EmployeeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.mockito.Mockito.*;

/**
 * Integrations tests of {@link EmployeeServiceImpl}.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@RunWith(SpringRunner.class)
public class EmployeeServiceImplTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public EmployeeRepository getEmployeeRepository() {
            return mock(EmployeeRepository.class);
        }

        @Bean
        public EmployeeService getEmployeeService() {
            return new EmployeeServiceImpl(getEmployeeRepository());
        }
    }

    @Before
    public void setUp() {
        Employee employee = Employee.builder()
                .id(1L)
                .fullName("Harry Potter")
                .dateOfBirth(new GregorianCalendar(1989, Calendar.AUGUST, 7).getTime())
                .phoneNumber("9-3/4")
                .emailAddress("harryPotter@gmail.com")
                .position("magician")
                .dateOfEmployment(new GregorianCalendar(2000, Calendar.SEPTEMBER, 1).getTime())
                .departmentId(1L)
                .build();

        when(employeeRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(employee));
    }

    @Test
    public void whenGetOneEmployeeById_ThenReturnEmployee() {
        Employee employee = employeeService.getOneEmployeeById(1L);

        verify(employeeRepository, times(1) ).findById(1L);

        Assert.assertEquals(1, employee.getId().intValue());
        Assert.assertEquals("Harry Potter", employee.getFullName());
        Assert.assertEquals(new GregorianCalendar(1989, Calendar.AUGUST, 7).getTime(), employee.getDateOfBirth());
        Assert.assertEquals("9-3/4", employee.getPhoneNumber());
        Assert.assertEquals("harryPotter@gmail.com", employee.getEmailAddress());
        Assert.assertEquals("magician", employee.getPosition());
        Assert.assertEquals(new GregorianCalendar(2000, Calendar.SEPTEMBER, 1).getTime(), employee.getDateOfEmployment());
        Assert.assertEquals(1, employee.getDepartmentId().intValue());
    }
}