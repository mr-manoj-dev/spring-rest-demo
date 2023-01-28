package com.example.employee.service;

import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service("EmployeeService")
public class EmployeeServiceImpl implements  EmployeeService {

    @Autowired
    private EmployeeRepository  employeeRepository;
    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> fetchEmployeeList() {
        return (List<Employee>)employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(Employee employee, Long empId) {
        Employee e = employeeRepository.findById(empId).get();
        if(Objects.nonNull(employee.getName()) && !"".equalsIgnoreCase(employee.getName())) {
            e.setName(employee.getName());
        }
        if(Objects.nonNull(employee.getJoiningDate()) && !"".equalsIgnoreCase(employee.getJoiningDate())) {
            e.setJoiningDate(employee.getJoiningDate());
        }
        return employeeRepository.save(e);
    }

    @Override
    public void deleteEmployeeById(Long empId) {
        employeeRepository.deleteById(empId);
    }
}
