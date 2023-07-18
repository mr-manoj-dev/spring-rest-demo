package com.example.employee.service;

import com.example.employee.entity.Employee;
import com.example.employee.exception.UserNotFoundException;
import com.example.employee.repository.EmployeeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service("EmployeeService")
public class EmployeeServiceImpl implements  EmployeeService {

    @Autowired
    private EmployeeRepository  employeeRepository;
    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> fetchAllEmployees() {
        return (List<Employee>)employeeRepository.findAll();
    }

    @Override
    public List<Employee> fetchEmployeeList(PageRequest pageRequest) {
        Page<Employee> empPage =employeeRepository.findAll(pageRequest);
        return empPage.getContent();
    }

    @Override
    public Employee getEmployee(Long empId) throws UserNotFoundException {
        Optional<Employee> emp = employeeRepository.findById(empId);
        return emp.orElseThrow(() -> new UserNotFoundException("Employee "+empId+" not found!"));
    }

    @Override
    public Employee updateEmployee(Employee employee, Long empId) throws UserNotFoundException {
        Employee existingEmployee = employeeRepository.findById(empId).orElseThrow(() -> new UserNotFoundException("Employee "+empId+" not found!"));
        // As this PUT operation, all the fields need to updated from the input entity to existing employee
        if(Objects.nonNull(employee.getName()) && !"".equalsIgnoreCase(employee.getName())) {
            existingEmployee.setName(employee.getName());
        }
        if(Objects.nonNull(employee.getJoiningDate()) && !"".equalsIgnoreCase(employee.getJoiningDate())) {
            existingEmployee.setJoiningDate(employee.getJoiningDate());
        }
        if(Objects.nonNull(employee.getEmail()) && !"".equalsIgnoreCase(employee.getEmail())) {
            existingEmployee.setEmail(employee.getEmail());
        }

        return employeeRepository.save(existingEmployee);
    }

    @Override
    public Employee updateEmployee(Long empId, Map<String,Object> fields) throws Exception {
        Employee existingEmployee = employeeRepository.findById(empId).orElseThrow(() -> new UserNotFoundException("Employee "+empId+" not found!"));
        Employee updatedEmployee = getEmployeeUsingMapper(fields, existingEmployee);
        return employeeRepository.save(updatedEmployee);
        /*
        Optional<Employee> existingEmployee = employeeRepository.findById(empId);
        if(existingEmployee.isPresent()) {
            //Employee updatedEmployee = getEmployeeUsingReflection(fields, existingEmployee.get());
            Employee updatedEmployee = getEmployeeUsingMapper(fields, existingEmployee.get());
            return employeeRepository.save(updatedEmployee);
        }
        return null;
        */
    }



    @Override
    public void deleteEmployeeById(Long empId) throws Exception {
        employeeRepository.findById(empId).orElseThrow(() -> new UserNotFoundException("Employee "+empId+" not found!"));
        employeeRepository.deleteById(empId);
    }

    private Employee getEmployeeUsingReflection(Map<String, Object> fields, Employee existingEmployee) {
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Employee.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, existingEmployee, value);
        });
        return existingEmployee;
    }

    private Employee getEmployeeUsingMapper(Map<String, Object> fields, Employee existingEmployee) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // Convert the fields map to a JSON string
            String json = objectMapper.writeValueAsString(fields);

            // Read the JSON string into a Map object
            Map<String, Object> fieldValues = objectMapper.readValue(json, Map.class);
            // Use the ObjectMapper to update the existingEmployee object
            ObjectReader reader = objectMapper.readerForUpdating(existingEmployee);
            existingEmployee = reader.readValue(objectMapper.writeValueAsString(fieldValues));
        }catch (JsonProcessingException jpe) {
            // Log json processing exception:
        }
        return existingEmployee;

    }
}
