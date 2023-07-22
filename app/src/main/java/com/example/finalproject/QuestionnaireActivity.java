package com.example.finalproject;

import static com.example.finalproject.LoginTabFragment.SHARED_PREFS_KEY;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Response;

public class QuestionnaireActivity extends AppCompatActivity {
    private ApiService apiService;
    private Context context;
    private SharedPreferences sharedPreferences;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        context = getBaseContext();
        apiService = ApiService.getInstance(context);

        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");
        String apiEmail = email.replaceFirst("@", "__");

        if (savedInstanceState == null) {
            Question1Fragment question1Fragment = new Question1Fragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, question1Fragment);
            transaction.commit();
        }

        JsonObject requestBody = new JsonObject();

        apiService.submitQuestionnaire(apiEmail, requestBody, new ApiService.DataSubmitCallback() {
            @Override
            public void onSuccess(ResponseModel response) {
                int responseCode = response.getResponseCode();
                if (responseCode == 200) {
                    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

                    if (currentFragment instanceof Question1Fragment) {
                        Bundle bundle = currentFragment.getArguments();
                        if (bundle != null) {
                            String name = bundle.getString("name");
                            requestBody.addProperty("name", name);
                        }
                        Question2Fragment question2Fragment = new Question2Fragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, question2Fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } else if (currentFragment instanceof Question2Fragment) {
                        Bundle bundle = currentFragment.getArguments();
                        if (bundle != null) {
                            String date = bundle.getString("date");
                            requestBody.addProperty("date", date);
                        }
                        Question3Fragment question3Fragment = new Question3Fragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, question3Fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } else if (currentFragment instanceof Question3Fragment) {
                        Bundle bundle = currentFragment.getArguments();
                        if (bundle != null) {
                            String gender = bundle.getString("gender");
                            requestBody.addProperty("gender", gender);
                        }
                        Question4Fragment question4Fragment = new Question4Fragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, question4Fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } else if (currentFragment instanceof Question4Fragment) {
                        Bundle bundle = currentFragment.getArguments();
                        if (bundle != null) {
                            String dietType = bundle.getString("dietType");
                            requestBody.addProperty("dietType", dietType);
                        }
                        Question5Fragment question5Fragment = new Question5Fragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, question5Fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } else if (currentFragment instanceof Question5Fragment) {
                        Bundle bundle = currentFragment.getArguments();
                        if (bundle != null) {
                            String fitnessLevel = bundle.getString("fitnessLevel");
                            requestBody.addProperty("fitnessLevel", fitnessLevel);
                        }
                        Question6Fragment question6Fragment = new Question6Fragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, question6Fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } else if (currentFragment instanceof Question6Fragment) {
                        Bundle bundle = currentFragment.getArguments();
                        if (bundle != null) {
                            ArrayList<String> focusZonesArrayList = bundle.getStringArrayList("selectedFocusZones");
                            requestBody.add("focusZones", new Gson().toJsonTree(focusZonesArrayList));
                        }
                        Question7Fragment question7Fragment = new Question7Fragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, question7Fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } else if (currentFragment instanceof Question7Fragment) {
                        Bundle bundle = currentFragment.getArguments();
                        if (bundle != null) {
                            ArrayList<String> physicalLimitationsArrayList = bundle.getStringArrayList("selectedPhysicalLimitations");
                            requestBody.add("physicalLimitation", new Gson().toJsonTree(physicalLimitationsArrayList));
                        }
                        Question8Fragment question8Fragment = new Question8Fragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, question8Fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } else if (currentFragment instanceof Question8Fragment) {
                        Bundle bundle = currentFragment.getArguments();
                        if (bundle != null) {
                            String startingWeight = bundle.getString("startingWeight");
                            String targetWeight = bundle.getString("targetWeight");
                            String height = bundle.getString("height");
                            requestBody.addProperty("startingWeight", startingWeight);
                            requestBody.addProperty("targetWeight", targetWeight);
                            requestBody.addProperty("height", height);
                        }
                    }
              }
        }

                @Override
                public void onError(String errorMessage) {}

                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if (response.isSuccessful()) {
                        ResponseModel data = response.body();
                        onSuccess(data);
                    } else {
                        String errorMessage = "Error: " + response.code();
                        onError(errorMessage);
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable throwable) {}
            });
    }
}
