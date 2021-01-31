package com.service;

import com.model.Department;

import java.util.List;

/**
 * Service interface for {@link com.model.Department}
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

public interface DepartmentService {
    List<Department> getAllDepartmentsWithTheirEmployees();

    Department getOneDepartmentById(Long id);

    void createDepartment(Department department);

    void deleteDepartmentById(Long id);
}
