package com.example.jobs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jobs.database.FavoriteVacancy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FavoriteVacancyAdapter extends RecyclerView.Adapter<FavoriteVacancyAdapter.FavoriteViewHolder> {

    private List<FavoriteVacancy> favoriteVacancies;
    private OnItemClickListener itemClickListener;
    private OnDeleteClickListener deleteClickListener;

    public interface OnItemClickListener {
        void onItemClick(FavoriteVacancy vacancy, int position);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(FavoriteVacancy vacancy, int position);
    }

    public FavoriteVacancyAdapter(List<FavoriteVacancy> favoriteVacancies) {
        this.favoriteVacancies = favoriteVacancies;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteClickListener = listener;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_vacancy, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        FavoriteVacancy vacancy = favoriteVacancies.get(position);
        holder.bind(vacancy, position);
    }

    @Override
    public int getItemCount() {
        return favoriteVacancies.size();
    }

    public void updateData(List<FavoriteVacancy> newData) {
        this.favoriteVacancies = newData;
        notifyDataSetChanged();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {
        private TextView tvVacancyTitle;
        private TextView tvCompanyName;
        private TextView tvSalary;
        private TextView tvSavedDate;
        private ImageView ivCompanyLogo;
        private ImageButton btnDelete;

        FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVacancyTitle = itemView.findViewById(R.id.tvVacancyTitle);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
            tvSalary = itemView.findViewById(R.id.tvSalary);
            tvSavedDate = itemView.findViewById(R.id.tvSavedDate);
            ivCompanyLogo = itemView.findViewById(R.id.ivCompanyLogo);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        void bind(final FavoriteVacancy vacancy, final int position) {
            tvVacancyTitle.setText(vacancy.getName());
            
            if (vacancy.getEmployerName() != null && !vacancy.getEmployerName().isEmpty()) {
                tvCompanyName.setText(vacancy.getEmployerName());
                tvCompanyName.setVisibility(View.VISIBLE);
            } else {
                tvCompanyName.setVisibility(View.GONE);
            }
            
            if (vacancy.getSalaryText() != null && !vacancy.getSalaryText().isEmpty()) {
                tvSalary.setText(vacancy.getSalaryText());
                tvSalary.setVisibility(View.VISIBLE);
            } else {
                tvSalary.setVisibility(View.GONE);
            }
            
            // Saqlangan sana
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
            String formattedDate = dateFormat.format(new Date(vacancy.getSavedTime()));
            tvSavedDate.setText("Saqlangan: " + formattedDate);
            
            // Kompaniya logosi
            if (vacancy.getEmployerLogo() != null && !vacancy.getEmployerLogo().isEmpty()) {
                Glide.with(ivCompanyLogo.getContext())
                        .load(vacancy.getEmployerLogo())
                        .placeholder(R.drawable.ic_company_placeholder)
                        .into(ivCompanyLogo);
            } else {
                ivCompanyLogo.setImageResource(R.drawable.ic_company_placeholder);
            }
            
            // Itemga bosilganda
            itemView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(vacancy, position);
                }
            });
            
            // O'chirish tugmasi
            btnDelete.setOnClickListener(v -> {
                if (deleteClickListener != null) {
                    deleteClickListener.onDeleteClick(vacancy, position);
                }
            });
        }
    }
} 