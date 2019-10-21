package com.christine.dreamhouseapp.userInterface.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.christine.dreamhouseapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashBoardContractors extends AppCompatActivity {
    @BindView(R.id.contractorsTextView) TextView mContractorTextView;
    @BindView(R.id.contractorsListView) ListView mContractorsListView;
    private String[] contractors = new String[] {"Henry Matthews","Timothy Owindi","MiCkey Jones","Victoria Rumba","Justin Matthews","Bold Binther"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_contractors);

        ButterKnife.bind(this);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,contractors);
        mContractorsListView.setAdapter(adapter);

        mContractorsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String contractor = ((TextView)view).getText().toString();
                FragmentManager fm = getSupportFragmentManager();
                ProfileActivity pActivity = new ProfileActivity();
                pActivity.show(fm,"simple Fragments");
//                Toast.makeText(DashBoardContractors.this, contractor, Toast.LENGTH_SHORT).show();
            }
        });
        Intent i = getIntent();
        String Name = i.getStringExtra("name");
        mContractorTextView.setText("Welcome " + Name + "."+ " Here are all the contractors we have : ");





    }
}
