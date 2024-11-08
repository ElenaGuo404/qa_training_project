package com.ims.test;

import com.ims.service.EmployeeService;
import com.ims.model.Employee;
import com.ims.dao.EmployeeDAO;
import com.ims.utility.DatabaseConfig;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestEmployeeService {

    private EmployeeService employeeService;
    private EmployeeDAO employeeDAO;

    public TestEmployeeService(DatabaseConfig dbConfig) {
        employeeDAO = new EmployeeDAO(dbConfig);
        this.employeeService = new EmployeeService(employeeDAO);
    }


    @Test
    public void testCreateEmployee_ValidInput() {
        employeeService = new EmployeeService(employeeDAO);
        Employee employee = new Employee("John", "Doe", "john.doe@example.com", "Engineering", 50000.0f);
        employeeService.createEmployee(employee);
        assertNotNull(employee.getEmployee_id()); // Ensure the employee was created with an ID
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
