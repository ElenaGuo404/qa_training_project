package com.ims.dao;

import com.ims.model.Employee;
import com.ims.utility.DatabaseConfig;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeDAO {

    private final DatabaseConfig dbConfig;

    public EmployeeDAO(DatabaseConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    public void createEmployee(Employee employee) {
        String sql = "INSERT INTO employees (first_name, last_name, email, department, salary) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dbConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, employee.getFirst_name());
            preparedStatement.setString(2, employee.getLast_name());
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.setString(4, employee.getDepartment());
            preparedStatement.setFloat(5, employee.getSalary());
            preparedStatement.executeUpdate();

            // Retrieve the auto-generated employee ID
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    employee.setEmployee_id(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating employee: " + e.getMessage());
        }
    }

    public Employee readEmployee(int employee_id) {
        System.out.println("1-----------------");

        String sql = "SELECT * FROM employees WHERE employee_id = ?";
        Employee employee = null;
        try (Connection connection = dbConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            System.out.println("2-----------------");

            preparedStatement.setInt(1, employee_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("3-----------------");

                employee = new Employee(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("department"),
                        resultSet.getFloat("salary")
                );
                System.out.println("4-----------------");

                employee.setEmployee_id(resultSet.getInt("employee_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading employee: " + e.getMessage());
        }
        return employee;
    }

    public Employee updateEmployee(Employee employee) {
        String sql = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, department = ?, salary = ? WHERE employee_id = ?";
        try (Connection connection = dbConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employee.getFirst_name());
            preparedStatement.setString(2, employee.getLast_name());
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.setString(4, employee.getDepartment());
            preparedStatement.setFloat(5, employee.getSalary());
            preparedStatement.setInt(6, employee.getEmployee_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating employee: " + e.getMessage());
        }
        return employee;
    }

    public boolean deleteEmployee(int employee_id) {
        String sql = "DELETE FROM employees WHERE employee_id = ?";
        try (Connection connection = dbConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, employee_id);
            return preparedStatement.executeUpdate() > 0; // Return true if at least one row was deleted
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting employee: " + e.getMessage());
        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";

        try (Connection connection = dbConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Employee employee = new Employee(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("department"),
                        resultSet.getFloat("salary")
                );
                employee.setEmployee_id(resultSet.getInt("employee_id"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving employees: " + e.getMessage());
        }
        return employees;
    }
}
