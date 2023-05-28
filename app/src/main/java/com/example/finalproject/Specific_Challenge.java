package com.example.finalproject;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Specific_Challenge extends AppCompatActivity {

    ImageView challengeImage, back;
    TextView challengeName, difficultyLevel, description;
    Button joinButton;

    int constraintLayoutId;

    SharedPreferences sharedPreferences = Specific_Challenge.this.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
    String email = sharedPreferences.getString("email", "");
    String apiEmail = email.replaceFirst("@","__");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_challenge);

        challengeImage = findViewById(R.id.challenge_image);
        back = findViewById(R.id.back);
        challengeName = findViewById(R.id.challenge_name);
        difficultyLevel = findViewById(R.id.challenge_difficulty_level);
        description = findViewById(R.id.description);
        joinButton = findViewById(R.id.join_btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Specific_Challenge.this, Challenges.class);
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

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String challengeNameValue = challengeName.getText().toString();
                new SignUpChallengeTask().execute(challengeNameValue);
            }
        });

        new FetchChallengeDetailsTask().execute();

    }
    private class FetchChallengeDetailsTask extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... voids) {
            String apiUrl = "http://10.0.2.2:8181/challenge/" + apiEmail;

            try {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    String jsonResponse = convertInputStreamToString(inputStream);
                    return new JSONObject(jsonResponse);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject challengeObject) {
            if (challengeObject != null) {
                try {
                    String challengeNameValue = challengeObject.getString("challengeName");
                    String difficultyLevelValue = challengeObject.getString("difficultyLevel");
                    String imageUri = challengeObject.getString("image");
                    String descriptionValue = challengeObject.getString("description");

                    challengeName.setText(challengeNameValue);
                    difficultyLevel.setText(difficultyLevelValue);
                    description.setText(descriptionValue);

                    // Load the challenge image using the provided URI
                    new LoadImageTask().execute(imageUri);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    private class SignUpChallengeTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
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

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    String jsonResponse = convertInputStreamToString(inputStream);
                    JSONObject response = new JSONObject(jsonResponse);
                    return response.getBoolean("joined");
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean joined) {
            if (joined) {
                Toast.makeText(Specific_Challenge.this, "Challenge joined successfully", Toast.LENGTH_SHORT).show();
                joinButton.setText("Joined");
            } else {
                Toast.makeText(Specific_Challenge.this, "Failed to join the challenge", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder response = new StringBuilder();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            response.append(new String(buffer, 0, bytesRead));
        }
        inputStream.close();
        return response.toString();
    }
}