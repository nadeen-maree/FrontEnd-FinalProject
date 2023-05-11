package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView profileImage;
    TextView profileNameTextView, profileDateTextView, profileGenderTextView, profileDietTypeTextView,
            profileFitnessLevelTextView, profileFocusZonesTextView, profilePhysicalLimitationsTextView,
            profileStartingWeightTextView, profileTargetWeightTextView, profileHeightTextView;
    Button editProfileButton;

    private Context mContext;
    private static final int REQUEST_CODE_EDIT_PROFILE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mContext = this; // initialize the context
        SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_EDIT_PROFILE && resultCode == RESULT_OK) {
            SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);

            String name = data.getStringExtra("name");
            prefs.edit().putString("name", name).apply();

            String date = data.getStringExtra("date");
            prefs.edit().putString("date", date).apply();

            String gender = data.getStringExtra("gender");
            prefs.edit().putString("gender", gender).apply();

            String dietType = data.getStringExtra("dietType");
            prefs.edit().putString("dietType", dietType).apply();

            String fitnessLevel = data.getStringExtra("fitnessLevel");
            prefs.edit().putString("fitnessLevel", fitnessLevel).apply();

            String focusZones = data.getStringExtra("focusZones");
            prefs.edit().putString("focusZones", focusZones).apply();

            String physicalLimitations = data.getStringExtra("physicalLimitations");
            prefs.edit().putString("physicalLimitations", physicalLimitations).apply();

            String startingWeight = data.getStringExtra("startingWeight");
            prefs.edit().putString("starting_weight", startingWeight).apply();

            String targetWeight = data.getStringExtra("targetWeight");
            prefs.edit().putString("target_weight", targetWeight).apply();

            String height = data.getStringExtra("height");
            prefs.edit().putString("height", height).apply();

            String imageUrl = prefs.getString("image", null);

            if (getApplicationContext() == null) {
                return;
            }

            if (imageUrl != null) {
                Picasso.get().load(Uri.parse(imageUrl)).into(profileImage);
            } else {
                // Set a default image if the image URI is not available
                profileImage.setImageResource(R.drawable.ic_launcher_foreground);
            }

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
        }
    }


}