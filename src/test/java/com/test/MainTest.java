package com.test;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.dev.Entity.Company;
import com.dev.utils.XlsxWrite;

public class MainTest {

    @Test
    public void testInsertCompany() {
        Company company = new Company(
                LocalDate.now(),
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
                LocalDate.now().plusDays(7),
                "Premier contact positif");

        XlsxWrite.writeToExcel(company);
        System.out.println("✅ Test d’écriture terminé (vérifie ton fichier Excel)");
    }
}
