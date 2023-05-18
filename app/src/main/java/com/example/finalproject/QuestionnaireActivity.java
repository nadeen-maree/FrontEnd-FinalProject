package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class QuestionnaireActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        if (savedInstanceState == null) {
            Question1Fragment question1Fragment = new Question1Fragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, question1Fragment);
            transaction.commit();
        }
    }

//    @Override
//    public void onHttpPostComplete(String result) {
//        Toast.makeText(this, "HTTP POST Response: " + result, Toast.LENGTH_SHORT).show();
//
//        //Start the HTTP GET request from Question2Fragment
//        Question2Fragment question2Fragment = new Question2Fragment();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.fragment_container, question2Fragment)
//                .addToBackStack(null)
//                .commit();
//    }
//
//    @Override
//    public void onHttpGetComplete(String result) {
//
//    }

    public void performHttpPostRequest(String url, String postData) {
        new HttpPostTask().execute(url, postData);
    }

    private class HttpPostTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {
            String urlStr = params[0];
            String postData = params[1];
            String response = "";

            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("Content-Length", Integer.toString(postData.length()));

                // Write POST data to request body
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(postData);
                os.flush();
                os.close();

                // Read response from server
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response += line;
                    }
                } else {
                    Toast.makeText(QuestionnaireActivity.this, "Unknown internal failure", Toast.LENGTH_SHORT).show();
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    // Parse the JSON response
                    JSONObject json = new JSONObject(result);

                    // Extract data from the JSON response
                    String message = json.getString("message");
                    int status = json.getInt("status");

                    // Perform actions or update UI based on the response
                    if (status == HttpURLConnection.HTTP_OK) {
                        // Success response
                        Toast.makeText(QuestionnaireActivity.this, message, Toast.LENGTH_SHORT).show();

                    } else {
                        // Error response
                        Toast.makeText(QuestionnaireActivity.this, "Unknown internal failure: " , Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // Handle case when the result is null or empty
                Toast.makeText(QuestionnaireActivity.this, "Empty response", Toast.LENGTH_SHORT).show();

            }
        }
    }
}