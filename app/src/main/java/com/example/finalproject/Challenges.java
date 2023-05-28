package com.example.finalproject;

import static com.example.finalproject.LoginTabFragment.SHARED_PREFS_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Challenges extends AppCompatActivity {

    TextView personal_plan, food, challenges, user_profile;
    FloatingActionButton exe;
    ConstraintLayout challenge1, challenge2, challenge3, challenge4, challenge5;
    TextView challengeName1, challengeName2, challengeName3, challengeName4, challengeName5;

    SharedPreferences sharedPreferences = Challenges.this.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
    String email = sharedPreferences.getString("email", "");
    String apiEmail = email.replaceFirst("@","__");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);

        personal_plan = findViewById(R.id.tab1_txt);
        food = findViewById(R.id.tab2_txt);
        challenges = findViewById(R.id.tab4_txt);
        user_profile = findViewById(R.id.tab5_txt);
        exe = findViewById(R.id.training_btn);
        challenge1 = findViewById(R.id.challenge1_constraint_layout);
        challenge2 = findViewById(R.id.challenge2_constraint_layout);
        challenge3 = findViewById(R.id.challenge3_constraint_layout);
        challenge4 = findViewById(R.id.challenge4_constraint_layout);
        challenge5 = findViewById(R.id.challenge5_constraint_layout);
        challengeName1 = findViewById(R.id.challenge1_name);
        challengeName2 = findViewById(R.id.challenge2_name);
        challengeName3 = findViewById(R.id.challenge3_name);
        challengeName4 = findViewById(R.id.challenge4_name);
        challengeName5 = findViewById(R.id.challenge5_name);



        personal_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Challenges.this, MainActivity.class);
                startActivity(intent);
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Challenges.this, Food.class);
                startActivity(intent);
            }
        });

        challenges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Challenges.this, Challenges.class);
                startActivity(intent);
            }
        });

        exe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Challenges.this, TrainingActivity.class);
                startActivity(intent);
            }
        });

        challenge1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int constraintLayoutId = R.id.challenge1_constraint_layout;
                Intent intent = new Intent(Challenges.this, Specific_Challenge.class);
                intent.putExtra("constraintLayoutId", constraintLayoutId);
                startActivity(intent);
            }
        });

        challenge2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int constraintLayoutId = R.id.challenge2_constraint_layout;
                Intent intent = new Intent(Challenges.this, Specific_Challenge.class);
                intent.putExtra("constraintLayoutId", constraintLayoutId);
                startActivity(intent);
            }
        });
        challenge3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int constraintLayoutId = R.id.challenge3_constraint_layout;
                Intent intent = new Intent(Challenges.this, Specific_Challenge.class);
                intent.putExtra("constraintLayoutId", constraintLayoutId);
                startActivity(intent);
            }
        });

        challenge4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int constraintLayoutId = R.id.challenge4_constraint_layout;
                Intent intent = new Intent(Challenges.this, Specific_Challenge.class);
                intent.putExtra("constraintLayoutId", constraintLayoutId);
                startActivity(intent);
            }
        });

        challenge5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int constraintLayoutId = R.id.challenge5_constraint_layout;
                Intent intent = new Intent(Challenges.this, Specific_Challenge.class);
                intent.putExtra("constraintLayoutId", constraintLayoutId);
                startActivity(intent);
            }
        });

        new FetchChallengesTask().execute();

    }

    private class FetchChallengesTask extends AsyncTask<String, Void, JSONArray> {


        @Override
        protected JSONArray doInBackground(String... params) {
            String apiUrl = "http://10.0.2.2:8181/challenge/" + apiEmail;

            try {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    return new JSONArray(response.toString());
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONArray challengesArray) {
            if (challengesArray != null) {
                try {
                    for (int i = 0; i < challengesArray.length(); i++) {
                        JSONObject challengeObject = challengesArray.getJSONObject(i);
                        String challengeName = challengeObject.getString("challengeName");
                        setChallengeName(i, challengeName);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setChallengeName(int index, String challengeName) {
        switch (index) {
            case 0:
                challengeName1.setText(challengeName);
                break;
            case 1:
                challengeName2.setText(challengeName);
                break;
            case 2:
                challengeName3.setText(challengeName);
                break;
            case 3:
                challengeName4.setText(challengeName);
                break;
            case 4:
                challengeName5.setText(challengeName);
                break;
        }
    }
}