package com.example.jobs.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.jobs.Vacancy;

import java.util.List;

public class FavoriteRepository {
    
    private FavoriteVacancyDao favoriteVacancyDao;
    private LiveData<List<FavoriteVacancy>> allFavorites;
    
    public FavoriteRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        favoriteVacancyDao = database.favoriteVacancyDao();
        allFavorites = favoriteVacancyDao.getAllFavorites();
    }
    
    public void insert(Vacancy vacancy) {
        String employerName = (vacancy.getEmployer() != null) ? vacancy.getEmployer().getName() : "";
        String employerLogo = (vacancy.getEmployer() != null && 
                              vacancy.getEmployer().getLogoUrls() != null) ? 
                              vacancy.getEmployer().getLogoUrls().getLogo90() : "";
        String salaryText = (vacancy.getSalary() != null) ? 
                            vacancy.getSalary().getFormattedSalary() : "";
        String requirements = (vacancy.getSnippet() != null) ? 
                            vacancy.getSnippet().getRequirement() : "";
        String responsibilities = (vacancy.getSnippet() != null) ? 
                            vacancy.getSnippet().getResponsibility() : "";
                            
        FavoriteVacancy favoriteVacancy = new FavoriteVacancy(
                vacancy.getId(),
                vacancy.getName(),
                vacancy.getUrl(),
                employerName,
                employerLogo,
                salaryText,
                requirements,
                responsibilities,
                vacancy.getPublishedDate()
        );
        
        new InsertAsyncTask(favoriteVacancyDao).execute(favoriteVacancy);
    }
    
    public void delete(String vacancyId) {
        new DeleteAsyncTask(favoriteVacancyDao).execute(vacancyId);
    }
    
    public LiveData<List<FavoriteVacancy>> getAllFavorites() {
        return allFavorites;
    }
    
    public LiveData<FavoriteVacancy> getFavoriteById(String id) {
        return favoriteVacancyDao.getFavoriteById(id);
    }
    
    public boolean isFavorite(String id) {
        try {
            return new IsFavoriteAsyncTask(favoriteVacancyDao).execute(id).get() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private static class InsertAsyncTask extends AsyncTask<FavoriteVacancy, Void, Void> {
        private FavoriteVacancyDao asyncTaskDao;
        
        InsertAsyncTask(FavoriteVacancyDao dao) {
            asyncTaskDao = dao;
        }
        
        @Override
        protected Void doInBackground(FavoriteVacancy... favoriteVacancies) {
            asyncTaskDao.insert(favoriteVacancies[0]);
            return null;
        }
    }
    
    private static class DeleteAsyncTask extends AsyncTask<String, Void, Void> {
        private FavoriteVacancyDao asyncTaskDao;
        
        DeleteAsyncTask(FavoriteVacancyDao dao) {
            asyncTaskDao = dao;
        }
        
        @Override
        protected Void doInBackground(String... ids) {
            asyncTaskDao.deleteById(ids[0]);
            return null;
        }
    }
    
    private static class IsFavoriteAsyncTask extends AsyncTask<String, Void, Integer> {
        private FavoriteVacancyDao asyncTaskDao;
        
        IsFavoriteAsyncTask(FavoriteVacancyDao dao) {
            asyncTaskDao = dao;
        }
        
        @Override
        protected Integer doInBackground(String... ids) {
            return asyncTaskDao.isFavorite(ids[0]);
        }
    }
} 