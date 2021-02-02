package com.controller;

import com.model.Employee;
import com.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * REST controller for {@link Employee}'s resources.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@RestController
@RequestMapping("/employees")
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getOneEmployeeById(@PathVariable @Min(value = 1, message = "must be greater than or equal to 1") Long employeeId) {
        return new ResponseEntity<>(employeeService.getOneEmployeeById(employeeId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpHeaders> createEmployee(@Valid @RequestBody Employee employee, UriComponentsBuilder uriComponentsBuilder) {
        employeeService.createEmployee(employee);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponentsBuilder.path("/employees/{id}").buildAndExpand(employee.getId()).toUri());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable @Min(value = 1, message = "must be greater than or equal to 1") Long employeeId) {
        employeeService.deleteEmployeeById(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/free")
    public ResponseEntity<List<Employee>> getAllEmployeesWhichDoNotBelongToAnyDepartment() {
        return new ResponseEntity<>(employeeService.getAllEmployeesWhichDoNotBelongToAnyDepartment(), HttpStatus.OK);
    }

    @PutMapping("/{employeeId}/remove/department")
    public ResponseEntity<Void> removeEmployeeFromDepartment(@PathVariable @Min(value = 1, message = "must be greater than or equal to 1") Long employeeId) {
        employeeService.removeEmployeeFromDepartment(employeeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{employeeId}/add/department/{departmentId}")
    public ResponseEntity<Void> addEmployeeToDepartment(@PathVariable @Min(value = 1, message = "must be greater than or equal to 1") Long employeeId,
                                                            @PathVariable @Min(value = 1, message = "must be greater than or equal to 1") Long departmentId) {
        employeeService.addEmployeeToDepartment(employeeId, departmentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
