package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FastingActivity extends AppCompatActivity {
    TextView personal_plan, food, challenges, user_profile;
    FloatingActionButton exe;
    ConstraintLayout fasting13Hours, fasting16Hours, fasting18Hours, fasting20Hours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fasting);

        personal_plan = findViewById(R.id.tab1_txt);
        food = findViewById(R.id.tab2_txt);
        challenges = findViewById(R.id.tab4_txt);
        user_profile = findViewById(R.id.tab5_txt);
        exe = findViewById(R.id.training_btn);
        fasting13Hours = findViewById(R.id.fasting_13_hours_constraint_layout);
        fasting16Hours = findViewById(R.id.fasting_16_hours_constraint_layout);
        fasting18Hours = findViewById(R.id.fasting_18_hours_constraint_layout);
        fasting20Hours = findViewById(R.id.fasting_20_hours_constraint_layout);


        personal_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(FastingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(FastingActivity.this, Food.class);
                startActivity(intent);
            }
        });

        challenges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(FastingActivity.this, Challenges.class);
                startActivity(intent);
            }
        });

        exe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(FastingActivity.this, TrainingActivity.class);
                startActivity(intent);
            }
        });

        fasting13Hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int fastingPeriod13 = 13;
                int constraintLayoutId = R.id.fasting_13_hours_constraint_layout;
                Intent intent = new Intent(FastingActivity.this, FastingDetails.class);
                intent.putExtra("fastingPeriod", fastingPeriod13);
                intent.putExtra("constraintLayoutId", constraintLayoutId);
                startActivity(intent);
            }
        });

        fasting16Hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int fastingPeriod16 = 16;
                int constraintLayoutId = R.id.fasting_16_hours_constraint_layout;
                Intent intent = new Intent(FastingActivity.this, FastingDetails.class);
                intent.putExtra("fastingPeriod", fastingPeriod16);
                intent.putExtra("constraintLayoutId", constraintLayoutId);
                startActivity(intent);
            }
        });
        fasting18Hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int fastingPeriod18 = 18;
                int constraintLayoutId = R.id.fasting_18_hours_constraint_layout;
                Intent intent = new Intent(FastingActivity.this, FastingDetails.class);
                intent.putExtra("fastingPeriod", fastingPeriod18);
                intent.putExtra("constraintLayoutId", constraintLayoutId);
                startActivity(intent);
            }
        });
        fasting20Hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int fastingPeriod20 = 20;
                int constraintLayoutId = R.id.fasting_20_hours_constraint_layout;
                Intent intent = new Intent(FastingActivity.this, FastingDetails.class);
                intent.putExtra("fastingPeriod", fastingPeriod20);
                intent.putExtra("constraintLayoutId", constraintLayoutId);
                startActivity(intent);
            }
        });
    }
}