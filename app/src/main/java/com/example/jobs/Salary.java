package com.example.jobs;

import com.google.gson.annotations.SerializedName;

public class Salary {
    private Integer from;
    private Integer to;
    private String currency;
    
    @SerializedName("gross")
    private boolean isGross;
    
    public Integer getFrom() {
        return from;
    }
    
    public void setFrom(Integer from) {
        this.from = from;
    }
    
    public Integer getTo() {
        return to;
    }
    
    public void setTo(Integer to) {
        this.to = to;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public boolean isGross() {
        return isGross;
    }
    
    public void setGross(boolean gross) {
        isGross = gross;
    }
    
    public String getFormattedSalary() {
        if (from == null && to == null) {
            return "Kelishilgan";
        }
        
        if (from != null && to != null) {
            return String.format("%d - %d %s", from, to, currency);
        } else if (from != null) {
            return String.format("%d+ %s", from, currency);
        } else {
            return String.format("...%d %s", to, currency);
        }
    }
} 