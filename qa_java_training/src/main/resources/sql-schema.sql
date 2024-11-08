DROP schema `ims_db`;

CREATE SCHEMA IF NOT EXISTS `ims_db`;

USE ims_db;

CREATE TABLE IF NOT EXISTS `employees` (
    `employee_id` INT NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(55) NOT NULL,
    `last_name` VARCHAR(55) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `department` VARCHAR(55) NOT NULL,
    `salary` FLOAT NOT NULL,
    PRIMARY KEY (`employee_id`)

);
