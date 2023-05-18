package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.progresviews.ProgressWheel;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class FastingDetails extends AppCompatActivity {
    ImageView back;
    CircularProgressBar circularProgressBar;
    TextView timerTextView;
    Button startStopButton;
    private int fastingPeriod = 13; // fasting period in hours
    private int eatingPeriod = 11; // eating period in hours

    private CountDownTimer countDownTimer;
    private long timeRemaining;
    private boolean isTimerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fasting_details);

        circularProgressBar = findViewById(R.id.circularProgressBar);
        timerTextView = findViewById(R.id.timer_text_view);
        startStopButton = findViewById(R.id.start_stop_button);

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FastingDetails.this, FastingActivity.class);
                startActivity(intent);
            }
        });

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
    }
    private void startTimer() {
        timeRemaining = fastingPeriod * 60 * 60 * 1000; // convert fastingPeriod to milliseconds
        countDownTimer = new CountDownTimer(timeRemaining, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                updateTimerText();
                updateProgressWheel();
            }

            @Override
            public void onFinish() {
                // Handle timer finished
                // ...
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
        circularProgressBar.setProgress(progress);

    }
}