package com.example.finalproject;

import static android.content.Context.MODE_PRIVATE;

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

public class Question4Fragment extends Fragment {

    private RadioGroup dietTypeRadioGroup;
    private RadioButton traditionalRadioButton, vegetarianRadioButton, ketoRadioButton;
    private FloatingActionButton nextButton4;

    public Question4Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question4, container, false);

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
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE).edit();
                editor.putString("dietType", dietType);
                editor.apply();

                Bundle bundle = new Bundle();
                bundle.putString("dietType", dietType);
                Question5Fragment question5Fragment = new Question5Fragment();
                question5Fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, question5Fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}
