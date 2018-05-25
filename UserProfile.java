package com.doodlz.husain.animemaniacs;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class UserProfile extends AppCompatActivity {
    private String FirebaseUser;
    private DatabaseReference postDBR,MyPreds,likedDatabase,pDatabase;
    private String profile_pic;
    private String bio;
    private String userName;
    private RecyclerView RV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        RV=(RecyclerView)findViewById(R.id.userProfileRV);
        Log.d("UserProfile"," reached stage 0");

        FirebaseUser= FirebaseAuth.getInstance().getCurrentUser().getUid();

        postDBR= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseUser).child("my_prediction_posts");

        MyPreds=FirebaseDatabase.getInstance().getReference().child("Predictions");

        pDatabase = FirebaseDatabase.getInstance().getReference().child("Predictions").child("Naruto").child("0-30");



        likedDatabase=FirebaseDatabase.getInstance().getReference().child("LikedUsers").child("PredictionUsers");


    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("UserProfile"," reached stage 1");

        FirebaseRecyclerAdapter<Predictions,PredictionViewHolder> FBRA =new FirebaseRecyclerAdapter<Predictions,PredictionViewHolder>(


                Predictions.class,
                R.layout.prediction_overlay,
                PredictionViewHolder.class,
                postDBR

        ){


            @Override
            protected void populateViewHolder(PredictionViewHolder viewHolder, Predictions model, int position) {
                Log.d("UserProfile","running in UP populateViewHolder");
            }

            @Override
            public void onBindViewHolder(PredictionViewHolder viewHolder, int position) {
                super.onBindViewHolder(viewHolder, position);

                Log.d("UserProfile","running in UP onBind");

            }
        };

        RV.setAdapter(FBRA);
    }

    public static class PredictionViewHolder extends RecyclerView.ViewHolder {

        View mView;
        ImageButton pUpvote;
        DatabaseReference likeDBR;

        TextView upvotestv;
        TextView predictionContenttv;
        TextView usernametv;
        String userName = "Husain";

        public PredictionViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            pUpvote = mView.findViewById(R.id.pupvote);

            upvotestv = mView.findViewById(R.id.numOfUpvotes);
            predictionContenttv = mView.findViewById(R.id.predictionContent);

            usernametv = mView.findViewById(R.id.username);
        }

        public void setPredictionContent(String predictionContent) {
            predictionContenttv.setText(predictionContent);
        }

        public void setUpvotes(int upvotes) {
            upvotestv.setText(String.valueOf(upvotes));
            Log.d("upvotes", String.valueOf(upvotes));
        }

        public void showLikedButton(int visibility) {
            pUpvote.setVisibility(visibility);
        }

        public void setUserName(String username) {
            usernametv.setText(username);
        }

        public void setLikeButton(final String post_key) {
            likeDBR = FirebaseDatabase.getInstance().getReference().child("LikedUsers").child("PredictionUsers");
            likeDBR.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(post_key).hasChild(userName)) {
                        pUpvote.setImageResource(R.drawable.redthumb);


                    } else {
                        pUpvote.setImageResource(R.drawable.whitethumb);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }
}
