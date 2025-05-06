package com.example.jobs;

import com.google.gson.annotations.SerializedName;

public class Vacancy {
    @SerializedName("id")
    private String id;
    
    private String name;
    
    @SerializedName("alternate_url")
    private String url;
    
    @SerializedName("employer")
    private Employer employer;
    
    @SerializedName("salary")
    private Salary salary;
    
    @SerializedName("published_at")
    private String publishedDate;
    
    @SerializedName("snippet")
    private Snippet snippet;
    
    private boolean isFavorite = false;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
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
    
    public Employer getEmployer() {
        return employer;
    }
    
    public void setEmployer(Employer employer) {
        this.employer = employer;
    }
    
    public Salary getSalary() {
        return salary;
    }
    
    public void setSalary(Salary salary) {
        this.salary = salary;
    }
    
    public String getPublishedDate() {
        return publishedDate;
    }
    
    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }
    
    public Snippet getSnippet() {
        return snippet;
    }
    
    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }
    
    public boolean isFavorite() {
        return isFavorite;
    }
    
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
