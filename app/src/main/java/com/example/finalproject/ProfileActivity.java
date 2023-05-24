package com.example.finalproject;

import static com.example.finalproject.LoginTabFragment.SHARED_PREFS_KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileActivity extends AppCompatActivity {

    TextView personal_plan, food, challenges, user_profile;
    FloatingActionButton exe;
    CircleImageView profileImage;
    TextView profileNameTextView, profileDateTextView, profileGenderTextView, profileDietTypeTextView,
            profileFitnessLevelTextView, profileFocusZonesTextView, profilePhysicalLimitationsTextView,
            profileStartingWeightTextView, profileTargetWeightTextView, profileHeightTextView;
    Button editProfileButton;

    private Context mContext;
    private static final int REQUEST_CODE_EDIT_PROFILE = 1;

    SharedPreferences prefs;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        SharedPreferences sharedPreferences = ProfileActivity.this.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
//        String email = sharedPreferences.getString("email", "");
//        String GET_PROFILE_URL = "http://10.0.2.2:8181/questionnaire?email" + email;

        //new HttpGetTask().execute(GET_PROFILE_URL);

        prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);

        mContext = this; // initialize the context
        apiService = ApiService.getInstance(mContext);


        personal_plan = findViewById(R.id.tab1_txt);
        food = findViewById(R.id.tab2_txt);
        challenges = findViewById(R.id.tab4_txt);
        user_profile = findViewById(R.id.tab5_txt);
        exe = findViewById(R.id.training_btn);

        profileImage = findViewById(R.id.profile_image);
        profileNameTextView = findViewById(R.id.profile_name);
        profileDateTextView = findViewById(R.id.profile_date);
        profileDietTypeTextView = findViewById(R.id.profile_diet_type);
        profileGenderTextView = findViewById(R.id.profile_gender);
        profileFitnessLevelTextView = findViewById(R.id.profile_fitness_level);
        profileFocusZonesTextView = findViewById(R.id.profile_focus_zones);
        profilePhysicalLimitationsTextView = findViewById(R.id.profile_physical_limitations);
        profileStartingWeightTextView = findViewById(R.id.profile_starting_weight);
        profileTargetWeightTextView = findViewById(R.id.profile_target_weight);
        profileHeightTextView = findViewById(R.id.profile_height);
        editProfileButton = findViewById(R.id.edit_profile_button);


        personal_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ProfileActivity.this, Food.class);
                startActivity(intent);
            }
        });

        challenges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ProfileActivity.this, Challenges.class);
                startActivity(intent);
            }
        });

        exe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ProfileActivity.this, TrainingActivity.class);
                startActivity(intent);
            }
        });


        String name = prefs.getString("name", "");
        profileNameTextView.setText(name);

        String date = prefs.getString("date", "");
        profileDateTextView.setText(date);

        String gender = prefs.getString("gender", "");
        profileGenderTextView.setText(gender);

        String dietType = prefs.getString("dietType", "");
        profileDietTypeTextView.setText(dietType);

        String fitnessLevel = prefs.getString("fitnessLevel", "");
        profileFitnessLevelTextView.setText(fitnessLevel);

        String focusZones = prefs.getString("FocusZones", "");
        profileFocusZonesTextView.setText(focusZones);

        String physicalLimitations = prefs.getString("physicalLimitations", "");
        profilePhysicalLimitationsTextView.setText(physicalLimitations);

        String startingWeight = prefs.getString("starting_weight", "");
        profileStartingWeightTextView.setText(startingWeight);

        String targetWeight = prefs.getString("target_weight", "");
        profileTargetWeightTextView.setText(targetWeight);

        String height = prefs.getString("height", "");
        profileHeightTextView.setText(height);

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEditProfileActivity();
            }
        });
    }

    private void startEditProfileActivity() {
        Intent intent = new Intent(this, Edit_Profile.class);
        startActivityForResult(intent, REQUEST_CODE_EDIT_PROFILE);
    }

    private void updateProfileData(String name, String date, String gender, String dietType,
                                   String fitnessLevel, String focusZones, String physicalLimitations,
                                   String startingWeight, String targetWeight, String height, String imageUri) {
        SharedPreferences sharedPreferences = ProfileActivity.this.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        String apiEmail = email.replaceFirst("@","__");
        String baseUrl = "http://10.0.2.2:8181/questionnaires/";
        String endpoint = apiEmail;
        String query = "name=" + name + "&date=" + date + "&gender=" + gender + "&dietType=" + dietType + "&fitnessLevel=" + fitnessLevel +
                "&focusZones=" + focusZones + "&physicalLimitations=" + physicalLimitations +
                "&startingWeight=" + startingWeight + "&targetWeight=" + targetWeight + "&height=" + height + "&imageUri=" + imageUri;
        String url = baseUrl + endpoint + "?" + query;

        // Make the HTTPS GET request using the ApiService
        apiService.get(url, new ApiService.DataSubmitCallback() {

            @Override
            public void onSuccess(ResponseModel response) {

            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(ProfileActivity.this, errorMessage, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    try {
                        // Parse the JSON response and update the profile data
                        JSONObject profileData = new JSONObject(String.valueOf(response));

                        String name = profileData.optString("name", "");
                        String date = profileData.optString("date", "");
                        String gender = profileData.optString("gender", "");
                        String dietType = profileData.optString("dietType", "");
                        String fitnessLevel = profileData.optString("fitnessLevel", "");
                        String focusZones = profileData.optString("focusZones", "");
                        String physicalLimitations = profileData.optString("physicalLimitations", "");
                        String startingWeight = profileData.optString("startingWeight", "");
                        String targetWeight = profileData.optString("targetWeight", "");
                        String height = profileData.optString("height", "");

                        // Update the local SharedPreferences with the new profile data
                        prefs.edit()
                                .putString("name", name)
                                .putString("date", date)
                                .putString("gender", gender)
                                .putString("dietType", dietType)
                                .putString("fitnessLevel", fitnessLevel)
                                .putString("focusZones", focusZones)
                                .putString("physicalLimitations", physicalLimitations)
                                .putString("starting_weight", startingWeight)
                                .putString("target_weight", targetWeight)
                                .putString("height", height)
                                .apply();

                        String imageUri = prefs.getString("image", null);

                        // Update the UI with the new profile data
                        profileNameTextView.setText(name);
                        profileDateTextView.setText(date);
                        profileGenderTextView.setText(gender);
                        profileDietTypeTextView.setText(dietType);
                        profileFitnessLevelTextView.setText(fitnessLevel);
                        profileFocusZonesTextView.setText(focusZones);
                        profilePhysicalLimitationsTextView.setText(physicalLimitations);
                        profileStartingWeightTextView.setText(startingWeight);
                        profileTargetWeightTextView.setText(targetWeight);
                        profileHeightTextView.setText(height);

                        if (imageUri != null) {
                            Picasso.get().load(Uri.parse(imageUri)).into(profileImage);
                        } else {
                            // Set a default image if the image URI is not available
                            profileImage.setImageResource(R.drawable.ic_launcher_foreground);
                        }

                        Toast.makeText(mContext, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(mContext, "Failed to update profile", Toast.LENGTH_SHORT).show();
                    }
            }
        }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable throwable) {
                        String errorMessage = "Request failed: " + throwable.getMessage();
                        onError(errorMessage);
                    }
                    });
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT_PROFILE && resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            String date = data.getStringExtra("date");
            String gender = data.getStringExtra("gender");
            String dietType = data.getStringExtra("dietType");
            String fitnessLevel = data.getStringExtra("fitnessLevel");
            String focusZones = data.getStringExtra("focusZones");
            String physicalLimitations = data.getStringExtra("physicalLimitations");
            String startingWeight = data.getStringExtra("startingWeight");
            String targetWeight = data.getStringExtra("targetWeight");
            String height = data.getStringExtra("height");
            String imageUri = prefs.getString("image", null);

            updateProfileData(name, date, gender, dietType, fitnessLevel, focusZones,
                    physicalLimitations, startingWeight, targetWeight, height, imageUri);
        }
    }
