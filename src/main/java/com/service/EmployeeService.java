package com.service;

import com.model.Employee;

import java.util.List;

/**
 * Service interface for {@link com.model.Employee}
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

public interface EmployeeService {
    Employee getOneEmployeeById(Long id);

    void createEmployee(Employee employee);

    void deleteEmployeeById(Long id);

    void removeEmployeeFromDepartment(Long employeeId);

    void addEmployeeToDepartment(Long employeeId, Long departmentId);

    List<Employee> getAllEmployeesWhichDoNotBelongToAnyDepartment();
}
