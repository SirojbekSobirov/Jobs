package com.example.jobs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etFullName;
    private TextInputEditText etEmail;
    private TextInputEditText etPhone;
    private TextInputEditText etPassword;
    private TextInputEditText etConfirmPassword;
    private MaterialButton btnRegister;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "LoginPrefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_FULL_NAME = "fullName";
    private static final String KEY_PHONE = "phone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        initViews();
        setupListeners();
    }

    private void initViews() {
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private void setupListeners() {
        btnRegister.setOnClickListener(v -> register());
        
        findViewById(R.id.tvLogin).setOnClickListener(v -> {
            finish();
        });
    }

    private void register() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(fullName)) {
            etFullName.setError("To'liq ismingizni kiriting");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Emailni kiriting");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("To'g'ri email kiriting");
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            etPhone.setError("Telefon raqamni kiriting");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Parolni kiriting");
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Parol kamida 6 ta belgidan iborat bo'lishi kerak");
            return;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Parollar mos kelmadi");
            return;
        }

        // Bu yerda ro'yxatdan o'tish logikasi
        // Masalan, API dan yoki ma'lumotlar bazasidan
        // Hozircha test ma'lumotlar

        // Ma'lumotlarni saqlash
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_FULL_NAME, fullName);
        editor.putString(KEY_PHONE, phone);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();

        Toast.makeText(this, "Ro'yxatdan o'tish muvaffaqiyatli", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        finish();
    }
} 