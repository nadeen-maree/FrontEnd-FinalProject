package com.example.finalproject;

import static com.example.finalproject.LoginTabFragment.SHARED_PREFS_KEY;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
<<<<<<< HEAD
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Response;
=======

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2

public class QuestionnaireActivity extends AppCompatActivity {
    private ApiService apiService;
    private Context context;
    private SharedPreferences sharedPreferences;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        context = getBaseContext();
        apiService = ApiService.getInstance(context);

        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");
        String apiEmail = email.replaceFirst("@", "__");

        if (savedInstanceState == null) {
            Question1Fragment question1Fragment = new Question1Fragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, question1Fragment);
            transaction.commit();
        }

<<<<<<< HEAD
        JsonObject requestBody = new JsonObject();

        apiService.submitQuestionnaire(apiEmail, requestBody, new ApiService.DataSubmitCallback() {
            @Override
            public void onSuccess(ResponseModel response) {
                int responseCode = response.getResponseCode();
                if (responseCode == 200) {
                    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

                    if (currentFragment instanceof Question1Fragment) {
                        Bundle bundle = currentFragment.getArguments();
                        if (bundle != null) {
                            String name = bundle.getString("name");
                            requestBody.addProperty("name", name);
                        }
                        Question2Fragment question2Fragment = new Question2Fragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, question2Fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } else if (currentFragment instanceof Question2Fragment) {
                        Bundle bundle = currentFragment.getArguments();
                        if (bundle != null) {
                            String date = bundle.getString("date");
                            requestBody.addProperty("date", date);
                        }
                        Question3Fragment question3Fragment = new Question3Fragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, question3Fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } else if (currentFragment instanceof Question3Fragment) {
                        Bundle bundle = currentFragment.getArguments();
                        if (bundle != null) {
                            String gender = bundle.getString("gender");
                            requestBody.addProperty("gender", gender);
                        }
                        Question4Fragment question4Fragment = new Question4Fragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, question4Fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } else if (currentFragment instanceof Question4Fragment) {
                        Bundle bundle = currentFragment.getArguments();
                        if (bundle != null) {
                            String dietType = bundle.getString("dietType");
                            requestBody.addProperty("dietType", dietType);
                        }
                        Question5Fragment question5Fragment = new Question5Fragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, question5Fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } else if (currentFragment instanceof Question5Fragment) {
                        Bundle bundle = currentFragment.getArguments();
                        if (bundle != null) {
                            String fitnessLevel = bundle.getString("fitnessLevel");
                            requestBody.addProperty("fitnessLevel", fitnessLevel);
                        }
                        Question6Fragment question6Fragment = new Question6Fragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, question6Fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } else if (currentFragment instanceof Question6Fragment) {
                        Bundle bundle = currentFragment.getArguments();
                        if (bundle != null) {
                            ArrayList<String> focusZonesArrayList = bundle.getStringArrayList("selectedFocusZones");
                            requestBody.add("focusZones", new Gson().toJsonTree(focusZonesArrayList));
                        }
                        Question7Fragment question7Fragment = new Question7Fragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, question7Fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } else if (currentFragment instanceof Question7Fragment) {
                        Bundle bundle = currentFragment.getArguments();
                        if (bundle != null) {
                            ArrayList<String> physicalLimitationsArrayList = bundle.getStringArrayList("selectedPhysicalLimitations");
                            requestBody.add("physicalLimitation", new Gson().toJsonTree(physicalLimitationsArrayList));
                        }
                        Question8Fragment question8Fragment = new Question8Fragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, question8Fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } else if (currentFragment instanceof Question8Fragment) {
                        Bundle bundle = currentFragment.getArguments();
                        if (bundle != null) {
                            String startingWeight = bundle.getString("startingWeight");
                            String targetWeight = bundle.getString("targetWeight");
                            String height = bundle.getString("height");
                            requestBody.addProperty("startingWeight", startingWeight);
                            requestBody.addProperty("targetWeight", targetWeight);
                            requestBody.addProperty("height", height);
                        }
                    }
              }
        }

                @Override
                public void onError(String errorMessage) {}

                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if (response.isSuccessful()) {
                        ResponseModel data = response.body();
                        onSuccess(data);
                    } else {
                        String errorMessage = "Error: " + response.code();
                        onError(errorMessage);
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable throwable) {}
            });
    }
}
=======
//        JsonObject requestBody = new JsonObject();
//        requestBody.addProperty("name", Question1Fragment.getName());
//        requestBody.addProperty("date", Question2Fragment.getDate()); // Replace with the actual date value
//        requestBody.addProperty("gender", Question3Fragment.getGender());
//        requestBody.addProperty("dietType", Question4Fragment.getDietType());
//        requestBody.addProperty("fitnessLevel", Question5Fragment.getFitnessLevel());
//        requestBody.addProperty("focusZones", new Gson().toJson(Question6Fragment.getFocusZones()));
//        requestBody.addProperty("physicalLimitation", new Gson().toJson(Question7Fragment.getPhysicalLimitations()));
//        requestBody.addProperty("startingWeight", Question8Fragment.getStartingWeight());
//        requestBody.addProperty("targetWeight",  Question8Fragment.getTargetWeight());
//        requestBody.addProperty("height",  Question8Fragment.getHeight());
//        requestBody.addProperty("Image", Edit_Profile.getImageUri());
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
//
//    public void performHttpPostRequest(String url, String postData) {
//        new HttpPostTask().execute(url, postData);
//    }
//
//    private class HttpPostTask extends AsyncTask<String, Void, String> {
//
//        protected String doInBackground(String... params) {
//            String urlStr = params[0];
//            String postData = params[1];
//            String response = "";
//
//            try {
//                URL url = new URL(urlStr);
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("POST");
//                conn.setDoOutput(true);
//                conn.setDoInput(true);
//                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//                conn.setRequestProperty("Content-Length", Integer.toString(postData.length()));
//
//                // Write POST data to request body
//                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
//                os.writeBytes(postData);
//                os.flush();
//                os.close();
//
//                // Read response from server
//                int responseCode = conn.getResponseCode();
//                if (responseCode == HttpURLConnection.HTTP_OK) {
//                    InputStream inputStream = conn.getInputStream();
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                    String line;
//                    while ((line = bufferedReader.readLine()) != null) {
//                        response += line;
//                    }
//                } else {
//                    Toast.makeText(QuestionnaireActivity.this, "Unknown internal failure", Toast.LENGTH_SHORT).show();
//                    return null;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return response;
//        }
//
//        protected void onPostExecute(String result) {
//            if (result != null) {
//                try {
//                    // Parse the JSON response
//                    JSONObject json = new JSONObject(result);
//
//                    // Extract data from the JSON response
//                    String message = json.getString("message");
//                    int status = json.getInt("status");
//
//                    // Perform actions or update UI based on the response
//                    if (status == HttpURLConnection.HTTP_OK) {
//                        // Success response
//                        Toast.makeText(QuestionnaireActivity.this, message, Toast.LENGTH_SHORT).show();
//
//                    } else {
//                        // Error response
//                        Toast.makeText(QuestionnaireActivity.this, "Unknown internal failure: " , Toast.LENGTH_SHORT).show();
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                // Handle case when the result is null or empty
//                Toast.makeText(QuestionnaireActivity.this, "Empty response", Toast.LENGTH_SHORT).show();
//
//            }
//        }
//    }
}
>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2
