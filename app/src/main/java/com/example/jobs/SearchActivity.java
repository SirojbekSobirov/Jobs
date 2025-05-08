package com.example.jobs;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchResultsAdapter.OnJobClickListener {
    private BottomNavigationView bottomNavigationView;
    private TextInputEditText etSearch;
    private RecyclerView recyclerView;
    private SearchResultsAdapter adapter;
    private List<Job> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        setupBottomNavigation();
        setupRecyclerView();
        loadTestData();
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        etSearch = findViewById(R.id.etSearch);
        recyclerView = findViewById(R.id.recyclerView);
        searchResults = new ArrayList<>();
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setSelectedItemId(R.id.navigation_search);
        
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            
            if (itemId == R.id.navigation_home) {
                startActivity(new Intent(SearchActivity.this, MainActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.navigation_favorites) {
                startActivity(new Intent(SearchActivity.this, FavoritesActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.navigation_profile) {
                startActivity(new Intent(SearchActivity.this, ProfileActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.navigation_search) {
                return true;
            }
            
            return false;
        });
    }

    private void setupRecyclerView() {
        adapter = new SearchResultsAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadTestData() {
        // Test ma'lumotlari
        searchResults.add(new Job(
            "Android Developer",
            "Tech Company",
            "Tashkent",
            "$2000-3000",
            "Android ilovalar yaratish",
            "Kotlin, Java, Android SDK"
        ));
        
        searchResults.add(new Job(
            "Backend Developer",
            "IT Solutions",
            "Tashkent",
            "$2500-3500",
            "Backend tizimlarni yaratish",
            "Java, Spring, PostgreSQL"
        ));

        adapter.setJobs(searchResults);
    }

    @Override
    public void onJobClick(Job job) {
        // Ish o'rinini bosganingizda nima qilish kerakligi
        Toast.makeText(this, "Tanlangan ish: " + job.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
} 