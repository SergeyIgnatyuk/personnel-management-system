package com.service;

import com.exceptions.ResourceNotFoundException;
import com.model.Department;
import com.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link DepartmentService} interface.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public List<Department> getAllDepartmentsWithTheirEmployees() {
        return departmentRepository.findAll().stream().sorted((o1, o2) -> (int) (o1.getId() - o2.getId())).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Department getOneDepartmentById(Long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department with ID: " + id + " Not Found!")) ;
    }

    @Override
    @Transactional
    public void createDepartment(Department department) {
        departmentRepository.save(department);
    }

    @Override
    @Transactional
    public void deleteDepartmentById(Long id) {
        departmentRepository.deleteById(id);
    }
}
