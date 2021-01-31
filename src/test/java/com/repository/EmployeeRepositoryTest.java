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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Integrations tests of {@link EmployeeRepository}.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    @Rollback(value = true)
    public void whenGetOneEmployeeById_thenReturnEmployee() {
        Employee employee = employeeRepository.findById(1L).get();

        Assert.assertEquals(1, employee.getId().intValue());
        Assert.assertEquals("Sergey Sergeev", employee.getFullName());
        Assert.assertEquals(new GregorianCalendar(1989, Calendar.AUGUST, 7).getTime(), employee.getDateOfBirth());
        Assert.assertEquals("+37529-532-65-31", employee.getPhoneNumber());
        Assert.assertEquals("signatuk89@gmail.com", employee.getEmailAddress());
        Assert.assertEquals("Java Developer", employee.getPosition());
        Assert.assertEquals(new GregorianCalendar(2016, Calendar.OCTOBER, 14).getTime(), employee.getDateOfEmployment());
        Assert.assertEquals(2, employee.getDepartmentId().intValue());
    }

    @Test
    @Rollback(value = true)
    public void createEmployeeTest() {
        employeeRepository.save(Employee.builder()
                .fullName("Petya Petrov")
                .dateOfBirth(new GregorianCalendar(1989, Calendar.AUGUST, 7).getTime())
                .phoneNumber("+37529-532-65-31")
                .emailAddress("ppetrov89@gmail.com")
                .position("Java Developer")
                .dateOfEmployment(new GregorianCalendar(2016, Calendar.OCTOBER, 14).getTime())
                .departmentId(2L)
                .build());

        List<Employee> sortedByIdEmployeeList = employeeRepository.findAll().stream().sorted((o1, o2) -> (int) (o1.getId() - o2.getId())).collect(Collectors.toList());
        Employee lastEmployeeFromList = sortedByIdEmployeeList.get(sortedByIdEmployeeList.size() - 1);

        Assert.assertEquals(5, sortedByIdEmployeeList.size());
        Assert.assertEquals(5, lastEmployeeFromList.getId().intValue());
        Assert.assertEquals("Petya Petrov", lastEmployeeFromList.getFullName());
        Assert.assertEquals(new GregorianCalendar(1989, Calendar.AUGUST, 7).getTime(), lastEmployeeFromList.getDateOfBirth());
        Assert.assertEquals("+37529-532-65-31", lastEmployeeFromList.getPhoneNumber());
        Assert.assertEquals("ppetrov89@gmail.com", lastEmployeeFromList.getEmailAddress());
        Assert.assertEquals("Java Developer", lastEmployeeFromList.getPosition());
        Assert.assertEquals(new GregorianCalendar(2016, Calendar.OCTOBER, 14).getTime(), lastEmployeeFromList.getDateOfEmployment());
        Assert.assertEquals(2, lastEmployeeFromList.getDepartmentId().intValue());
    }

    @Test
    @Rollback(value = true)
    public void deleteEmployeeTest() {
        employeeRepository.deleteById(1L);

        List<Employee> sortedByIdEmployeeList = employeeRepository.findAll().stream().sorted((o1, o2) -> (int) (o1.getId() - o2.getId())).collect(Collectors.toList());
        List<Department> sortedByIdDepartmentList = departmentRepository.findAll().stream().sorted((o1, o2) -> (int) (o1.getId() - o2.getId())).collect(Collectors.toList());
        Department secondDepartmentFromList = sortedByIdDepartmentList.get(1);

        Assert.assertEquals(3, sortedByIdEmployeeList.size());
        Assert.assertEquals(3, sortedByIdDepartmentList.size());
        Assert.assertEquals(1, secondDepartmentFromList.getEmployees().size());
    }
}