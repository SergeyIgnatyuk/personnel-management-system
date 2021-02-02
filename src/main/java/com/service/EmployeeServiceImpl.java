package com.service;

import com.exceptions.ResourceNotFoundException;
import com.model.Employee;
import com.repository.EmployeeRepository;
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
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository ) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public Employee getOneEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee with ID: " + id + " Not Found!")) ;
    }

    @Override
    @Transactional
    public void createEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Employee> getAllEmployeesWhichDoNotBelongToAnyDepartment() {
        return employeeRepository.getEmployeesByDepartmentIdIsNull();
    }

    @Override
    @Transactional
    public void removeEmployeeFromDepartment(Long employeeId) {
        employeeRepository.removeEmployeeFromDepartment(employeeId);
    }

    @Override
    @Transactional
    public void addEmployeeToDepartment(Long employeeId, Long departmentId) {
        employeeRepository.addEmployeeToDepartment(employeeId, departmentId);
    }
}
