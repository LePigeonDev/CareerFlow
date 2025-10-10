package com.dev.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CompanyTable {

    public static boolean createTable() {
        String sql = """
                    CREATE TABLE IF NOT EXISTS Company (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        dateApplication TEXT,
                        companyName TEXT NOT NULL,
                        city TEXT,
                        webSite TEXT,
                        urlApplication TEXT,
                        emailCompany TEXT,
                        phoneCompany TEXT,
                        nameContact TEXT,
                        emailContact TEXT,
                        phoneContact TEXT,
                        channelSending TEXT,
                        status TEXT,
                        relaunchDate TEXT,
                        comment TEXT
                    );
                """;

        try (Connection connection = DBConnect.connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
            System.out.println("[VALIDATION] : Can create table 'Company ': ");
            return true;
        } catch (SQLException e) {
            System.out.println("[ERROR] : Unable to create table 'Company ': " + e.getMessage());
            return false;
        }
    }
}
