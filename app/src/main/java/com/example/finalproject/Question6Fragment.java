package com.example.finalproject;

import static android.content.Context.MODE_PRIVATE;
import static com.example.finalproject.LoginTabFragment.SHARED_PREFS_KEY;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;

public class Question6Fragment extends Fragment {
    CheckBox chestCheckbox, backCheckbox, armsCheckbox, legsCheckbox, absCheckbox;
    private FloatingActionButton nextButton6;
    private Context context;
    ArrayList<String> selectedFocusZones;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public Question6Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_question6, container, false);

        chestCheckbox = view.findViewById(R.id.chest_checkbox);
        backCheckbox = view.findViewById(R.id.back_checkbox);
        armsCheckbox = view.findViewById(R.id.arms_checkbox);
        legsCheckbox = view.findViewById(R.id.legs_checkbox);
        absCheckbox = view.findViewById(R.id.abs_checkbox);
        nextButton6 = view.findViewById(R.id.next6_button);

        nextButton6.setOnClickListener(new View.OnClickListener() {
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
                selectedFocusZones = new ArrayList<>();

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

                // Save the selected focus zones to shared preferences
                String focusZonesString = TextUtils.join(",", selectedFocusZones);
                editor.putString("focusZones", focusZonesString).apply();

                JsonObject requestBody = new JsonObject();
                JsonArray focusZonesArray = new JsonArray();

                for (String focusZones : selectedFocusZones) {
                    focusZonesArray.add(focusZones);
                }

                 Question7Fragment question7Fragment = new Question7Fragment();
                 FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                 transaction.replace(R.id.fragment_container, question7Fragment);
                 transaction.addToBackStack(null);
                 transaction.commit();
            }
        });
        return view;
    }

    public String getFocusZones() {
        // Retrieve the selected focus zones from shared preferences
        return sharedPreferences.getString("focusZones", "");
    }
}
