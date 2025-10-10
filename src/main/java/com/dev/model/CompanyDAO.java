package com.dev.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.dev.Database.DBConnect;
import com.dev.Entity.Company;

public class CompanyDAO {

    public static boolean insertCompany(Company company) {
        String sql = """
            INSERT INTO Company(
                dateApplication,
                companyName,
                city,
                webSite,
                urlApplication,
                emailCompany,
                phoneCompany,
                nameContact,
                emailContact,
                phoneContact,
                channelSending,
                status,
                relaunchDate,
                comment
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection connection = DBConnect.connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1,  company.getDateApplication());
            statement.setString(2,  company.getCompanyName());
            statement.setString(3,  company.getCity());
            statement.setString(4,  company.getWebSite());
            statement.setString(5,  company.getUrlApplication());
            statement.setString(6,  company.getEmailCompany());
            statement.setString(7,  company.getPhoneCompany());
            statement.setString(8,  company.getNameContact());
            statement.setString(9,  company.getEmailContact());
            statement.setString(10, company.getPhoneContact());
            statement.setString(11, company.getChannelSending());
            statement.setString(12, company.getStatus());
            statement.setString(13, company.getRelaunchDate());
            statement.setString(14, company.getComment());

            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("[ERROR][insertCompany] " + e.getMessage());
            return false;
        }
    }
}
