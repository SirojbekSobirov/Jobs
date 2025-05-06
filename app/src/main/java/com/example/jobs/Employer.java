package com.example.jobs;

import com.google.gson.annotations.SerializedName;

public class Employer {
    private String name;
    
    @SerializedName("logo_urls")
    private LogoUrls logoUrls;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public LogoUrls getLogoUrls() {
        return logoUrls;
    }
    
    public void setLogoUrls(LogoUrls logoUrls) {
        this.logoUrls = logoUrls;
    }
    
    public static class LogoUrls {
        @SerializedName("90")
        private String logo90;
        
        public String getLogo90() {
            return logo90;
        }
        
        public void setLogo90(String logo90) {
            this.logo90 = logo90;
        }
    }
} 