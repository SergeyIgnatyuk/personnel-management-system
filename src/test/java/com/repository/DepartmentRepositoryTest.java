package com.repository;

import com.model.Department;
import com.model.Employee;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @Rollback(value = true)
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
    @Rollback(value = true)
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

    @Test
    @Rollback(value = true)
    public void createDepartmentTest() {
        departmentRepository.save(Department.builder()
                .name("Accountant Department")
                .description("Accountant Department")
                .phoneNumber("111-111")
                .dateOfFormation(new GregorianCalendar(2015, Calendar.MARCH, 15).getTime())
                .build());

        List<Department> sortedDepartmentsListById = departmentRepository.findAll().stream().sorted((o1, o2) -> (int) (o1.getId() - o2.getId())).collect(Collectors.toList());
        Department lastDepartmentFromList = sortedDepartmentsListById.get(sortedDepartmentsListById.size() - 1);

        Assert.assertEquals(4, sortedDepartmentsListById.size());
        Assert.assertEquals(4, lastDepartmentFromList.getId().intValue());
        Assert.assertEquals("Accountant Department", lastDepartmentFromList.getName());
        Assert.assertEquals("Accountant Department", lastDepartmentFromList.getDescription());
        Assert.assertEquals("111-111", lastDepartmentFromList.getPhoneNumber());
        Assert.assertEquals(new GregorianCalendar(2015, Calendar.MARCH, 15).getTime(), lastDepartmentFromList.getDateOfFormation());
    }

    @Test
    @Rollback(value = true)
    public void deleteDepartmentByIdTest() {
        departmentRepository.deleteById(2L);

        List<Department> sortedByIdDepartmentsList = departmentRepository.findAll().stream().sorted((o1, o2) -> (int) (o1.getId() - o2.getId())).collect(Collectors.toList());
        List<Employee> sortedByIdEmployeesList = employeeRepository.findAll().stream().sorted((o1, o2) -> (int) (o1.getId() - o2.getId())).collect(Collectors.toList());
        Employee firstEmployeeFromList = sortedByIdEmployeesList.get(0);

        Assert.assertEquals(2, sortedByIdDepartmentsList.size());
        Assert.assertEquals(4, sortedByIdEmployeesList.size());
        Assert.assertNull(firstEmployeeFromList.getDepartmentId());
    }
}