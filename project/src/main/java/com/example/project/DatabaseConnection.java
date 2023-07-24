package com.example.project;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 */
public class DatabaseConnection {
    /**
     *
     */
    public Connection databaseLink;

    /**
     * @return
     */
    public Connection getConnection(){
        String databaseName = "henry";
        String databaseUser = "root";
        String databasePassword = "hunberry143";
        String url = "jdbc:mysql://127.0.0.1/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url,databaseUser,databasePassword);
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return databaseLink;
    }
}
