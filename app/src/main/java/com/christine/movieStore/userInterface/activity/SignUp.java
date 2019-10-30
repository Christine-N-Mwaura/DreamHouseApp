package com.christine.movieStore.userInterface.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.christine.movieStore.R;
import com.christine.movieStore.userInterface.activity.movieActivities.movieMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class signup extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.nameEditText) EditText mName;
    @BindView(R.id.emailEditText)EditText mEmail;
    @BindView(R.id.phoneNoEditText)EditText mPhoneNo;
    @BindView(R.id.passwordEditText)EditText mPassword;
    @BindView(R.id.signUpButton) Button mSignUpButton;
    private static final String TAG = signup.class.getSimpleName();

    private FirebaseAuth mFirebaseAuth;
    private ProgressDialog progressDialog;
    private  FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mSignUpButton.setOnClickListener(this);
        createAuthStateListener();


    }

    private void createAuthStateListener() {
        authStateListener = firebaseAuth -> {
            final FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Intent intent = new Intent(signup.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        };
    }

    boolean isValidEmail(String email) {
        boolean isGoodEmail =
                (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            mEmail.setError("Please enter a valid email address");
            return false;
        }
        return isGoodEmail;
    }

    private boolean isValidName(String name) {
        if (name.equals("")) {
            mName.setError("Please enter your name");
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 6) {
            mPassword.setError("Please create a password containing at least 6 characters");
            return false;
        }
        return true;
    }




    @Override
    public void onClick(View v) {

        if (v == mSignUpButton) {

            createNewUser();

            }
        }


    private void createNewUser() {
        final String name = mName.getText().toString().trim();
        final String email = mEmail.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();


        boolean validEmail = isValidEmail(email);
        boolean validName = isValidName(name);
        boolean validPassword = isValidPassword(password);

        if (!validEmail || !validName || !validPassword) return;

        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Intent intent=new Intent(signup.this,MainActivity.class);
                        startActivity(intent);
                        Log.d(TAG, "Authentication successful");
                    } else {
                        Toast.makeText(signup.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}


