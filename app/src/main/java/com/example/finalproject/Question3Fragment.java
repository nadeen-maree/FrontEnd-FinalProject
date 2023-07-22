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

public class Question3Fragment extends Fragment {
    private RadioGroup genderRadioGroup;
    private RadioButton maleRadioButton, femaleRadioButton, otherRadioButton;
    private FloatingActionButton nextButton3;
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public Question3Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_question3, container, false);

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

                // Save the gender to shared preferences
                editor.putString("gender", gender).apply();

                // Proceed to the next fragment
                Question4Fragment question4Fragment = new Question4Fragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, question4Fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

    public String getGender() {
        // Retrieve the gender from shared preferences
        return sharedPreferences.getString("gender", "");
    }
}

