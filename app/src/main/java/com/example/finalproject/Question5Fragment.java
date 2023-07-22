package com.example.finalproject;

import static android.content.Context.MODE_PRIVATE;
import static com.example.finalproject.LoginTabFragment.SHARED_PREFS_KEY;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;

public class Question5Fragment extends Fragment {
    private RadioGroup fitnessLevelRadioGroup;
    private FloatingActionButton nextButton5;
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    static String finalFitnessLevel = "";

    private SharedPreferences sharedPreferences;
    private String email;

    public Question5Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_question5, container, false);
<<<<<<< HEAD
=======
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");
        String apiEmail = email.replaceFirst("@", "__");

        context = getContext();
        apiService = ApiService.getInstance(context);
>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2

        fitnessLevelRadioGroup = view.findViewById(R.id.fitness_level_radio_group);
        nextButton5 = view.findViewById(R.id.next5_button);

        nextButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = fitnessLevelRadioGroup.getCheckedRadioButtonId();
                String fitnessLevel = "";

                if (selectedId == R.id.beginner_radio_button) {
                    fitnessLevel = "Beginner";
                } else if (selectedId == R.id.intermediate_radio_button) {
                    fitnessLevel = "Intermediate";
                } else if (selectedId == R.id.advanced_radio_button) {
                    fitnessLevel = "Advanced";
                } else {
<<<<<<< HEAD
                    // Show error message and return
                    Toast.makeText(getContext(), "Please select a fitness level", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save the fitness level to shared preferences
                editor.putString("fitnessLevel", fitnessLevel).apply();

                 Question6Fragment question6Fragment = new Question6Fragment();
                 FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                 transaction.replace(R.id.fragment_container, question6Fragment);
                 transaction.addToBackStack(null);
                 transaction.commit();
=======
                // Show error message and return
                Toast.makeText(getContext(), "Please select a fitness level", Toast.LENGTH_SHORT).show();
                return;
            }
                finalFitnessLevel = fitnessLevel;
                JsonObject requestBody = new JsonObject();
                requestBody.addProperty("fitnessLevel", finalFitnessLevel);
                apiService.submitQuestionnaire(apiEmail,requestBody, new ApiService.DataSubmitCallback() {
                    @Override
                    public void onSuccess(ResponseModel response) {
                        Bundle bundle = new Bundle();
                        bundle.putString("fitnessLevel", finalFitnessLevel);
                        Question6Fragment question6Fragment = new Question6Fragment();
                        question6Fragment.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, question6Fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
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
>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2
            }
        });
        return view;
    }

<<<<<<< HEAD
    public String getFitnessLevel() {
        // Retrieve the fitness level from shared preferences
        return sharedPreferences.getString("fitnessLevel", "");
    }
=======
    public static String getFitnessLevel(){
        return finalFitnessLevel;
    }
//    private class HttpPostTask extends AsyncTask<String, Void, String> {
//
//        private String fitnessLevel;
//        String response = "";
//        @Override
//        protected String doInBackground(String... params) {
//            fitnessLevel = params[0];
//            String urlStr = "http://example.com/api/fitnessLevel";
//            String postData = "fitnessLevel=" + fitnessLevel;
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
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return response;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            if (result != null) {
//                try {
//                    JSONObject json = new JSONObject(result);
//
//
//                    // Navigate to next question
//                    Bundle bundle = new Bundle();
//                    bundle.putString("fitnessLevel", fitnessLevel);
//                    Question6Fragment question6Fragment = new Question6Fragment();
//                    question6Fragment.setArguments(bundle);
//                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                    transaction.replace(R.id.fragment_container, question6Fragment);
//                    transaction.addToBackStack(null);
//                    transaction.commit();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2
}
