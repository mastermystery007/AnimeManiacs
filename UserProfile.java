package com.doodlz.husain.animemaniacs;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    ArrayList<String> DescriptionId = new ArrayList<>();
     ArrayList<String> PredictionId = new ArrayList<>();
     ArrayList<Predictions> myPreds =new ArrayList<>();
    ArrayList<UPanime> upa = new ArrayList<>();


    public   int startAtId = 1;

    RecyclerView RV;
    PredictionAdapter pa;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        RV=(RecyclerView)findViewById(R.id.userProfileRV);

        FirebaseUser= FirebaseAuth.getInstance().getCurrentUser().getUid();

        postDBR= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseUser);

        MyPreds=FirebaseDatabase.getInstance().getReference().child("Predictions");

        MyDescs=FirebaseDatabase.getInstance().getReference().child("Descriptions");


    }

    @Override
    protected void onStart() {
        super.onStart();




        postDBR.child("my_prediction_posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot id:dataSnapshot.getChildren()){

                    Log.d("UserProfiles","id = "+id.getKey());
                    UPanime up= id.getValue(UPanime.class);
                    up.setPostId(id.getKey());

                    Log.d("UserProfiles","id = "+up.getAnime()+up.getRange()+up.getPostId());
                    Log.d("UserProfiles","id = "+upa.size());
                    upa.add(up);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        //TODO cast as a task
        /*task.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    for(int i=0;i<upa.size();i++){
                        String anime=upa.get(i).getAnime();
                        String postId=upa.get(i).getPostId();
                        String range=upa.get(i).getRange();

                        MyPreds.child(anime).child(range).child(postId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                               myPreds.add ((Predictions)dataSnapshot.getValue(Predictions.class));
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
                pa.notifyDataSetChanged();
            }
        });*/




            //asynchronous callback
        //add onComplete listener and then fill in the arrayList of preds












        /*for(int i=0;i<upanime.size();i++){
            String range=upanime.get(i).getRange();
            String anime=upanime.get(i).getAnime();
            String post=upanime.get(i).getPostId();


            MyPreds.child(anime).child(range).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Predictions p = (Predictions)dataSnapshot.child(post).getValue();
                    Log.d("UserProfiles","predDetails = "+p.showData());


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }*/






        pa=new PredictionAdapter(this,myPreds);
        RV.setAdapter(pa);
    }



    public void goToNextPageClicked(View view) {
        myPreds.clear();



        Query recentPredictionPostsQuery = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("my_prediction_posts")
                .startAt(startAtId).limitToFirst(20);


        recentPredictionPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot id:dataSnapshot.getChildren()){
                    PredictionId.add(id.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });



        /*FirebaseDatabase.getInstance().getReference().child("Predictions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(String id:PredictionId){
                    myPreds.add((Predictions)dataSnapshot.child(id).getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


        pa.notifyDataSetChanged();


    }

    public static class PredictionViewHolder extends RecyclerView.ViewHolder{

        View mView;
        ImageButton pUpvote;
        DatabaseReference likeDBR;

        TextView upvotestv;
        TextView predictionContenttv;
        TextView usernametv;
        String userName=Users.getUserName();

        public PredictionViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            pUpvote= mView.findViewById(R.id.pupvote);

            upvotestv= mView.findViewById(R.id.numOfUpvotes);
            predictionContenttv= mView.findViewById(R.id.predictionContent);

            usernametv= mView.findViewById(R.id.username);
        }
        public void setPredictionContent(String predictionContent) {
            predictionContenttv.setText(predictionContent);
        }
        public void setUpvotes(int upvotes) {
            upvotestv.setText(String.valueOf(upvotes));
            Log.d("upvotes",String.valueOf(upvotes));
        }

        public void showLikedButton(int visibility){
            pUpvote.setVisibility(visibility);
        }
        public void setUserName(String username) {
            usernametv.setText(username);
        }

        public void setLikeButton(final String post_key){
            likeDBR=FirebaseDatabase.getInstance().getReference().child("LikedUsers").child("PredictionUsers");
            likeDBR.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(post_key).hasChild(userName)){
                        pUpvote.setImageResource(R.drawable.redthumb);


                    }else {pUpvote.setImageResource(R.drawable.whitethumb);}
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }

    }
    public static class DescriptionViewHolder extends RecyclerView.ViewHolder{

        View mView;
        ImageButton pUpvote;
        DatabaseReference likeDBR;

        TextView upvotestv;
        TextView predictionContenttv;
        TextView usernametv;
        String userName=Users.getUserName();

        public DescriptionViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            pUpvote= mView.findViewById(R.id.pupvote);

            upvotestv= mView.findViewById(R.id.numOfUpvotes);
            predictionContenttv= mView.findViewById(R.id.predictionContent);

            usernametv= mView.findViewById(R.id.username);
        }
        public void setDescriptionContent(String predictionContent) {
            predictionContenttv.setText(predictionContent);
        }
        public void setUpvotes(int upvotes) {
            upvotestv.setText(String.valueOf(upvotes));
            Log.d("upvotes",String.valueOf(upvotes));
        }

        public void showLikedButton(int visibility){
            pUpvote.setVisibility(visibility);
        }
        public void setUserName(String username) {
            usernametv.setText(username);
        }

        public void setLikeButton(final String post_key){
            likeDBR=FirebaseDatabase.getInstance().getReference().child("LikedUsers").child("PredictionUsers");
            likeDBR.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(post_key).hasChild(userName)){
                        pUpvote.setImageResource(R.drawable.redthumb);


                    }else {pUpvote.setImageResource(R.drawable.whitethumb);}
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }

    }


}

