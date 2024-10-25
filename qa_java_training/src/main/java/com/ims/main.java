package com.ims;

import com.ims.ui.EmployeeCLI;
import com.ims.utility.DatabaseConfig;

import java.sql.SQLException;

public class main {

    public static void main(String[] args) throws SQLException {
        DatabaseConfig dbConfig = new DatabaseConfig();
        EmployeeCLI cli = new EmployeeCLI(dbConfig);
//        dbConfig.getConnection();
        cli.start();
    }
}
