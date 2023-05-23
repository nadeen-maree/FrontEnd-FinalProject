package com.example.finalproject;

import static android.content.Context.MODE_PRIVATE;
import static com.example.finalproject.LoginTabFragment.SHARED_PREFS_KEY;

import android.content.Context;
import android.content.SharedPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private static ApiService instance;
    private Retrofit retrofit;
    private MyApiService myApiService;

    private ApiService() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8181/questionnaires/" + email)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private Context getActivity() {
        return null;
    }

    public static ApiService getInstance() {
        if (instance == null) {
            instance = new ApiService();
        }
        return instance;
    }

    public void submitName(String name, final DataSubmitCallback callback) {
        Call<ResponseModel> call = myApiService.submitName(name);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    ResponseModel data = response.body();
                    callback.onResponse(call, response);
                    callback.onSuccess(data);
                } else {
                    String errorMessage = "Error: " + response.code();
                    callback.onFailure(call, new Throwable(errorMessage));
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
}

    public void submitDate(String date, final DataSubmitCallback callback) {
        Call<ResponseModel> call = myApiService.submitDate(date);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    ResponseModel data = response.body();
                    callback.onResponse(call, response);
                    callback.onSuccess(data);
                } else {
                    String errorMessage = "Error: " + response.code();
                    callback.onFailure(call, new Throwable(errorMessage));
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void submitGender(String gender, final DataSubmitCallback callback) {
        Call<ResponseModel> call = myApiService.submitGender(gender);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    ResponseModel data = response.body();
                    callback.onResponse(call, response);
                    callback.onSuccess(data);
                } else {
                    String errorMessage = "Error: " + response.code();
                    callback.onFailure(call, new Throwable(errorMessage));
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void submitDietType(String dietType, final DataSubmitCallback callback) {
        Call<ResponseModel> call = myApiService.submitDietType(dietType);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    ResponseModel data = response.body();
                    callback.onResponse(call, response);
                    callback.onSuccess(data);
                } else {
                    String errorMessage = "Error: " + response.code();
                    callback.onFailure(call, new Throwable(errorMessage));
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void submitFitnessLevel(String fitnessLevel, final DataSubmitCallback callback) {
        Call<ResponseModel> call = myApiService.submitFitnessLevel(fitnessLevel);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    ResponseModel data = response.body();
                    callback.onResponse(call, response);
                    callback.onSuccess(data);
                } else {
                    String errorMessage = "Error: " + response.code();
                    callback.onFailure(call, new Throwable(errorMessage));
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void submitFocusZones(String focusZones, final DataSubmitCallback callback) {
        Call<ResponseModel> call = myApiService.submitFocusZones(focusZones);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    ResponseModel data = response.body();
                    callback.onResponse(call, response);
                    callback.onSuccess(data);
                } else {
                    String errorMessage = "Error: " + response.code();
                    callback.onFailure(call, new Throwable(errorMessage));
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void submitPhysicalLimitations(String physicalLimitations, final DataSubmitCallback callback) {
        Call<ResponseModel> call = myApiService.submitPhysicalLimitations(physicalLimitations);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    ResponseModel data = response.body();
                    callback.onResponse(call, response);
                    callback.onSuccess(data);
                } else {
                    String errorMessage = "Error: " + response.code();
                    callback.onFailure(call, new Throwable(errorMessage));
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void submitUserDetails(String startingWeight, String targetWeight, String height, final DataSubmitCallback callback) {
        Call<ResponseModel> call = myApiService.submitUserDetails(startingWeight, targetWeight,height);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    ResponseModel data = response.body();
                    callback.onResponse(call, response);
                    callback.onSuccess(data);
                } else {
                    String errorMessage = "Error: " + response.code();
                    callback.onFailure(call, new Throwable(errorMessage));
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void updateProfile(String name, String gender, String dietType, String fitnessLevel, String focusZones, String physicalLimitations, String startingWeight, String targetWeight, String height, String imageUri, final DataSubmitCallback callback) {
        Call<ResponseModel> call = myApiService.updateProfile(name, gender, dietType, fitnessLevel, focusZones, physicalLimitations, startingWeight, targetWeight, height, imageUri);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    ResponseModel data = response.body();
                    callback.onResponse(call, response);
                    callback.onSuccess(data);
                } else {
                    String errorMessage = "Error: " + response.code();
                    callback.onFailure(call, new Throwable(errorMessage));
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void get(String url, final DataSubmitCallback dataSubmitCallback) {
        url = String.valueOf(getInstance().retrofit.baseUrl());
    }

    // Add other API methods for your specific use case

    public interface DataSubmitCallback {

        void onSuccess(ResponseModel response);
        
        void onError(String errorMessage);

        void onResponse(Call<ResponseModel> call, Response<ResponseModel> response);

        void onFailure(Call<ResponseModel> call, Throwable throwable);
    }

}
