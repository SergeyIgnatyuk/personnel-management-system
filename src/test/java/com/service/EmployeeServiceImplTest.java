package com.service;

import com.model.Employee;
import com.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

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
    private com.service.EmployeeService employeeService;

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public EmployeeRepository getEmployeeRepository() {
            return mock(EmployeeRepository.class);
        }

        @Bean
        public com.service.EmployeeService getEmployeeService() {
            return new EmployeeServiceImpl(getEmployeeRepository());
        }
    }

    @Test
    public void whenGetOneEmployeeById_findByIdShouldBeCalled() {
        when(employeeRepository.findById(1L)).thenReturn(java.util.Optional.of(new Employee()));
        employeeService.getOneEmployeeById(1L);
        verify(employeeRepository, times(1) ).findById(1L);
    }

    @Test
    public void whenCreateEmployee_saveShouldBeCalled() {
        Employee employee = new Employee();
        employeeService.createEmployee(employee);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void whenDeleteEmployee_deleteByIdShouldBeCalled() {
        employeeService.deleteEmployeeById(1L);
        verify(employeeRepository, times(1)).deleteById(1L);
    }
}