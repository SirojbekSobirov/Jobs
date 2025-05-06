package com.example.jobs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public class VacancyDetailActivity extends AppCompatActivity {
    
    private static final String EXTRA_VACANCY = "extra_vacancy";
    
    private ImageView ivCompanyLogo;
    private TextView tvVacancyTitle;
    private TextView tvCompanyName;
    private TextView tvSalary;
    private TextView tvRequirements;
    private TextView tvResponsibilities;
    private TextView tvPublishedDate;
    private ImageButton btnFavorite;
    private Button btnApply;
    
    private Vacancy vacancy;
    
    public static Intent newIntent(Context context, Vacancy vacancy) {
        Intent intent = new Intent(context, VacancyDetailActivity.class);
        String vacancyJson = new Gson().toJson(vacancy);
        intent.putExtra(EXTRA_VACANCY, vacancyJson);
        return intent;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy_detail);
        
        // Enable back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Vakansiya tafsiloti");
        }
        
        initViews();
        parseIntent();
        displayVacancyDetails();
        setupListeners();
    }
    
    private void initViews() {
        ivCompanyLogo = findViewById(R.id.ivCompanyLogo);
        tvVacancyTitle = findViewById(R.id.tvVacancyTitle);
        tvCompanyName = findViewById(R.id.tvCompanyName);
        tvSalary = findViewById(R.id.tvSalary);
        tvRequirements = findViewById(R.id.tvRequirements);
        tvResponsibilities = findViewById(R.id.tvResponsibilities);
        tvPublishedDate = findViewById(R.id.tvPublishedDate);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnApply = findViewById(R.id.btnApply);
    }
    
    private void parseIntent() {
        if (getIntent().hasExtra(EXTRA_VACANCY)) {
            String vacancyJson = getIntent().getStringExtra(EXTRA_VACANCY);
            vacancy = new Gson().fromJson(vacancyJson, Vacancy.class);
        } else {
            Toast.makeText(this, "Vakansiya ma'lumotlari topilmadi", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    
    private void displayVacancyDetails() {
        if (vacancy == null) return;
        
        tvVacancyTitle.setText(vacancy.getName());
        
        if (vacancy.getEmployer() != null) {
            tvCompanyName.setText(vacancy.getEmployer().getName());
            
            if (vacancy.getEmployer().getLogoUrls() != null && 
                vacancy.getEmployer().getLogoUrls().getLogo90() != null) {
                Glide.with(this)
                        .load(vacancy.getEmployer().getLogoUrls().getLogo90())
                        .placeholder(R.drawable.ic_company_placeholder)
                        .into(ivCompanyLogo);
            } else {
                ivCompanyLogo.setImageResource(R.drawable.ic_company_placeholder);
            }
        } else {
            tvCompanyName.setVisibility(View.GONE);
            ivCompanyLogo.setImageResource(R.drawable.ic_company_placeholder);
        }
        
        if (vacancy.getSalary() != null) {
            tvSalary.setText(vacancy.getSalary().getFormattedSalary());
            tvSalary.setVisibility(View.VISIBLE);
        } else {
            tvSalary.setVisibility(View.GONE);
        }
        
        if (vacancy.getSnippet() != null) {
            if (vacancy.getSnippet().getRequirement() != null) {
                tvRequirements.setText(Html.fromHtml(vacancy.getSnippet().getRequirement(), Html.FROM_HTML_MODE_COMPACT));
                tvRequirements.setVisibility(View.VISIBLE);
            } else {
                tvRequirements.setVisibility(View.GONE);
            }
            
            if (vacancy.getSnippet().getResponsibility() != null) {
                tvResponsibilities.setText(Html.fromHtml(vacancy.getSnippet().getResponsibility(), Html.FROM_HTML_MODE_COMPACT));
                tvResponsibilities.setVisibility(View.VISIBLE);
            } else {
                tvResponsibilities.setVisibility(View.GONE);
            }
        } else {
            tvRequirements.setVisibility(View.GONE);
            tvResponsibilities.setVisibility(View.GONE);
        }
        
        btnFavorite.setImageResource(
                vacancy.isFavorite() ? R.drawable.ic_favorite_filled : R.drawable.ic_favorite_border
        );
    }
    
    private void setupListeners() {
        btnFavorite.setOnClickListener(v -> {
            vacancy.setFavorite(!vacancy.isFavorite());
            btnFavorite.setImageResource(
                    vacancy.isFavorite() ? R.drawable.ic_favorite_filled : R.drawable.ic_favorite_border
            );
            // TODO: Favorite status should be saved in database
        });
        
        btnApply.setOnClickListener(v -> {
            if (vacancy.getUrl() != null) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(vacancy.getUrl()));
                startActivity(browserIntent);
            } else {
                Toast.makeText(this, "Ariza berish uchun havola mavjud emas", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 