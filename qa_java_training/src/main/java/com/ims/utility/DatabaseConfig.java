package com.ims.utility;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
        return DriverManager.getConnection(getDatabaseUrl(), getDatabaseUsername(), getDatabasePassword());
    }
}

