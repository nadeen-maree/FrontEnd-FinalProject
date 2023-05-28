package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class FastingDetails extends AppCompatActivity {
    ImageView back;
    CircularProgressBar circularProgressBar;
    TextView percentageTextView;
    TextView timerTextView, fastingHours;
    Button startStopButton;

    private CountDownTimer countDownTimer;
    private long timeRemaining;
    private boolean isTimerRunning;

    int fastingPeriod;

    private static final String RERUN_KEY = "rerun";

    private static final String TIMER_RUNNING_KEY = "timerRunning";
    private static final String TIME_REMAINING_KEY = "timeRemaining";
    private static final String FASTING_PERIOD_KEY = "fastingPeriod";

    private SharedPreferences sharedPreferences;

    private int constraintLayoutId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fasting_details);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Check if the app is run for the first time in the current session
        boolean isRerun = isRerun();

        // Clear saved data if the app is rerun
        if (isRerun) {
            clearSavedData();
        }

        // Set the first run flag to false to indicate subsequent runs
        setRerunFlag(false);

        circularProgressBar = findViewById(R.id.circularProgressBar);
        percentageTextView = findViewById(R.id.percentageTextView);
        timerTextView = findViewById(R.id.timer_text_view);
        startStopButton = findViewById(R.id.start_stop_button);
        fastingHours = findViewById(R.id.fasting_hours);


        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FastingDetails.this, FastingActivity.class);
                startActivity(intent);
            }
        });

        int fastingPeriod = getIntent().getIntExtra("fastingPeriod", 0);
        constraintLayoutId = getIntent().getIntExtra("constraintLayoutId", 0);

        // Assign the fastingPeriod based on the constraintLayoutId
        if (constraintLayoutId == R.id.fasting_13_hours_constraint_layout) {
            // Fasting period for constraintLayout1
            this.fastingPeriod = 13;
            fastingHours.setText("13 ");
        } else if (constraintLayoutId == R.id.fasting_16_hours_constraint_layout) {
            // Fasting period for constraintLayout2
            this.fastingPeriod = 16;
            fastingHours.setText("16 ");
        } else if(constraintLayoutId == R.id.fasting_18_hours_constraint_layout){
            this.fastingPeriod = 18;
            fastingHours.setText("18 ");
        } else if(constraintLayoutId == R.id.fasting_20_hours_constraint_layout){
            this.fastingPeriod = 20;
            fastingHours.setText("20 ");
        }

        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTimerRunning) {
                    stopTimer();
                } else {
                    startTimer();
                }
            }
        });



        // Restore the state if available
        if (savedInstanceState != null) {
            isTimerRunning = savedInstanceState.getBoolean(TIMER_RUNNING_KEY);
            timeRemaining = savedInstanceState.getLong(TIME_REMAINING_KEY);
        } else {
            isTimerRunning = sharedPreferences.getBoolean(TIMER_RUNNING_KEY + constraintLayoutId, false);
            timeRemaining = sharedPreferences.getLong(TIME_REMAINING_KEY + constraintLayoutId, 0);
        }

        if (isTimerRunning) {
            startTimer();
        } else {
            updateTimerText();
            updateProgressWheel();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(TIMER_RUNNING_KEY, isTimerRunning);
        outState.putLong(TIME_REMAINING_KEY, timeRemaining);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Save the timer state if the app is still running
        if (!isFinishing()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(TIMER_RUNNING_KEY + constraintLayoutId, isTimerRunning);
            editor.putLong(TIME_REMAINING_KEY + constraintLayoutId, timeRemaining);
            editor.apply();
        } else {
            // Clear saved data if the app is finished (closed)
            clearSavedData();
        }
    }

    private boolean isRerun() {
        SharedPreferences preferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        return preferences.getBoolean(RERUN_KEY, true);
    }

    private void setRerunFlag(boolean isRerun) {
        SharedPreferences preferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(RERUN_KEY, isRerun);
        editor.apply();
    }

    private void clearSavedData() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        setRerunFlag(true);
    }


    private void startTimer() {
        if (timeRemaining == 0) {
            timeRemaining = fastingPeriod * 60 * 60 * 1000; // convert fastingPeriod to milliseconds
        }
        countDownTimer = new CountDownTimer(timeRemaining, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                updateTimerText();
                updateProgressWheel();
            }

            @Override
            public void onFinish() {
                Toast.makeText(FastingDetails.this, "Timer Finished!", Toast.LENGTH_SHORT).show();

                // Reset the timer state
                isTimerRunning = false;
                timeRemaining = 0;

                // Update the UI
                updateTimerText();
                updateProgressWheel();
            }
        }.start();

        startStopButton.setText("Stop");
        isTimerRunning = true;
    }

    private void stopTimer() {
        countDownTimer.cancel();
        startStopButton.setText("Start");
        isTimerRunning = false;
    }

    private void updateTimerText() {
        int hours = (int) (timeRemaining / (1000 * 60 * 60));
        int minutes = (int) ((timeRemaining % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) ((timeRemaining % (1000 * 60)) / 1000);

        String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timerTextView.setText(time);
    }

    private void updateProgressWheel() {
        int progress = (int) (((fastingPeriod * 60 * 60 * 1000) - timeRemaining) / (float) (fastingPeriod * 60 * 60 * 1000) * 100);
        circularProgressBar.setProgress(progress + 1);
        String percentage = progress+1 + "%";
        percentageTextView.setText(percentage);

    }

}