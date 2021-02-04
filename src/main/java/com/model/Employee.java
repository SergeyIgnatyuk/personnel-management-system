package com.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Simple JavaBean domain object that represents a Employee is build with {@link lombok}.
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
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The database generated employee ID")
    private Long id;

    @Column(name = "full_name")
    @NotEmpty(message = "Full name must be not empty")
    @ApiModelProperty(notes = "The employee full name")
    private String fullName;

    @Column(name = "date_of_birth")
    @NotNull(message = "Date of birth must be not empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @ApiModelProperty(notes = "The employee date of birth")
    private Date dateOfBirth;

    @Column(name = "phone_number")
    @NotEmpty(message = "Phone number must be not empty")
    @ApiModelProperty(notes = "The employee phone number")
    private String phoneNumber;

    @Column(name = "email_address")
    @NotEmpty(message = "Email address must be not empty")
    @ApiModelProperty(notes = "The employee email address")
    private String emailAddress;

    @Column(name = "position")
    @NotEmpty(message = "Position must be not empty")
    @Size(min = 2, max = 30, message = "Position must be between 3 and 30 characters")
    @ApiModelProperty(notes = "The employee position")
    private String position;

    @Column(name = "date_of_employment")
    @NotNull(message = "Date of employment must be not empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @ApiModelProperty(notes = "Date of employment")
    private Date dateOfEmployment;

    @Column(name = "department_id")
    @JsonIgnore
    private Long departmentId;
}
