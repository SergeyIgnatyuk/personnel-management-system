package com.repository;

import com.model.Department;
import com.model.Employee;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

/**
 * Integrations tests of {@link DepartmentRepository}.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void whenGetAllDepartmentsWithTheirEmployees_thenReturnDepartments() {
        List<Department> departments = departmentRepository.findAll();
        Department firstDepartmentFromList = departments.get(0);
        Set<Employee> employeesFromFirstDepartment = firstDepartmentFromList.getEmployees();

        Assert.assertEquals(3, departments.size());
        Assert.assertEquals(1, firstDepartmentFromList.getId().intValue());
        Assert.assertEquals("QA Department", firstDepartmentFromList.getName());
        Assert.assertEquals("QA Department", firstDepartmentFromList.getDescription());
        Assert.assertEquals("55-89-89", firstDepartmentFromList.getPhoneNumber());
        Assert.assertEquals(new GregorianCalendar(2015, Calendar.MARCH, 15).getTime(), firstDepartmentFromList.getDateOfFormation());
        Assert.assertEquals(1, employeesFromFirstDepartment.size());
    }

    @Test
    public void whenGetOneDepartmentById_thenReturnDepartment() {
        Department department = departmentRepository.findById(1L).get();
        Set<Employee> employeesFromDepartment = department.getEmployees();

        Assert.assertEquals(1, department.getId().intValue());
        Assert.assertEquals("QA Department", department.getName());
        Assert.assertEquals("QA Department", department.getDescription());
        Assert.assertEquals("55-89-89", department.getPhoneNumber());
        Assert.assertEquals(new GregorianCalendar(2015, Calendar.MARCH, 15).getTime(), department.getDateOfFormation());
        Assert.assertEquals(1, employeesFromDepartment.size());
    }
}