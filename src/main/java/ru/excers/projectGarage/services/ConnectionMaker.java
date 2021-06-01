package ru.excers.projectGarage.services;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionMaker {
    private static String URL = "jdbc:postgresql://localhost:5432/spring_pgd";
    private static String USERNAME = "postgres";
    private static String PASSWORD = "1";
    private static Connection connection;

    public Connection makeConnection(){


            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        return  connection;

    }

}
