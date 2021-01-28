package com.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

/**
 * Simple JavaBean domain object that represents a Department is build with {@link lombok}.
 * There is validation with {@link javax.validation.constraints}.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    @NotEmpty(message = "Department name must be not empty")
    @Size(min = 2, max = 30, message = "Department name must be between 2 and 30 characters")
    private String name;
    @Column(name = "description")
    @NotEmpty(message = "Department description must be not empty")
    private String description;
    @Column(name = "phone_number")
    @NotEmpty(message = "Department phone number must be not empty")
    private String phoneNumber;
    @Column(name = "date_of_formation")
    @NotNull(message = "Date of formation must be not empty")
    private Date dateOfFormation;
    @OneToMany
    @JoinColumn(name = "department_id", nullable = false, insertable = false, updatable = false)
    private Set<Employee> employees;
}
