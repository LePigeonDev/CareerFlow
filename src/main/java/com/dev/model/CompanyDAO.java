package com.dev.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    public static List<Company> getAllCompany() {
        List<Company> companyList = new ArrayList<>();
        String sql = "SELECT * FROM Company";
        try (Connection connection = DBConnect.connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Company company = new Company(
                    resultSet.getString("dateApplication"),
                    resultSet.getString("companyName"),
                    resultSet.getString("city"),
                    resultSet.getString("webSite"),
                    resultSet.getString("urlApplication"),
                    resultSet.getString("emailCompany"),
                    resultSet.getString("phoneCompany"),
                    resultSet.getString("nameContact"),
                    resultSet.getString("emailContact"),
                    resultSet.getString("phoneContact"),
                    resultSet.getString("channelSending"),
                    resultSet.getString("status"),
                    resultSet.getString("relaunchDate"),
                    resultSet.getString("comment")
                );
                company.setId(resultSet.getInt("id")); 
                companyList.add(company);
            }

        } catch (SQLException e) {
            System.err.println("[ERROR][getAllCompanies] " + e.getMessage());
        }

        return companyList;
    
    }


    public static List<Company> getCompanyById(int id) {
        List<Company> companyList = new ArrayList<>();
        String sql = "SELECT * FROM Company WHERE id = ?";

        try (Connection connection = DBConnect.connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {  
                while (resultSet.next()) {
                    Company c = new Company(
                        resultSet.getString("dateApplication"),
                        resultSet.getString("companyName"),
                        resultSet.getString("city"),
                        resultSet.getString("webSite"),
                        resultSet.getString("urlApplication"),
                        resultSet.getString("emailCompany"),
                        resultSet.getString("phoneCompany"),
                        resultSet.getString("nameContact"),
                        resultSet.getString("emailContact"),
                        resultSet.getString("phoneContact"),
                        resultSet.getString("channelSending"),
                        resultSet.getString("status"),
                        resultSet.getString("relaunchDate"),
                        resultSet.getString("comment")
                    );
                    c.setId(resultSet.getInt("id"));
                    companyList.add(c);
                }
            }
        } catch (SQLException e) {
            System.err.println("[ERROR][getCompanyById] " + e.getMessage());
        }
        return companyList;
    }


    public static List<Company> getCompanyByStatus(String status) {
        List<Company> companyList = new ArrayList<>();
        String sql = "SELECT * FROM Company WHERE status = ?";

        try (Connection connection = DBConnect.connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);
            try (ResultSet resultSet = statement.executeQuery()) {  
                while (resultSet.next()) {
                    Company c = new Company(
                        resultSet.getString("dateApplication"),
                        resultSet.getString("companyName"),
                        resultSet.getString("city"),
                        resultSet.getString("webSite"),
                        resultSet.getString("urlApplication"),
                        resultSet.getString("emailCompany"),
                        resultSet.getString("phoneCompany"),
                        resultSet.getString("nameContact"),
                        resultSet.getString("emailContact"),
                        resultSet.getString("phoneContact"),
                        resultSet.getString("channelSending"),
                        resultSet.getString("status"),
                        resultSet.getString("relaunchDate"),
                        resultSet.getString("comment")
                    );
                    c.setId(resultSet.getInt("id"));
                    companyList.add(c);
                }
            }
        } catch (SQLException e) {
            System.err.println("[ERROR][getCompanyByStatus] " + e.getMessage());
        }
        return companyList;
    }

    public static boolean deleteCompanyById(int id) {
        String sql = "DELETE FROM Company WHERE id = ?";

        try (Connection connection = DBConnect.connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("[ERROR][deleteCompanyById] " + e.getMessage());
            return false;
        }
    }

    public static boolean updateCompanyFielById(String fieldName, String newValue, int id) {
        if (fieldName == "id") {
            return false;
        }
        if (fieldName == "companyName" && (newValue == null || newValue == "")) {
            return false;
        }

        String sql = "UPDATE Company SET " + fieldName + " = ? WHERE id = ?";
        
        try (Connection connection = DBConnect.connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newValue.trim());
            statement.setInt(2, id);
            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("[ERROR][updateCompanyStatusById] " + e.getMessage());
            return false;
        }
    }

    
}
