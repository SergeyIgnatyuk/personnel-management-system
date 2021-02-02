package com.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
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
    @Column(name = "id")
    @ApiModelProperty(notes = "The database generated department ID")
    private Long id;

    @Column(name = "name")
    @NotEmpty(message = "Department name must be not empty")
    @ApiModelProperty(notes = "The department name")
    private String name;

    @Column(name = "description")
    @NotEmpty(message = "Department description must be not empty")
    @ApiModelProperty(notes = "The department description")
    private String description;

    @Column(name = "phone_number")
    @NotEmpty(message = "Department phone number must be not empty")
    @ApiModelProperty(notes = "The department phone number")
    private String phoneNumber;

    @Column(name = "date_of_formation")
    @NotNull(message = "Date of formation must be not empty")
    @ApiModelProperty(notes = "The department date of formation")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfFormation;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @ApiModelProperty(notes = "Employees which belong to this department")
    private Set<Employee> employees = new HashSet<>();

    @PreRemove
    private void deleteDepartmentFromEmployee() {
        employees.forEach(employee -> employee.setDepartmentId(null));
    }

}
