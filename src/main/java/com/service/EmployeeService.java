package com.service;

import com.model.Employee;

/**
 * Service interface for {@link com.model.Employee}
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

public interface EmployeeService {
    Employee getOneEmployeeById(Long id);
}
