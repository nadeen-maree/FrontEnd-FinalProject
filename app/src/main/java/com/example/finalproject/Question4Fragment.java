package com.example.finalproject;

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

    private RadioGroup fitnessLevelRadioGroup;
    private FloatingActionButton nextButton4;

    public Question4Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question4, container, false);

        fitnessLevelRadioGroup = view.findViewById(R.id.fitness_level_radio_group);
        nextButton4 = view.findViewById(R.id.next4_button);

        nextButton4.setOnClickListener(new View.OnClickListener() {
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
                Bundle bundle = new Bundle();
                bundle.putString("fitness_level", fitnessLevel);
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
