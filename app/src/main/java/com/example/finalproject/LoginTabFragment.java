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
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginTabFragment extends Fragment {
     EditText email, pass;
     TextView forgetPass;
     private Button login;
     float v = 0;
    static final String SHARED_PREFS_KEY = "myPrefs";
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);

        email = root.findViewById(R.id.email);
        pass = root.findViewById(R.id.pass);
        forgetPass = root.findViewById(R.id.forget_pass);
        login = root.findViewById(R.id.login_btn);

        email.setTranslationX(800);
        pass.setTranslationX(800);
        forgetPass.setTranslationX(800);
        login.setTranslationX(800);

        email.setAlpha(v);
        pass.setAlpha(v);
        forgetPass.setAlpha(v);
        login.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgetPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = email.getText().toString();
                String logInEmail = userEmail.replaceFirst("@","__");
                String userPassword = pass.getText().toString();

                HttpPostTask task = new HttpPostTask();
                task.execute("http://10.0.2.2:8181/users/login", logInEmail, userPassword);

                Intent intent= new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

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