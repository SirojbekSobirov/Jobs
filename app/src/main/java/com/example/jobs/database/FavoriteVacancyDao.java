package com.example.jobs.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteVacancyDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteVacancy vacancy);
    
    @Delete
    void delete(FavoriteVacancy vacancy);
    
    @Query("DELETE FROM favorite_vacancies WHERE id = :id")
    void deleteById(String id);
    
    @Query("SELECT * FROM favorite_vacancies ORDER BY savedTime DESC")
    LiveData<List<FavoriteVacancy>> getAllFavorites();
    
    @Query("SELECT * FROM favorite_vacancies WHERE id = :id LIMIT 1")
    LiveData<FavoriteVacancy> getFavoriteById(String id);
    
    @Query("SELECT COUNT(*) FROM favorite_vacancies WHERE id = :id")
    int isFavorite(String id);
} 