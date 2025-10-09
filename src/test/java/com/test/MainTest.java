package com.test;

import java.nio.file.Path;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.dev.Entity.Company;
import com.dev.utils.CSVExportJackson;

public class MainTest {
    private static final Path _FILE_ = Path.of("src","main","data","csv","application.csv");
    @Test
    public void testInsertCompany() throws Exception {
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

        CSVExportJackson.write(_FILE_, company, Company.class, true);
        System.out.println("✅ Test d’écriture terminé (vérifie ton fichier Excel)");
    }
}
