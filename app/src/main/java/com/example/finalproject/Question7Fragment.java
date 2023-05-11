package com.example.finalproject;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class Question7Fragment extends Fragment {

    CheckBox noneCheckbox, kneePainCheckbox, backPainCheckbox, limitedMobilityCheckbox, OtherCheckbox;
    private FloatingActionButton nextButton7;

    public Question7Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question7, container, false);

        noneCheckbox = view.findViewById(R.id.none_checkbox);
        kneePainCheckbox = view.findViewById(R.id.knee_pain_checkbox);
        backPainCheckbox = view.findViewById(R.id.back_pain_checkbox);
        limitedMobilityCheckbox = view.findViewById(R.id.limited_mobility_checkbox);
        OtherCheckbox = view.findViewById(R.id.other_checkbox);
        nextButton7 = view.findViewById(R.id.next7_button);

        // Add OnCheckedChangeListener to each CheckBox
        noneCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // If "noneCheckbox" is checked, uncheck all other checkboxes
                if (isChecked) {
                    kneePainCheckbox.setChecked(false);
                    backPainCheckbox.setChecked(false);
                    limitedMobilityCheckbox.setChecked(false);
                    OtherCheckbox.setChecked(false);
                }
            }
        });

        kneePainCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // If any of the other checkboxes are checked, uncheck "noneCheckbox"
                if (isChecked) {
                    noneCheckbox.setChecked(false);
                }
            }
        });

        backPainCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // If any of the other checkboxes are checked, uncheck "noneCheckbox"
                if (isChecked) {
                    noneCheckbox.setChecked(false);
                }
            }
        });

        limitedMobilityCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // If any of the other checkboxes are checked, uncheck "noneCheckbox"
                if (isChecked) {
                    noneCheckbox.setChecked(false);
                }
            }
        });

        OtherCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // If any of the other checkboxes are checked, uncheck "noneCheckbox"
                if (isChecked) {
                    noneCheckbox.setChecked(false);
                }
            }
        });

        nextButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the checked state of each checkbox
                boolean noneChecked = noneCheckbox.isChecked();
                boolean kneePainChecked = kneePainCheckbox.isChecked();
                boolean backPainChecked = backPainCheckbox.isChecked();
                boolean limitedMobilityChecked = limitedMobilityCheckbox.isChecked();
                boolean OtherChecked = OtherCheckbox.isChecked();

                // Check if at least one checkbox is checked
                if (!noneChecked && !kneePainChecked && !backPainChecked && !limitedMobilityChecked && !OtherChecked) {
                    // Display an error message
                    Toast.makeText(getActivity(), "Please select at least one physical limitations or none option", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a list to store the selected focus zones
                List<String> selectedPhysicalLimitations = new ArrayList<>();

                // Check each checkbox and add its label to the list if it's checked
                if (noneChecked) {
                    selectedPhysicalLimitations.add(noneCheckbox.getText().toString());
                }
                if (kneePainChecked) {
                    selectedPhysicalLimitations.add(kneePainCheckbox.getText().toString());
                }
                if (backPainChecked) {
                    selectedPhysicalLimitations.add(backPainCheckbox.getText().toString());
                }
                if (limitedMobilityChecked) {
                    selectedPhysicalLimitations.add(limitedMobilityCheckbox.getText().toString());
                }
                if (OtherChecked) {
                    selectedPhysicalLimitations.add(OtherCheckbox.getText().toString());
                }

                // Check if the "none" checkbox is checked and uncheck all other checkboxes if it is
                if (noneChecked) {
                    kneePainCheckbox.setChecked(false);
                    backPainCheckbox.setChecked(false);
                    limitedMobilityCheckbox.setChecked(false);
                    OtherCheckbox.setChecked(false);
                } else {
                    // Check if any other checkbox is checked and uncheck the "none" checkbox if it is
                    if (kneePainChecked || backPainChecked || limitedMobilityChecked || OtherChecked) {
                        noneCheckbox.setChecked(false);
                    }
                }

                // Create a string from the list of selected physical limitations
                String selectedPhysicalLimitationsString = TextUtils.join(", ", selectedPhysicalLimitations);

                SharedPreferences.Editor editor = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE).edit();
                editor.putString("physicalLimitations", selectedPhysicalLimitationsString);
                editor.apply();

                // Create a bundle and add the selected physical limitations to it
                Bundle bundle = new Bundle();
                bundle.putString("selected_physical_limitations", selectedPhysicalLimitationsString);

                // Create a new instance of the next fragment and set its arguments
                Question8Fragment question8Fragment = new Question8Fragment();
                question8Fragment.setArguments(bundle);

                // Replace the current fragment with the next one
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, question8Fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }
}