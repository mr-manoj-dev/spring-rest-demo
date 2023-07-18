package com.example.employee.resources;

import com.example.employee.dto.EmpRQ;
import com.example.employee.entity.Employee;
import com.example.employee.exception.UserNotFoundException;
import com.example.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getPaginatedEmployees(
            @RequestParam(name="page", defaultValue = "1") int page,
            @RequestParam(name="size", defaultValue = "100") int size
            ) {
        //TODO Need to fix, Page<Employee> from repository returning empty content
        return ResponseEntity.ok(employeeService.fetchEmployeeList(PageRequest.of(page, size)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.fetchAllEmployees());
    }

    @PostMapping
    public ResponseEntity<Employee> saveEmployee(@RequestBody  @Valid EmpRQ empRQ) {
        Employee employee = Employee.build(null, empRQ.getName(), empRQ.getJoiningDate(), empRQ.getEmail());
        return ResponseEntity.ok(employeeService.saveEmployee(employee));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Employee> saveEmployee(@PathVariable("id") Long empId) throws Exception {
        return ResponseEntity.ok(employeeService.getEmployee(empId));
    }

    // PUT operation : To update
    @PutMapping(value = "/v1/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody  @Valid EmpRQ empRQ, @PathVariable("id") Long empId) throws Exception {
        Employee employee = Employee.build(empId, empRQ.getName(), empRQ.getJoiningDate(), empRQ.getEmail());
        return ResponseEntity.ok(employeeService.updateEmployee(employee,  empId));
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Employee> updateEmployeeFields(@PathVariable("id") Long empId, @RequestBody Map<String, Object> fields) throws Exception {

        return ResponseEntity.ok(employeeService.updateEmployee(empId, fields));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long empId) throws Exception {
        employeeService.deleteEmployeeById(empId);
        // Return a 200 OK response with a message indicating that the employee was deleted
        return ResponseEntity.ok(empId +" Deleted successfully.");
    }

}
