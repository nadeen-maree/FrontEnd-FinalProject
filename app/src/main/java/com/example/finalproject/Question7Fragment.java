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
import android.widget.CompoundButton;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;

public class Question7Fragment extends Fragment {
    CheckBox noneCheckbox, kneePainCheckbox, backPainCheckbox, limitedMobilityCheckbox, otherCheckbox;
    private FloatingActionButton nextButton7;
    private Context context;
    ArrayList<String> selectedPhysicalLimitations;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public Question7Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_question7, container, false);

        noneCheckbox = view.findViewById(R.id.none_checkbox);
        kneePainCheckbox = view.findViewById(R.id.knee_pain_checkbox);
        backPainCheckbox = view.findViewById(R.id.back_pain_checkbox);
        limitedMobilityCheckbox = view.findViewById(R.id.limited_mobility_checkbox);
        otherCheckbox = view.findViewById(R.id.other_checkbox);
        nextButton7 = view.findViewById(R.id.next7_button);

        noneCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    kneePainCheckbox.setChecked(false);
                    backPainCheckbox.setChecked(false);
                    limitedMobilityCheckbox.setChecked(false);
                    otherCheckbox.setChecked(false);
                }
            }
        });

        nextButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean noneChecked = noneCheckbox.isChecked();
                boolean kneePainChecked = kneePainCheckbox.isChecked();
                boolean backPainChecked = backPainCheckbox.isChecked();
                boolean limitedMobilityChecked = limitedMobilityCheckbox.isChecked();
                boolean otherChecked = otherCheckbox.isChecked();

                if (!noneChecked && !kneePainChecked && !backPainChecked && !limitedMobilityChecked && !otherChecked) {
                    Toast.makeText(getActivity(), "Please select at least one physical limitation or none option", Toast.LENGTH_SHORT).show();
                    return;
                }

                selectedPhysicalLimitations = new ArrayList<>();

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
                if (otherChecked) {
                    selectedPhysicalLimitations.add(otherCheckbox.getText().toString());
                }

                if (noneChecked) {
                    kneePainCheckbox.setChecked(false);
                    backPainCheckbox.setChecked(false);
                    limitedMobilityCheckbox.setChecked(false);
                    otherCheckbox.setChecked(false);
                } else {
                    if (kneePainChecked || backPainChecked || limitedMobilityChecked || otherChecked) {
                        noneCheckbox.setChecked(false);
                    }
                }

                String physicalLimitationsString = TextUtils.join(",", selectedPhysicalLimitations);
                editor.putString("physicalLimitations", physicalLimitationsString).apply();

                JsonObject requestBody = new JsonObject();
                JsonArray physicalLimitationsArray = new JsonArray();

                for (String physicalLimitations : selectedPhysicalLimitations) {
                    physicalLimitationsArray.add(physicalLimitations);
                }

                 Question8Fragment question8Fragment = new Question8Fragment();
                 FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                 transaction.replace(R.id.fragment_container, question8Fragment);
                 transaction.addToBackStack(null);
                 transaction.commit();
            }
        });
        return view;
    }

    public String getPhysicalLimitations() {
        return sharedPreferences.getString("physicalLimitations", "");
    }
}
