package com.example.finalproject;

import static android.content.Context.MODE_PRIVATE;
import static com.example.finalproject.LoginTabFragment.SHARED_PREFS_KEY;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
<<<<<<< HEAD
=======

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2
import java.util.ArrayList;

public class Question6Fragment extends Fragment {
    CheckBox chestCheckbox, backCheckbox, armsCheckbox, legsCheckbox, absCheckbox;
    private FloatingActionButton nextButton6;
    private Context context;
    ArrayList<String> selectedFocusZones;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    static ArrayList<String> selectedFocusZones;

    private SharedPreferences sharedPreferences;
    private String email;

    public Question6Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = requireContext();
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question6, container, false);
<<<<<<< HEAD
=======
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");
        String apiEmail = email.replaceFirst("@", "__");

        context = getContext();
        apiService = ApiService.getInstance(context);
>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2

        chestCheckbox = view.findViewById(R.id.chest_checkbox);
        backCheckbox = view.findViewById(R.id.back_checkbox);
        armsCheckbox = view.findViewById(R.id.arms_checkbox);
        legsCheckbox = view.findViewById(R.id.legs_checkbox);
        absCheckbox = view.findViewById(R.id.abs_checkbox);
        nextButton6 = view.findViewById(R.id.next6_button);

        nextButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the checked state of each checkbox
                boolean chestChecked = chestCheckbox.isChecked();
                boolean backChecked = backCheckbox.isChecked();
                boolean armsChecked = armsCheckbox.isChecked();
                boolean legsChecked = legsCheckbox.isChecked();
                boolean absChecked = absCheckbox.isChecked();

                // Check if at least one checkbox is checked
                if (!chestChecked && !backChecked && !armsChecked && !legsChecked && !absChecked) {
                    // Display an error message
                    Toast.makeText(getActivity(), "Please select at least one focus zone", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a list to store the selected focus zones
                selectedFocusZones = new ArrayList<>();

                // Check each checkbox and add its label to the list if it's checked
                if (chestChecked) {
                    selectedFocusZones.add(chestCheckbox.getText().toString());
                }
                if (backChecked) {
                    selectedFocusZones.add(backCheckbox.getText().toString());
                }
                if (armsChecked) {
                    selectedFocusZones.add(armsCheckbox.getText().toString());
                }
                if (legsChecked) {
                    selectedFocusZones.add(legsCheckbox.getText().toString());
                }
                if (absChecked) {
                    selectedFocusZones.add(absCheckbox.getText().toString());
                }
                JsonObject requestBody = new JsonObject();
                JsonArray focusZonesArray = new JsonArray();

                for (String focusZone : selectedFocusZones) {
                    focusZonesArray.add(focusZone);
                }

<<<<<<< HEAD
                // Save the selected focus zones to shared preferences
                String focusZonesString = TextUtils.join(",", selectedFocusZones);
                editor.putString("focusZones", focusZonesString).apply();

                JsonObject requestBody = new JsonObject();
                JsonArray focusZonesArray = new JsonArray();
=======
                requestBody.add("focusZones", focusZonesArray);


                apiService.submitQuestionnaire(apiEmail,requestBody, new ApiService.DataSubmitCallback() {
                    @Override
                    public void onSuccess(ResponseModel response) {
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("selectedFocusZones", selectedFocusZones);
                        Question7Fragment question7Fragment = new Question7Fragment();
                        question7Fragment.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, question7Fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2

                for (String focusZones : selectedFocusZones) {
                    focusZonesArray.add(focusZones);
                }

                 Question7Fragment question7Fragment = new Question7Fragment();
                 FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                 transaction.replace(R.id.fragment_container, question7Fragment);
                 transaction.addToBackStack(null);
                 transaction.commit();
            }
        });
        return view;
    }

<<<<<<< HEAD
    public String getFocusZones() {
        // Retrieve the selected focus zones from shared preferences
        return sharedPreferences.getString("focusZones", "");
    }
}
=======
    public static ArrayList getFocusZones(){
        return selectedFocusZones;
    }

//    private class HttpPostTask extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... params) {
//            String url = params[0];
//            String selectedFocusZonesString = params[1];
//
//            try {
//                URL obj = new URL(url);
//                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//                // Set the request method and headers
//                con.setRequestMethod("POST");
//                con.setRequestProperty("Content-Type", "application/json");
//
//                // Create the request body
//                JSONObject requestBody = new JSONObject();
//                requestBody.put("selectedFocusZones", selectedFocusZonesString);
//
//                // Convert the request body to a byte array and set it as the request entity
//                byte[] requestBodyBytes = requestBody.toString().getBytes("UTF-8");
//                con.setDoOutput(true);
//                con.setFixedLengthStreamingMode(requestBodyBytes.length);
//                OutputStream outputStream = con.getOutputStream();
//                outputStream.write(requestBodyBytes);
//                outputStream.flush();
//                outputStream.close();
//
//                // Read the response from the server
//                int responseCode = con.getResponseCode();
//                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//                String inputLine;
//                StringBuilder response = new StringBuilder();
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();
//
//                // Return the response from the server
//                return response.toString();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String response) {
//            super.onPostExecute(response);
//
//            if (response != null) {
//                // Handle the response from the server
//                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
//            } else {
//                // Handle the error
//                Toast.makeText(getActivity(), "An error occurred", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//
//    }
}
>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2
