package com.example.finalproject;

import static android.content.Context.MODE_PRIVATE;

import static com.example.finalproject.LoginTabFragment.SHARED_PREFS_KEY;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Response;


public class Question8Fragment extends Fragment {

    private NumberPicker startingWeightPicker, targetWeightPicker, heightPicker;
    private FloatingActionButton finishButton;

    private ApiService apiService;
    private Context context;

    //private SharedPreferences sharedPreferences;


    private static final int MAX_WEIGHT_KG = 150;
    private static final int MAX_HEIGHT_CM = 250;

    public Question8Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question8, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);

        context = getContext();
        apiService = ApiService.getInstance(context);

        //sharedPreferences = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);

        startingWeightPicker = view.findViewById(R.id.starting_weight_picker);
        targetWeightPicker = view.findViewById(R.id.target_weight_picker);
        heightPicker = view.findViewById(R.id.height_picker);
        finishButton = view.findViewById(R.id.finish_button);

        // Set up the starting weight picker
        startingWeightPicker.setMinValue(0);
        startingWeightPicker.setMaxValue(MAX_WEIGHT_KG);
        startingWeightPicker.setValue(70);

        // Set up the target weight picker
        targetWeightPicker.setMinValue(0);
        targetWeightPicker.setMaxValue(MAX_WEIGHT_KG);
        targetWeightPicker.setValue(50);

        // Set up the target weight picker
        heightPicker.setMinValue(0);
        heightPicker.setMaxValue(MAX_HEIGHT_CM);
        heightPicker.setValue(160);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int startingWeight = startingWeightPicker.getValue();
                int targetWeight = targetWeightPicker.getValue();
                int height = heightPicker.getValue();

                // Check if any of the number pickers haven't been used
                if (startingWeight == startingWeightPicker.getMinValue() &&
                        targetWeight == targetWeightPicker.getMinValue() &&
                        height == heightPicker.getMinValue()) {
                    Toast.makeText(getActivity(), "Please select your height, weight, and target weight ", Toast.LENGTH_SHORT).show();
                    return;
                }

                String startingWeightString = Integer.toString(startingWeight);
                String targetWeightString = Integer.toString(targetWeight);
                String heightString = Integer.toString(height);
                apiService.submitUserDetails(startingWeightString, targetWeightString, heightString, new ApiService.DataSubmitCallback() {
                    @Override
                    public void onSuccess(ResponseModel response) {
                        Bundle bundle = new Bundle();
                        bundle.putString("startingWeight", startingWeightString);
                        bundle.putString("targetWeight", targetWeightString);
                        bundle.putString("height", heightString);
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                    }

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
                    public void onFailure(Call<ResponseModel> call, Throwable throwable) {
                        String errorMessage = "Request failed: " + throwable.getMessage();
                        onError(errorMessage);
                    }
                });
            }
        });

        return view;
    }

//    private class HttpPostTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            String startingWeight = params[0];
//            String targetWeight = params[1];
//            String height = params[2];
//            String urlStr = "http://example.com/api/questionnaire";
//
//            try {
//                URL url = new URL(urlStr);
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("POST");
//                conn.setRequestProperty("Content-Type", "application/json");
//                conn.setDoOutput(true);
//
//                // Create the JSON request body
//                JSONObject requestBody = new JSONObject();
//                requestBody.put("starting_weight", startingWeight);
//                requestBody.put("target_weight", targetWeight);
//                requestBody.put("height", height);
//
//                // Write the JSON request body to the request stream
//                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
//                writer.write(requestBody.toString());
//                writer.flush();
//
//                // Read the response from the server
//                int responseCode = conn.getResponseCode();
//                if (responseCode == HttpURLConnection.HTTP_OK) {
//                    // Successful response
//                    InputStream inputStream = conn.getInputStream();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//                    StringBuilder response = new StringBuilder();
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        response.append(line);
//                    }
//                    reader.close();
//
//                    // Return the response as a string
//                    return response.toString();
//                } else {
//                    // Error handling for unsuccessful response
//                    return null;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            if (result != null) {
//                // Handle the response from the server
//                Toast.makeText(getActivity(), "Response: " + result, Toast.LENGTH_SHORT).show();
//            } else {
//                // Error handling for unsuccessful response
//                Toast.makeText(getActivity(), "An error occurred", Toast.LENGTH_SHORT).show();
//            }
//
//            // Do something with the questionnaire data, e.g. start a new activity
//
//        }
//    }
}