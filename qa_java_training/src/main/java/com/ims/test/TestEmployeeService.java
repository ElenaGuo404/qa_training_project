package com.ims.test;

import com.ims.service.EmployeeService;
import com.ims.utility.DBConnection;
import com.ims.model.Employee;
import com.ims.dao.EmployeeDAO;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TestEmployeeService {

    private EmployeeService employeeService;
    EmployeeDAO employeeDAO = new EmployeeDAO();

    @Test
    public void testCreateEmployee_ValidInput() {
        employeeService = new EmployeeService(employeeDAO);
        Employee employee = new Employee("John", "Doe", "john.doe@example.com", "Engineering", 50000.0f);
        employeeService.createEmployee(employee);
        assertNotNull(employee.getEmployeeId()); // Ensure the employee was created with an ID
    }

    @Test
    public void testCreateEmployee_InvalidEmail() {
        Employee employee = new Employee("John", "Doe", "invalid-email", "Engineering", 50000.0f);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.createEmployee(employee);
        });
        assertEquals("Invalid email format.", exception.getMessage());
    }


}
