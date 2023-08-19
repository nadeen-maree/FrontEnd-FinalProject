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

public class Question1Fragment extends Fragment {
    private EditText nameEditText;
    FloatingActionButton nextButton;
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

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

        nameEditText = view.findViewById(R.id.user_name_editText);
        nextButton = view.findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString().trim(); // Get the name from the EditText

                if (name.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }

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

    public String getName() {
        // Retrieve the name from shared preferences
        return sharedPreferences.getString("name", "");
    }
}
