package com.ims.test;

import com.ims.model.Employee;
import com.ims.dao.EmployeeDAO;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TestMain {

    EmployeeDAO employeeDAO;

    @Test
    public void testCreateEmployee() {
        employeeDAO = new EmployeeDAO();
        Employee employee = new Employee("Alice", "Hows", "alice.hows@example.com", "Finance", 7000.0f);
        employeeDAO.createEmployee(employee);

        Employee retrieved = employeeDAO.readEmployee(employee.getEmployeeId());
        assertNotNull(retrieved);
        assertEquals("Alice", retrieved.getFirstName());
        assertEquals("Hows",retrieved.getLastName());
    }

    @Test
    public void testUpdateEmployee() {
        employeeDAO = new EmployeeDAO();
        Employee employee = new Employee("Mark", "Smith", "mark.smith@example.com", "Business", 55000.0f);
        employeeDAO.createEmployee(employee);

        employee.setFirstName("Markus");
        employee.setEmail("mark.smith.new@example.com");
        employeeDAO.updateEmployee(employee);

        Employee updatedEmployee = employeeDAO.readEmployee(employee.getEmployeeId());

        assertNotNull(updatedEmployee);
        assertEquals("Markus", updatedEmployee.getFirstName());
        assertEquals("mark.smith.new@example.com", updatedEmployee.getEmail());
        assertEquals("Business", updatedEmployee.getDepartment());
    }

    @Test
    public void testDeleteEmployee() {
        employeeDAO = new EmployeeDAO();
        Employee employee = new Employee("Alice", "Johnson", "alice.johnson@example.com", "Marketing", 65000.0f);
//        employeeDao.createEmployee(employee);
//
//        Employee retrieved = employeeDao.readEmployee(employee.getEmployeeId());
//        assertNotNull(retrieved);

        employeeDAO.deleteEmployee(14);

        Employee deleted = employeeDAO.readEmployee(employee.getEmployeeId());

        assertNull(deleted);
    }

    @Test
    public void testGetAllEmployees() {
        employeeDAO = new EmployeeDAO();

        Employee employee1 = new Employee("Tom", "Hanks", "tom.hanks@example.com", "Acting", 80000.0f);
        Employee employee2 = new Employee("Emma", "Watson", "emma.watson@example.com", "Acting", 75000.0f);
        employeeDAO.createEmployee(employee1);
        employeeDAO.createEmployee(employee2);

        // Retrieve all employees
        List<Employee> employees = employeeDAO.getAllEmployees();

        // Assertions to verify that all employees are retrieved
        assertNotNull(employees);
        assertEquals(4, employees.size());

        // Check if the retrieved employees match the inserted employees
        assertTrue(employees.stream().anyMatch(emp -> emp.getFirstName().equals("Tom") && emp.getLastName().equals("Hanks")));
        assertTrue(employees.stream().anyMatch(emp -> emp.getFirstName().equals("Emma") && emp.getLastName().equals("Watson")));
    }


}
