package com.christine.DreamhouseApp.userInterface.activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.christine.DreamhouseApp.R;
import com.christine.DreamhouseApp.userInterface.activity.movieActivities.movieMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.loginButton)Button mLoginButton;
    @BindView(R.id.noAccountTextView)TextView mNoAccount;
    @BindView(R.id.emailEditText)EditText mEmail;
    @BindView(R.id.passwordEditText)EditText mPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth mFirebaseAuth;
    private  FirebaseAuth.AuthStateListener authStateListener;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mLoginButton.setOnClickListener(this);
        mNoAccount.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        mFirebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() !=null){

                    startActivity(new Intent(MainActivity.this,movieMainActivity.class));
                }
            }
        };
    }

//
//        mNoAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this,signup.class);
//                startActivity(i);
//
//            }
//        });


//        mLoginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name = mName.getText().toString();
//                Intent i = new Intent(MainActivity.this, movieMainActivity.class);
//                i.putExtra("name",name);
//                startActivity(i);
//            }
//
//
//        });

    private void registerUser(){

        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if(email.isEmpty()){
            //name is empty
            Toast.makeText(this,"Please enter the correct email",Toast.LENGTH_SHORT).show();
            return;

        }
        else if (TextUtils.isEmpty(password)){

            //password is empty
            Toast.makeText(this,"Please enter the correct password",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        mFirebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Show the movie posters

                            Toast.makeText(MainActivity.this,"Registered successfully",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(MainActivity.this, movieMainActivity.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(MainActivity.this,"Couldn't register please try again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

        @Override
        public void onClick(View view){

            if(view == mLoginButton){
                registerUser();
            }
            if(view == mNoAccount){

                //signUpUser();
            }
        }



}
