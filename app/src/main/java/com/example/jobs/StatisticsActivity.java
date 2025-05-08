package com.example.jobs;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

public class StatisticsActivity extends AppCompatActivity {
    private TextView totalApplications;
    private TextView acceptedApplications;
    private TextView rejectedApplications;
    private TextView activeDays;
    private TextView totalViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeViews();
        loadStatistics();
    }

    private void initializeViews() {
        totalApplications = findViewById(R.id.totalApplications);
        acceptedApplications = findViewById(R.id.acceptedApplications);
        rejectedApplications = findViewById(R.id.rejectedApplications);
        activeDays = findViewById(R.id.activeDays);
        totalViews = findViewById(R.id.totalViews);
    }

    private void loadStatistics() {
        // Bu yerda statistikani yuklash logikasi
        // Masalan, API dan yoki ma'lumotlar bazasidan
        // Hozircha test ma'lumotlari
        totalApplications.setText("Jami arizalar: 25");
        acceptedApplications.setText("Qabul qilingan: 15");
        rejectedApplications.setText("Rad etilgan: 10");
        activeDays.setText("Faol kunlar: 30");
        totalViews.setText("Jami ko'rishlar: 150");
    }
} 