package com.example.finalproject;

import static com.example.finalproject.LoginTabFragment.SHARED_PREFS_KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.progresviews.ProgressWheel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Water_Tracker extends AppCompatActivity {
    TextView personal_plan, food, challenges, user_profile;
    FloatingActionButton exe;
    private ProgressWheel progressWheel;
    private TextView mlTargetText, ml_message_TextView;
    private Button drinkWaterButton;
    private int totalMl = 0;

    String apiEmail = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_tracker);

        SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = Water_Tracker.this.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        apiEmail = email.replaceFirst("@","__");

        personal_plan = findViewById(R.id.tab1_txt);
        food = findViewById(R.id.tab2_txt);
        challenges = findViewById(R.id.tab4_txt);
        user_profile = findViewById(R.id.tab5_txt);
        exe = findViewById(R.id.training_btn);

        progressWheel = findViewById(R.id.wheelProgress);
        mlTargetText = findViewById(R.id.mlTargetText);
        ml_message_TextView = findViewById(R.id.ml_message_TextView);
        drinkWaterButton = findViewById(R.id.drinkWaterButton);

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

        user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Water_Tracker.this, ProfileActivity.class);
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

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("totalMl", totalMl).apply();

                // Create an instance of the HttpPostTask and execute it
                Water_Tracker.HttpPostTask httpPostTask = new HttpPostTask();
                httpPostTask.execute();

                // Create an instance of the HttpGetTask and execute it
                Water_Tracker.HttpGetTask httpGetTask = new HttpGetTask();
                httpGetTask.execute();
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

    private class HttpPostTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String urlStr = "http://10.0.2.2:8181/water/" + apiEmail;
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                // Create the JSON request body
                JSONObject requestBody = new JSONObject();
                requestBody.put("totalMl", totalMl);


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
                    Toast.makeText(Water_Tracker.this, "Unknown internal failure", Toast.LENGTH_SHORT).show();
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
                try {
                    JSONObject responseJson = new JSONObject(result);
                    int updatedTotalMl = responseJson.optInt("totalMl", totalMl);

                    // Update the variables with the new values
                    totalMl = updatedTotalMl;
                    progressWheel.setPercentage(percentage(String.valueOf(totalMl)));
                    progressWheel.setStepCountText(String.valueOf(totalMl));

                    // ...remaining code...
                } catch (JSONException e) {
                    e.printStackTrace();
                    // Error handling for invalid response
                   // Toast.makeText(Water_Tracker.this, "Invalid response from server", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(Water_Tracker.this, "POST Response: " + result, Toast.LENGTH_SHORT).show();
            } else {
                // Error handling for unsuccessful response
              //  Toast.makeText(Water_Tracker.this, "POST request failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class HttpGetTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            // Perform the HTTP GET request
            String urlStr = "http://10.0.2.2:8181/water/" + apiEmail;

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
                    Toast.makeText(Water_Tracker.this, "Unknown internal failure", Toast.LENGTH_SHORT).show();
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
                try {
                    JSONObject responseJson = new JSONObject(result);
                    int updatedTotalMl = responseJson.optInt("totalMl", totalMl);

                    // Update the variables with the new values
                    totalMl = updatedTotalMl;
                    progressWheel.setPercentage(percentage(String.valueOf(totalMl)));
                    progressWheel.setStepCountText(String.valueOf(totalMl));

                } catch (JSONException e) {
                    e.printStackTrace();
                    // Error handling for invalid response
                    //Toast.makeText(Water_Tracker.this, "Invalid response from server", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(Water_Tracker.this, "GET Response: " + result, Toast.LENGTH_SHORT).show();
            } else {
                // Error handling for unsuccessful response
                //Toast.makeText(Water_Tracker.this, "GET request failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}