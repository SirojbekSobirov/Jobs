package com.example.jobs;

import android.app.Application;

import com.example.jobs.database.FavoriteRepository;

public class JobsApplication extends Application {
    
    private FavoriteRepository favoriteRepository;
    
    @Override
    public void onCreate() {
        super.onCreate();
        favoriteRepository = new FavoriteRepository(this);
    }
    
    public FavoriteRepository getFavoriteRepository() {
        return favoriteRepository;
    }
} 