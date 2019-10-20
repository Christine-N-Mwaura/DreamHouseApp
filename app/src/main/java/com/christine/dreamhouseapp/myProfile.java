package com.christine.dreamhouseapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;


public class myProfile extends Fragment {
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    ImageView mProfileImage;
    ImageView mEditImageView;
    TextView mContractorName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_my_dashboard,container,false);
         mProfileImage = (ImageView) view.findViewById(R.id.profileImageView);
        mEditImageView = (ImageView) view.findViewById(R.id.editImageView);



        mEditImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
//        Intent i = getIntent();
//        String Name = i.getStringExtra("name");
//        mContractorTextView.setText("Welcome " + Name + "."+ " Here are all the contractors we have : ");
//

//        mProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
        return view;
    }
    private void openGallery(){

        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){

            imageUri = data.getData();
            mProfileImage.setImageURI(imageUri);

        }
    }
}
