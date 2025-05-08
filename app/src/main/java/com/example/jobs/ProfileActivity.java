package com.example.jobs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;

public class ProfileActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private MaterialButton btnSave;
    private MaterialButton btnUploadResume;
    private MaterialButton btnLogout;
    private TextInputEditText etFullName;
    private TextInputEditText etEmail;
    private TextInputEditText etPhone;
    private ChipGroup chipGroupSkills;
    private MaterialButton btnAddSkill;
    private CircleImageView ivProfile;
    private List<String> skills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        setupBottomNavigation();
        loadProfileData();
        setupListeners();
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        btnSave = findViewById(R.id.btnSave);
        btnUploadResume = findViewById(R.id.btnUploadResume);
        btnLogout = findViewById(R.id.btnLogout);
        
        ivProfile = findViewById(R.id.ivProfile);
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        chipGroupSkills = findViewById(R.id.chipGroupSkills);
        btnAddSkill = findViewById(R.id.btnAddSkill);
        
        skills = new ArrayList<>();
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
        
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            
            if (itemId == R.id.navigation_home) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.navigation_favorites) {
                startActivity(new Intent(ProfileActivity.this, FavoritesActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.navigation_search) {
                startActivity(new Intent(ProfileActivity.this, SearchActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.navigation_profile) {
                return true;
            }
            
            return false;
        });
    }
    
    private void loadProfileData() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        
        String fullName = sharedPreferences.getString("fullName", "");
        String email = sharedPreferences.getString("email", "");
        String phone = sharedPreferences.getString("phone", "");
        
        etFullName.setText(fullName);
        etEmail.setText(email);
        etPhone.setText(phone);
        
        // Test ko'nikmalar
        addSkill("Android Development");
        addSkill("Java");
        addSkill("Kotlin");
    }
    
    private void setupListeners() {
        ivProfile.setOnClickListener(v -> openImagePicker());
        findViewById(R.id.btnEditImage).setOnClickListener(v -> openImagePicker());
        
        btnAddSkill.setOnClickListener(v -> showAddSkillDialog());
        
        btnSave.setOnClickListener(v -> saveProfile());
        
        btnUploadResume.setOnClickListener(v -> openFilePicker());

        btnLogout.setOnClickListener(v -> showLogoutDialog());
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Chiqish")
                .setMessage("Haqiqatan ham chiqmoqchimisiz?")
                .setPositiveButton("Ha", (dialog, which) -> {
                    // Saqlangan ma'lumotlarni tozalash
                    SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();

                    // LoginActivityga qaytish
                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Yo'q", null)
                .show();
    }
    
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }
    
    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, 2);
    }

    private void showAddSkillDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_skill, null);
        TextInputEditText etSkillName = dialogView.findViewById(R.id.etSkillName);
        MaterialButton btnCancel = dialogView.findViewById(R.id.btnCancel);
        MaterialButton btnAdd = dialogView.findViewById(R.id.btnAdd);

        AlertDialog dialog = new AlertDialog.Builder(this)
            .setView(dialogView)
            .create();

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnAdd.setOnClickListener(v -> {
            String skillName = etSkillName.getText().toString().trim();
            if (!skillName.isEmpty()) {
                addSkill(skillName);
                dialog.dismiss();
            } else {
                etSkillName.setError("Ko'nikma nomini kiriting");
            }
        });

        dialog.show();
    }

    private void addSkill(String skill) {
        if (!skills.contains(skill)) {
            skills.add(skill);
            Chip chip = new Chip(this);
            chip.setText(skill);
            chip.setCloseIconVisible(true);
            chip.setOnCloseIconClickListener(v -> {
                chipGroupSkills.removeView(chip);
                skills.remove(skill);
            });
            chipGroupSkills.addView(chip);
        }
    }

    private void saveProfile() {
        String fullName = etFullName.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();

        if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Barcha maydonlarni to'ldiring", Toast.LENGTH_SHORT).show();
            return;
        }

        // Bu yerda profil ma'lumotlarini saqlash
        // Masalan, API dan yoki ma'lumotlar bazasidan
        Toast.makeText(this, "Profil saqlandi", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 1) {
                // Rasm tanlandi
                Uri selectedImage = data.getData();
                ivProfile.setImageURI(selectedImage);
            } else if (requestCode == 2) {
                // Rezume fayli tanlandi
                Uri selectedFile = data.getData();
                // TODO: Faylni nusxalash va saqlash
                Toast.makeText(this, "Rezume yuklandi", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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