package com.example.employee.resources;

import com.example.employee.model.Employee;
import com.example.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = "/employee")
    List<Employee> getAllEmployees() {
        return employeeService.fetchEmployeeList();
    }

    @PostMapping(value = "/employee")
    public Employee saveEmployee(@Valid @RequestBody  Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @GetMapping(value = "/employee/{id}")
    public Employee saveEmployee(@PathVariable("id") Long empId) {
        List<Employee> empList =  employeeService.fetchEmployeeList()
                .stream()
                .filter(employee -> employee.getEmpId() == empId)
                .collect(Collectors.toList());
        if(empList.size() != 0) {
            return empList.get(0);
        } else {
            return null;
        }
    }

    // PUT operation : To update
    @PutMapping(value = "/employee/{id}")
    public Employee updateEmployee(@RequestBody Employee employee, @PathVariable("id") Long empId)  {
        return employeeService.updateEmployee(employee,  empId);
    }

    @DeleteMapping(value = "/employee/{id}")
    public String deleteEmployee(@PathVariable("id") Long empId) {
        employeeService.deleteEmployeeById(empId);
        return empId + " Deleted successfully!";
    }

}
