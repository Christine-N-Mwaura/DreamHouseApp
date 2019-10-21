package com.christine.dreamhouseapp.userInterface.activity;

import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.christine.dreamhouseapp.R;

public class ProfileActivity extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.activity_profile,container,false);
        Button mProfile = (Button) view.findViewById(R.id.addImageButton);

        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }
}