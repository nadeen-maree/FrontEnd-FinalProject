package com.example.finalproject;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignupTabFragment extends Fragment {

    EditText email;
    EditText mobileNum;
    EditText pass;
    EditText confirmPass;
    Button signup;
    float v = 0;

    static final String SHARED_PREFS_KEY = "myPrefs";
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        //SharedPreferences.Editor editor = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE).edit();

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);

        email = root.findViewById(R.id.signup_email);
        mobileNum = root.findViewById(R.id.mobile_num);
        pass = root.findViewById(R.id.signup_pass);
        confirmPass = root.findViewById(R.id.conf_pass);
        signup = root.findViewById(R.id.sigmup_button);

        email.setTranslationX(800);
        mobileNum.setTranslationX(800);
        pass.setTranslationX(800);
        confirmPass.setTranslationX(800);
        signup.setTranslationX(800);

        email.setAlpha(v);
        mobileNum.setAlpha(v);
        pass.setAlpha(v);
        confirmPass.setAlpha(v);
        signup.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        mobileNum.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        confirmPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        signup.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QuestionnaireActivity.class);
                startActivity(intent);

                String userEmail = email.getText().toString();
                String signUpEmail = userEmail.replaceFirst("@","__");
                //editor.putString("userEmail", userEmail).apply();
                String userPassword = pass.getText().toString();
               // editor.putString("userPassword", userPassword).apply();
                String userMobileNumber = mobileNum.getText().toString();
               // editor.putString("userMobileNumber", userMobileNumber).apply();
                String userConfirmPassword = confirmPass.getText().toString();
               // editor.putString("userConfirmPassword", userConfirmPassword).apply();

                SignupTabFragment.HttpPostTask task = new SignupTabFragment.HttpPostTask();
                task.execute("http://10.0.2.2:8181/users/login", signUpEmail, userPassword,userMobileNumber, userConfirmPassword);

                String sharedEmail = email.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", sharedEmail);
                editor.apply();
            }
        });

        return root;
    }
    private class HttpPostTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {
            String response = "";
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("email", email.getText().toString());
                jsonParam.put("password", pass.getText().toString());
                jsonParam.put("mobileNumber", mobileNum.getText().toString());
                jsonParam.put("confirmPassword", confirmPass.getText().toString());

                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(jsonParam.toString());
                os.flush();
                os.close();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response += line;
                    }
                }else if(responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR){
                    Toast.makeText(getActivity(), "Unknown internal failure", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }


        protected void onPostExecute(String result) {
            try {
                JSONObject jsonResponse = new JSONObject(result);
                String message = jsonResponse.getString("message");
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

