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
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;
<<<<<<< HEAD

public class Question1Fragment extends Fragment {
    private EditText nameEditText;
=======

import retrofit2.Call;
import retrofit2.Response;

public class Question1Fragment extends Fragment {

    private static EditText nameEditText;
>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2
    FloatingActionButton nextButton;
    private Context context;
<<<<<<< HEAD
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
=======

    private SharedPreferences sharedPreferences;
    private String email;
    //private HttpRequestListener httpRequestListener;
>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2

    public Question1Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_question1, container, false);

<<<<<<< HEAD
=======
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE).edit();
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");
        String apiEmail = email.replaceFirst("@", "__");

>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2
        nameEditText = view.findViewById(R.id.user_name_editText);
        nextButton = view.findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString().trim(); // Get the name from the EditText

                JsonObject requestBody = new JsonObject();
                requestBody.addProperty("name", name);

                if (name.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter your name", Toast.LENGTH_SHORT).show();
<<<<<<< HEAD
                    return;
                }
=======
                } else {
                    apiService.submitQuestionnaire(apiEmail, requestBody, new ApiService.DataSubmitCallback() {
                        @Override
                        public void onSuccess(ResponseModel response) {
                            int responseCode = response.getResponseCode();
                            if (responseCode == 200) {
                                Bundle bundle = new Bundle();
                                bundle.putString("name", name);
                                Question2Fragment question2Fragment = new Question2Fragment();
                                question2Fragment.setArguments(bundle);
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.fragment_container, question2Fragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }else{
                                Toast.makeText(getActivity(), "response code not equal 200", Toast.LENGTH_SHORT).show();

                            }
                        }
>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2

                // Save the name to shared preferences
                editor.putString("name", name).apply();

                // Create the request body
                JsonObject requestBody = new JsonObject();
                requestBody.addProperty("name", name);

                // Proceed to the next fragment
                Question2Fragment question2Fragment = new Question2Fragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, question2Fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

<<<<<<< HEAD
    public String getName() {
        // Retrieve the name from shared preferences
        return sharedPreferences.getString("name", "");
    }
=======
public static String getName(){
     String name = nameEditText.getText().toString();
    return name;
}
//    private class HttpPostTask extends AsyncTask<String, Void, String> {
//
//        protected String doInBackground(String... params) {
//            String response = "";
//            try {
//                URL url = new URL(params[0]);
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("POST");
//                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//                conn.setDoOutput(true);
//                conn.setDoInput(true);
//
//                JSONObject jsonParam = new JSONObject();
//                jsonParam.put("name", nameEditText.getText().toString());
//
//                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
//                os.writeBytes(jsonParam.toString());
//                os.flush();
//                os.close();
//
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
//        protected void onPostExecute(String result) {
//                try {
//                    JSONObject jsonResponse = new JSONObject(result);
//                    String message = jsonResponse.getString("message");
//                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//
//                    // Call the interface method to notify the Activity
//                    if (httpRequestListener != null) {
//                        httpRequestListener.onHttpPostComplete(result);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//    }
//    public interface HttpRequestListener {
//        void onHttpPostComplete(String result);
//        void onHttpGetComplete(String result);
//    }
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        try {
//            httpRequestListener = (HttpRequestListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString() + " must implement HttpRequestListener");
//        }
//    }


>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2
}
