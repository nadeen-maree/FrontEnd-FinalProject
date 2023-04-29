package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SignupTabFragment extends Fragment {

    TextView email;
    TextView mobileNum;
    TextView pass;
    TextView confirmPass;
    Button signup;
    float v = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        email = root.findViewById(R.id.signup_email);
        mobileNum = root.findViewById(R.id.mobile_num);
        pass = root.findViewById(R.id.signup_pass);
        confirmPass = root.findViewById(R.id.conf_pass);
        signup = root.findViewById(R.id.sigmup_button);

        email.setTranslationX(800);
        mobileNum.setTranslationX(800);
        pass.setTranslationX(800);
        confirmPass.setTranslationX(800);
        signup.setTranslationX(800);

        email.setAlpha(v);
        mobileNum.setAlpha(v);
        pass.setAlpha(v);
        confirmPass.setAlpha(v);
        signup.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        mobileNum.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        confirmPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        signup.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QuestionnaireActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}

