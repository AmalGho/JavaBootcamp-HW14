package com.example.employeesmanagement.Controller;

import com.example.employeesmanagement.ApiResponse.ApiResponse;
import com.example.employeesmanagement.Model.Employee;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    ArrayList<Employee> employees = new ArrayList<>();

    @GetMapping("/get")
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    @PostMapping("/add")
    public ResponseEntity addEmployee(@RequestBody @Valid Employee employee, Errors errors) {
        if (errors.hasErrors()){
            String msg = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
        employees.add(employee);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("employee added"));
    }


    @PutMapping("/update/{index}")
    public ResponseEntity updateEmployee(@PathVariable int index, @RequestBody @Valid Employee employee, Errors errors) {
        if (errors.hasErrors()) {
            String msg = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
        employees.set(index, employee);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("employee information updated"));
    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity deleteEmployee(@PathVariable int index) {
        employees.remove(index);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("employee deleted"));
    }


    @PutMapping("/applyLeave/{index}/{leaveDays}")
    public ResponseEntity applyAnnualLeave(@PathVariable int index, @PathVariable int leaveDays) {
        if (employees.get(index).isOnLeave()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("the employee is already on leave"));
        }else if (employees.get(index).getAnnualLeave() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("the employee consumed all annual leave days"));
        } else if (employees.get(index).getAnnualLeave() < leaveDays) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("the employee does not have enough annual leave"));
        }

        employees.get(index).setOnLeave(true);
        employees.get(index).setAnnualLeave(employees.get(index).getAnnualLeave() - leaveDays);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("applied"));
    }
}
