package com.example.finalproject;

import static com.example.finalproject.LoginTabFragment.SHARED_PREFS_KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Edit_Profile extends AppCompatActivity {

    TextView personal_plan, food, challenges, user_profile;
    FloatingActionButton exe;
    CircleImageView profileImage;
    EditText userName;
    TextView genderText, birthDateText, dietTypeText, fitnessLevelText, focusZonesText, physicalLimitationsText, startingWeightText,
            targetWeightText, heightText;
    RadioGroup genderRadioGroup, dietTypeRadioGroup, fitnessLevelRadioGroup;
    LinearLayout genderPickerContainer, dietTypePickerContainer, fitnessLevelPickerContainer, focusZonesContainer, physicalLimitationsCheckboxContainer, startingWeightPickerContainer,
            targetWeightPickerContainer, heightPickerContainer;
    CheckBox chestCheckbox, backCheckbox, armsCheckbox, legsCheckbox, absCheckbox, noneCheckBox, kneePainCheckBox, backPainCheckBox, limitedMobilityCheckBox, otherCheckBox;
    NumberPicker startingWeightPicker, targetWeightPicker, heightPicker;
    Button genderOkButton, dietTypeOkButton, fitnessLevelOkButton, focusZonesOkButton, physicalLimitationsOkButton,
            startingWeightButton, targetWeightButton, heightButton, saveButton;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int MAX_WEIGHT_KG = 150;
    private static final int MAX_HEIGHT_CM = 250;
    private Uri selectedImageUri; // declare the URI variable outside of the method


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        personal_plan = findViewById(R.id.tab1_txt);
        food = findViewById(R.id.tab2_txt);
        challenges = findViewById(R.id.tab4_txt);
        user_profile = findViewById(R.id.tab5_txt);
        exe = findViewById(R.id.training_btn);

        profileImage = findViewById(R.id.edit_profile_image);
        userName = findViewById(R.id.edit_profile_name);
        genderText = findViewById(R.id.gender_text);
        genderRadioGroup = findViewById(R.id.gender_radio_group);
        genderOkButton = findViewById(R.id.gender_ok_button);
        genderPickerContainer = findViewById(R.id.gender_picker_container);
        dietTypeText = findViewById(R.id.diet_type_text);
        dietTypeRadioGroup = findViewById(R.id.diet_type_radio_group);
        dietTypeOkButton = findViewById(R.id.diet_type_ok_button);
        dietTypePickerContainer = findViewById(R.id.diet_type_picker_container);
        fitnessLevelText = findViewById(R.id.fitness_level_text);
        fitnessLevelRadioGroup = findViewById(R.id.fitness_level_radio_group);
        fitnessLevelOkButton = findViewById(R.id.fitness_level_ok_button);
        fitnessLevelPickerContainer = findViewById(R.id.fitness_level_picker_container);
        focusZonesText = findViewById(R.id.focus_zones_text);
        focusZonesOkButton = findViewById(R.id.focus_zones_ok_button);
        focusZonesContainer = findViewById(R.id.focus_zones_checkbox_container);
        chestCheckbox = findViewById(R.id.chest_checkbox);
        backCheckbox = findViewById(R.id.back_checkbox);
        armsCheckbox = findViewById(R.id.arms_checkbox);
        legsCheckbox = findViewById(R.id.legs_checkbox);
        absCheckbox = findViewById(R.id.abs_checkbox);
        birthDateText = findViewById(R.id.birth_of_date_text);
        physicalLimitationsText = findViewById(R.id.physical_limitations_text);
        physicalLimitationsOkButton = findViewById(R.id.physical_limitations_ok_button);
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
        saveButton = findViewById(R.id.save_button);

        personal_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Edit_Profile.this, MainActivity.class);
                startActivity(intent);
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Edit_Profile.this, Food.class);
                startActivity(intent);
            }
        });

        challenges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Edit_Profile.this, Challenges.class);
                startActivity(intent);
            }
        });

        exe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Edit_Profile.this, TrainingActivity.class);
                startActivity(intent);
            }
        });


