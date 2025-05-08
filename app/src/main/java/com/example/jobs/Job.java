package com.example.jobs;

public class Job {
    private String title;
    private String company;
    private String location;
    private String salary;
    private String description;
    private String requirements;

    public Job(String title, String company, String location, String salary, String description, String requirements) {
        this.title = title;
        this.company = company;
        this.location = location;
        this.salary = salary;
        this.description = description;
        this.requirements = requirements;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public String getSalary() {
        return salary;
    }

    public String getDescription() {
        return description;
    }

    public String getRequirements() {
        return requirements;
    }
} 