package com.doodlz.husain.animemaniacs;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewPoll extends AppCompatActivity {


    private RecyclerView pollList;
    private DatabaseReference pollDBR;
    private DatabaseReference userPolledDBR;

    private String user_id = "husain";
    private boolean hasUserVoted = false;
    private  boolean shouldKeepProcessing=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_poll);

        pollList = (RecyclerView) findViewById(R.id.pollList);
        pollList.setHasFixedSize(true);
        pollList.setLayoutManager(new LinearLayoutManager(this));
        pollDBR = FirebaseDatabase.getInstance().getReference().child("Poll");
        userPolledDBR = FirebaseDatabase.getInstance().getReference().child("PolledUsers");


    }

    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Poll, PollViewHolder> FBRA = new FirebaseRecyclerAdapter<Poll, PollViewHolder>(

                Poll.class,
                R.layout.poll_overlay,
                PollViewHolder.class,
                pollDBR
        ) {




            @Override
            protected void populateViewHolder(final PollViewHolder viewHolder, Poll model, int position) {

                final String post_key = getRef(position).getKey();

                viewHolder.setQuestion(model.getAnimeName());
                viewHolder.setOption1(model.getOption1());
                viewHolder.setOption2(model.getOption2());
                viewHolder.setOption3(model.getOption3());
                viewHolder.setOption4(model.getOption4());

                viewHolder.setPolledOption(post_key);





                viewHolder.pollOption1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shouldKeepProcessing=true;

                        userPolledDBR.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                  if(shouldKeepProcessing) {
                                      if (dataSnapshot.child(post_key).hasChild(user_id)) {

                                          userPolledDBR.child(post_key).child(user_id).removeValue();
                                          shouldKeepProcessing = false;
                                          viewHolder.pollOption1.setTextColor(Color.GRAY);

                                      } else {

                                          userPolledDBR.child(post_key).child("husain").setValue("option1");
                                          Log.d("keys", "Value added to Database ");
                                          shouldKeepProcessing = false;
                                          viewHolder.pollOption1.setTextColor(Color.GREEN);


                                      }
                                  }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {


                                }
                            });


                    }
                });

                viewHolder.pollOption2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shouldKeepProcessing=true;

                        userPolledDBR.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(shouldKeepProcessing) {
                                    if (dataSnapshot.child(post_key).hasChild(user_id)) {

                                        userPolledDBR.child(post_key).child(user_id).removeValue();
                                        shouldKeepProcessing = false;
                                        viewHolder.pollOption2.setTextColor(Color.GRAY);


                                    } else {
                                        userPolledDBR.child(post_key).child("husain").setValue("option2");
                                        Log.d("keys", "Value added to Database ");
                                        shouldKeepProcessing = false;
                                        viewHolder.pollOption2.setTextColor(Color.GREEN);


                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }
                });

                viewHolder.pollOption3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        shouldKeepProcessing=true;

                        userPolledDBR.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(shouldKeepProcessing) {
                                    if (dataSnapshot.child(post_key).hasChild(user_id)) {

                                        userPolledDBR.child(post_key).child(user_id).removeValue();
                                        shouldKeepProcessing = false;
                                        viewHolder.pollOption3.setTextColor(Color.GRAY);


                                    } else {
                                        userPolledDBR.child(post_key).child("husain").setValue("option3");
                                        Log.d("keys", "Value added to Database ");
                                        shouldKeepProcessing = false;
                                        viewHolder.pollOption3.setTextColor(Color.GREEN);


                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }
                });




            }


        };
pollList.setAdapter(FBRA);


        //get item position
        //get poll id from firebase
        //get polled users
        //set option


    }


   /*
       public void pollOption1Clicked(View view) {
        userPolledDBR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(post_key).hasChild(user_id)) {
                    hasUserVoted = true;
                    TextView option1 = (TextView) findViewById(R.id.pollOption1);
                    option1.setTextColor(Color.GREEN);
                    userPolledDBR.keepSynced(true);


                } else {
                    if (!hasUserVoted) {
                        userPolledDBR.child(post_key).child(user_id).setValue("option1");
                        Log.d("PolledUserAdded", "Value added to Database ");
                        TextView option1 = (TextView) findViewById(R.id.pollOption1);
                        option1.setTextColor(Color.GREEN);
                        userPolledDBR.keepSynced(true);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public void pollOption2Clicked(View view) {
    }

    public void pollOption3Clicked(View view) {
    }

    public void pollOption4Clicked(View view) {
    }
   */


    public static class PollViewHolder extends RecyclerView.ViewHolder {


        View mView;
        TextView pollOption1,pollOption2,pollOption3,pollOption4,poll_question;
        DatabaseReference polledUsersDBR;
        private String user_id = "husain";

        public PollViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            pollOption1 = mView.findViewById(R.id.pollOption1);
            pollOption2 = mView.findViewById(R.id.pollOption2);
            pollOption3 = mView.findViewById(R.id.pollOption3);
            pollOption4 = mView.findViewById(R.id.pollOption4);
            poll_question = mView.findViewById(R.id.pollQuestion);

            polledUsersDBR=FirebaseDatabase.getInstance().getReference().child("PolledUsers");
        }




        public void setPolledOption(final String post_key){

                polledUsersDBR.child(post_key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(user_id)) {
                            //UNABLE TO RETRIEVE OPTION FROM FIREBASE//
                            String userOption = (String) dataSnapshot.child(user_id).getValue();
                            ////

                            Log.d("epiphany", "post_key and user_option: " + userOption);
                            if (userOption.contentEquals("option1") ) {
                                pollOption1.setTextColor(Color.GREEN);
                            }
                            if (userOption.contentEquals("option2")) {
                                pollOption2.setTextColor(Color.GREEN);
                            }
                            if (userOption.contentEquals("option3")) {
                                pollOption3.setTextColor(Color.GREEN);
                            }
                            if (userOption.contentEquals("option4")) {
                                pollOption4.setTextColor(Color.GREEN);
                            }


                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        }
        public void setQuestion(String question){poll_question.setText(question);}
        public void setOption1(String option1){
            pollOption1.setText(option1);
        }
        public void setOption2(String option2){
            pollOption2.setText(option2);
        }
        public void setOption3(String option3){
            pollOption3.setText(option3);
        }
        public void setOption4(String option4){
            pollOption4.setText(option4);
        }


        //TODO {CREATE SET OPTIONS AND UPVOTES FOR ALL OPTIONS}
    }

}



