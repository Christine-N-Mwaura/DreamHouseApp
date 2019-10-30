package com.christine.movieStore.userInterface.activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.christine.movieStore.R;
import com.christine.movieStore.userInterface.activity.movieActivities.MovieMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.spark.submitbutton.SubmitButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.loginButton)
    SubmitButton mLoginButton;
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

        progressDialog = new ProgressDialog(this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user !=null){
                   //getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
                    startActivity(new Intent(MainActivity.this, MovieMainActivity.class));
                }
            }
       };

        mLoginButton.setOnClickListener(this);
        mNoAccount.setOnClickListener(this);
    }


        private void logIn(){
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                Toast.makeText(MainActivity.this,"Field is empty",Toast.LENGTH_SHORT).show();
            }else{

                progressDialog.setMessage("Logging in...");
                progressDialog.show();
                mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Sign in problem",Toast.LENGTH_SHORT).show();

                        }else if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"LogIn successful",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(MainActivity.this, MovieMainActivity.class);
                            startActivity(i);
                        }
                    }
                });
            }

        }

        @Override
        public void onClick(View view){

            if(view == mLoginButton){
                logIn();


            }
            if(view == mNoAccount){
                Intent i = new Intent(MainActivity.this, SignUp.class);
               startActivity(i);

            }
        }



}
