package com.doodlz.husain.animemaniacs;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity {
    private String FirebaseUser;
    private DatabaseReference postDBR,MyPreds,MyDescs;
    private String profile_pic;
    private String bio;
    private String userName;
    ArrayList<Opinion> myOpinions=new ArrayList<>();
    ArrayList<String> opinionId = new ArrayList<>();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        FirebaseUser= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        postDBR= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseUser).child("my_posts");
        MyPreds=FirebaseDatabase.getInstance().getReference().child("Predictions");
        MyDescs=FirebaseDatabase.getInstance().getReference().child("Descriptions");

    }

    @Override
    protected void onStart() {
        super.onStart();

        Query uploadedOpinionId = postDBR.limitToFirst(15);
        opinionId.add(uploadedOpinionId.toString());
        for(int i=0;i<opinionId.size();i++){Log.d("UP",opinionId.get(i));}
    }
}
