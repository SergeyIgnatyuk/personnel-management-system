package com.controller;

import com.model.Department;
import com.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{id}")
    public ResponseEntity<Department> getOneDepartmentById(@PathVariable @Min(value = 1, message = "must be greater than or equal to 1") Long id) {
        return new ResponseEntity<>(departmentService.getOneDepartmentById(id), HttpStatus.OK);
    }
}
