package com.ims.utility;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.*;

@Configuration
public class DatabaseConfig {

    @Bean
    public Dotenv dotenv() {
        return Dotenv.load();
    }

    public String getDatabaseUrl() {
        return dotenv().get("DB_URL");
    }

    public String getDatabaseUsername() {
        return dotenv().get("DB_USERNAME");
    }

    public String getDatabasePassword() {
        return dotenv().get("DB_PASSWORD");
    }

    public Connection getConnection() throws SQLException {
        System.out.println("Database Connected");
        return DriverManager.getConnection(getDatabaseUrl(), getDatabaseUsername(), getDatabasePassword());
    }

//    public void testConnection() {
//        try (Connection conn = getConnection()) {
//            // Executing a query
//            String sql = "SELECT * FROM employees";
//            try (Statement stmt = conn.createStatement();
//                 ResultSet rs = stmt.executeQuery(sql)) {
//                while (rs.next()) {
//                    // Process each row of the result set
//                    int id = rs.getInt("employee_id");
//                    String name = rs.getString("first_name");
//                    // Do something with the data
//                    System.out.println("ID: " + id + ", Name: " + name);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        DatabaseConfig db = new DatabaseConfig();
//        db.testConnection();
//    }
}


