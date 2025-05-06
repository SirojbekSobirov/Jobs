package com.example.jobs;

import java.util.List;

public class VacancyResponse {
    private List<Vacancy> items;

    public List<Vacancy> getItems() {
        return items;
    }

    public void setItems(List<Vacancy> items) {
        this.items = items;
    }
}
