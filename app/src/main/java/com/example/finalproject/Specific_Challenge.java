package com.example.finalproject;

import static com.example.finalproject.LoginTabFragment.SHARED_PREFS_KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class Specific_Challenge extends AppCompatActivity {
    ImageView challengeImage, back;
    TextView challengeName, difficultyLevel, description;
    Button joinButton;
    int constraintLayoutId;
    String apiEmail = "";
    String specificChallengeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_challenge);

        SharedPreferences sharedPreferences = Specific_Challenge.this.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        apiEmail = email.replaceFirst("@", "__");

        specificChallengeName = getIntent().getStringExtra("challengeName");

        challengeImage = findViewById(R.id.challenge_image);
        back = findViewById(R.id.back);
        challengeName = findViewById(R.id.challenge_name);
        difficultyLevel = findViewById(R.id.challenge_difficulty_level);
        description = findViewById(R.id.description);
        joinButton = findViewById(R.id.join_btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Specific_Challenge.this, Challenges.class);
                startActivity(intent);
            }
        });

        // Get the constraintLayoutId from the intent extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            constraintLayoutId = extras.getInt("constraintLayoutId");
        }

        // Execute the API request
        new FetchChallengeDetailsTask().execute();
        //new LoadImageTask().execute();

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String challengeNameValue = challengeName.getText().toString();
                new SignUpChallengeTask().execute(challengeNameValue);
            }
        });
    }

    private class FetchChallengeDetailsTask extends AsyncTask<Void, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(Void... voids) {
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
        protected void onPostExecute(JSONArray challengeArray) {
            if (challengeArray != null) {
                try {
                    for (int i = 0; i < challengeArray.length(); i++) {
                        JSONObject challengeObject = challengeArray.getJSONObject(i);
                        String challengeNameValue = challengeObject.getString("challengeName");
                        String difficultyLevelValue = challengeObject.getString("difficultyLevel");
                        String imageUri = challengeObject.getString("image");
                        String descriptionValue = challengeObject.getString("description");
                        if(Objects.equals(specificChallengeName, challengeNameValue)) {
                            // Set the challenge details in the UI
                            challengeName.setText(challengeNameValue);
                            difficultyLevel.setText(difficultyLevelValue);
                            description.setText(descriptionValue);
                            // Load the challenge image using the provided URI
                            new LoadImageTask().execute(imageUri);
                            break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
            @Override
            protected Bitmap doInBackground(String... params) {
                String imageUri = params[0];
                try {
                    URL url = new URL(imageUri);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    return BitmapFactory.decodeStream(input);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    challengeImage.setImageBitmap(bitmap);
                }
            }
        }
    }

        private class SignUpChallengeTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                String apiUrl = "http://10.0.2.2:8181/challenge/" + apiEmail;

                try {
                    URL url = new URL(apiUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("PUT");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);

                    JSONObject requestBody = new JSONObject();
                    requestBody.put("challengeName", params[0]);

                    OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
                    outputStream.write(requestBody.toString().getBytes());
                    outputStream.flush();

                    // Read the response from the server
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Successful response
                        InputStream inputStream = connection.getInputStream();
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
                        Toast.makeText(Specific_Challenge.this, "Unknown internal failure", Toast.LENGTH_SHORT).show();
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
                    joinButton.setText("JOINED");

                } else {
                    // Error handling for unsuccessful response
                    Toast.makeText(Specific_Challenge.this, "Can't join this challenge", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }