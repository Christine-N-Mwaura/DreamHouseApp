package com.christine.movieStore.userInterface.activity.movieActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.christine.movieStore.R;
import com.christine.movieStore.model.Movie;
import com.christine.movieStore.userInterface.adapter.FavoritesAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<Movie> favoritesList = new ArrayList<>();
    FavoritesAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        databaseReference= FirebaseDatabase.getInstance().getReference("favorites");
        listView = findViewById(R.id.listViewTxt);
        arrayAdapter = new FavoritesAdapter(favoritesList, this);

        listView.setAdapter(arrayAdapter);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("trying something", String.valueOf(dataSnapshot));
                Movie value = dataSnapshot.getValue(Movie.class);
                favoritesList.add(value);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
