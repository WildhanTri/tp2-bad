package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBUtil {
    
    public static Connection getConnection() throws SQLException{
        String url = "jdbc:mysql://localhost:3306/tp2-bad";
        String user = "root";
        String password = "root";
        
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to MySQL database");
            return conn;
            // Use the conn object to execute SQL queries and update statements
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
    
    
    public static void closeConnection(Connection con) throws SQLException{
        if(con!=null){
            con.close();
            System.out.println("connection closed");
        }
    }	
}