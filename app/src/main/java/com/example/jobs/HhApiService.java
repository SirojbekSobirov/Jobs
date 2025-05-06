package com.example.jobs;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HhApiService {
    @GET("vacancies")
    Call<VacancyResponse> getVacancies(@Query("text") String text);
}
