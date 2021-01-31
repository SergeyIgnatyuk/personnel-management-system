package com.service;

import com.model.Department;
import com.repository.DepartmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

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

    @Test
    public void whenGetAllDepartmentsWithTheirEmployees_findAllShouldBeCalled() {
        departmentService.getAllDepartmentsWithTheirEmployees();
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    public void whenGetOneDepartmentById_findByIdShouldBeCalled() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(new Department()));
        departmentService.getOneDepartmentById(1L);
        verify(departmentRepository, times(1)).findById(1L);
    }

    @Test
    public void whenCreateDepartment_saveShouldBeCalled() {
        Department department = new Department();
        departmentService.createDepartment(department);
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    public void whenDeleteDepartmentById_deleteByIdShouldBeCalled() {
        departmentService.deleteDepartmentById(1L);
        verify(departmentRepository, times(1)).deleteById(1L);
    }
}