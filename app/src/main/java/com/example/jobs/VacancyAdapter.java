package com.example.jobs;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class VacancyAdapter extends RecyclerView.Adapter<VacancyAdapter.VacancyViewHolder> {

    private List<Vacancy> vacancies;
    private OnVacancyClickListener listener;
    private OnFavoriteClickListener favoriteListener;

    public interface OnVacancyClickListener {
        void onVacancyClick(Vacancy vacancy, int position);
    }

    public interface OnFavoriteClickListener {
        void onFavoriteClick(Vacancy vacancy, int position);
    }

    public VacancyAdapter(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    public void setOnVacancyClickListener(OnVacancyClickListener listener) {
        this.listener = listener;
    }

    public void setOnFavoriteClickListener(OnFavoriteClickListener listener) {
        this.favoriteListener = listener;
    }

    @NonNull
    @Override
    public VacancyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vacancy, parent, false);
        return new VacancyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VacancyViewHolder holder, int position) {
        Vacancy vacancy = vacancies.get(position);
        holder.bind(vacancy, position);
    }

    @Override
    public int getItemCount() {
        return vacancies.size();
    }

    public void updateData(List<Vacancy> newVacancies) {
        this.vacancies = newVacancies;
        notifyDataSetChanged();
    }

    class VacancyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvVacancyTitle;
        private TextView tvCompanyName;
        private TextView tvSalary;
        private TextView tvRequirements;
        private TextView tvPublishedDate;
        private ImageView ivCompanyLogo;
        private ImageButton btnFavorite;

        VacancyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVacancyTitle = itemView.findViewById(R.id.tvVacancyTitle);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
            tvSalary = itemView.findViewById(R.id.tvSalary);
            tvRequirements = itemView.findViewById(R.id.tvRequirements);
            tvPublishedDate = itemView.findViewById(R.id.tvPublishedDate);
            ivCompanyLogo = itemView.findViewById(R.id.ivCompanyLogo);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
        }

        void bind(final Vacancy vacancy, final int position) {
            tvVacancyTitle.setText(vacancy.getName());
            
            if (vacancy.getEmployer() != null) {
                tvCompanyName.setText(vacancy.getEmployer().getName());
                
                if (vacancy.getEmployer().getLogoUrls() != null && 
                    vacancy.getEmployer().getLogoUrls().getLogo90() != null) {
                    Glide.with(ivCompanyLogo.getContext())
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
            
            if (vacancy.getSnippet() != null && vacancy.getSnippet().getRequirement() != null) {
                tvRequirements.setText(Html.fromHtml(vacancy.getSnippet().getRequirement(), Html.FROM_HTML_MODE_COMPACT));
                tvRequirements.setVisibility(View.VISIBLE);
            } else {
                tvRequirements.setVisibility(View.GONE);
            }
            
            if (vacancy.getPublishedDate() != null) {
                tvPublishedDate.setText(formatDate(vacancy.getPublishedDate()));
                tvPublishedDate.setVisibility(View.VISIBLE);
            } else {
                tvPublishedDate.setVisibility(View.GONE);
            }
            
            btnFavorite.setImageResource(
                    vacancy.isFavorite() ? R.drawable.ic_favorite_filled : R.drawable.ic_favorite_border
            );
            
            btnFavorite.setOnClickListener(v -> {
                vacancy.setFavorite(!vacancy.isFavorite());
                btnFavorite.setImageResource(
                        vacancy.isFavorite() ? R.drawable.ic_favorite_filled : R.drawable.ic_favorite_border
                );
                
                if (favoriteListener != null) {
                    favoriteListener.onFavoriteClick(vacancy, position);
                }
            });
            
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onVacancyClick(vacancy, position);
                }
            });
        }
        
        private String formatDate(String dateString) {
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault());
                inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date date = inputFormat.parse(dateString);
                
                long now = System.currentTimeMillis();
                long timeDiff = now - date.getTime();
                
                // Soat va kun bo'yicha formatlash
                if (timeDiff < 24 * 60 * 60 * 1000) {
                    long hours = timeDiff / (60 * 60 * 1000);
                    return hours + " soat oldin";
                } else {
                    long days = timeDiff / (24 * 60 * 60 * 1000);
                    return days + " kun oldin";
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return dateString;
            }
        }
    }
}
