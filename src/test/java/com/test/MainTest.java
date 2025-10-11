package com.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.dev.Database.CompanyTable;
import com.dev.Database.DBConnect;
import com.dev.Entity.Company;
import com.dev.model.CompanyDAO;
import java.sql.Statement;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static String today()   { return DTF.format(LocalDate.now()); }
    private static String in7Days() { return DTF.format(LocalDate.now().plusDays(7)); }

    @BeforeAll
    static void initDB() {
        boolean ok = CompanyTable.createTable();
        assertTrue(ok, "Échec de la création de la table 'Company' avant les tests.");
    }

    @BeforeEach
void resetDbAndSeed() {
    // 1) Vider la table
    // 2) Réinitialiser l’AUTOINCREMENT (sqlite_sequence)
    // 3) Insérer 5 entreprises différentes
    try (Connection c = DBConnect.connect(); Statement st = c.createStatement()) {
        st.executeUpdate("DELETE FROM Company;");
        st.executeUpdate("DELETE FROM sqlite_sequence WHERE name='Company';");
    } catch (SQLException e) {
        fail("Reset DB échoué : " + e.getMessage());
    }

    // Liste des 5 seeds
    Company[] seeds = {
        new Company(
            today(), "EcoMobil", "Lyon",
            "https://ecomobil.fr", "https://ecomobil.fr/offre-42",
            "contact@ecomobil.fr", "04 72 00 00 00",
            "Alice Martin", "alice.martin@ecomobil.fr", "06 12 34 56 78",
            "Email", "Candidaté", in7Days(), "Entreprise de mobilité durable."
        ),
        new Company(
            today(), "Storaix", "Aix-les-Bains",
            "https://storaix.fr", "https://storaix.fr/pergolas",
            "contact@storaix.fr", "04 79 00 00 00",
            "Bob Durand", "bob@storaix.fr", "06 11 22 33 44",
            "LinkedIn", "Refusé", in7Days(), "Fabricant de stores et pergolas."
        ),
        new Company(
            today(), "GreenTech", "Grenoble",
            "https://greentech.io", "https://greentech.io/jobs",
            "hr@greentech.io", "04 76 12 12 12",
            "Chloé Dubois", "chloe@greentech.io", "07 77 88 99 00",
            "Site Web", "Candidaté", in7Days(), "Startup énergies renouvelables."
        ),
        new Company(
            today(), "CyberWave", "Paris",
            "https://cyberwave.fr", "https://cyberwave.fr/carrieres",
            "jobs@cyberwave.fr", "01 40 10 10 10",
            "David Lefevre", "david@cyberwave.fr", "06 45 23 78 90",
            "Email", "Refusé", in7Days(), "Société cybersécurité française."
        ),
        new Company(
            today(), "AeroSoft", "Toulouse",
            "https://aerosoft.fr", "https://aerosoft.fr/recrutement",
            "rh@aerosoft.fr", "05 34 56 78 90",
            "Emma Rossi", "emma.rossi@aerosoft.fr", "06 98 76 54 32",
            "Indeed", "Refusé", in7Days(), "Entreprise aéronautique logicielle."
        )
    };

    for (Company c : seeds) {
        assertTrue(CompanyDAO.insertCompany(c),
            "Insertion échouée pour " + c.getCompanyName());
    }

    System.out.println("[SEED] 5 entreprises insérées pour les tests.");
}

    // ----------------- TESTS -----------------

    @Tag("test:1")
    @DisplayName("Test:1 - Insert d'une entreprise valide")
    @Test
    public void testInsertCompany() {
        Company company = newCompany(
                DTF.format(LocalDate.now()),
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
                DTF.format(LocalDate.now().plusDays(7)),
                "Premier contact positif"
        );

        boolean result = CompanyDAO.insertCompany(company);
        assertTrue(result, "L’insertion d’une entreprise valide devrait réussir.");
    }

    @Tag("test:2")
    @DisplayName("Test:2 - Insert avec champs facultatifs vides")
    @Test
    public void testInsertCompanyWithEmptyValue() {
        Company company = newCompany(
                DTF.format(LocalDate.now()),
                "Storaix",
                null, // city vide
                "https://ecomobil.fr",
                "https://ecomobil.fr/offre-42",
                "contact@ecomobil.fr",
                "04 72 00 00 00",
                "Alice Martin",
                "alice.martin@ecomobil.fr",
                null, // phoneContact vide
                "Email",
                null, // status vide
                DTF.format(LocalDate.now().plusDays(7)),
                "Premier contact"
        );

        boolean result = CompanyDAO.insertCompany(company);
        assertTrue(result, "L’insertion avec des champs facultatifs (null/vides) devrait réussir.");
    }

    @Tag("test:3")
    @DisplayName("Test:3 - Insert refusé si companyName est null (NOT NULL)")
    @Test
    public void testInsertCompanyWithCommpanyNameEmpty() {
        Company company = newCompany(
                DTF.format(LocalDate.now()),
                null, 
                "Lyon",
                "https://ecomobil.fr",
                "https://ecomobil.fr/offre-42",
                "contact@ecomobil.fr",
                "04 72 00 00 00",
                "Alice Martin",
                "alice.martin@ecomobil.fr",
                "04 72 00 00 00",
                "Email",
                "En Attente",
                DTF.format(LocalDate.now().plusDays(7)),
                "Premier contact"
        );

        boolean result = CompanyDAO.insertCompany(company);
        assertFalse(result, "L’insertion devrait échouer car 'companyName' est NOT NULL en base.");
    }

    @Tag("test:4")
    @DisplayName("Test:4 - getAllCompany() retourne des lignes")
    @Test
    public void testGetAllCompany() {
        List<Company> companies = CompanyDAO.getAllCompany();
        assertAll(
            () -> assertNotNull(companies, "La liste retournée ne doit pas être null."),
            () -> assertFalse(companies.isEmpty(), "La liste ne devrait pas être vide après des insertions.")
        );
    }

    @Tag("test:5")
    @DisplayName("Test:5 - getCompanyById(1) retourne EcoMobil (si id=1 existe)")
    @Test
    public void testGetCompanyById() {
        List<Company> companyByID = CompanyDAO.getCompanyById(1);

        assertAll(
            () -> assertNotNull(companyByID, "La recherche par ID ne doit pas retourner null."),
            () -> assertFalse(companyByID.isEmpty(), "Aucune entreprise trouvée pour id=1."),
            () -> assertEquals("EcoMobil", companyByID.get(0).getCompanyName(),
                    "Le nom retourné pour id=1 devrait être 'EcoMobil'.")
        );
    }

    @Tag("test:6")
    @DisplayName("Test:6  getCompanyByStatus(status) retourne uniquement les company avec le status souhaité(Candidaté, Refusé)")
    @Test
    public void testGetCompanyByStatus() {
        List<Company> companyByStatusCandidate = CompanyDAO.getCompanyByStatus("Candidaté");
        List<Company> companyByStatusRefuse = CompanyDAO.getCompanyByStatus("Refusé");
        assertAll(
            () -> assertNotNull(companyByStatusCandidate, "La recherche par Status ne doit pas retourner null."),
            () -> assertFalse(companyByStatusCandidate.isEmpty(), "Aucune entreprise trouvée pour le status = Candidaté."),
            () -> assertNotNull(companyByStatusRefuse, "La recherche par Status ne doit pas retourner null."),
            () -> assertFalse(companyByStatusRefuse.isEmpty(), "Aucune entreprise trouvée pour le status = Refusé.")
        );
        System.out.println("| Obtenir les par Status Company |");
        readList(companyByStatusCandidate);
        readList(companyByStatusRefuse);
    }

    @Tag("test:7")
    @DisplayName("Test:7 - Supprimer une entreprise par ID")
    @Test
    public void testDeleteCompanyById() {
        boolean result = CompanyDAO.deleteCompanyById(1);
        assertTrue(result, "Suppression d'une entreprise devrait réussir.");
        List<Company> allCompany = CompanyDAO.getAllCompany();
        System.out.println("| Suppresion Company |");
        readList(allCompany);
    }

    @Tag("test:8")
    @DisplayName("Test:8 - Mettre à jour n'importe quel champ par ID")
    @Test
    public void testUpdateCompanyFielById() {
        boolean resultCity = CompanyDAO.updateCompanyFielById("city", "Rumilly", 2 );
        boolean resultCityWithSpace = CompanyDAO.updateCompanyFielById("city", "Rumilly ", 2 );
        boolean resultId = CompanyDAO.updateCompanyFielById("id", "1", 20 );
        boolean resultCompanyNameNothing = CompanyDAO.updateCompanyFielById("companyName", "", 3 );
        boolean resultCompanyNameNull = CompanyDAO.updateCompanyFielById("companyName", null, 2 );
        boolean resultCompanyName = CompanyDAO.updateCompanyFielById("companyName", "Sandisk", 1 );
        boolean resultToday = CompanyDAO.updateCompanyFielById("dateApplication", DTF.format(LocalDate.now().plusDays(7)), 1 );

        assertAll(
            () -> assertTrue(resultCity, "Mise à jour d'une ville devrait réussir."),
            () -> assertTrue(resultCityWithSpace, "Mise à jour d'une ville avec un espace devrait réussir."),
            () -> assertFalse(resultId, "Mise à jour d'une id devrait échoué."),
            () -> assertFalse(resultCompanyNameNothing, "Mise à jour d'un nom d'entreprise par '' devrait échoué."),
            () -> assertFalse(resultCompanyNameNull, "Mise à jour d'un nom d'entreprise par null devrait échoué."),
            () -> assertFalse(resultCompanyNameNull, "Mise à jour d'un nom d'entreprise par null devrait échoué."),
            () -> assertTrue(resultCompanyName, "Mise à jour d'un nom d'entreprise devrait réussir."),
            () -> assertTrue(resultToday, "Mise à jour d'une date devrait réussir.")
        );
        
        List<Company> allCompany = CompanyDAO.getAllCompany();
        System.out.println("| Update Company |");
        readList(allCompany);
    }
    // ----------------- HELPERS -----------------

    /** Petit helper pour créer rapidement une Company lisible dans les tests. */
    private static Company newCompany(
            String dateApplication,
            String companyName,
            String city,
            String webSite,
            String urlApplication,
            String emailCompany,
            String phoneCompany,
            String nameContact,
            String emailContact,
            String phoneContact,
            String channelSending,
            String status,
            String relaunchDate,
            String comment
    ) {
        return new Company(
                dateApplication, companyName, city, webSite, urlApplication,
                emailCompany, phoneCompany, nameContact, emailContact, phoneContact,
                channelSending, status, relaunchDate, comment
        );
    }


    private void readList(List<Company> companyList) {
        for (Company company : companyList) {
            System.out.println("------------------------------------------------------");
            System.out.println("ID                : " + company.getId());
            System.out.println("Date publication  : " + company.getDateApplication());
            System.out.println("Nom entreprise    : " + company.getCompanyName());
            System.out.println("Ville             : " + company.getCity());
            System.out.println("Site web          : " + company.getWebSite());
            System.out.println("URL de l’offre    : " + company.getUrlApplication());
            System.out.println("Email entreprise  : " + company.getEmailCompany());
            System.out.println("Téléphone entr.   : " + company.getPhoneCompany());
            System.out.println("Nom du contact    : " + company.getNameContact());
            System.out.println("Email contact     : " + company.getEmailContact());
            System.out.println("Téléphone contact : " + company.getPhoneContact());
            System.out.println("Canal d’envoi     : " + company.getChannelSending());
            System.out.println("Statut            : " + company.getStatus());
            System.out.println("Date de relance   : " + company.getRelaunchDate());
            System.out.println("Commentaire       : " + company.getComment());
            System.out.println("------------------------------------------------------\n");
        }
    }

}
