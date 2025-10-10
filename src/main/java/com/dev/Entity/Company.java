package com.dev.Entity;

import java.time.format.DateTimeFormatter;

public class Company {

    // ==== Constantes utiles (colonnes + format date SQLite en TEXT) ====
    public static final String TABLE_NAME = "Company";
    public static final String COL_ID = "id";
    public static final String COL_DATE_APPLICATION = "dateApplication";
    public static final String COL_COMPANY_NAME = "companyName";
    public static final String COL_CITY = "city";
    public static final String COL_WEBSITE = "webSite";
    public static final String COL_URL_APPLICATION = "urlApplication";
    public static final String COL_EMAIL_COMPANY = "emailCompany";
    public static final String COL_PHONE_COMPANY = "phoneCompany";
    public static final String COL_NAME_CONTACT = "nameContact";
    public static final String COL_EMAIL_CONTACT = "emailContact";
    public static final String COL_PHONE_CONTACT = "phoneContact";
    public static final String COL_CHANNEL_SENDING = "channelSending";
    public static final String COL_STATUS = "status";
    public static final String COL_RELAUNCH_DATE = "relaunchDate";
    public static final String COL_COMMENT = "comment";

    // ==== Champs ====
    private Integer id; // null avant insertion (AUTOINCREMENT en DB)

    private String dateApplication;

    private String companyName;
    private String city;
    private String webSite;
    private String urlApplication;
    private String emailCompany;
    private String phoneCompany;
    private String nameContact;
    private String emailContact;
    private String phoneContact;
    private String channelSending;
    private String status;

    private String relaunchDate;

    private String comment;

    // ==== Constructeurs ====
    public Company() {
    }

    /** Constructeur pour créer un nouvel enregistrement (id géré par SQLite). */
    public Company(String dateApplication, String companyName, String city, String webSite,
            String urlApplication, String emailCompany, String phoneCompany, String nameContact,
            String emailContact, String phoneContact, String channelSending, String status,
            String relaunchDate, String comment) {
        this.dateApplication = dateApplication;
        this.companyName = companyName;
        this.city = city;
        this.webSite = webSite;
        this.urlApplication = urlApplication;
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

    // ==== Getters / Setters ====
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDateApplication() {
        return dateApplication;
    }

    public void setDateApplication(String dateApplication) {
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

    public String getUrlApplication() {
        return urlApplication;
    }

    public void setUrlApplication(String urlApplication) {
        this.urlApplication = urlApplication;
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

    public String getRelaunchDate() {
        return relaunchDate;
    }

    public void setRelaunchDate(String relaunchDate) {
        this.relaunchDate = relaunchDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
