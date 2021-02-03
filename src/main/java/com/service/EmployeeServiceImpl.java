package com.service;

import com.exceptions.ResourceNotFoundException;
import com.model.Employee;
import com.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link com.service.EmployeeService} interface.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository ) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public Employee getOneEmployeeById(Long id) {
        LOGGER.debug("Trying to find a employee with ID = {}", id);
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee with ID: " + id + " Not Found!")) ;
    }

    @Override
    @Transactional
    public void createEmployee(Employee employee) {
        LOGGER.debug("Creation of a new employee");
        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void deleteEmployeeById(Long id) {
        LOGGER.debug("Deleting a employee with ID = {}", id);
        employeeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Employee> getAllEmployeesWhichDoNotBelongToAnyDepartment() {
        LOGGER.debug("Get all employees which do not belong to any department");
        return employeeRepository.getEmployeesByDepartmentIdIsNull();
    }

    @Override
    @Transactional
    public void removeEmployeeFromDepartment(Long employeeId) {
        LOGGER.debug("Remove employee with ID = {} from department", employeeId);
        employeeRepository.removeEmployeeFromDepartment(employeeId);
    }

    @Override
    @Transactional
    public void addEmployeeToDepartment(Long employeeId, Long departmentId) {
        LOGGER.debug("Add employee with ID = {} to department with ID {}", employeeId, departmentId);
        employeeRepository.addEmployeeToDepartment(employeeId, departmentId);
    }
}
