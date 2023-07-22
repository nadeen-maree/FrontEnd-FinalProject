package com.example.finalproject;

import static com.example.finalproject.LoginTabFragment.SHARED_PREFS_KEY;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;

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
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, Food.class);
                startActivity(intent);
            }
        });

        challenges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, Challenges.class);
                startActivity(intent);
            }
        });

        exe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, TrainingActivity.class);
                startActivity(intent);
            }
        });

        user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
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

        String focusZones = prefs.getString("focusZones", "");
        profileFocusZonesTextView.setText(focusZones);

        String physicalLimitations = prefs.getString("physicalLimitations", "");
        profilePhysicalLimitationsTextView.setText(physicalLimitations);

        String startingWeight = prefs.getString("startingWeight", "");
        profileStartingWeightTextView.setText(startingWeight);

        String targetWeight = prefs.getString("targetWeight", "");
        profileTargetWeightTextView.setText(targetWeight);

        String height = prefs.getString("height", "");
        profileHeightTextView.setText(height);

        String imageUriString = prefs.getString("Image", "");
        Uri imageUri = Uri.parse(imageUriString);

        // Load the image into the CircleImageView using Glide or any other image loading library
        Glide.with(this)
                .load(imageUri)
                .into(profileImage);

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
                                   String startingWeight, String targetWeight, String height, String image) {
        SharedPreferences sharedPreferences = ProfileActivity.this.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        String apiEmail = email.replaceFirst("@", "__");
        String baseUrl = "http://10.0.2.2:8181/questionnaires/";
        String endpoint = apiEmail;
        String query = "name=" + name + "&date=" + date + "&gender=" + gender + "&dietType=" + dietType + "&fitnessLevel=" + fitnessLevel +
                "&focusZones=" + focusZones + "&physicalLimitations=" + physicalLimitations +
                "&startingWeight=" + startingWeight + "&targetWeight=" + targetWeight + "&height=" + height + "&image=" + image;
        String url = baseUrl + endpoint + "?" + query;

        //HTTPS GET request uses the ApiService
        apiService.get(url, new ApiService.DataSubmitCallback() {

            @Override
            public void onSuccess(ResponseModel response) {}

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
                        String image = prefs.getString("Image", null);
                        // Update the local SharedPreferences with the new profile data
                        prefs.edit()
                                .putString("name", name)
                                .putString("date", date)
                                .putString("gender", gender)
                                .putString("dietType", dietType)
                                .putString("fitnessLevel", fitnessLevel)
                                .putString("focusZones", focusZones)
                                .putString("physicalLimitations", physicalLimitations)
                                .putString("startingWeight", startingWeight)
                                .putString("targetWeight", targetWeight)
                                .putString("height", height)
                                .putString("Image", image)
                                .apply();


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

                        if (image != null) {
                            Picasso.get().load(Uri.parse(image)).into(profileImage);
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
            String image = prefs.getString("Image", null);

            updateProfileData(name, date, gender, dietType, fitnessLevel, focusZones,
                    physicalLimitations, startingWeight, targetWeight, height, image);
        }
    }
}