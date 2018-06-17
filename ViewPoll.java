package com.doodlz.husain.animemaniacs;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewPoll extends AppCompatActivity {


    private RecyclerView pollList;
    private DatabaseReference pollDBR;
    private DatabaseReference userPolledDBR;
    private String user_id ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_poll);



        pollList = (RecyclerView) findViewById(R.id.pollList);
        pollList.setHasFixedSize(true);
        pollList.setLayoutManager(new LinearLayoutManager(this));

        pollDBR = FirebaseDatabase.getInstance().getReference().child("Poll");
        userPolledDBR = FirebaseDatabase.getInstance().getReference().child("LikedUsers").child("PolledUsers");

        user_id=FirebaseAuth.getInstance().getCurrentUser().getUid();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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

                Log.d("ViewPollBoiz","PopulateViewHolder is running");
                viewHolder.setQuestion(model.getPollQuestion());
                viewHolder.setOption1(model.getOption1());
                viewHolder.setOption2(model.getOption2());
                viewHolder.setOption3(model.getOption3());
                viewHolder.setOption4(model.getOption4());
                viewHolder.setAnimeName(model.getAnimeName());

                viewHolder.setPolledOption(post_key);











                viewHolder.pollOption1.setOnClickListener(new View.OnClickListener() {


                    Poll tempPoll;
                    boolean shouldKeepProcessing=false;
                    boolean taskCompleted=false;

                    @Override
                    public void onClick(View v) {

                        shouldKeepProcessing=true;

                        pollDBR.child(post_key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                tempPoll=dataSnapshot.getValue(Poll.class);
                                taskCompleted=true;

                                Log.d("ViewPollBoiz", "the poll details are: " + dataSnapshot.getValue());
                                tempPoll.getDetails();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });




                        userPolledDBR.child(post_key).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                  if(shouldKeepProcessing && taskCompleted) {
                                      if (dataSnapshot.hasChild(user_id)) {
                                          String option=dataSnapshot.child(user_id).getValue().toString();
                                          Log.d("ViewPollBoiz", "option is "+option);

                                          if(option.equals("option1")){

                                              Log.d("ViewPollBoiz","Clicked 1");
                                              Log.d("ViewPollBoiz","option1");
                                              userPolledDBR.child(post_key).child(user_id).removeValue();
                                              Integer op1votes = tempPoll.getOp1votes()-1;
                                              pollDBR.child(post_key).child("op1votes").setValue(op1votes);

                                              taskCompleted=false;

                                          }
                                          if(option.equals("option2")){
                                              Log.d("ViewPollBoiz","Clicked 1");
                                              Log.d("ViewPollBoiz","option2");
                                              userPolledDBR.child(post_key).child(user_id).setValue("option1");
                                              Integer op1votes= tempPoll.getOp1votes()+1;
                                              Integer op2votes= tempPoll.getOp2votes()-1;
                                              pollDBR.child(post_key).child("op1votes").setValue(op1votes);
                                              pollDBR.child(post_key).child("op2votes").setValue(op2votes);

                                              taskCompleted=false;

                                          }
                                          if(option.equals("option3")){

                                              Log.d("ViewPollBoiz","Clicked 1");
                                              Log.d("ViewPollBoiz","option3");
                                              userPolledDBR.child(post_key).child(user_id).setValue("option1");
                                              Integer op1votes=tempPoll.getOp1votes()+1;
                                              Integer op3votes=tempPoll.getOp3votes()-1;
                                              pollDBR.child(post_key).child("op1votes").setValue(op1votes);
                                              pollDBR.child(post_key).child("op3votes").setValue(op3votes);

                                              taskCompleted=false;


                                          }
                                          if(option.equals("option4")){
                                              Log.d("ViewPollBoiz","Clicked 1");
                                              Log.d("ViewPollBoiz","option4");
                                              userPolledDBR.child(post_key).child(user_id).setValue("option1");
                                              Integer op1votes=tempPoll.getOp1votes()+1;
                                               Integer op4votes=tempPoll.getOp4votes()-1;
                                              pollDBR.child(post_key).child("op1votes").setValue(op1votes);
                                              pollDBR.child(post_key).child("op4votes").setValue(op4votes);

                                              taskCompleted=false;

                                          }

                                      } else {

                                          userPolledDBR.child(post_key).child(user_id).setValue("option1");
                                          Integer op1votes=tempPoll.getOp1votes()+1;
                                          pollDBR.child(post_key).child("op1votes").setValue(op1votes);
                                          Log.d("ViewPollBoiz", "Clicked 1"  );







                                          taskCompleted=false;


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
                    Poll tempPoll;
                    boolean shouldKeepProcessing=false;
                    boolean taskCompleted=false;
                    @Override
                    public void onClick(View v) {
                        shouldKeepProcessing=true;
                        pollDBR.child(post_key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                tempPoll=dataSnapshot.getValue(Poll.class);
                                taskCompleted=true;
                                Log.d("ViewPollBoiz", "datasnapshot is"+dataSnapshot.getValue());
                                tempPoll.getDetails();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });



                        userPolledDBR.child(post_key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(shouldKeepProcessing && taskCompleted) {
                                    if (dataSnapshot.hasChild(user_id)) {
                                        String option=dataSnapshot.child(user_id).getValue().toString();
                                        Log.d("ViewPollBoiz", "option "+option);
                                        if(option.equals("option1")){

                                            Log.d("ViewPollBoiz","Clicked 2");
                                            Log.d("ViewPollBoiz","option1");
                                            userPolledDBR.child(post_key).child(user_id).setValue("option2");
                                            Integer op1votes=tempPoll.getOp1votes()-1;
                                            Integer op2votes=tempPoll.getOp2votes()+1;
                                            pollDBR.child(post_key).child("op1votes").setValue(op1votes);
                                            pollDBR.child(post_key).child("op2votes").setValue(op2votes);

                                            taskCompleted=false;

                                        }
                                        if(option.equals("option2")){
                                            Log.d("ViewPollBoiz","Clicked 2");
                                            Log.d("ViewPollBoiz","option2");

                                            userPolledDBR.child(post_key).child(user_id).removeValue();
                                            Integer op2votes=tempPoll.getOp2votes()-1;
                                            pollDBR.child(post_key).child("op2votes").setValue(op2votes);
                                            taskCompleted=false;

                                        }
                                        if (option.equals("option3")){
                                            Log.d("ViewPollBoiz","Clicked 2");
                                            Log.d("ViewPollBoiz","option3");
                                            userPolledDBR.child(post_key).child(user_id).setValue("option2");
                                            Integer op3votes=tempPoll.getOp3votes()-1;
                                            Integer op2votes=tempPoll.getOp2votes()+1;
                                            pollDBR.child(post_key).child("op3votes").setValue(op3votes);
                                            pollDBR.child(post_key).child("op2votes").setValue(op2votes);

                                            taskCompleted=false;


                                        }
                                        if(option.equals("option4")){
                                            Log.d("ViewPollBoiz","Clicked 2");
                                            Log.d("ViewPollBoiz","option4");
                                            userPolledDBR.child(post_key).child(user_id).setValue("option2");
                                            Integer op2votes=tempPoll.getOp2votes()+1;
                                            Integer op4votes=tempPoll.getOp4votes()-1;
                                            pollDBR.child(post_key).child("op2votes").setValue(op2votes);
                                            pollDBR.child(post_key).child("op4votes").setValue(op4votes);

                                            taskCompleted=false;
                                        }






                                    } else {
                                        userPolledDBR.child(post_key).child(user_id).setValue("option2");
                                        Integer op2votes=tempPoll.getOp2votes()+1;

                                        Log.d("ViewPollBoiz","Clicked 2");
                                        pollDBR.child(post_key).child("op2votes").setValue(op2votes);


                                        taskCompleted=false;

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
                    Poll tempPoll;
                    boolean shouldKeepProcessing=false;
                    boolean taskCompleted=false;
                    @Override
                    public void onClick(View v) {
                        shouldKeepProcessing=true;
                        pollDBR.child(post_key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                tempPoll=dataSnapshot.getValue(Poll.class);
                                taskCompleted=true;
                                Log.d("ViewPollBoiz", "datasnapshot is"+dataSnapshot.getValue());
                                tempPoll.getDetails();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });



                        userPolledDBR.child(post_key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(shouldKeepProcessing && taskCompleted) {
                                    if (dataSnapshot.hasChild(user_id)) {
                                        String option=dataSnapshot.child(user_id).getValue().toString();
                                        Log.d("ViewPollBoiz", "option "+option);
                                        if(option.equals("option1")){

                                            Log.d("ViewPollBoiz","Clicked 3");
                                            Log.d("ViewPollBoiz","option1");
                                            userPolledDBR.child(post_key).child(user_id).setValue("option3");
                                            Integer op1votes=tempPoll.getOp1votes()-1;
                                            Integer op3votes=tempPoll.getOp3votes()+1;
                                            pollDBR.child(post_key).child("op1votes").setValue(op1votes);
                                            pollDBR.child(post_key).child("op3votes").setValue(op3votes);

                                            taskCompleted=false;

                                        }
                                        if(option.equals("option2")){
                                            Log.d("ViewPollBoiz","Clicked 3");
                                            Log.d("ViewPollBoiz","option4");
                                            userPolledDBR.child(post_key).child(user_id).setValue("option3");
                                            Integer op3votes=tempPoll.getOp3votes()+1;
                                            Integer op2votes=tempPoll.getOp2votes()-1;
                                            pollDBR.child(post_key).child("op3votes").setValue(op3votes);
                                            pollDBR.child(post_key).child("op2votes").setValue(op2votes);

                                            taskCompleted=false;

                                        }
                                        if (option.equals("option3")){
                                            Log.d("ViewPollBoiz","Clicked 3");
                                            Log.d("ViewPollBoiz","option3");
                                            userPolledDBR.child(post_key).child(user_id).removeValue();
                                            Integer op3votes=tempPoll.getOp3votes()-1;
                                            pollDBR.child(post_key).child("op3votes").setValue(op3votes);
                                            taskCompleted=false;

                                            taskCompleted=false;


                                        }
                                        if(option.equals("option4")){
                                            Log.d("ViewPollBoiz","Clicked 3");
                                            Log.d("ViewPollBoiz","option4");
                                            userPolledDBR.child(post_key).child(user_id).setValue("option3");
                                            Integer op3votes=tempPoll.getOp3votes()+1;
                                            Integer op4votes=tempPoll.getOp4votes()-1;
                                            pollDBR.child(post_key).child("op3votes").setValue(op3votes);
                                            pollDBR.child(post_key).child("op4votes").setValue(op4votes);

                                            taskCompleted=false;
                                        }






                                    } else {
                                        userPolledDBR.child(post_key).child(user_id).setValue("option3");
                                        Integer op3votes=tempPoll.getOp3votes()+1;

                                        Log.d("ViewPollBoiz","Clicked 3");
                                        pollDBR.child(post_key).child("op3votes").setValue(op3votes);


                                        taskCompleted=false;

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {


                            }
                        });


                    }
                });



                viewHolder.pollOption4.setOnClickListener(new View.OnClickListener() {
                    Poll tempPoll;
                    boolean shouldKeepProcessing=false;
                    boolean taskCompleted=false;
                    @Override
                    public void onClick(View v) {
                        shouldKeepProcessing=true;
                        pollDBR.child(post_key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                tempPoll=dataSnapshot.getValue(Poll.class);
                                taskCompleted=true;
                                Log.d("ViewPollBoiz", "datasnapshot is"+dataSnapshot.getValue());
                                tempPoll.getDetails();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });



                        userPolledDBR.child(post_key).addListenerForSingleValueEvent(new ValueEventListener(){
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(shouldKeepProcessing && taskCompleted) {
                                    if (dataSnapshot.hasChild(user_id)) {
                                        String option=dataSnapshot.child(user_id).getValue().toString();
                                        Log.d("ViewPollBoiz", "option "+option);
                                        if(option.equals("option1")){

                                            Log.d("ViewPollBoiz","Clicked 4");
                                            Log.d("ViewPollBoiz","option1");
                                            userPolledDBR.child(post_key).child(user_id).setValue("option4");
                                            Integer op1votes=tempPoll.getOp1votes()-1;
                                            Integer op4votes=tempPoll.getOp4votes()+1;
                                            pollDBR.child(post_key).child("op1votes").setValue(op1votes);
                                            pollDBR.child(post_key).child("op4votes").setValue(op4votes);

                                            taskCompleted=false;

                                        }
                                        if(option.equals("option2")){
                                            Log.d("ViewPollBoiz","Clicked 4");
                                            Log.d("ViewPollBoiz","option2");
                                            userPolledDBR.child(post_key).child(user_id).setValue("option4");
                                            Integer op4votes=tempPoll.getOp4votes()+1;
                                            Integer op2votes=tempPoll.getOp2votes()-1;
                                            pollDBR.child(post_key).child("op2votes").setValue(op2votes);
                                            pollDBR.child(post_key).child("op4votes").setValue(op4votes);

                                            taskCompleted=false;

                                        }
                                        if (option.equals("option3")){
                                            Log.d("ViewPollBoiz","Clicked 4");
                                            Log.d("ViewPollBoiz","option3");
                                            userPolledDBR.child(post_key).child(user_id).setValue("option4");
                                            Integer op4votes=tempPoll.getOp4votes()+1;
                                            Integer op3votes=tempPoll.getOp3votes()-1;
                                            pollDBR.child(post_key).child("op3votes").setValue(op3votes);
                                            pollDBR.child(post_key).child("op4votes").setValue(op4votes);

                                            taskCompleted=false;
                                        }

                                        if(option.equals("option4")){
                                            Log.d("ViewPollBoiz","Clicked 4");
                                            Log.d("ViewPollBoiz","option4");
                                            userPolledDBR.child(post_key).child(user_id).removeValue();
                                            Integer op4votes=tempPoll.getOp4votes()-1;
                                            pollDBR.child(post_key).child("op4votes").setValue(op4votes);
                                            taskCompleted=false;
                                        }






                                    } else {
                                        userPolledDBR.child(post_key).child(user_id).setValue("option4");
                                        Integer op4votes=tempPoll.getOp4votes()+1;

                                        Log.d("ViewPollBoiz","Clicked 4");
                                        pollDBR.child(post_key).child("op4votes").setValue(op4votes);


                                        taskCompleted=false;

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




    }

    public void viewPollClicked(View view) {
        startActivity(new Intent(ViewPoll.this,AddPoll.class));
    }


    public static class PollViewHolder extends RecyclerView.ViewHolder {


        View mView;
        TextView pollOption1,pollOption2,pollOption3,pollOption4,poll_question,vanimeName;
        DatabaseReference polledUsersDBR;



        private String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        public PollViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            pollOption1 = mView.findViewById(R.id.pollOption1);
            pollOption2 = mView.findViewById(R.id.pollOption2);
            pollOption3 = mView.findViewById(R.id.pollOption3);
            pollOption4 = mView.findViewById(R.id.pollOption4);
            poll_question = mView.findViewById(R.id.pollQuestion);
            vanimeName=mView.findViewById(R.id.PollAnimeName);

            polledUsersDBR=FirebaseDatabase.getInstance().getReference().child("LikedUsers").child("PolledUsers");
        }




        public void setPolledOption(final String post_key){


            polledUsersDBR.child(post_key).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(user_id) ) {

                            String userOption = (String) dataSnapshot.child(user_id).getValue();
                            Log.d("ViewPollBoiz", "populate viewholder:post-key: " +post_key);
                            Log.d("ViewPollBoiz", "populate viewholder:user-id: " + user_id);

                            Log.d("ViewPollBoiz", "populate viewholder:user_option: " + dataSnapshot.child(user_id).getValue());


                            if (userOption.contentEquals("option1")){
                                Log.d("ViewPollBoiz", "option1" );
                                pollOption1.setTextColor(Color.GREEN);
                                pollOption2.setTextColor(Color.GRAY);
                                pollOption3.setTextColor(Color.GRAY);
                                pollOption4.setTextColor(Color.GRAY);

                            }

                            if (userOption.contentEquals("option2")){
                                Log.d("ViewPollBoiz", "option2" );
                                pollOption1.setTextColor(Color.GRAY);
                                pollOption2.setTextColor(Color.GREEN);
                                pollOption3.setTextColor(Color.GRAY);
                                pollOption4.setTextColor(Color.GRAY);

                            }

                            if (userOption.contentEquals("option3")){
                                Log.d("ViewPollBoiz", "option3" );
                                pollOption1.setTextColor(Color.GRAY);
                                pollOption2.setTextColor(Color.GRAY);
                                pollOption3.setTextColor(Color.GREEN);
                                pollOption4.setTextColor(Color.GRAY);

                            }

                            if (userOption.contentEquals("option4")){
                                Log.d("ViewPollBoiz", "option4" );
                                pollOption1.setTextColor(Color.GRAY);
                                pollOption2.setTextColor(Color.GRAY);
                                pollOption3.setTextColor(Color.GRAY);
                                pollOption4.setTextColor(Color.GREEN);

                            }



                        }else {

                            pollOption1.setTextColor(Color.GRAY);
                            pollOption2.setTextColor(Color.GRAY);
                            pollOption3.setTextColor(Color.GRAY);
                            pollOption4.setTextColor(Color.GRAY);

                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



        }
        public void setQuestion(String question){poll_question.setText(question);}
        public void setAnimeName(String animeName){vanimeName.setText(animeName);}
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



    }

}



