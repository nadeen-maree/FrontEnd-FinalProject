package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.progresviews.ProgressWheel;
import java.util.Locale;

public class FastingDetails extends AppCompatActivity {
    ImageView back;
//    ProgressWheel progressWheel;
//    TextView timerTextView;
//    Button startStopButton;
//    private int fastingPeriod = 13; // fasting period in hours
//    private int eatingPeriod = 11; // eating period in hours
//    private boolean timerRunning = false;
//    private boolean progressRunning = false;
//    private int progress = 0;
//    private int maxProgress = 100;
//    private long startTime;
//    private AsyncTask<Void, Long, Void> timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fasting_details);
//
//        progressWheel = findViewById(R.id.wheelProgress);
//        timerTextView = findViewById(R.id.timer_text_view);
//        startStopButton = findViewById(R.id.start_stop_button);

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FastingDetails.this, FastingActivity.class);
                startActivity(intent);
            }
        });
    }
}