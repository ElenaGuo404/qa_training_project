package com.ims.test;

import com.ims.model.Employee;
import com.ims.dao.EmployeeDAO;
import com.ims.service.EmployeeService;
import com.ims.utility.DatabaseConfig;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TestMain {

    private EmployeeDAO employeeDAO;
    private EmployeeService employeeService;

    public TestMain(DatabaseConfig dbConfig) {
        employeeDAO = new EmployeeDAO(dbConfig);
        this.employeeService = new EmployeeService(employeeDAO);
    }

    @Test
    public void testCreateEmployee() {
        Employee employee = new Employee("Alice", "Hows", "alice.hows@example.com", "Finance", 7000.0f);
        employeeDAO.createEmployee(employee);

        Employee retrieved = employeeDAO.readEmployee(employee.getEmployee_id());
        assertNotNull(retrieved);
        assertEquals("Alice", retrieved.getFirst_name());
        assertEquals("Hows",retrieved.getLast_name());
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = new Employee("Mark", "Smith", "mark.smith@example.com", "Business", 55000.0f);
        employeeDAO.createEmployee(employee);

        employee.setFirst_name("Markus");
        employee.setEmail("mark.smith.new@example.com");
        employeeDAO.updateEmployee(employee);

        Employee updatedEmployee = employeeDAO.readEmployee(employee.getEmployee_id());

        assertNotNull(updatedEmployee);
        assertEquals("Markus", updatedEmployee.getFirst_name());
        assertEquals("mark.smith.new@example.com", updatedEmployee.getEmail());
        assertEquals("Business", updatedEmployee.getDepartment());
    }

    @Test
    public void testDeleteEmployee() {
        Employee employee = new Employee("Alice", "Johnson", "alice.johnson@example.com", "Marketing", 65000.0f);
//        employeeDao.createEmployee(employee);
//
//        Employee retrieved = employeeDao.readEmployee(employee.getEmployeeId());
//        assertNotNull(retrieved);

        employeeDAO.deleteEmployee(14);

        Employee deleted = employeeDAO.readEmployee(employee.getEmployee_id());

        assertNull(deleted);
    }

    @Test
    public void testGetAllEmployees() {

        Employee employee1 = new Employee("Tom", "Hanks", "tom.hanks@example.com", "Acting", 80000.0f);
        Employee employee2 = new Employee("Emma", "Watson", "emma.watson@example.com", "Acting", 75000.0f);
        employeeDAO.createEmployee(employee1);
        employeeDAO.createEmployee(employee2);

        List<Employee> employees = employeeDAO.getAllEmployees();

        assertNotNull(employees);
        assertEquals(4, employees.size());

        assertTrue(employees.stream().anyMatch(emp -> emp.getFirst_name().equals("Tom") && emp.getLast_name().equals("Hanks")));
        assertTrue(employees.stream().anyMatch(emp -> emp.getFirst_name().equals("Emma") && emp.getLast_name().equals("Watson")));
    }


}
