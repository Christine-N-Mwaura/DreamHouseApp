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

public class MainActivity extends AppCompatActivity{
    @BindView(R.id.loginButton)Button mLoginButton;
    @BindView(R.id.signUpButton)Button mSignUpButton;
    @BindView(R.id.emailEditText)EditText mEmail;
    @BindView(R.id.passwordEditText)EditText mPassword;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,signup.class);
                startActivity(i);

            }
        });




            mLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = mEmail.getText().toString();
                    showToast(email);
                    Intent i = new Intent(MainActivity.this,DashBoardContractors.class);
                    startActivity(i);
                }


        });




    }
    private void showToast(String text){
        Toast.makeText(MainActivity.this,text,Toast.LENGTH_LONG).show();


    }
}
