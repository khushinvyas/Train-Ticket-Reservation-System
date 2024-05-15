package com.khushin.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.khushin.beans.TrainException;
import com.khushin.constant.ResponseCode;

public class DBUtil {
    private static Connection con;

    static {
        String connectionString = "jdbc:mysql://localhost:3306/Reservation"; // Modify this with your MySQL server address and database name
        String username = "root"; // Replace "your_username" with your MySQL username
        String password = "password"; // Replace "your_password" with your MySQL password

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(connectionString, username, password);
            System.out.println("Connection Success!!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws TrainException {
        if (con == null)
            throw new TrainException(ResponseCode.DATABASE_CONNECTION_FAILURE);
        return con;
    }
}