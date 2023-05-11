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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class Question5Fragment extends Fragment {


    CheckBox chestCheckbox, backCheckbox, armsCheckbox, legsCheckbox, absCheckbox;
    private FloatingActionButton nextButton5;

    public Question5Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question5, container, false);

        chestCheckbox = view.findViewById(R.id.chest_checkbox);
        backCheckbox = view.findViewById(R.id.back_checkbox);
        armsCheckbox = view.findViewById(R.id.arms_checkbox);
        legsCheckbox = view.findViewById(R.id.legs_checkbox);
        absCheckbox = view.findViewById(R.id.abs_checkbox);
        nextButton5 = view.findViewById(R.id.next5_button);

        nextButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the checked state of each checkbox
                boolean chestChecked = chestCheckbox.isChecked();
                boolean backChecked = backCheckbox.isChecked();
                boolean armsChecked = armsCheckbox.isChecked();
                boolean legsChecked = legsCheckbox.isChecked();
                boolean absChecked = absCheckbox.isChecked();

                // Check if at least one checkbox is checked
                if (!chestChecked && !backChecked && !armsChecked && !legsChecked && !absChecked) {
                    // Display an error message
                    Toast.makeText(getActivity(), "Please select at least one focus zone", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a list to store the selected focus zones
                List<String> selectedFocusZones = new ArrayList<>();

                // Check each checkbox and add its label to the list if it's checked
                if (chestChecked) {
                    selectedFocusZones.add(chestCheckbox.getText().toString());
                }
                if (backChecked) {
                    selectedFocusZones.add(backCheckbox.getText().toString());
                }
                if (armsChecked) {
                    selectedFocusZones.add(armsCheckbox.getText().toString());
                }
                if (legsChecked) {
                    selectedFocusZones.add(legsCheckbox.getText().toString());
                }
                if (absChecked) {
                    selectedFocusZones.add(absCheckbox.getText().toString());
                }

                // Create a string from the list of selected focus zones
                String selectedFocusZonesString = TextUtils.join(", ", selectedFocusZones);

                SharedPreferences.Editor editor = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE).edit();
                editor.putString("FocusZones", selectedFocusZonesString);
                editor.apply();

                // Create a bundle and add the selected focus zones to it
                Bundle bundle = new Bundle();
                bundle.putString("selected_focus_zones", selectedFocusZonesString);

                // Create a new instance of the next fragment and set its arguments
                Question6Fragment question6Fragment = new Question6Fragment();
                question6Fragment.setArguments(bundle);

                // Replace the current fragment with the next one
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, question6Fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }
}