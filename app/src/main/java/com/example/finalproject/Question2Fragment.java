package com.example.finalproject;

import static android.content.Context.MODE_PRIVATE;
import static com.example.finalproject.LoginTabFragment.SHARED_PREFS_KEY;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
<<<<<<< HEAD
=======
import com.google.gson.JsonObject;

>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2
import java.util.Calendar;
import java.util.Locale;
public class Question2Fragment extends Fragment {
<<<<<<< HEAD
    private TextView dateText;
=======

    private static TextView dateText;
>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2
    FloatingActionButton nextButton2;
    private Context context;
<<<<<<< HEAD
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
=======

    private SharedPreferences sharedPreferences;
    private String email;
    //private HttpRequestListener httpRequestListener;
>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2

    public Question2Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_question2, container, false);
<<<<<<< HEAD
=======
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");
        String apiEmail = email.replaceFirst("@", "__");

        context = getContext();
        apiService = ApiService.getInstance(context);
>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2

        dateText = view.findViewById(R.id.birthdate_text);
        nextButton2 = view.findViewById(R.id.next2_button);

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        nextButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = dateText.getText().toString().trim();

                if (date.equals("dd/MM/yyyy")) {
                    Toast.makeText(getContext(), "Please select a date", Toast.LENGTH_SHORT).show();
                    return;
                }

<<<<<<< HEAD
                // Save the date to shared preferences
                editor.putString("date", date).apply();
=======
                JsonObject requestBody = new JsonObject();
                requestBody.addProperty("date", date);

                apiService.submitQuestionnaire(apiEmail, requestBody, new ApiService.DataSubmitCallback() {
                    @Override
                    public void onSuccess(ResponseModel response) {
                        Bundle bundle = new Bundle();
                        bundle.putString("date", date);
                        Question3Fragment question3Fragment = new Question3Fragment();
                        question3Fragment.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, question3Fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2

                // Proceed to the next fragment
                Question3Fragment question3Fragment = new Question3Fragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, question3Fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Set the selected date to the TextView
                        dateText.setText(String.format(Locale.US, "%02d/%02d/%d", dayOfMonth, month + 1, year));
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
<<<<<<< HEAD
=======

    public static String getDate(){
        String date = dateText.getText().toString();
        return date;
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
//                jsonParam.put("date", dateText.getText().toString());
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
//            try {
//                JSONObject jsonResponse = new JSONObject(result);
//                String message = jsonResponse.getString("message");
//                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//
//                // Call the interface method to notify the Activity
//                if (httpRequestListener != null) {
//                    httpRequestListener.onHttpPostComplete(result);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public interface HttpRequestListener {
//        void onHttpPostComplete(String result);
//        void onHttpGetComplete(String result);
//    }
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        try {
//            httpRequestListener = (Question2Fragment.HttpRequestListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString() + " must implement HttpRequestListener");
//        }
//    }
>>>>>>> 4abad96672427dfefce71a1344d472c8b28634d2

    public String getDate() {
        // Retrieve the date from shared preferences
        return sharedPreferences.getString("date", "");
    }
}
