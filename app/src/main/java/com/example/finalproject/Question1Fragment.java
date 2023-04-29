package com.example.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Question1Fragment extends Fragment {

    private EditText nameEditText;
    FloatingActionButton nextButton;

    public Question1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question1, container, false);

        nameEditText = view.findViewById(R.id.user_name_editText);
        nextButton = view.findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter your name", Toast.LENGTH_SHORT).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", name);
                    Question2Fragment question2Fragment = new Question2Fragment();
                    question2Fragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, question2Fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        return view;
    }
}
