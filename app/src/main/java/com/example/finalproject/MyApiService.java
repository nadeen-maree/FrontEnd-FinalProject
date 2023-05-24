package com.example.finalproject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MyApiService {
    @FormUrlEncoded
    @POST("submitName")
    Call<ResponseModel> submitName(@Field("name") String name);

    @FormUrlEncoded
    @POST("submitDate")
    Call<ResponseModel> submitDate(@Field("date") String date);

    @FormUrlEncoded
    @POST("submitGender")
    Call<ResponseModel> submitGender(@Field("gender") String gender);

    @FormUrlEncoded
    @POST("submitDietType")
    Call<ResponseModel> submitDietType(@Field("dietType") String dietType);

    @FormUrlEncoded
    @POST("submitFitnessLevel")
    Call<ResponseModel> submitFitnessLevel(@Field("fitnessLevel") String fitnessLevel);

    @FormUrlEncoded
    @POST("submitFocusZones")
    Call<ResponseModel> submitFocusZones(@Field("focusZones") ArrayList<String> focusZones);

    @FormUrlEncoded
    @POST("submitPhysicalLimitations")
    Call<ResponseModel> submitPhysicalLimitations(@Field("physicalLimitations") ArrayList<String> physicalLimitations);

    @FormUrlEncoded
    @POST("submitUserDetails")
    Call<ResponseModel> submitUserDetails(@Field("startingWeight") String startingWeight, @Field("targetWeight") String targetWeight, @Field("height") String height);

    @FormUrlEncoded
    @POST("updateProfile")
    Call<ResponseModel> updateProfile(@Field("name") String name, @Field("gender") String gender, @Field("dietType") String dietType, @Field("fitnessLevel") String fitnessLevel, @Field("focusZones") ArrayList<String> focusZones, @Field("physicalLimitations") ArrayList<String> physicalLimitations,@Field("startingWeight") String startingWeight, @Field("targetWeight") String targetWeight, @Field("height") String height, @Field("imageUri") String imageUri);
}

