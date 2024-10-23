package com.ims.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DBConnection {

    public static void main(String[] args) {
        // Database connection variables
        String url = "jdbc:mysql://localhost:3306/ims_db";
        String username = "root";
        String password = "root";


        // Establishing the connection
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            // Executing a query
            String sql = "SELECT * FROM employees";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    // Process each row of the result set
                    int id = rs.getInt("employee_id");
                    String name = rs.getString("first_name");
                    // Do something with the data
                    System.out.println("ID: " + id + ", Name: " + name);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
