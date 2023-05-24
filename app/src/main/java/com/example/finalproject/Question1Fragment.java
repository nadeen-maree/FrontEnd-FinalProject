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

import retrofit2.Call;
import retrofit2.Response;

public class Question1Fragment extends Fragment {

    private EditText nameEditText;
    FloatingActionButton nextButton;

    private ApiService apiService;
    private Context context;

    //private HttpRequestListener httpRequestListener;

    public Question1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();
        apiService = ApiService.getInstance(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question1, container, false);

        SharedPreferences.Editor editor = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE).edit();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);

        nameEditText = view.findViewById(R.id.user_name_editText);
        nextButton = view.findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                editor.putString("name", name).apply();

                if (name.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter your name", Toast.LENGTH_SHORT).show();
                } else {
                    apiService.submitName(name, new ApiService.DataSubmitCallback() {
                        @Override
                        public void onSuccess(ResponseModel response) {
                            Bundle bundle = new Bundle();
                            bundle.putString("name", name);
                            Question2Fragment question2Fragment = new Question2Fragment();
                            question2Fragment.setArguments(bundle);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, question2Fragment);
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
                }
            }
        });
        return view;
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


}
