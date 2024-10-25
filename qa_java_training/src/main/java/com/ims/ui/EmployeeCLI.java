package com.ims.ui;

import com.ims.dao.EmployeeDAO;
import com.ims.model.Employee;
import com.ims.service.EmployeeService;
import com.ims.utility.DatabaseConfig;

import java.util.List;
import java.util.Scanner;

public class EmployeeCLI {
    private final EmployeeService employeeService;
    private final Scanner scanner;

    public EmployeeCLI(DatabaseConfig dbConfig) {
        EmployeeDAO employeeDAO = new EmployeeDAO(dbConfig);
        this.employeeService = new EmployeeService(employeeDAO);
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int choice;
        do {
            System.out.println("\n--- Employee Management System ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employee by ID");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee by ID");
            System.out.println("5. List All Employees");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> viewEmployeeById();
                case 3 -> updateEmployee();
                case 4 -> deleteEmployeeById();
                case 5 -> listAllEmployees();
                case 6 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }

    private void addEmployee() {
        try {
            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            validateEmail(email);  // Validate email format
            System.out.print("Enter department: ");
            String department = scanner.nextLine();
            System.out.print("Enter salary: ");
            float salary = scanner.nextFloat();
            scanner.nextLine(); // Consume newline

            Employee employee = new Employee(firstName, lastName, email, department, salary);
            employeeService.createEmployee(employee);
            System.out.println("Employee added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void updateEmployee() {
        System.out.print("Enter employee ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Employee existingEmployee = employeeService.readEmployee(id);
        if (existingEmployee != null) {
            try {
                System.out.print("Enter new first name (or press enter to keep current): ");
                String firstName = scanner.nextLine();
                if (!firstName.isEmpty()) {
                    existingEmployee.setFirstName(firstName);
                }
                System.out.print("Enter new last name (or press enter to keep current): ");
                String lastName = scanner.nextLine();
                if (!lastName.isEmpty()) {
                    existingEmployee.setLastName(lastName);
                }
                System.out.print("Enter new email (or press enter to keep current): ");
                String email = scanner.nextLine();
                if (!email.isEmpty()) {
                    validateEmail(email);  // Validate email format
                    existingEmployee.setEmail(email);
                }
                System.out.print("Enter new department (or press enter to keep current): ");
                String department = scanner.nextLine();
                if (!department.isEmpty()) {
                    existingEmployee.setDepartment(department);
                }
                System.out.print("Enter new salary (or press enter to keep current): ");
                String salaryInput = scanner.nextLine();
                if (!salaryInput.isEmpty()) {
                    existingEmployee.setSalary(Float.parseFloat(salaryInput));
                }

                employeeService.updateEmployee(existingEmployee);
                System.out.println("Employee updated successfully.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        } else {
            System.out.println("Employee not found.");
        }
    }

    private void validateEmail(String email) {
        if (email == null || !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
    }

    private void viewEmployeeById() {
        System.out.print("Enter employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Employee employee = employeeService.readEmployee(id);
        if (employee != null) {
            System.out.println(employee);
        } else {
            System.out.println("Employee not found.");
        }
    }

    private void deleteEmployeeById() {
        System.out.print("Enter employee ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (employeeService.deleteEmployee(id)) {
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    private void listAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee employee : employees) {
                System.out.println(employee);
            }
        }
    }
}
