package com.example.jobs;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotificationsAdapter adapter;
    private List<Notification> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.notificationsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notifications = new ArrayList<>();
        // Bu yerda bildirishnomalarni yuklash kerak
        loadNotifications();

        adapter = new NotificationsAdapter(notifications);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fabClearAll = findViewById(R.id.fabClearAll);
        fabClearAll.setOnClickListener(view -> clearAllNotifications());
    }

    private void loadNotifications() {
        // Bu yerda bildirishnomalarni yuklash logikasi
        // Masalan, API dan yoki ma'lumotlar bazasidan
    }

    private void clearAllNotifications() {
        notifications.clear();
        adapter.notifyDataSetChanged();
        // Bu yerda bildirishnomalarni o'chirish logikasi
        // Masalan, API dan yoki ma'lumotlar bazasidan
    }
} 