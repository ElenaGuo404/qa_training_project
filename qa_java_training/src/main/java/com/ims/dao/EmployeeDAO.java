package com.ims.dao;

import com.ims.model.Employee;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    Dotenv dotenv = Dotenv.load();

    String url = dotenv.get("DB_URL");
    String username = dotenv.get("DB_USERNAME");
    String password = dotenv.get("DB_PASSWORD");

    // JDBC Connection
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public void createEmployee(Employee employee) {
        String sql = "INSERT INTO employees (first_name, last_name, email, department, salary) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.setString(4, employee.getDepartment());
            preparedStatement.setFloat(5, employee.getSalary());
            preparedStatement.executeUpdate();

            // Retrieve the auto-generated employee ID
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    employee.setEmployeeId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Employee readEmployee(int employee_id){
        String sql = "SELECT * FROM employees WHERE employee_id = ?";
        Employee employee = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, employee_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employee = new Employee(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("department"),
                        resultSet.getFloat("salary")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    };

    public void updateEmployee(Employee employee){
        String sql = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, department = ?, salary = ? WHERE employee_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.setString(4, employee.getDepartment());
            preparedStatement.setFloat(5, employee.getSalary());
            preparedStatement.setInt(6, employee.getEmployeeId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };

    public void deleteEmployee(int employee_id){
        String sql = "DELETE FROM employees WHERE employee_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, employee_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };

    public List<Employee> getAllEmployees(){
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";

        try (Connection connection = getConnection();
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
                // Set the employeeId after retrieving from the database
                employee.setEmployeeId(resultSet.getInt("employee_id"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    };


}
