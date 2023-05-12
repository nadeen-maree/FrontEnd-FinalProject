package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.progresviews.ProgressWheel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Add_Food extends AppCompatActivity {

    TextView personal_plan, food, challenges, user_profile;
    FloatingActionButton exe;

    TextView targetText, caloriesMessageTextView;
    EditText breakfastEditText, lunchEditText, dinnerEditText, snackEditText;
    ProgressWheel progressWheel;

    int totalCalories = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        personal_plan = findViewById(R.id.tab1_txt);
        food = findViewById(R.id.tab2_txt);
        challenges = findViewById(R.id.tab4_txt);
        user_profile = findViewById(R.id.tab5_txt);
        exe = findViewById(R.id.training_btn);

        targetText = findViewById(R.id.targetText);
        breakfastEditText = findViewById(R.id.breakfast);
        lunchEditText = findViewById(R.id.lunch);
        dinnerEditText = findViewById(R.id.dinner);
        snackEditText = findViewById(R.id.snack);
        progressWheel = findViewById(R.id.wheelProgress);
        TextView caloriesMessageTextView = findViewById(R.id.calories_message_TextView);
        caloriesMessageTextView.setVisibility(View.GONE);

        int targetCalories = Integer.parseInt(targetText.getText().toString().replaceAll("[^\\d]", ""));
        int maxLength = String.valueOf(targetCalories).length(); // get length of targetCalories as max length
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(maxLength);
        breakfastEditText.setFilters(filterArray);
        lunchEditText.setFilters(filterArray);
        dinnerEditText.setFilters(filterArray);
        snackEditText.setFilters(filterArray);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        int savedTotalCalories = sharedPreferences.getInt("totalCalories", 0);
        totalCalories = savedTotalCalories;
        progressWheel.setPercentage(percentage(String.valueOf(totalCalories)));
        progressWheel.setStepCountText(String.valueOf(totalCalories));

        personal_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Add_Food.this, MainActivity.class);
                startActivity(intent);
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Add_Food.this, Food.class);
                startActivity(intent);
            }
        });

        challenges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Add_Food.this, Challenges.class);
                startActivity(intent);
            }
        });

        exe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Add_Food.this, TrainingActivity.class);
                startActivity(intent);
            }
        });

        // Define the TextWatcher
        TextWatcher mealCaloriesTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Update the totalCalories and progressWheel
                updateTotalCaloriesAndProgressWheel();
            }

            // Define the method to update the totalCalories and progressWheel
            private void updateTotalCaloriesAndProgressWheel() {
                int breakfastCalories = 0;
                int lunchCalories = 0;
                int dinnerCalories = 0;
                int snackCalories = 0;

                // Parse the EditText fields to integers
                if (!breakfastEditText.getText().toString().isEmpty()) {
                    breakfastCalories = Integer.parseInt(breakfastEditText.getText().toString());
                }
                if (!lunchEditText.getText().toString().isEmpty()) {
                    lunchCalories = Integer.parseInt(lunchEditText.getText().toString());
                }
                if (!dinnerEditText.getText().toString().isEmpty()) {
                    dinnerCalories = Integer.parseInt(dinnerEditText.getText().toString());
                }
                if (!snackEditText.getText().toString().isEmpty()) {
                    snackCalories = Integer.parseInt(snackEditText.getText().toString());
                }

                // Calculate the totalCalories and update the progressWheel
                totalCalories = breakfastCalories + lunchCalories + dinnerCalories + snackCalories;
                if (totalCalories > targetCalories) {
                    String message = "You have exceeded the recommended calories";
                    caloriesMessageTextView.setText(message);
                    caloriesMessageTextView.setVisibility(View.VISIBLE);
                } else {
                    caloriesMessageTextView.setVisibility(View.GONE);
                }
                progressWheel.setPercentage(percentage(String.valueOf(totalCalories)));
                progressWheel.setStepCountText(String.valueOf(totalCalories));

                // Save the updated values to SharedPreferences
                editor.putString("breakfast", breakfastEditText.getText().toString()).apply();
                editor.putString("lunch", lunchEditText.getText().toString()).apply();
                editor.putString("dinner", dinnerEditText.getText().toString()).apply();
                editor.putString("snack", snackEditText.getText().toString()).apply();
                editor.putInt("totalCalories", totalCalories).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Do nothing
            }
        };

// Register the TextWatcher on the EditText fields
        breakfastEditText.addTextChangedListener(mealCaloriesTextWatcher);
        lunchEditText.addTextChangedListener(mealCaloriesTextWatcher);
        dinnerEditText.addTextChangedListener(mealCaloriesTextWatcher);
        snackEditText.addTextChangedListener(mealCaloriesTextWatcher);

        String savedBreakfast = sharedPreferences.getString("breakfast", "");
        String savedLunch = sharedPreferences.getString("lunch", "");
        String savedDinner = sharedPreferences.getString("dinner", "");
        String savedSnack = sharedPreferences.getString("snack", "");

        // Set the text of the EditTexts to the saved data
        breakfastEditText.setText(savedBreakfast);
        lunchEditText.setText(savedLunch);
        dinnerEditText.setText(savedDinner);
        snackEditText.setText(savedSnack);
    }

    public int percentage(String calorie) {
        int calorieNumber = Integer.parseInt(calorie);
        String target = targetText.getText().toString();
        Double targetCalories = (double) Integer.parseInt(target.replaceAll("[^\\d]", ""));
        Double percentage = ((calorieNumber / targetCalories) * 360);
        int intPercentage = percentage.intValue();

        return intPercentage;
    }
}