package com.example.finalproject;

import static android.content.Context.MODE_PRIVATE;
import static com.example.finalproject.LoginTabFragment.SHARED_PREFS_KEY;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Question8Fragment extends Fragment {
    private NumberPicker startingWeightPicker, targetWeightPicker, heightPicker;
    private FloatingActionButton finishButton;
    private SharedPreferences sharedPreferences;
    private static final int MAX_WEIGHT_KG = 150;
    private static final int MAX_HEIGHT_CM = 250;

    public Question8Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question8, container, false);
        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);

        startingWeightPicker = view.findViewById(R.id.starting_weight_picker);
        targetWeightPicker = view.findViewById(R.id.target_weight_picker);
        heightPicker = view.findViewById(R.id.height_picker);
        finishButton = view.findViewById(R.id.finish_button);

        // Set up the starting weight picker
        startingWeightPicker.setMinValue(0);
        startingWeightPicker.setMaxValue(MAX_WEIGHT_KG);
        startingWeightPicker.setValue(70);

        // Set up the target weight picker
        targetWeightPicker.setMinValue(0);
        targetWeightPicker.setMaxValue(MAX_WEIGHT_KG);
        targetWeightPicker.setValue(50);

        // Set up the target weight picker
        heightPicker.setMinValue(0);
        heightPicker.setMaxValue(MAX_HEIGHT_CM);
        heightPicker.setValue(160);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int startingWeight = startingWeightPicker.getValue();
                int targetWeight = targetWeightPicker.getValue();
                int height = heightPicker.getValue();

                // Check if any of the number pickers haven't been used
                if (startingWeight == startingWeightPicker.getMinValue() &&
                        targetWeight == targetWeightPicker.getMinValue() &&
                        height == heightPicker.getMinValue()) {
                    Toast.makeText(getActivity(), "Please select your height, weight, and target weight", Toast.LENGTH_SHORT).show();
                    return;
                }

                String startingWeightString = Integer.toString(startingWeight);
                String targetWeightString = Integer.toString(targetWeight);
                String heightString = Integer.toString(height);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("startingWeight", startingWeightString);
                editor.putString("targetWeight", targetWeightString);
                editor.putString("height", heightString);
                editor.apply();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
