package com.christine.dreamhouseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class signup extends AppCompatActivity {
    @BindView(R.id.nameEditText) EditText mName;
    @BindView(R.id.emailEditText)EditText mEmail;
    @BindView(R.id.phoneNoEditText)EditText mPhoneNo;
    @BindView(R.id.passwordEditText)EditText mPassword;
    @BindView(R.id.signUpButton) Button mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(signup.this,MainActivity.class);
                String name = mName.getText().toString();
                String email = mEmail.getText().toString();
                String phone = mPhoneNo.getText().toString();
                showToast(name);
                showToast(email);
                showToast(phone);
                startActivity(i);

            }
        });
    }
    private void showToast(String text){
        Toast.makeText(signup.this,text,Toast.LENGTH_LONG).show();


    }
}
