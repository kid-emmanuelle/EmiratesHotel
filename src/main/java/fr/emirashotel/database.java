package fr.emirashotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {
    public static Connection connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/emirates", "root", "");
            System.out.println("Connected to the database successfully!");
            return connect;
        } catch (Exception e) {
            System.err.println("Error connecting to the database:");
            e.printStackTrace();
        }
        return null;
    }
}