// Set an OnClickListener to the CircleImageView
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });


        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Update the name EditText with the new value
                String name = userName.getText().toString().trim();
                if (!name.isEmpty()) {
                    // Store the value somewhere, such as in a SharedPreferences
                    SharedPreferences.Editor editor = getSharedPreferences("myPrefs", MODE_PRIVATE).edit();
                    editor.putString("name", name);
                    editor.apply();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });


        // Set click listener for the genderText
        genderText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGenderOptions(view);
            }
        });


        // Set click listener for the okButton
        genderOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGenderOkButtonClick(view);
            }
        });

        // Set click listener for the genderText
        dietTypeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDietTypeOptions(view);
            }
        });


        // Set click listener for the okButton
        dietTypeOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDietTypeOkButtonClick(view);
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

        // Set click listener for the fitnessLevelEditText
        focusZonesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFocusZonesOptions(view);
            }
        });


        // Set click listener for the okButton
        focusZonesOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFocusZonesOkButtonClick(view);
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
                showPhysicalLimitationsOptions(v);
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

        // Set click listener for physical limitations EditText
        physicalLimitationsOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPhysicalLimitationsOkButtonClick(v);
            }
        });


        // Set up the starting weight picker
        startingWeightPicker.setMinValue(0);
        startingWeightPicker.setMaxValue(MAX_WEIGHT_KG);
        startingWeightPicker.setValue(70);

        startingWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartingWeightOkButtonClick(v);
            }
        });

        // Set up the target weight picker
        targetWeightPicker.setMinValue(0);
        targetWeightPicker.setMaxValue(MAX_WEIGHT_KG);
        targetWeightPicker.setValue(50);

        targetWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTargetWeightOkButtonClick(v);
            }
        });


        // Height
        // Set up the target weight picker
        heightPicker.setMinValue(0);
        heightPicker.setMaxValue(MAX_HEIGHT_CM);
        heightPicker.setValue(160);

        heightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHeightOkButtonClick(v);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClick();
            }
        });
    }

    // Override onActivityResult to handle the result of the intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            // add these log statements to check the values of imageUri and selectedImageUri
            Log.d("DEBUG", "imageUri: " + imageUri);
            Log.d("DEBUG", "selectedImageUri before: " + selectedImageUri);

            selectedImageUri = imageUri;

            // add this log statement to check the value of selectedImageUri after it has been assigned
            Log.d("DEBUG", "selectedImageUri after: " + selectedImageUri);

            // load the image using Glide library and set it to the circleImageView
            if (selectedImageUri != null) {
                Glide.with(this)
                        .load(selectedImageUri)
                        .into(profileImage);
            }
        }
    }

    // Called when the genderText is clicked
    public void showGenderOptions(View view) {
        // Show the radio group and OK button
        genderRadioGroup.setVisibility(View.VISIBLE);
        genderOkButton.setVisibility(View.VISIBLE);

        // Show the container layout
        genderPickerContainer.setVisibility(View.VISIBLE);
    }

    public void onGenderOkButtonClick(View view) {
        genderPickerContainer.setVisibility(View.GONE);

        // Get the selected radio button text and display it in the edit text
        int selectedId = genderRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        String selectedText = selectedRadioButton.getText().toString();
        genderText.setText(selectedText);
    }

    // Called when the dietTypeText is clicked
    public void showDietTypeOptions(View view) {
        // Show the radio group and OK button
        dietTypeRadioGroup.setVisibility(View.VISIBLE);
        dietTypeOkButton.setVisibility(View.VISIBLE);

        // Show the container layout
        dietTypePickerContainer.setVisibility(View.VISIBLE);
    }

    public void onDietTypeOkButtonClick(View view) {
        dietTypePickerContainer.setVisibility(View.GONE);

        // Get the selected radio button text and display it in the edit text
        int selectedId = dietTypeRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        String selectedText = selectedRadioButton.getText().toString();
        dietTypeText.setText(selectedText);
    }

    // Called when the fitnessLevelText is clicked
    public void showFitnessLevelOptions(View view) {
        // Show the radio group and OK button
        fitnessLevelRadioGroup.setVisibility(View.VISIBLE);
        fitnessLevelOkButton.setVisibility(View.VISIBLE);

        // Show the container layout
        fitnessLevelPickerContainer.setVisibility(View.VISIBLE);
    }

    public void onFitnessLevelOkButtonClick(View view) {
        fitnessLevelPickerContainer.setVisibility(View.GONE);

        // Get the selected radio button text and display it in the edit text
        int selectedId = fitnessLevelRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        String selectedText = selectedRadioButton.getText().toString();
        fitnessLevelText.setText(selectedText);
    }

    // Called when the focusZonesText is clicked
    public void showFocusZonesOptions(View view) {
            focusZonesContainer.setVisibility(View.VISIBLE);
    }

    public void onFocusZonesOkButtonClick(View view) {
        StringBuilder selectedZonesBuilder = new StringBuilder();

        for (int i = 0; i < focusZonesContainer.getChildCount(); i++) {
            View childView = focusZonesContainer.getChildAt(i);

            if (childView instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) childView;

                if (checkBox.isChecked()) {
                    selectedZonesBuilder.append(checkBox.getText().toString()).append(", ");
                }
            }
        }

        String selectedZonesText = selectedZonesBuilder.toString().trim();

        if (selectedZonesText.isEmpty()) {
            selectedZonesText = "Select focus zones";
        } else {
            selectedZonesText = selectedZonesText.substring(0, selectedZonesText.length() - 1);
        }

        TextView focusZonesTextView = findViewById(R.id.focus_zones_text);
        focusZonesTextView.setText(selectedZonesText);

        focusZonesContainer.setVisibility(View.GONE);
    }

    public void showPhysicalLimitationsOptions(View view) {
            physicalLimitationsCheckboxContainer.setVisibility(View.VISIBLE);
    }

    private void onPhysicalLimitationsOkButtonClick(View view) {
        StringBuilder sb = new StringBuilder();
        if (noneCheckBox.isChecked()) {
            sb.append(noneCheckBox.getText()).append(", ");
        }
        if (kneePainCheckBox.isChecked()) {
            sb.append(kneePainCheckBox.getText()).append(", ");
        }
        if (backPainCheckBox.isChecked()) {
            sb.append(backPainCheckBox.getText()).append(", ");
        }
        if (limitedMobilityCheckBox.isChecked()) {
            sb.append(limitedMobilityCheckBox.getText()).append(", ");
        }
        if (otherCheckBox.isChecked()) {
            sb.append(otherCheckBox.getText()).append(", ");
        }
        // Remove the trailing comma and space if any
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }

        // Set the physical limitations text to the selected options
        String physicalLimitations = sb.toString();
        physicalLimitationsText.setText(physicalLimitations);

        // Hide the checkboxes container
        physicalLimitationsCheckboxContainer.setVisibility(View.GONE);
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
    private void onStartingWeightOkButtonClick(View view) {
        startingWeightPickerContainer.setVisibility(View.GONE);
        // Get the selected weight from the picker
        int startingWeight = startingWeightPicker.getValue();

        // Update the starting weight text
        startingWeightText.setText(String.valueOf(startingWeight));
    }

    public void showTargetWeightPicker(View view) {
        targetWeightPickerContainer.setVisibility(View.VISIBLE);
    }

    private void onTargetWeightOkButtonClick(View view) {
        targetWeightPickerContainer.setVisibility(View.GONE);
        // Get the selected weight from the picker
        int targetWeight = targetWeightPicker.getValue();

        // Update the starting weight text
        targetWeightText.setText(String.valueOf(targetWeight));
    }

    public void showHeightPicker(View view) {
        heightPickerContainer.setVisibility(View.VISIBLE);
    }

    private void onHeightOkButtonClick(View view) {
        // Get the selected height value from the picker
        int height = heightPicker.getValue();

        // Set the selected height value to the height EditText
        heightText.setText(String.valueOf(height));

        // Hide the height picker container
        heightPickerContainer.setVisibility(View.GONE);
    }


    private void onSaveButtonClick() {
        SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String name = userName.getText().toString();
        String date = birthDateText.getText().toString();
        String gender = genderText.getText().toString();
        String dietType = dietTypeText.getText().toString();
        String fitnessLevel = fitnessLevelText.getText().toString();
        String focusZones = focusZonesText.getText().toString();
        String physicalLimitations = physicalLimitationsText.getText().toString();
        String startingWeight = startingWeightText.getText().toString();
        String targetWeight = targetWeightText.getText().toString();
        String height = heightText.getText().toString();
        String imageUri = prefs.getString("image", null);
            if (imageUri != null) {
                Glide.with(this).load(Uri.parse(imageUri)).into(profileImage);
            }

        editor.putString("name", name);
        editor.putString("date", date);
        editor.putString("gender", gender);
        editor.putString("dietType", dietType);
        editor.putString("fitnessLevel", fitnessLevel);
        editor.putString("FocusZones", focusZones);
        editor.putString("physicalLimitations", physicalLimitations);
        editor.putString("starting_weight", startingWeight);
        editor.putString("target_weight", targetWeight);
        editor.putString("height", height);


        // add the selected image URI as a string to the shared preferences
        if (selectedImageUri != null) {
            editor.putString("image", selectedImageUri.toString());
        }

        editor.apply();

        Intent intent = new Intent();
        intent.putExtra("name", name);
        intent.putExtra("date", date);
        intent.putExtra("gender", gender);
        intent.putExtra("dietType", dietType);
        intent.putExtra("fitnessLevel", fitnessLevel);
        intent.putExtra("focusZones", focusZones);
        intent.putExtra("physicalLimitations", physicalLimitations);
        intent.putExtra("startingWeight", startingWeight);
        intent.putExtra("targetWeight", targetWeight);
        intent.putExtra("height", height);

        if (selectedImageUri != null) {
            profileImage.setImageURI(selectedImageUri);
        } else {
            profileImage.setImageResource(R.drawable.ic_launcher_foreground);
        }

        // Create the JSON request body
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("name", name);
            requestBody.put("date", date);
            requestBody.put("gender", gender);
            requestBody.put("dietType", dietType);
            requestBody.put("fitnessLevel", fitnessLevel);
            requestBody.put("focusZones", focusZones);
            requestBody.put("physicalLimitations", physicalLimitations);
            requestBody.put("startingWeight", startingWeight);
            requestBody.put("targetWeight", targetWeight);
            requestBody.put("height", height);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Create an instance of HttpPostTask and execute it
        SharedPreferences sharedPreferences = Edit_Profile.this.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        String url = "http://10.0.2.2:8181/questionnaire?email" + email;
        HttpPostTask task = new HttpPostTask(url, requestBody.toString(), new HttpPostTask.OnHttpPostTaskCompleted() {
            @Override
            public void onHttpPostTaskCompleted(String response) {
                // Process the response as needed
                // ...
                System.out.println("Response: " + response);
            }
        });
        task.execute();

        setResult(HttpURLConnection.HTTP_OK, intent);
        finish();
    }

    public static class HttpPostTask extends AsyncTask<String, Void, String> {
        private final String url;
        private final String requestBody;
        private final OnHttpPostTaskCompleted listener;

        public HttpPostTask(String url, String requestBody, OnHttpPostTaskCompleted listener) {
            this.url = url;
            this.requestBody = requestBody;
            this.listener = listener;
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                // Create the connection
                URL url = new URL(this.url);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);

                // Set the request body
                OutputStream outputStream = urlConnection.getOutputStream();
                outputStream.write(requestBody.getBytes());
                outputStream.flush();
                outputStream.close();

                // Read the response
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    StringBuilder response = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    return response.toString();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (listener != null) {
                listener.onHttpPostTaskCompleted(response);
            }
        }

        public interface OnHttpPostTaskCompleted {
            void onHttpPostTaskCompleted(String response);
        }

    }


}