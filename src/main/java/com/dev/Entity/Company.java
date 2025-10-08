package com.dev.Entity;

import java.time.LocalDate;
import com.dev.Annotation.ExcelColumn; 

public class Company {
    private static int counter = 0;
    
    @ExcelColumn(header = "ID", order = 0)
    private int id;

    @ExcelColumn(header = "Date_Candidature", order = 1)
    private LocalDate dateApplication;

    @ExcelColumn(header = "Entreprise", order = 2)
    private String companyName;
    
    @ExcelColumn(header = "Ville", order = 3)
    private String city;

    @ExcelColumn(header = "Site_Web", order = 4)
    private String webSite;
    
    @ExcelColumn(header = "URL_Offre", order = 5)
    private String url;

    @ExcelColumn(header = "Email_Entreprise", order = 6)
    private String emailCompany;

    @ExcelColumn(header = "Téléphone_entreprise", order = 7)
    private String phoneCompany;

    @ExcelColumn(header = "Contact_Nom", order = 8)
    private String nameContact;

    @ExcelColumn(header = "Contact_Email", order = 9)
    private String emailContact;

    @ExcelColumn(header = "Contact_Téléphone", order = 10)
    private String phoneContact;

    @ExcelColumn(header = "Canal_Envoi", order = 11)
    private String channelSending;

    @ExcelColumn(header = "Status", order = 12)
    private String status;

    @ExcelColumn(header = "Date_Relance", order = 13)
    private LocalDate relaunchDate;

    @ExcelColumn(header = "Commentaire", order = 14)
    private String comment;

    public Company(LocalDate dateApplication, String companyName, String city, String webSite,
                String url, String emailCompany, String phoneCompany, String nameContact,
                String emailContact, String phoneContact, String channelSending, String status,
                LocalDate relaunchDate, String comment) {

        this.id += counter;
        this.dateApplication = dateApplication;
        this.companyName = companyName;
        this.city = city;
        this.webSite = webSite;
        this.url = url;
        this.emailCompany = emailCompany;
        this.phoneCompany = phoneCompany;
        this.nameContact = nameContact;
        this.emailContact = emailContact;
        this.phoneContact = phoneContact;
        this.channelSending = channelSending;
        this.status = status;
        this.relaunchDate = relaunchDate;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDateApplication() {
        return dateApplication;
    }

    public void setDateApplication(LocalDate dateApplication) {
        this.dateApplication = dateApplication;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmailCompany() {
        return emailCompany;
    }

    public void setEmailCompany(String emailCompany) {
        this.emailCompany = emailCompany;
    }

    public String getPhoneCompany() {
        return phoneCompany;
    }

    public void setPhoneCompany(String phoneCompany) {
        this.phoneCompany = phoneCompany;
    }

    public String getNameContact() {
        return nameContact;
    }

    public void setNameContact(String nameContact) {
        this.nameContact = nameContact;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    public String getPhoneContact() {
        return phoneContact;
    }

    public void setPhoneContact(String phoneContact) {
        this.phoneContact = phoneContact;
    }

    public String getChannelSending() {
        return channelSending;
    }

    public void setChannelSending(String channelSending) {
        this.channelSending = channelSending;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getRelaunchDate() {
        return relaunchDate;
    }

    public void setRelaunchDate(LocalDate relaunchDate) {
        this.relaunchDate = relaunchDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
