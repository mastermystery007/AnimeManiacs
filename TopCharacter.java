package com.doodlz.husain.animemaniacs;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TopCharacter  extends AppCompatActivity{

    Button submitButton;
    EditText mainChars;
    DatabaseReference protaganists;
    String anime;
    String range;
    int charCount;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_char);

        submitButton=(Button)findViewById(R.id.btnDone);
        mainChars=(EditText) findViewById(R.id.mainCharET);

        anime= getIntent().getExtras().getString("anime_name_key");
        range= getIntent().getExtras().getString("range_name_key");
        charCount= getIntent().getExtras().getInt("char_count");


        Log.d("TopChars","the info is"+anime+" "+range+" "+charCount);

        protaganists= FirebaseDatabase.getInstance().getReference().child("Range").child(anime).child(range);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mainProtaginists=mainChars.getText().toString();
                final String charCounto= String.valueOf(charCount+1);

                protaganists.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        protaganists.child(charCounto).setValue(mainProtaginists);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
                finish();
            }
        });
    }
}
