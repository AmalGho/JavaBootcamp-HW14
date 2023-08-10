package com.example.employeesmanagement.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.time.Year;
import java.util.Date;

@Data
@AllArgsConstructor
public class Employee {

    @NotEmpty(message = "id should not be empty")
    @Length(min = 3, message = "id length should be more than 2")
    private String id;

    @NotEmpty(message = "name should not be empty")
    @Length(min = 5, message = "name length should be more than 4")
    private String name;

    @NotEmpty(message = "age should not be empty")
    @Min(value = 26, message = "age must be above 25")
    @Pattern(regexp = "^[0-9]+$", message = "age should be numbers only")
    private String age;

    @NotEmpty(message = "position should not be empty")
    @Pattern(regexp = "\\W*((?i)supervisor|coordinator(?-i))\\W*", message = "employee position should be supervisor or coordinator only")
    private String position;

    @AssertFalse(message = "on leave must assign as false")
    private boolean onLeave;

    @NotNull(message = "employment year should not be empty")
    @PastOrPresent(message = "employment year must be a date in the past or in the present")
    @DateTimeFormat(pattern = "yyyy-MM-dd", style = "S-")
    private Date employmentYear;

    @NotNull(message = "annual leave should not be empty")
    @PositiveOrZero(message = "annual leave should be positive number")
    private int annualLeave;

}
