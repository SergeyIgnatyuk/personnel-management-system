package com.controller;

import com.model.Department;
import com.model.Employee;
import com.service.DepartmentService;
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
 * REST controller for {@link com.model.Department}'s resources.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@RestController
@RequestMapping("/departments")
@Validated
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartmentsWithTheirEmployees() {
        return new ResponseEntity<>(departmentService.getAllDepartmentsWithTheirEmployees(), HttpStatus.OK);
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<Department> getOneDepartmentById(@PathVariable @Min(value = 1, message = "must be greater than or equal to 1") Long departmentId) {
        return new ResponseEntity<>(departmentService.getOneDepartmentById(departmentId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpHeaders> createDepartment(@Valid @RequestBody Department department, UriComponentsBuilder uriComponentsBuilder) {
        departmentService.createDepartment(department);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponentsBuilder.path("/departments/{id}").buildAndExpand(department.getId()).toUri());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Void> deleteDepartmentById(@PathVariable @Min(value = 1, message = "must be greater than or equal to 1") Long departmentId) {
        departmentService.deleteDepartmentById(departmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