//            @Override
//            public void onSuccess(String response) {
//                try {
//                    // Parse the JSON response and update the profile data
//                    JSONObject profileData = new JSONObject(response);
//
//                    String name = profileData.optString("name", "");
//                    String date = profileData.optString("date", "");
//                    String gender = profileData.optString("gender", "");
//                    String dietType = profileData.optString("dietType", "");
//                    String fitnessLevel = profileData.optString("fitnessLevel", "");
//                    String focusZones = profileData.optString("focusZones", "");
//                    String physicalLimitations = profileData.optString("physicalLimitations", "");
//                    String startingWeight = profileData.optString("startingWeight", "");
//                    String targetWeight = profileData.optString("targetWeight", "");
//                    String height = profileData.optString("height", "");
//
//                    // Update the local SharedPreferences with the new profile data
//                    SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
//                    prefs.edit()
//                            .putString("name", name)
//                            .putString("date", date)
//                            .putString("gender", gender)
//                            .putString("dietType", dietType)
//                            .putString("fitnessLevel", fitnessLevel)
//                            .putString("focusZones", focusZones)
//                            .putString("physicalLimitations", physicalLimitations)
//                            .putString("starting_weight", startingWeight)
//                            .putString("target_weight", targetWeight)
//                            .putString("height", height)
//                            .apply();
//
//                    // Update the UI with the new profile data
//                    profileNameTextView.setText(name);
//                    profileDateTextView.setText(date);
//                    profileGenderTextView.setText(gender);
//                    profileDietTypeTextView.setText(dietType);
//                    profileFitnessLevelTextView.setText(fitnessLevel);
//                    profileFocusZonesTextView.setText(focusZones);
//                    profilePhysicalLimitationsTextView.setText(physicalLimitations);
//                    profileStartingWeightTextView.setText(startingWeight);
//                    profileTargetWeightTextView.setText(targetWeight);
//                    profileHeightTextView.setText(height);
//
//                    Toast.makeText(mContext, "Profile updated successfully", Toast.LENGTH_SHORT).show();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(mContext, "Failed to update profile", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(String errorMessage) {
//                Toast.makeText(mContext, "Failed to update profile", Toast.LENGTH_SHORT).show();
//            }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == REQUEST_CODE_EDIT_PROFILE && resultCode == RESULT_OK) {
//            SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
//
//            String name = data.getStringExtra("name");
//            prefs.edit().putString("name", name).apply();
//
//            String date = data.getStringExtra("date");
//            prefs.edit().putString("date", date).apply();
//
//            String gender = data.getStringExtra("gender");
//            prefs.edit().putString("gender", gender).apply();
//
//            String dietType = data.getStringExtra("dietType");
//            prefs.edit().putString("dietType", dietType).apply();
//
//            String fitnessLevel = data.getStringExtra("fitnessLevel");
//            prefs.edit().putString("fitnessLevel", fitnessLevel).apply();
//
//            String focusZones = data.getStringExtra("focusZones");
//            prefs.edit().putString("focusZones", focusZones).apply();
//
//            String physicalLimitations = data.getStringExtra("physicalLimitations");
//            prefs.edit().putString("physicalLimitations", physicalLimitations).apply();
//
//            String startingWeight = data.getStringExtra("startingWeight");
//            prefs.edit().putString("starting_weight", startingWeight).apply();
//
//            String targetWeight = data.getStringExtra("targetWeight");
//            prefs.edit().putString("target_weight", targetWeight).apply();
//
//            String height = data.getStringExtra("height");
//            prefs.edit().putString("height", height).apply();
//
//            String imageUrl = prefs.getString("image", null);
//
//            if (getApplicationContext() == null) {
//                return;
//            }
//
//            if (imageUrl != null) {
//                Picasso.get().load(Uri.parse(imageUrl)).into(profileImage);
//            } else {
//                // Set a default image if the image URI is not available
//                profileImage.setImageResource(R.drawable.ic_launcher_foreground);
//            }
//
//            // Update the UI with the new profile data
//            profileNameTextView.setText(name);
//            profileDateTextView.setText(date);
//            profileGenderTextView.setText(gender);
//            profileDietTypeTextView.setText(dietType);
//            profileFitnessLevelTextView.setText(fitnessLevel);
//            profileFocusZonesTextView.setText(focusZones);
//            profilePhysicalLimitationsTextView.setText(physicalLimitations);
//            profileStartingWeightTextView.setText(startingWeight);
//            profileTargetWeightTextView.setText(targetWeight);
//            profileHeightTextView.setText(height);
//        }
    }

