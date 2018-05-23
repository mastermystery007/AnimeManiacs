package com.doodlz.husain.animemaniacs;



import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;

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

import static android.webkit.ConsoleMessage.MessageLevel.LOG;


public class PredictionViewer extends Fragment {

    private RecyclerView pPredictionList;

    private DatabaseReference pDatabase,likedDatabase,userDatabase;
    private String anime;
    private String range;
    Button submitpredButton;


    EditText predText;
    String userName="Husain";

     static PredictionViewer instance;

    public static PredictionViewer getInstance() {
        if (instance == null) {
            instance = new PredictionViewer();
        }
        return instance;
    }
    public PredictionViewer() {}

    @Override
    public  View onCreateView(LayoutInflater inflator , ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflator.inflate(R.layout.activity_prediction_viewer, container, false);



        //initialization
        pPredictionList = view.findViewById(R.id.predictionLayout);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        pPredictionList.setLayoutManager(linearLayoutManager);
        pPredictionList.setHasFixedSize(true);





        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            savedInstanceState=getArguments();

            anime = savedInstanceState.getString("anime_name_key");
            range = savedInstanceState.getString("chapter_range_key");

        Log.d("predictionViewer",""+anime);
        Log.d("predictionViewer",""+range);

        submitpredButton= getActivity().findViewById(R.id.submitBTN);
        predText= getActivity().findViewById(R.id.commentET);

        pDatabase = FirebaseDatabase.getInstance().getReference().child("Predictions").child(anime).child(range);

        likedDatabase=FirebaseDatabase.getInstance().getReference().child("LikedUsers").child("PredictionUsers");

        userDatabase=FirebaseDatabase.getInstance().getReference().child("Users");


        submitpredButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                String predContent=predText.getText().toString().trim();
                Predictions prediction=new Predictions(0,predContent,0,userName);
                 String key = pDatabase.push().getKey();
                 pDatabase.child(key).setValue(prediction);


                Log.d("PredictionViewers","the key id is"+key);


                   String userID= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();


                   userDatabase.child(userID).child("my_prediction_posts").child(key).child("anime").setValue(anime);

                   userDatabase.child(userID).child("my_prediction_posts").child(key).child("range").setValue(range);

                   predText.setText("");

                    }

                });
    }



    public void onStart(){
        super.onStart();


       FirebaseRecyclerAdapter<Predictions,PredictionViewHolder> FBRA =new FirebaseRecyclerAdapter<Predictions,PredictionViewHolder>(

                Predictions.class,
                R.layout.prediction_overlay,
                PredictionViewHolder.class,
                pDatabase

        ) {

           @Override
           public void onBindViewHolder(PredictionViewHolder viewHolder, int position) {
               super.onBindViewHolder(viewHolder, position);
           }


           @Override
            protected void populateViewHolder(final PredictionViewHolder viewHolder, final Predictions model, int position) {

                final String post_key = getRef(position).getKey();

                viewHolder.setPredictionContent(model.getPredictionContent());
                viewHolder.setUserName(model.getUserName());
                viewHolder.setUpvotes(model.getUpvotes());
                viewHolder.setLikeButton(post_key);

                Log.d("PredViewer"," "+model.showData());

                viewHolder.pUpvote.setOnClickListener(new View.OnClickListener() {
                    boolean shouldKeepProcessing1=false;
                    boolean shouldKeepProcessing2= false;
                    boolean jobIsFinished=false;
                    int realUpvotesInt;


                    int numUpvotesint;

                    @Override
                    public void onClick(View v) {

                        viewHolder.pUpvote.setEnabled(false);
                        shouldKeepProcessing1=true;
                        shouldKeepProcessing2=true;




                        pDatabase.addListenerForSingleValueEvent(new ValueEventListener() {


                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(shouldKeepProcessing2){
                                  String realUpvotes= dataSnapshot.child(post_key).child("upvotes").getValue().toString();
                                  realUpvotesInt=Integer.parseInt(realUpvotes);
                                  shouldKeepProcessing2=false;
                                  jobIsFinished=true;
                                    Log.d("PredViewer"," data has changed");
                                }
                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        likedDatabase.addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(DataSnapshot dataSnapshot) {
                             if (shouldKeepProcessing1 && jobIsFinished) {
                                 if ( dataSnapshot.child(post_key).hasChild(userName)) {

                                     viewHolder.pUpvote.setImageResource(R.drawable.whitethumb);
                                     likedDatabase.child(post_key).child(userName).removeValue();



                                     numUpvotesint=realUpvotesInt-1;
                                     viewHolder.setUpvotes(numUpvotesint);
                                     pDatabase.child(post_key).child("upvotes").setValue(numUpvotesint);


                                     viewHolder.pUpvote.setEnabled(true);
                                     shouldKeepProcessing1=false;
                                     jobIsFinished=false;

                                     Log.d("PredViewer"," data changed");


                                 }
                                 else {

                                     likedDatabase.child(post_key).child(userName).setValue("liked");


                                     viewHolder.pUpvote.setImageResource(R.drawable.redthumb);

                                     numUpvotesint= realUpvotesInt + 1;
                                     viewHolder.setUpvotes(numUpvotesint);

                                     pDatabase.child(post_key).child("upvotes").setValue(numUpvotesint);
                                     shouldKeepProcessing1=false;


                                     viewHolder.pUpvote.setEnabled(true);
                                     jobIsFinished=false;

                                     Log.d("PredViewer"," data changed");

                                 }

                             }
                         }

                         @Override
                         public void onCancelled(DatabaseError databaseError) {

                             viewHolder.pUpvote.setEnabled(true);
                         }
                     });


                    }
                });
            }
        };



        pPredictionList.setAdapter(FBRA);


        /*submitpredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String predictionContent = predText.getText().toString();
                Predictions predictions = new Predictions(0,predictionContent,0,"Husain Mistry");
                FirebaseDatabase.getInstance().getReference("Predictions").child(anime).child(range).push().setValue(predictions);
            }
        });*/
    }



    public static class PredictionViewHolder extends RecyclerView.ViewHolder{

        View mView;
        ImageButton pUpvote;
        DatabaseReference likeDBR;

        TextView upvotestv;
        TextView predictionContenttv;
        TextView usernametv;
        String userName="Husain";

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

        public void setUserName(String username) {
            usernametv.setText(username);
        }

        public void setLikeButton(final String post_key){
            likeDBR=FirebaseDatabase.getInstance().getReference().child("LikedUsers").child("PredictionUsers");
            likeDBR.addListenerForSingleValueEvent(new ValueEventListener() {
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
