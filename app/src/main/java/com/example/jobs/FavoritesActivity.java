package com.example.jobs;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobs.database.FavoriteRepository;
import com.example.jobs.database.FavoriteVacancy;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoriteVacancyAdapter adapter;
    private BottomNavigationView bottomNavigationView;
    private FavoriteRepository favoriteRepository;
    private TextView tvEmptyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // Enable back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Saqlangan vakansiyalar");
        }

        favoriteRepository = ((JobsApplication) getApplication()).getFavoriteRepository();

        initViews();
        setupBottomNavigation();
        loadFavorites();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        tvEmptyState = findViewById(R.id.tvEmptyState);
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setSelectedItemId(R.id.navigation_favorites);
        
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            
            if (itemId == R.id.navigation_home) {
                finish();
                return true;
            } else if (itemId == R.id.navigation_favorites) {
                return true;
            } else if (itemId == R.id.navigation_profile) {
                startActivity(new Intent(FavoritesActivity.this, ProfileActivity.class));
                finish();
                return true;
            }
            
            return false;
        });
    }

    private void loadFavorites() {
        favoriteRepository.getAllFavorites().observe(this, favoriteVacancies -> {
            if (favoriteVacancies != null && !favoriteVacancies.isEmpty()) {
                tvEmptyState.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                
                if (adapter == null) {
                    adapter = new FavoriteVacancyAdapter(favoriteVacancies);
                    recyclerView.setAdapter(adapter);
                    
                    adapter.setOnItemClickListener((vacancy, position) -> {
                        // TODO: Implement detail view for saved vacancies
                    });
                    
                    adapter.setOnDeleteClickListener((vacancy, position) -> {
                        favoriteRepository.delete(vacancy.getId());
                    });
                } else {
                    adapter.updateData(favoriteVacancies);
                }
            } else {
                tvEmptyState.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 