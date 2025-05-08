package com.example.jobs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder> {
    private List<Notification> notifications;

    public NotificationsAdapter(List<Notification> notifications) {
        this.notifications = notifications != null ? notifications : new ArrayList<>();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.bind(notification);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications != null ? notifications : new ArrayList<>();
        notifyDataSetChanged();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        private final TextView tvMessage;
        private final TextView tvTime;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvTime = itemView.findViewById(R.id.tvTime);
        }

        public void bind(Notification notification) {
            tvTitle.setText(notification.getTitle());
            tvMessage.setText(notification.getMessage());
            tvTime.setText(notification.getTime());
        }
    }
} 