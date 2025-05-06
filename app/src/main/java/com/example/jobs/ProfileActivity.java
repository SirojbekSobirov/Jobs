package com.example.jobs;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

public class ProfileActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Button btnSaveProfile;
    private Button btnUploadResume;
    private TextInputLayout tilName, tilEmail, tilPhone, tilSkills, tilExperience, tilEducation;
    private EditText etName, etEmail, etPhone, etSkills, etExperience, etEducation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Enable back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Mening profilim");
        }

        initViews();
        setupBottomNavigation();
        loadProfileData();
        setupListeners();
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        btnUploadResume = findViewById(R.id.btnUploadResume);
        
        tilName = findViewById(R.id.tilName);
        tilEmail = findViewById(R.id.tilEmail);
        tilPhone = findViewById(R.id.tilPhone);
        tilSkills = findViewById(R.id.tilSkills);
        tilExperience = findViewById(R.id.tilExperience);
        tilEducation = findViewById(R.id.tilEducation);
        
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etSkills = findViewById(R.id.etSkills);
        etExperience = findViewById(R.id.etExperience);
        etEducation = findViewById(R.id.etEducation);
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
        
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            
            if (itemId == R.id.navigation_home) {
                finish();
                return true;
            } else if (itemId == R.id.navigation_favorites) {
                startActivity(new Intent(ProfileActivity.this, FavoritesActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.navigation_profile) {
                return true;
            }
            
            return false;
        });
    }
    
    private void loadProfileData() {
        // SharedPreferences yoki boshqa mahalliy xotiradan ma'lumotlarni yuklash
        // TODO: Load data from SharedPreferences
    }
    
    private void setupListeners() {
        btnSaveProfile.setOnClickListener(v -> {
            if (validateInputs()) {
                saveProfileData();
                Toast.makeText(this, "Profil ma'lumotlari saqlandi", Toast.LENGTH_SHORT).show();
            }
        });
        
        btnUploadResume.setOnClickListener(v -> {
            // Rezume yuklash
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, 1);
        });
    }
    
    private boolean validateInputs() {
        boolean isValid = true;
        
        if (etName.getText().toString().trim().isEmpty()) {
            tilName.setError("Ism-familiyani kiriting");
            isValid = false;
        } else {
            tilName.setError(null);
        }
        
        if (etEmail.getText().toString().trim().isEmpty()) {
            tilEmail.setError("Email manzilni kiriting");
            isValid = false;
        } else {
            tilEmail.setError(null);
        }
        
        if (etPhone.getText().toString().trim().isEmpty()) {
            tilPhone.setError("Telefon raqamni kiriting");
            isValid = false;
        } else {
            tilPhone.setError(null);
        }
        
        return isValid;
    }
    
    private void saveProfileData() {
        // Ma'lumotlarni SharedPreferences yoki boshqa mahalliy xotiraga saqlash
        // TODO: Save to SharedPreferences
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Rezume fayli tanlandi
            // TODO: Faylni nusxalash va saqlash
            Toast.makeText(this, "Rezume yuklandi", Toast.LENGTH_SHORT).show();
        }
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