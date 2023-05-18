package com.example.finalproject;

import static com.example.finalproject.LoginTabFragment.SHARED_PREFS_KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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
        caloriesMessageTextView = findViewById(R.id.calories_message_TextView);
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

                // Create an instance of the HttpPostTask and execute it
                HttpPostTask httpPostTask = new HttpPostTask();
                httpPostTask.execute();

                // Create an instance of the HttpGetTask and execute it
                HttpGetTask httpGetTask = new HttpGetTask();
                httpGetTask.execute();
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

    SharedPreferences sharedPreferences = Add_Food.this.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
    String email = sharedPreferences.getString("email", "");

    private class HttpPostTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            // Perform the HTTP POST request here
            String urlStr = "http://10.0.2.2:8181/addfood?email" + email;
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                // Create the JSON request body
                JSONObject requestBody = new JSONObject();
                requestBody.put("breakfast", breakfastEditText.getText().toString());
                requestBody.put("lunch", lunchEditText.getText().toString());
                requestBody.put("dinner", dinnerEditText.getText().toString());
                requestBody.put("snack", snackEditText.getText().toString());

                // Write the JSON request body to the request stream
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(requestBody.toString());
                writer.flush();

                // Read the response from the server
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Successful response
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    // Return the response as a string
                    return response.toString();
                } else {
                    Toast.makeText(Add_Food.this, "Unknown internal failure", Toast.LENGTH_SHORT).show();
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                // Handle the response from the server
                Toast.makeText(Add_Food.this, "POST Response: " + result, Toast.LENGTH_SHORT).show();
            } else {
                // Error handling for unsuccessful response
                Toast.makeText(Add_Food.this, "POST request failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class HttpGetTask extends AsyncTask<Void, Void, String> {

        @Override
            protected String doInBackground(Void... params) {
                // Perform the HTTP GET request here
                String urlStr = "http://10.0.2.2:8181/addfood?email" + email +
                        "target=" + totalCalories +
                        "percentage=" + percentage(String.valueOf(totalCalories)) +
                        "breakfast=" + breakfastEditText.getText().toString() +
                        "lunch=" + lunchEditText.getText().toString() +
                        "dinner=" + dinnerEditText.getText().toString() +
                        "snack=" + snackEditText.getText().toString();

                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-Type", "application/json");

                    // Read the response from the server
                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Successful response
                        InputStream inputStream = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();
                        // Return the response as a string
                        return response.toString();
                    } else {
                        Toast.makeText(Add_Food.this, "Unknown internal failure", Toast.LENGTH_SHORT).show();
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    // Handle the response from the server
                    Toast.makeText(Add_Food.this, "GET Response: " + result, Toast.LENGTH_SHORT).show();
                } else {
                    // Error handling for unsuccessful response
                    Toast.makeText(Add_Food.this, "GET request failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
}