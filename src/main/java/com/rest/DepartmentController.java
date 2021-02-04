package com.rest;

import com.model.Department;
import com.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Api(value="management system", tags="Operations on departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('departments:read')")
    @ApiOperation(value = "View a list of departments with their employees")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<List<Department>> getAllDepartmentsWithTheirEmployees() {
        return new ResponseEntity<>(departmentService.getAllDepartmentsWithTheirEmployees(), HttpStatus.OK);
    }

    @GetMapping("/{departmentId}")
    @PreAuthorize("hasAuthority('departments:read')")
    @ApiOperation(value = "View one department by ID with their employees")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved department"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Department> getOneDepartmentById(@PathVariable @Min(value = 1, message = "must be greater than or equal to 1") Long departmentId) {
        return new ResponseEntity<>(departmentService.getOneDepartmentById(departmentId), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('departments:write')")
    @ApiOperation(value = "Create new department without employees")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created department"),
            @ApiResponse(code = 201, message = "Successfully created department"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<HttpHeaders> createDepartment(@Valid @RequestBody Department department, UriComponentsBuilder uriComponentsBuilder) {
        departmentService.createDepartment(department);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponentsBuilder.path("/departments/{id}").buildAndExpand(department.getId()).toUri());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @DeleteMapping("/{departmentId}")
    @PreAuthorize("hasAuthority('departments:write')")
    @ApiOperation(value = "Delete department by ID, and their employees will be not belong to any department")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted department"),
            @ApiResponse(code = 204, message = "Successfully deleted department"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Void> deleteDepartmentById(@PathVariable @Min(value = 1, message = "must be greater than or equal to 1") Long departmentId) {
        departmentService.deleteDepartmentById(departmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
