package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.progresviews.ProgressWheel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Water_Tracker extends AppCompatActivity {
    TextView personal_plan, food, challenges, user_profile;
    FloatingActionButton exe;

    private ProgressWheel progressWheel;
    private TextView mlTargetText, ml_message_TextView;
    private Button drinkWaterButton;
    private int totalMl = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_tracker);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        personal_plan = findViewById(R.id.tab1_txt);
        food = findViewById(R.id.tab2_txt);
        challenges = findViewById(R.id.tab4_txt);
        user_profile = findViewById(R.id.tab5_txt);
        exe = findViewById(R.id.training_btn);

        progressWheel = findViewById(R.id.wheelProgress);
        mlTargetText = findViewById(R.id.mlTargetText);
        ml_message_TextView = findViewById(R.id.ml_message_TextView);
        drinkWaterButton = findViewById(R.id.drinkWaterButton);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        int savedTotalMl = sharedPreferences.getInt("totalMl", 0);
        totalMl = savedTotalMl;
        progressWheel.setPercentage(percentage(String.valueOf(totalMl)));
        progressWheel.setStepCountText(String.valueOf(totalMl));

        personal_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Water_Tracker.this, MainActivity.class);
                startActivity(intent);
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Water_Tracker.this, Food.class);
                startActivity(intent);
            }
        });

        challenges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Water_Tracker.this, Challenges.class);
                startActivity(intent);
            }
        });

        exe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Water_Tracker.this, TrainingActivity.class);
                startActivity(intent);
            }
        });

        ml_message_TextView.setVisibility(View.GONE);
        drinkWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mlTarget = Integer.parseInt(mlTargetText.getText().toString().replace(",", ""));
                totalMl += 250;
                if (totalMl > mlTarget) {
                    String message = "You have exceeded the recommended milliliters";
                    ml_message_TextView.setText(message);
                    ml_message_TextView.setVisibility(View.VISIBLE);
                } else {
                    ml_message_TextView.setVisibility(View.GONE);
                }
                progressWheel.setPercentage(percentage(String.valueOf(totalMl)));
                progressWheel.setStepCountText(String.valueOf(totalMl));

                editor.putInt("totalMl", totalMl).apply();
            }
        });
    }
    public int percentage(String ml) {
        int calorieNumber = Integer.parseInt(ml);
        String target = mlTargetText.getText().toString();
        Double targetCalories = (double) Integer.parseInt(target.replaceAll("[^\\d]", ""));
        Double percentage = ((calorieNumber / targetCalories) * 360);
        int intPercentage = percentage.intValue();

        return intPercentage;
    }
}