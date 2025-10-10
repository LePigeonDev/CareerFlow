package com.dev.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static final String URL = "jdbc:sqlite:mydatabase.db";

    public static Connection connect() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(URL);
            
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver SQLite introuvable (org.xerial:sqlite-jdbc).", e);
        } catch (SQLException e) {
            throw new RuntimeException("Connexion SQLite échouée: " + e.getMessage(), e);
        }
        
        return connection;
    }
}
