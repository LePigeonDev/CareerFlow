package com.test;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.dev.Database.CompanyTable;
import com.dev.Entity.Company;
import com.dev.model.CompanyDAO;
import static org.junit.jupiter.api.Assertions.*;


public class MainTest {
    @BeforeAll
    static void initDB() {
        CompanyTable.createTable();
    }


    @Tag("test:1")
    @DisplayName("Test:1 : The methods can write Company ")
    @Test
    public void testInsertCompany() throws Exception {
        Company company = new Company(
                LocalDate.now().toString(),
                "EcoMobil",
                "Lyon",
                "https://ecomobil.fr",
                "https://ecomobil.fr/offre-42",
                "contact@ecomobil.fr",
                "04 72 00 00 00",
                "Alice Martin",
                "alice.martin@ecomobil.fr",
                "06 12 34 56 78",
                "Email",
                "En Attente",
                LocalDate.now().plusDays(7).toString(),
                "Premier contact positif");

        boolean result = CompanyDAO.insertCompany(company);
        assertTrue(result, "Failed to insert a valid company into the SQLite database.");
    }

    @Tag("test:2")
    @DisplayName("Test:2 : The methods can write Company with empty value")
    @Test
    public void testInsertCompanyWithEmptyValue() throws Exception {
        Company company = new Company(
                LocalDate.now().toString(),
                "EcoMobil",
                null,
                "https://ecomobil.fr",
                "https://ecomobil.fr/offre-42",
                "contact@ecomobil.fr",
                "04 72 00 00 00",
                "Alice Martin",
                "alice.martin@ecomobil.fr",
                null,
                "Email",
                null,
                LocalDate.now().plusDays(7).toString(),
                "Premier contact");
        boolean result = CompanyDAO.insertCompany(company);
        assertTrue(result, "Failed to insert a company with empty optional values.");
    }


    @Tag("test:3")
    @DisplayName("Test:3 : The methods can't write Company with empty Company Name")
    @Test
    public void testInsertCompanyWithCommpanyNameEmpty() throws Exception {
        Company company = new Company(
                LocalDate.now().toString(),
                null,
                null,
                "https://ecomobil.fr",
                "https://ecomobil.fr/offre-42",
                "contact@ecomobil.fr",
                "04 72 00 00 00",
                "Alice Martin",
                "alice.martin@ecomobil.fr",
                null,
                "Email",
                null,
                LocalDate.now().plusDays(7).toString(),
                "Premier contact");
        boolean result = CompanyDAO.insertCompany(company);
        assertFalse(result, "The insert should have failed because the 'companyName' field is empty (NOT NULL in the database).");
    }
}
