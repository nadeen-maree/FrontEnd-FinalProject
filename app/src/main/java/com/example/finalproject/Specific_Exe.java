package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Specific_Exe extends AppCompatActivity {
    ImageView back;
    Button go_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_exe);

        back = findViewById(R.id.back);
        go_btn = findViewById(R.id.go_btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Specific_Exe.this, TrainingActivity.class);
                startActivity(intent);
            }
        });

        go_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Specific_Exe.this, Exercise_Started.class);
                startActivity(intent);
            }
        });
    }
}