package com.example.finalproject;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Question5Fragment extends Fragment {

    private RadioGroup fitnessLevelRadioGroup;
    private FloatingActionButton nextButton5;

    public Question5Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question5, container, false);

        fitnessLevelRadioGroup = view.findViewById(R.id.fitness_level_radio_group);
        nextButton5 = view.findViewById(R.id.next5_button);

        nextButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = fitnessLevelRadioGroup.getCheckedRadioButtonId();
                String fitnessLevel = "";

                if (selectedId == R.id.beginner_radio_button) {
                    fitnessLevel = "Beginner";
                } else if (selectedId == R.id.intermediate_radio_button) {
                    fitnessLevel = "Intermediate";
                } else if (selectedId == R.id.advanced_radio_button) {
                    fitnessLevel = "Advanced";
                } else {
                // Show error message and return
                Toast.makeText(getContext(), "Please select a fitness level", Toast.LENGTH_SHORT).show();
                return;
            }
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE).edit();
                editor.putString("fitnessLevel", fitnessLevel);
                editor.apply();

                Bundle bundle = new Bundle();
                bundle.putString("fitness_level", fitnessLevel);
                Question6Fragment question6Fragment = new Question6Fragment();
                question6Fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, question6Fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}