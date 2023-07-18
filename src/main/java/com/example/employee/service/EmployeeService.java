package com.example.employee.service;

import com.example.employee.entity.Employee;
import com.example.employee.exception.UserNotFoundException;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);

    List<Employee> fetchAllEmployees();

    List<Employee> fetchEmployeeList(PageRequest pageRequest);

    Employee updateEmployee(Employee employee, Long empId) throws UserNotFoundException;

    Employee updateEmployee(Long empId, Map<String, Object> fields) throws Exception;

    Employee getEmployee(Long empId) throws UserNotFoundException;

    void deleteEmployeeById(Long empId) throws Exception;

}
