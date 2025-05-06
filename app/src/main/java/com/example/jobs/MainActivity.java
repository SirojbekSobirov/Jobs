package com.example.jobs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobs.database.FavoriteRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VacancyAdapter adapter;
    private SearchView searchView;
    private HhApiService apiService;
    private BottomNavigationView bottomNavigationView;
    private FavoriteRepository favoriteRepository;
    private Chip chipIT, chipMarketing, chipFinance, chipRemote;
    private List<String> activeFilters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        favoriteRepository = ((JobsApplication) getApplication()).getFavoriteRepository();
        
        initViews();
        setupBottomNavigation();
        setupChips();
        
        // Joylashuv ruxsatini tekshirish
        checkLocationPermission();
        
        // Standart qidiruvni boshlash
        searchVacancies("android developer");
        
        // Qidiruv maydoni uchun tinglovchi
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String searchQuery = query;
                
                // Faol filterlarni qo'shish
                if (!activeFilters.isEmpty()) {
                    StringBuilder filterQuery = new StringBuilder(query);
                    for (String filter : activeFilters) {
                        filterQuery.append(" ").append(filter);
                    }
                    searchQuery = filterQuery.toString();
                }
                
                searchVacancies(searchQuery);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Matn o'zgarganda qidirishni xohlamasangiz, bu qismni o'zgartiring
                return false;
            }
        });
    }
    
    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        searchView = findViewById(R.id.searchView);
        
        apiService = ApiClient.getClient().create(HhApiService.class);
        
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        
        chipIT = findViewById(R.id.chipIT);
        chipMarketing = findViewById(R.id.chipMarketing);
        chipFinance = findViewById(R.id.chipFinance);
        chipRemote = findViewById(R.id.chipRemote);
    }
    
    private void setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            
            if (itemId == R.id.navigation_home) {
                return true;
            } else if (itemId == R.id.navigation_favorites) {
                startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
                return false;
            } else if (itemId == R.id.navigation_profile) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                return false;
            }
            
            return false;
        });
    }
    
    private void setupChips() {
        chipIT.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateFilter("IT", isChecked);
        });
        
        chipMarketing.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateFilter("Marketing", isChecked);
        });
        
        chipFinance.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateFilter("Moliya", isChecked);
        });
        
        chipRemote.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateFilter("Masofaviy", isChecked);
        });
    }
    
    private void updateFilter(String filter, boolean add) {
        if (add) {
            if (!activeFilters.contains(filter)) {
                activeFilters.add(filter);
            }
        } else {
            activeFilters.remove(filter);
        }
        
        // Agar aktiv filtrlash mavjud bo'lsa, qidiruvni qayta amalga oshirish
        if (!searchView.getQuery().toString().isEmpty()) {
            searchView.setQuery(searchView.getQuery(), true);
        }
    }
    
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }
    
    private void searchVacancies(String query) {
        Call<VacancyResponse> call = apiService.getVacancies(query);
        
        call.enqueue(new Callback<VacancyResponse>() {
            @Override
            public void onResponse(Call<VacancyResponse> call, Response<VacancyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Vacancy> vacancies = response.body().getItems();
                    setupAdapter(vacancies);
                } else {
                    Toast.makeText(MainActivity.this, "Vakansiyalar topilmadi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VacancyResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Xatolik: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void setupAdapter(List<Vacancy> vacancies) {
        if (adapter == null) {
            adapter = new VacancyAdapter(vacancies);
            recyclerView.setAdapter(adapter);
            
            // Vakansiya tanlanganida batafsil ma'lumotlarni ko'rsatish
            adapter.setOnVacancyClickListener((vacancy, position) -> {
                startActivity(VacancyDetailActivity.newIntent(MainActivity.this, vacancy));
            });
            
            // Saqlangan vakansiyalarni boshqarish
            adapter.setOnFavoriteClickListener((vacancy, position) -> {
                if (vacancy.isFavorite()) {
                    // Vacansiyani saqlash
                    favoriteRepository.insert(vacancy);
                    Toast.makeText(MainActivity.this, "Vakansiya saqlandi", Toast.LENGTH_SHORT).show();
                } else {
                    // Vacansiyani o'chirish
                    favoriteRepository.delete(vacancy.getId());
                    Toast.makeText(MainActivity.this, "Vakansiya saqlashdan o'chirildi", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            adapter.updateData(vacancies);
        }
        
        // Saqlangan vakansiyalarni tekshirish
        for (Vacancy vacancy : vacancies) {
            boolean isFavorite = favoriteRepository.isFavorite(vacancy.getId());
            vacancy.setFavorite(isFavorite);
        }
        adapter.notifyDataSetChanged();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        
        // Agar adapter mavjud bo'lsa, sevimli holatlarni yangilash
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
