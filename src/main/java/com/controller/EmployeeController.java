package com.controller;

import com.model.Employee;
import com.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value="management system", tags="Operations on employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{employeeId}")
    @ApiOperation(value = "View one employee by ID without department")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved employee"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Employee> getOneEmployeeById(@PathVariable @Min(value = 1, message = "must be greater than or equal to 1") Long employeeId) {
        return new ResponseEntity<>(employeeService.getOneEmployeeById(employeeId), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Create new employee which do not belong to any department")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created employee"),
            @ApiResponse(code = 201, message = "Successfully created employee"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<HttpHeaders> createEmployee(@Valid @RequestBody Employee employee, UriComponentsBuilder uriComponentsBuilder) {
        employeeService.createEmployee(employee);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponentsBuilder.path("/employees/{id}").buildAndExpand(employee.getId()).toUri());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @DeleteMapping("/{employeeId}")
    @ApiOperation(value = "Delete employee by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted employee"),
            @ApiResponse(code = 204, message = "Successfully deleted employee"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable @Min(value = 1, message = "must be greater than or equal to 1") Long employeeId) {
        employeeService.deleteEmployeeById(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/free")
    @ApiOperation(value = "View all employees which do not belong to any department")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<List<Employee>> getAllEmployeesWhichDoNotBelongToAnyDepartment() {
        return new ResponseEntity<>(employeeService.getAllEmployeesWhichDoNotBelongToAnyDepartment(), HttpStatus.OK);
    }

    @PutMapping("/{employeeId}/remove/department")
    @ApiOperation(value = "Get employee by ID and remove him from department")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully removed employee from department"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Void> removeEmployeeFromDepartment(@PathVariable @Min(value = 1, message = "must be greater than or equal to 1") Long employeeId) {
        employeeService.removeEmployeeFromDepartment(employeeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{employeeId}/add/department/{departmentId}")
    @ApiOperation(value = "Get employee by ID and add him to department")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added employee to department"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Void> addEmployeeToDepartment(@PathVariable @Min(value = 1, message = "must be greater than or equal to 1") Long employeeId,
                                                            @PathVariable @Min(value = 1, message = "must be greater than or equal to 1") Long departmentId) {
        employeeService.addEmployeeToDepartment(employeeId, departmentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
