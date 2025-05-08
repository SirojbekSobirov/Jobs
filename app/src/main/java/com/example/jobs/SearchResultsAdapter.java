package com.example.jobs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchResultViewHolder> {
    private List<Job> jobs;
    private OnJobClickListener listener;

    public interface OnJobClickListener {
        void onJobClick(Job job);
    }

    public SearchResultsAdapter(OnJobClickListener listener) {
        this.jobs = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_job, parent, false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        Job job = jobs.get(position);
        holder.bind(job);
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
        notifyDataSetChanged();
    }

    class SearchResultViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvJobTitle;
        private final TextView tvCompany;
        private final TextView tvLocation;
        private final TextView tvSalary;

        public SearchResultViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJobTitle = itemView.findViewById(R.id.tvJobTitle);
            tvCompany = itemView.findViewById(R.id.tvCompany);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvSalary = itemView.findViewById(R.id.tvSalary);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onJobClick(jobs.get(position));
                }
            });
        }

        public void bind(Job job) {
            tvJobTitle.setText(job.getTitle());
            tvCompany.setText(job.getCompany());
            tvLocation.setText(job.getLocation());
            tvSalary.setText(job.getSalary());
        }
    }
} 