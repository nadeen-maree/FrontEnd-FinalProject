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
import android.widget.CompoundButton;
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

public class Question7Fragment extends Fragment {
    CheckBox noneCheckbox, kneePainCheckbox, backPainCheckbox, limitedMobilityCheckbox, otherCheckbox;
    private FloatingActionButton nextButton7;
    private Context context;
    ArrayList<String> selectedPhysicalLimitations;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    static ArrayList<String> selectedPhysicalLimitations;

    private SharedPreferences sharedPreferences;
    private String email;

    public Question7Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_question7, container, false);
<<<<<<< HEAD
=======

        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");
        String apiEmail = email.replaceFirst("@", "__");

        context = getContext();
        apiService = ApiService.getInstance(context);
>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2

        noneCheckbox = view.findViewById(R.id.none_checkbox);
        kneePainCheckbox = view.findViewById(R.id.knee_pain_checkbox);
        backPainCheckbox = view.findViewById(R.id.back_pain_checkbox);
        limitedMobilityCheckbox = view.findViewById(R.id.limited_mobility_checkbox);
        otherCheckbox = view.findViewById(R.id.other_checkbox);
        nextButton7 = view.findViewById(R.id.next7_button);

        noneCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    kneePainCheckbox.setChecked(false);
                    backPainCheckbox.setChecked(false);
                    limitedMobilityCheckbox.setChecked(false);
                    otherCheckbox.setChecked(false);
                }
            }
        });

        nextButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean noneChecked = noneCheckbox.isChecked();
                boolean kneePainChecked = kneePainCheckbox.isChecked();
                boolean backPainChecked = backPainCheckbox.isChecked();
                boolean limitedMobilityChecked = limitedMobilityCheckbox.isChecked();
                boolean otherChecked = otherCheckbox.isChecked();

                if (!noneChecked && !kneePainChecked && !backPainChecked && !limitedMobilityChecked && !otherChecked) {
                    Toast.makeText(getActivity(), "Please select at least one physical limitation or none option", Toast.LENGTH_SHORT).show();
                    return;
                }

<<<<<<< HEAD
=======
                // Create a list to store the selected focus zones
>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2
                selectedPhysicalLimitations = new ArrayList<>();

                if (noneChecked) {
                    selectedPhysicalLimitations.add(noneCheckbox.getText().toString());
                }
                if (kneePainChecked) {
                    selectedPhysicalLimitations.add(kneePainCheckbox.getText().toString());
                }
                if (backPainChecked) {
                    selectedPhysicalLimitations.add(backPainCheckbox.getText().toString());
                }
                if (limitedMobilityChecked) {
                    selectedPhysicalLimitations.add(limitedMobilityCheckbox.getText().toString());
                }
                if (otherChecked) {
                    selectedPhysicalLimitations.add(otherCheckbox.getText().toString());
                }

                if (noneChecked) {
                    kneePainCheckbox.setChecked(false);
                    backPainCheckbox.setChecked(false);
                    limitedMobilityCheckbox.setChecked(false);
                    otherCheckbox.setChecked(false);
                } else {
                    if (kneePainChecked || backPainChecked || limitedMobilityChecked || otherChecked) {
                        noneCheckbox.setChecked(false);
                    }
                }

<<<<<<< HEAD
                String physicalLimitationsString = TextUtils.join(",", selectedPhysicalLimitations);
                editor.putString("physicalLimitations", physicalLimitationsString).apply();
=======
                JsonObject requestBody = new JsonObject();
                JsonArray physicalLimitationsArray = new JsonArray();

                for (String physicalLimitations : selectedPhysicalLimitations) {
                    physicalLimitationsArray.add(physicalLimitations);
                }

                requestBody.add("physicalLimitations", physicalLimitationsArray);

                apiService.submitQuestionnaire(apiEmail,requestBody, new ApiService.DataSubmitCallback() {
                    @Override
                    public void onSuccess(ResponseModel response) {
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("selectedPhysicalLimitations", selectedPhysicalLimitations);
                        Question8Fragment question8Fragment = new Question8Fragment();
                        question8Fragment.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, question8Fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2

                JsonObject requestBody = new JsonObject();
                JsonArray physicalLimitationsArray = new JsonArray();

                for (String physicalLimitations : selectedPhysicalLimitations) {
                    physicalLimitationsArray.add(physicalLimitations);
                }

                 Question8Fragment question8Fragment = new Question8Fragment();
                 FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                 transaction.replace(R.id.fragment_container, question8Fragment);
                 transaction.addToBackStack(null);
                 transaction.commit();
            }
        });
        return view;
    }

<<<<<<< HEAD
    public String getPhysicalLimitations() {
        return sharedPreferences.getString("physicalLimitations", "");
    }
}
=======
    public static ArrayList getPhysicalLimitations(){
        return selectedPhysicalLimitations;
    }

//    private class HttpPostTask extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... params) {
//            String url = params[0];
//            String physicalLimitationsString = params[1];
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
//                requestBody.put("physicalLimitations", physicalLimitationsString);
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
