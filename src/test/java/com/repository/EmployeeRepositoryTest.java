package com.repository;

import com.model.Employee;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.GregorianCalendar;

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

    @Test
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

}