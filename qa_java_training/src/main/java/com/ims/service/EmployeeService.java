package com.ims.service;

import com.ims.dao.EmployeeDAO;
import com.ims.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;

    public EmployeeService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Transactional
    public Employee createEmployee(Employee employee) {
        validateEmployee(employee);
        employeeDAO.createEmployee(employee);
        return employee;
    }

    public Employee readEmployee(int employee_id) {
        return employeeDAO.readEmployee(employee_id);
    }
//    @Transactional(readOnly = true)
//    public Optional<Employee> readEmployee(int employeeId) {
//        return Optional.ofNullable(employeeDAO.readEmployee(employeeId));
//    }

    public Employee updateEmployee(Employee employee) {
        validateEmployee(employee);
        return employeeDAO.updateEmployee(employee);
    }

    public boolean deleteEmployee(int employee_id) {
        return employeeDAO.deleteEmployee(employee_id);
    }

    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    // Validation
    private void validateEmployee(Employee employee) {
        if (employee.getFirst_name() == null || employee.getFirst_name().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty.");
        }
        if (employee.getLast_name() == null || employee.getLast_name().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty.");
        }
        if (employee.getEmail() == null || !employee.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (employee.getDepartment() == null || employee.getDepartment().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be null or empty.");
        }
        if (employee.getSalary() == null || employee.getSalary() < 0) {
            throw new IllegalArgumentException("Salary cannot be null or negative.");
        }
    }
}