//    private class HttpGetTask extends AsyncTask<String, Void, String> {
//
//        protected String doInBackground(String... params) {
//            String urlStr = params[0];
//            String response = "";
//
//            try {
//                URL url = new URL(urlStr);
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("GET");
//
//                // Read response from server
//                int responseCode = conn.getResponseCode();
//                if (responseCode == HttpURLConnection.HTTP_OK) {
//                    InputStream inputStream = conn.getInputStream();
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                    String line;
//                    while ((line = bufferedReader.readLine()) != null) {
//                        response += line;
//                    }
//                } else {
//                    Toast.makeText(ProfileActivity.this, "Unknown internal failure", Toast.LENGTH_SHORT).show();
//                    return null;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return response;
//        }
//
//        protected void onPostExecute(String result) {
//            if (result != null) {
//                try {
//                    // Parse the JSON response
//                    JSONObject json = new JSONObject(result);
//
//                    // Extract the profile data from the JSON
//                    String name = json.getString("name");
//                    String date = json.getString("date");
//                    String gender = json.getString("gender");
//                    String dietType = json.getString("dietType");
//                    String fitnessLevel = json.getString("fitnessLevel");
//                    String focusZones = json.getString("focusZones");
//                    String physicalLimitations = json.getString("physicalLimitations");
//                    String startingWeight = json.getString("startingWeight");
//                    String targetWeight = json.getString("targetWeight");
//                    String height = json.getString("height");
//                    String imageUrl = json.getString("image");
//
//                    // Extract other profile fields as needed
//
//                    // Update the UI with the profile data
//                    profileNameTextView.setText(name);
//                    profileDateTextView.setText(date);
//                    profileGenderTextView.setText(gender);
//                    profileDietTypeTextView.setText(dietType);
//                    profileFitnessLevelTextView.setText(fitnessLevel);
//                    profileFocusZonesTextView.setText(focusZones);
//                    profilePhysicalLimitationsTextView.setText(physicalLimitations);
//                    profileStartingWeightTextView.setText(startingWeight);
//                    profileTargetWeightTextView.setText(targetWeight);
//                    profileHeightTextView.setText(height);
//
//                    if (imageUrl != null) {
//                        Picasso.get().load(Uri.parse(imageUrl)).into(profileImage);
//                    } else {
//                        // Set a default image if the image URI is not available
//                        profileImage.setImageResource(R.drawable.ic_launcher_foreground);
//                    }
//                    // Update other profile fields as needed
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
