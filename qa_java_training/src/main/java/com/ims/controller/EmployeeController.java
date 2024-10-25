package com.ims.controller;

import com.ims.model.Employee;
import com.ims.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @GetMapping("/{employee_id}")
    public Employee getEmployee(@PathVariable int employee_id) {
        return employeeService.readEmployee(employee_id);
    }

    @PutMapping("/{employee_id}")
    public Employee updateEmployee(@PathVariable int employee_id, @RequestBody Employee employee) {
        employee.setEmployeeId(employee_id);
        return employeeService.updateEmployee(employee);
    }

    @DeleteMapping("/{employee_id}")
    public void deleteEmployee(@PathVariable int employee_id) {
        employeeService.deleteEmployee(employee_id);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
}
