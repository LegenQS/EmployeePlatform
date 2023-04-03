package com.qs.security.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataBaseInitializer {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Bean
    CommandLineRunner loadDatabase() {
        return new CommandLineRunner() {

            @Override
            public void run(String... args) throws Exception {

                jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `User` (\n" +
                        "    user_id INT auto_increment PRIMARY KEY,\n" +
                        "    username varchar(30) NOT NULL UNIQUE,\n" +
                        "    email varchar(25) NOT NULL UNIQUE,\n" +
                        "    `password` varchar(20) NOT NULL,\n" +
                        "    create_date timestamp, \n" +
                        "    last_modification_date timestamp, \n" +
                        "    `active` boolean \n" +
                        ");");

                jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `Role` (\n" +
                        "    role_id INT auto_increment PRIMARY KEY,\n" +
                        "    role_name varchar(15),\n" +
                        "    role_description varchar(100),\n" +
                        "    create_date timestamp,\n" +
                        "    last_modification_date timestamp \n" +
                        ");");

                jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS Token (\n" +
                        "    token_id INT auto_increment PRIMARY KEY,\n" +
                        "    token varchar(100), \n" +
                        "    email varchar(25), \n" +
                        "    expiration_date timestamp,\n" +
                        "    create_by INT, \n" +
                        "    FOREIGN KEY (create_by) REFERENCES `User`(user_id)\n" +
                        ");");

                jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS User_Role (\n" +
                        "    user_id INT,\n" +
                        "    role_id INT,\n" +
                        "    PRIMARY KEY(user_id, role_id),\n" +
                        "    FOREIGN KEY (user_id) REFERENCES `User`(user_id),\n" +
                        "    FOREIGN KEY (role_id) REFERENCES `Role`(role_id)\n" +
                        ");");

                jdbcTemplate.execute("insert IGNORE into `Role` values\n" +
                        "(1, 'HR', 'hr roles', now(), now()), " +
                        "(2, 'employee', 'employee role', now(), now());");
            }
        };
    }
}