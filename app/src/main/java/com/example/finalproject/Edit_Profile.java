package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Edit_Profile extends AppCompatActivity {

    CircleImageView profileImage;
    TextView fitnessLevelText, focusZonesText, birthDateText, physicalLimitationsText, startingWeightText,
            targetWeightText, heightText;
    RadioGroup fitnessLevelRadioGroup;
    LinearLayout fitnessLevelPickerContainer, focusZonesContainer, physicalLimitationsCheckboxContainer, startingWeightPickerContainer,
            targetWeightPickerContainer, heightPickerContainer;
    CheckBox noneCheckBox, kneePainCheckBox, backPainCheckBox, limitedMobilityCheckBox, otherCheckBox;
    NumberPicker startingWeightPicker, targetWeightPicker, heightPicker;
    Button fitnessLevelOkButton, startingWeightButton, targetWeightButton, heightButton;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int MAX_WEIGHT_KG = 150;
    private static final int MAX_HEIGHT_CM = 250;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        profileImage = findViewById(R.id.profile_image);
        fitnessLevelText = findViewById(R.id.fitness_level_text);
        fitnessLevelRadioGroup = findViewById(R.id.fitness_level_radio_group);
        fitnessLevelOkButton = findViewById(R.id.fitness_level_ok_button);
        fitnessLevelPickerContainer = findViewById(R.id.fitness_level_picker_container);
        focusZonesText = findViewById(R.id.focus_zones_text);
        focusZonesContainer = findViewById(R.id.focus_zones_checkbox_container);
        birthDateText = findViewById(R.id.birth_of_date_text);
        physicalLimitationsText = findViewById(R.id.physical_limitations_text);
        physicalLimitationsCheckboxContainer = findViewById(R.id.physical_limitations_checkbox_container);
        noneCheckBox = findViewById(R.id.none_checkbox);
        kneePainCheckBox = findViewById(R.id.knee_pain_checkbox);
        backPainCheckBox = findViewById(R.id.back_pain_checkbox);
        limitedMobilityCheckBox = findViewById(R.id.limited_mobility_checkbox);
        otherCheckBox = findViewById(R.id.other_checkbox);
        startingWeightPickerContainer = findViewById(R.id.starting_weight_picker_container);
        targetWeightPickerContainer = findViewById(R.id.target_weight_picker_container);
        startingWeightText = findViewById(R.id.starting_weight_text);
        targetWeightText = findViewById(R.id.target_weight_text);
        startingWeightPicker = findViewById(R.id.starting_weight_picker);
        targetWeightPicker = findViewById(R.id.target_weight_picker);
        startingWeightButton = findViewById(R.id.starting_weight_button);
        targetWeightButton = findViewById(R.id.target_weight_button);
        heightText = findViewById(R.id.height_text);
        heightPickerContainer = findViewById(R.id.height_picker_container);
        heightPicker = findViewById(R.id.height_picker);
        heightButton = findViewById(R.id.height_button);


// Set an OnClickListener to the CircleImageView
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to pick an image from the gallery
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                // Start the intent with a request code
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });



                // Set click listener for the fitnessLevelEditText
                fitnessLevelText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showFitnessLevelOptions(view);
                    }
                });


                // Set click listener for the okButton
                fitnessLevelOkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onFitnessLevelOkButtonClick(view);
                    }
                });

        // add checkboxes dynamically
        String[] focusZones = getResources().getStringArray(R.array.focus_zones_array);
        for (int i = 0; i < focusZones.length; i++) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(focusZones[i]);
            focusZonesContainer.addView(checkBox);
        }

        // hide the checkbox container initially
        focusZonesContainer.setVisibility(View.GONE);

        // set click listener for the edittext
        focusZonesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // toggle visibility of the checkbox container
                if (focusZonesContainer.getVisibility() == View.GONE) {
                    focusZonesContainer.setVisibility(View.VISIBLE);
                } else {
                    focusZonesContainer.setVisibility(View.GONE);
                }
            }
        });


        birthDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        // Set click listener for physical limitations EditText
        physicalLimitationsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (physicalLimitationsCheckboxContainer.getVisibility() == View.GONE) {
                    physicalLimitationsCheckboxContainer.setVisibility(View.VISIBLE);
                } else {
                    physicalLimitationsCheckboxContainer.setVisibility(View.GONE);
                }
            }
        });

        // Set click listeners for physical limitations checkboxes
        noneCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    kneePainCheckBox.setChecked(false);
                    backPainCheckBox.setChecked(false);
                    limitedMobilityCheckBox.setChecked(false);
                    otherCheckBox.setChecked(false);
                }
            }
        });

        kneePainCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    noneCheckBox.setChecked(false);
                }
            }
        });

        backPainCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    noneCheckBox.setChecked(false);
                }
            }
        });

        limitedMobilityCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    noneCheckBox.setChecked(false);
                }
            }
        });

        otherCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    noneCheckBox.setChecked(false);
                }
            }
        });

        // Set up the starting weight picker
        startingWeightPicker.setMinValue(0);
        startingWeightPicker.setMaxValue(MAX_WEIGHT_KG);
        startingWeightPicker.setValue(70);

        startingWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startingWeightPickerContainer.setVisibility(View.GONE);
            }
        });

        // Set up the target weight picker
        targetWeightPicker.setMinValue(0);
        targetWeightPicker.setMaxValue(MAX_WEIGHT_KG);
        targetWeightPicker.setValue(50);

        targetWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetWeightPickerContainer.setVisibility(View.GONE);
            }
        });


        // Height
        // Set up the target weight picker
        heightPicker.setMinValue(0);
        heightPicker.setMaxValue(MAX_HEIGHT_CM);
        heightPicker.setValue(160);
        heightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the height picker container
                heightPickerContainer.setVisibility(View.VISIBLE);
            }
        });
        heightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected height value from the picker
                int height = heightPicker.getValue();

                // Set the selected height value to the height EditText
                heightText.setText(String.valueOf(height));

                // Hide the height picker container
                heightPickerContainer.setVisibility(View.GONE);
            }
        });
    }

    // Override onActivityResult to handle the result of the intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the selected image URI and set it to the CircleImageView
            Uri imageUri = data.getData();
            profileImage.setImageURI(imageUri);
        }
    }

    // Called when the fitnessLevelEditText is clicked
    public void showFitnessLevelOptions(View view) {
        // Show the radio group and OK button
        fitnessLevelRadioGroup.setVisibility(View.VISIBLE);
        fitnessLevelOkButton.setVisibility(View.VISIBLE);

        // Show the container layout
        fitnessLevelPickerContainer.setVisibility(View.VISIBLE);
    }



//    public void onFitnessLevelEditTextClick(View view) {
//        // Show the radio group and OK button
//        showFitnessLevelOptions(view);
//    }

    public void onFitnessLevelOkButtonClick(View view) {
        // Hide the radio group and OK button
//        fitnessLevelRadioGroup.setVisibility(View.GONE);
//        fitnessLevelOkButton.setVisibility(View.GONE);
        fitnessLevelPickerContainer.setVisibility(View.GONE);

        // Get the selected radio button text and display it in the edit text
        int selectedId = fitnessLevelRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        String selectedText = selectedRadioButton.getText().toString();
        fitnessLevelText.setText(selectedText);
    }



    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Set the selected date to the EditText view
                        birthDateText.setText(String.format(Locale.US, "%02d/%02d/%d", dayOfMonth, month + 1, year));
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    public void showStartingWeightPicker(View view) {
        startingWeightPickerContainer.setVisibility(View.VISIBLE);
    }

    public void showTargetWeightPicker(View view) {
        targetWeightPickerContainer.setVisibility(View.VISIBLE);
    }


}