package com.example.employee.service;

import com.example.employee.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);

    List<Employee> fetchEmployeeList();

    Employee updateEmployee(Employee employee, Long empId);

    void deleteEmployeeById(Long empId);

}
