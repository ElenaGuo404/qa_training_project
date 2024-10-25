package com.ims.service;

import com.ims.dao.EmployeeDAO;
import com.ims.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeDAO employeeDAO;

    public EmployeeService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public Employee createEmployee(Employee employee) {
        validateEmployee(employee);
        employeeDAO.createEmployee(employee);
        return employee;
    }

    public Employee readEmployee(int employee_id) {
        return employeeDAO.readEmployee(employee_id);
    }

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
        if (employee.getFirstName() == null || employee.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty.");
        }
        if (employee.getLastName() == null || employee.getLastName().isEmpty()) {
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
