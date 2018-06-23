package com.doodlz.husain.animemaniacs;

import android.content.Intent;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserProfile extends AppCompatActivity {
    private String FirebaseUser;
    private DatabaseReference postDBR;
    private String profile_pic;

    private  TextView biotv,usernametv;
    Button editProfile;


    private RecyclerView RV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        RV=(RecyclerView)findViewById(R.id.userProfileRV);
        RV.setHasFixedSize(true);

        RV.setLayoutManager(new LinearLayoutManager(this));
        Log.d("UserProfile"," reached stage 0");

        biotv=(TextView)findViewById(R.id.BioTV);
        usernametv=(TextView)findViewById(R.id.UPusernameTV) ;

        FirebaseUser= FirebaseAuth.getInstance().getCurrentUser().getUid();
        editProfile=(Button)findViewById(R.id.editProfile);

        postDBR = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseUser).child("my_posts");


        biotv.setText(Users.getBio());
        usernametv.setText(Users.getUserName());



        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this,UpdateProfile.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();



        Log.d("UserProfile"," reached stage 1");

        FirebaseRecyclerAdapter<UPopinion,UPOpinionHolder> FBRA =new FirebaseRecyclerAdapter<UPopinion,UPOpinionHolder>(


                UPopinion.class,
                R.layout.user_profile_overlay,
                UPOpinionHolder.class,
                postDBR
        ){


            @Override
            protected void populateViewHolder(final UPOpinionHolder viewHolder,final UPopinion model, int position) {
                Log.d("UserProfile","running in UP populateViewHolder");
                final String post_key = getRef(position).getKey();



                viewHolder.setUserName(model.getUserName());
                viewHolder.setContent(model.getContent());
                viewHolder.setUpvotes(model.getUpvotes());
                viewHolder.setType(model.getType());
                Log.d("UserPYo","the details are:"+model.getContent()+model.getContent()+model.getUpvotes()+model.getType());
            }

            @Override
            public void onBindViewHolder(UPOpinionHolder viewHolder, int position) {
                super.onBindViewHolder(viewHolder, position);
            }
        };

        RV.setAdapter(FBRA);
    }

    public void editProfileClicked(View view) {
        Intent intent = new Intent(UserProfile.this,UpdateProfile.class);
        startActivity(intent);
    }


    public static class UPOpinionHolder extends RecyclerView.ViewHolder {

        View mView;


        TextView upvotestv;
        TextView contenttv;
        TextView usernametv;
        TextView type;


        public UPOpinionHolder(View itemView) {

            super(itemView);
            mView = itemView;

            upvotestv = mView.findViewById(R.id.UPnumOfUpvotes);
            contenttv = mView.findViewById(R.id.UPcommentcontent);
            usernametv = mView.findViewById(R.id.UPusername);
            type=mView.findViewById(R.id.UPtype);
        }

        public void setContent(String Content) {
            contenttv.setText(Content);
        }

        public void setUpvotes(int upvotes) {
            upvotestv.setText(String.valueOf(upvotes));
        }


        public void setType(String type0){
            type.setText(type0);}

        public void setUserName(String username) {
            usernametv.setText(username);
        }




    }
}
