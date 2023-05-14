package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.progresviews.ProgressWheel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResult;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.TimeUnit;
import com.google.android.gms.common.Scopes;


public class Walk_Tracker extends AppCompatActivity  implements  GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    TextView personal_plan, food, challenges, user_profile;
    FloatingActionButton exe;

    private GoogleApiClient mGoogleApiClient;

    TextView targetText;
    Button googleFitButton;
    ProgressWheel progressWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_tracker);

        personal_plan = findViewById(R.id.tab1_txt);
        food = findViewById(R.id.tab2_txt);
        challenges = findViewById(R.id.tab4_txt);
        user_profile = findViewById(R.id.tab5_txt);
        exe = findViewById(R.id.training_btn);

        targetText = findViewById(R.id.targetText);
        googleFitButton = findViewById(R.id.googleFitButton);
        progressWheel = findViewById(R.id.wheelProgress);

        int targetSteps = Integer.parseInt(targetText.getText().toString().replaceAll("[^\\d]", ""));
        int maxLength = String.valueOf(targetSteps).length(); // get length of targetCalories as max length


//
//        // Set up the Google API client
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addApi(Fitness.HISTORY_API)
//                .addApi(Fitness.SENSORS_API)
//                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .build();

        // Set up the OnClickListener for the Google Fit button
        googleFitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleApiClient.connect();
            }
        });

        personal_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Walk_Tracker.this, MainActivity.class);
                startActivity(intent);
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Walk_Tracker.this, Food.class);
                startActivity(intent);
            }
        });

        challenges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Walk_Tracker.this, Challenges.class);
                startActivity(intent);
            }
        });

        exe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Walk_Tracker.this, TrainingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onConnected(Bundle bundle) {
        // Retrieve the user's step count data for the past 7 days
        DataReadRequest readRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                .bucketByTime(7, TimeUnit.DAYS)
                .build();

        PendingResult<DataReadResult> result = Fitness.HistoryApi.readData(mGoogleApiClient, readRequest);

        result.setResultCallback(new ResultCallback<DataReadResult>() {
            @Override
            public void onResult(DataReadResult dataReadResult) {
                if (dataReadResult.getStatus().isSuccess()) {
                    // Process the step count data
                    int totalSteps = 0;
                    DataSet dataSet = dataReadResult.getDataSet(DataType.AGGREGATE_STEP_COUNT_DELTA);

                    for (DataPoint dataPoint : dataSet.getDataPoints()) {
                        for (Field field : dataPoint.getDataType().getFields()) {
                            int steps = dataPoint.getValue(field).asInt();
                            totalSteps += steps;
                        }
                    }

                    // Update the ProgressWheel with the total steps
                    progressWheel.setStepCountText(Integer.toString(totalSteps));
                    progressWheel.setPercentage(percentage(String.valueOf(totalSteps)));
                    progressWheel.setStepCountText(String.valueOf(totalSteps));

                } else {
                    // Handle the error
                }
            }
        });


    }

    public int percentage(String calorie) {
        int calorieNumber = Integer.parseInt(calorie);
        String target = targetText.getText().toString();
        Double targetSteps = (double) Integer.parseInt(target.replaceAll("[^\\d]", ""));
        Double percentage = ((calorieNumber / targetSteps) * 360);
        int intPercentage = percentage.intValue();

        return intPercentage;
    }

    @Override
    public void onConnectionSuspended(int i) {
        // Handle the suspension
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Handle the connection failure
    }

}