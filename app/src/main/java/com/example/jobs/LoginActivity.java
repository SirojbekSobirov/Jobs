package com.example.jobs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private MaterialButton btnLogin;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "LoginPrefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Agar avval kirilgan bo'lsa, MainActivityga o'tkazish
        if (sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            return;
        }

        initViews();
        setupListeners();
    }

    private void initViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void setupListeners() {
        btnLogin.setOnClickListener(v -> login());
        
        findViewById(R.id.tvRegister).setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void login() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Emailni kiriting");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Parolni kiriting");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("To'g'ri email kiriting");
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Parol kamida 6 ta belgidan iborat bo'lishi kerak");
            return;
        }

        // Bu yerda kirish logikasi
        // Masalan, API dan yoki ma'lumotlar bazasidan
        // Hozircha test ma'lumotlar
        if (email.equals("test@example.com") && password.equals("123456")) {
            // Kirish muvaffaqiyatli
            // Ma'lumotlarni saqlash
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_EMAIL, email);
            editor.putString(KEY_PASSWORD, password);
            editor.putBoolean(KEY_IS_LOGGED_IN, true);
            editor.apply();

            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Email yoki parol noto'g'ri", Toast.LENGTH_SHORT).show();
        }
    }
} 