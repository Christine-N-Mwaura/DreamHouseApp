package com.christine.dreamhouseapp.userInterface.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.christine.dreamhouseapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{
    @BindView(R.id.loginButton)Button mLoginButton;
    @BindView(R.id.noAccountTextView)TextView mNoAccount;
    @BindView(R.id.userNameEditText)EditText mName;
    @BindView(R.id.passwordEditText)EditText mPassword;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mNoAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,signup.class);
                startActivity(i);

            }
        });



//
//            mLoginButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String name = mName.getText().toString();
//                    Intent i = new Intent(MainActivity.this,DashBoardContractors.class);
//                    i.putExtra("name",name);
//                    startActivity(i);
//                }
//
//
//        });
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getText().toString();
                Intent i = new Intent(MainActivity.this,myDashboard.class);
                i.putExtra("name",name);
                startActivity(i);
            }


        });






    }
    private void showToast(String text){
        Toast.makeText(MainActivity.this,text,Toast.LENGTH_LONG).show();


    }

}
