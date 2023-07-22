package com.example.finalproject;

<<<<<<< HEAD
import static com.example.finalproject.LoginTabFragment.SHARED_PREFS_KEY;
=======
>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
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
import static com.example.finalproject.LoginTabFragment.SHARED_PREFS_KEY;


public class Add_Food extends AppCompatActivity {

    TextView personal_plan, food, challenges, user_profile;
    FloatingActionButton exe;
    TextView targetText, caloriesMessageTextView;
    EditText breakfastEditText, lunchEditText, dinnerEditText, snackEditText;
    ProgressWheel progressWheel;
<<<<<<< HEAD
    Button save;
=======

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS_KEY = "myPrefs";
>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2
    int totalCalories = 0;
    int targetCalories = 0;
    private String apiEmail = "";
    String breakfast = "", lunch = "", dinner = "", snack = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

<<<<<<< HEAD
        SharedPreferences sharedPreferences = Add_Food.this.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        apiEmail = email.replaceFirst("@","__");

=======
>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2
        personal_plan = findViewById(R.id.tab1_txt);
        food = findViewById(R.id.tab2_txt);
        challenges = findViewById(R.id.tab4_txt);
        user_profile = findViewById(R.id.tab5_txt);
        exe = findViewById(R.id.training_btn);

        save = findViewById(R.id.save);
        targetText = findViewById(R.id.targetText);
        breakfastEditText = findViewById(R.id.breakfast);
        lunchEditText = findViewById(R.id.lunch);
        dinnerEditText = findViewById(R.id.dinner);
        snackEditText = findViewById(R.id.snack);
        progressWheel = findViewById(R.id.wheelProgress);
        caloriesMessageTextView = findViewById(R.id.calories_message_TextView);
        caloriesMessageTextView.setVisibility(View.GONE);


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

        user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Add_Food.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        String targetCaloriesText = targetText.getText().toString().replaceAll("[^\\d]", "");
        if (!targetCaloriesText.isEmpty()) {
            targetCalories = Integer.parseInt(targetCaloriesText);
        } else {
            targetCalories = 0; // Set a default value
            // Show an error message to the user
            Toast.makeText(this, "Invalid target calories", Toast.LENGTH_SHORT).show();
        }
        int maxLength = String.valueOf(targetCalories).length();
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(maxLength);
        breakfastEditText.setFilters(filterArray);
        lunchEditText.setFilters(filterArray);
        dinnerEditText.setFilters(filterArray);
        snackEditText.setFilters(filterArray);

        int savedTotalCalories = sharedPreferences.getInt("totalCalories", 0);
        totalCalories = savedTotalCalories;
        progressWheel.setPercentage(percentage(String.valueOf(totalCalories)));
        progressWheel.setStepCountText(String.valueOf(totalCalories));

        String savedBreakfast = sharedPreferences.getString("breakfast", "");
        String savedLunch = sharedPreferences.getString("lunch", "");
        String savedDinner = sharedPreferences.getString("dinner", "");
        String savedSnack = sharedPreferences.getString("snack", "");
        breakfast = savedBreakfast;
        lunch = savedLunch;
        dinner = savedDinner;
        snack = savedSnack;
        breakfastEditText.setText(breakfast);
        lunchEditText.setText(lunch);
        dinnerEditText.setText(dinner);
        snackEditText.setText(snack);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        int breakfastCalories = 0;
                        int lunchCalories = 0;
                        int dinnerCalories = 0;
                        int snackCalories = 0;


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

                        totalCalories = breakfastCalories + lunchCalories + dinnerCalories + snackCalories;
                        if (totalCalories > targetCalories) {
                            String message = "You have exceeded the recommended calories";
                            caloriesMessageTextView.setText(message);
                            caloriesMessageTextView.setVisibility(View.VISIBLE);
                        } else {
                            caloriesMessageTextView.setVisibility(View.GONE);
                        }

                        breakfastEditText.setText(String.valueOf(breakfastCalories));
                        lunchEditText.setText(String.valueOf(lunchCalories));
                        dinnerEditText.setText(String.valueOf(dinnerCalories));
                        snackEditText.setText(String.valueOf(snackCalories));

                        breakfast = breakfastEditText.getText().toString();
                        lunch = lunchEditText.getText().toString();
                        dinner = dinnerEditText.getText().toString();
                        snack = snackEditText.getText().toString();

                        progressWheel.setPercentage(percentage(String.valueOf(totalCalories)));
                        progressWheel.setStepCountText(String.valueOf(totalCalories));

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("breakfast", breakfast);
                        editor.putString("lunch", lunch);
                        editor.putString("dinner", dinner);
                        editor.putString("snack", snack);
                        editor.putInt("totalCalories", totalCalories);
                        editor.apply();

                Add_Food.HttpPostTask httpPostTask = new HttpPostTask();
                httpPostTask.execute();

                HttpGetTask httpGetTask = new HttpGetTask();
                httpGetTask.execute();
            }
        });
    }

    public int percentage(String calorie) {
        int calorieNumber = Integer.parseInt(calorie);
        String target = targetText.getText().toString();
        Double targetCalories = (double) Integer.parseInt(target.replaceAll("[^\\d]", ""));
        Double percentage = ((calorieNumber / targetCalories) * 360);
        int intPercentage = percentage.intValue();

        return intPercentage;
    }

<<<<<<< HEAD
=======
    String email = sharedPreferences.getString("email", "");
    String apiEmail = email.replaceFirst("@","__");

>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2
    private class HttpPostTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String urlStr = "http://10.0.2.2:8181/food/" + apiEmail;
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                JSONObject requestBody = new JSONObject();
                requestBody.put("breakfast", breakfast);
                requestBody.put("lunch", lunch);
                requestBody.put("dinner", dinner);
                requestBody.put("snack", snack);

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
            } else {
                // Error handling for unsuccessful response
                Toast.makeText(Add_Food.this, "POST request failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class HttpGetTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String urlStr = "http://10.0.2.2:8181/food/" + apiEmail;

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
            } else {
                // Error handling for unsuccessful response
                Toast.makeText(Add_Food.this, "GET request failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}