package com.example.finalproject;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface MyApiService {
    @POST("questionnaires/{email}")
    Call<ResponseModel> submitQuestionnaire(
            @Path("email") String email,
            @Body JsonObject requestBody
    );
    @GET("/api/data")
    Call<ResponseModel> get(@Url String url);
}


