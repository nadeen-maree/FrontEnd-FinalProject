package com.example.finalproject;

import static android.content.Context.MODE_PRIVATE;

import static com.example.finalproject.LoginTabFragment.SHARED_PREFS_KEY;

import android.content.SharedPreferences;
import android.os.AsyncTask;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Question3Fragment extends Fragment {

    private RadioGroup genderRadioGroup;
    private RadioButton maleRadioButton, femaleRadioButton, otherRadioButton;
    private FloatingActionButton nextButton3;

    public Question3Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question3, container, false);

        SharedPreferences.Editor editor = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE).edit();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);

        genderRadioGroup = view.findViewById(R.id.gender_radio_group);
        maleRadioButton = view.findViewById(R.id.male_radio_button);
        femaleRadioButton = view.findViewById(R.id.female_radio_button);
        otherRadioButton = view.findViewById(R.id.other_radio_button);
        nextButton3 = view.findViewById(R.id.next3_button);

        nextButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gender = "";
                int selectedId = genderRadioGroup.getCheckedRadioButtonId();

                if (selectedId == maleRadioButton.getId()) {
                    gender = "Male";
                } else if (selectedId == femaleRadioButton.getId()) {
                    gender = "Female";
                } else if (selectedId == otherRadioButton.getId()) {
                    gender = "Other";
                } else {
                    // Show error message and return
                    Toast.makeText(getContext(), "Please select a gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                editor.putString("gender", gender);
                editor.apply();

                Bundle bundle = new Bundle();
                bundle.putString("gender", gender);
                Question4Fragment question4Fragment = new Question4Fragment();
                question4Fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, question4Fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                String email = sharedPreferences.getString("email", "");
                // Perform the HTTP POST request in the activity
                String url = "http://10.0.2.2:8181/questionnaire?email" + email;
                String postData = "gender=" + gender;
                ((QuestionnaireActivity) getActivity()).performHttpPostRequest(url, postData);
            }
        });

        return view;
    }
//    private class HttpPostTask extends AsyncTask<String, Void, String> {
//
//        private String gender;
//        String response = "";
//        @Override
//        protected String doInBackground(String... params) {
//            gender = params[0];
//            String urlStr = "http://example.com/api/gender";
//            String postData = "gender=" + gender;
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
//                    bundle.putString("gender", gender);
//                    Question4Fragment question4Fragment = new Question4Fragment();
//                    question4Fragment.setArguments(bundle);
//                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                    transaction.replace(R.id.fragment_container, question4Fragment);
//                    transaction.addToBackStack(null);
//                    transaction.commit();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//

}
