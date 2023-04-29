package com.example.finalproject;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Locale;


public class Question2Fragment extends Fragment {

    private TextView dateText;
    FloatingActionButton nextButton2;

    public Question2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question2, container, false);

        dateText = view.findViewById(R.id.birthdate_text);
        nextButton2 = view.findViewById(R.id.next2_button);

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        nextButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String date = dateText.getText().toString();
                if (date.equals("dd/MM/yyyy")) {
                    Toast.makeText(getContext(), "Please select a date", Toast.LENGTH_SHORT).show();
                    return;
                }
                    Bundle bundle = new Bundle();
                    bundle.putString("date", date);
                    Question3Fragment question3Fragment = new Question3Fragment();
                    question3Fragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, question3Fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
        });

        return view;
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Set the selected date to the EditText view
                        dateText.setText(String.format(Locale.US, "%02d/%02d/%d", dayOfMonth, month + 1, year));
                    }
                }, year, month, day);
        datePickerDialog.show();
    }


}