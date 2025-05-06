package com.example.jobs.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_vacancies")
public class FavoriteVacancy {
    
    @PrimaryKey
    @NonNull
    private String id;
    
    private String name;
    private String url;
    private String employerName;
    private String employerLogo;
    private String salaryText;
    private String requirements;
    private String responsibilities;
    private String publishedDate;
    private long savedTime;
    
    public FavoriteVacancy(String id, String name, String url, String employerName, 
                         String employerLogo, String salaryText, String requirements, 
                         String responsibilities, String publishedDate) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.employerName = employerName;
        this.employerLogo = employerLogo;
        this.salaryText = salaryText;
        this.requirements = requirements;
        this.responsibilities = responsibilities;
        this.publishedDate = publishedDate;
        this.savedTime = System.currentTimeMillis();
    }
    
    @NonNull
    public String getId() {
        return id;
    }
    
    public void setId(@NonNull String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getEmployerName() {
        return employerName;
    }
    
    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }
    
    public String getEmployerLogo() {
        return employerLogo;
    }
    
    public void setEmployerLogo(String employerLogo) {
        this.employerLogo = employerLogo;
    }
    
    public String getSalaryText() {
        return salaryText;
    }
    
    public void setSalaryText(String salaryText) {
        this.salaryText = salaryText;
    }
    
    public String getRequirements() {
        return requirements;
    }
    
    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }
    
    public String getResponsibilities() {
        return responsibilities;
    }
    
    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }
    
    public String getPublishedDate() {
        return publishedDate;
    }
    
    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }
    
    public long getSavedTime() {
        return savedTime;
    }
    
    public void setSavedTime(long savedTime) {
        this.savedTime = savedTime;
    }
} 