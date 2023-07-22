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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;

public class Question4Fragment extends Fragment {
    private RadioGroup dietTypeRadioGroup;
    private RadioButton traditionalRadioButton, vegetarianRadioButton, ketoRadioButton;
    private FloatingActionButton nextButton4;
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    static String finalDietType = "";

    private SharedPreferences sharedPreferences;
    private String email;

    public Question4Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_question4, container, false);

<<<<<<< HEAD
=======
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE).edit();
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");
        String apiEmail = email.replaceFirst("@", "__");

        context = getContext();
        apiService = ApiService.getInstance(context);

>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2
        dietTypeRadioGroup = view.findViewById(R.id.diet_type__radio_group);
        traditionalRadioButton = view.findViewById(R.id.traditional_radio_button);
        vegetarianRadioButton = view.findViewById(R.id.vegetarian_radio_button);
        ketoRadioButton = view.findViewById(R.id.keto_radio_button);
        nextButton4 = view.findViewById(R.id.next4_button);

        nextButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dietType = "";
                int selectedId = dietTypeRadioGroup.getCheckedRadioButtonId();

                if (selectedId == traditionalRadioButton.getId()) {
                    dietType = "Traditional";
                } else if (selectedId == vegetarianRadioButton.getId()) {
                    dietType = "Vegetarian";
                } else if (selectedId == ketoRadioButton.getId()) {
                    dietType = "Keto";
                } else {
                    // Show error message and return
                    Toast.makeText(getContext(), "Please select a diet type", Toast.LENGTH_SHORT).show();
                    return;
                }
<<<<<<< HEAD
=======
                finalDietType = dietType;

                JsonObject requestBody = new JsonObject();
                requestBody.addProperty("dietType", finalDietType);
                apiService.submitQuestionnaire(apiEmail,requestBody, new ApiService.DataSubmitCallback() {
                    @Override
                    public void onSuccess(ResponseModel response) {
                        Bundle bundle = new Bundle();
                        bundle.putString("dietType", finalDietType);
                        Question5Fragment question5Fragment = new Question5Fragment();
                        question5Fragment.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, question5Fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2

                // Save the diet type to shared preferences
                editor.putString("dietType", dietType).apply();

                 Question5Fragment question5Fragment = new Question5Fragment();
                 FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                 transaction.replace(R.id.fragment_container, question5Fragment);
                 transaction.addToBackStack(null);
                 transaction.commit();
            }
        });
        return view;
    }

<<<<<<< HEAD
    public String getDietType() {
        // Retrieve the diet type from shared preferences
        return sharedPreferences.getString("dietType", "");
    }
=======
    public static String getDietType(){
        return finalDietType;
    }
//    private class HttpPostTask extends AsyncTask<String, Void, String> {
//
//        private String dietType;
//        String response = "";
//        @Override
//        protected String doInBackground(String... params) {
//            dietType = params[0];
//            String urlStr = "http://example.com/api/dietType";
//            String postData = "dietType=" + dietType;
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
//                    // Handle response from server
//                    // ...
//
//                    // Navigate to next question
//                    Bundle bundle = new Bundle();
//                    bundle.putString("dietType", dietType);
//                    Question5Fragment question5Fragment = new Question5Fragment();
//                    question5Fragment.setArguments(bundle);
//                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                    transaction.replace(R.id.fragment_container, question5Fragment);
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
