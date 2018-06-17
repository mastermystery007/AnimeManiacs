package com.doodlz.husain.animemaniacs;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.doodlz.husain.animemaniacs.PredictionViewer.instance;

public class DescriptionViewer extends Fragment  {

    private RecyclerView descriptionViewerRV;

    private DatabaseReference dDatabase,likedDatabase,userDatabase;
    private String anime;
    private String range;

    private String FirebaseUser;

    Button submitDescriptionButton;
    EditText descText;

    String userName= Users.getUserName();

    static  DescriptionViewer instance;


    public static DescriptionViewer getInstance() {
        if (DescriptionViewer.instance == null) {
            DescriptionViewer.instance = new DescriptionViewer();
        }
        return instance;
    }

    public DescriptionViewer(){}



    @Override
    public  View onCreateView(LayoutInflater inflator , ViewGroup container,Bundle savedInstanceState) {

        View view = inflator.inflate(R.layout.activity_description_viewer, container, false);

        descriptionViewerRV = (RecyclerView) view.findViewById(R.id.descriptionViewer);
        descriptionViewerRV.setHasFixedSize(true);




        LinearLayoutManager lineaerLayoutManager = new LinearLayoutManager(getActivity());
        descriptionViewerRV.setLayoutManager(lineaerLayoutManager);





        return view;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        savedInstanceState=getArguments();

        anime = savedInstanceState.getString("anime_name_key");
        range = savedInstanceState.getString("chapter_range_key");


        dDatabase = FirebaseDatabase.getInstance().getReference().child("Description").child(anime).child(range);


        FirebaseUser=FirebaseAuth.getInstance().getCurrentUser().getUid();

        submitDescriptionButton=(Button)getActivity().findViewById(R.id.submitBTN);
        descText=(EditText)getActivity().findViewById(R.id.commentET);

        likedDatabase=FirebaseDatabase.getInstance().getReference().child("LikedUsers").child("DescriptionUsers");

        userDatabase=FirebaseDatabase.getInstance().getReference().child("Users");


        submitDescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descContent=descText.getText().toString().trim();
                Description description =new Description(descContent,0,userName,anime,FirebaseUser);
                //change downvotes with date and add uploaders Uid
                String key = dDatabase.push().getKey();
                dDatabase.child(key).setValue(description);



                String userID= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();


                userDatabase.child(userID).child("my_posts").child(key).child("content").setValue(descContent);
                userDatabase.child(userID).child("my_posts").child(key).child("upvotes").setValue(0);
                userDatabase.child(userID).child("my_posts").child(key).child("userName").setValue(userName);
                userDatabase.child(userID).child("my_posts").child(key).child("type").setValue("description");

                descText.setText("");
            }
        });


        Log.d("descViewer",""+anime);
        Log.d("descViewer",""+range);

    }
    public void onStart(){
        super.onStart();


        FirebaseRecyclerAdapter<Description,DescriptionViewHolder> FBRA =new FirebaseRecyclerAdapter<Description,DescriptionViewHolder>(

                Description.class,
                R.layout.description_overlay,
                DescriptionViewHolder.class,
                dDatabase


        ) {

            @Override
            protected void populateViewHolder(final DescriptionViewHolder viewHolder, Description model, int position) {

                final String post_key = getRef(position).getKey();
                viewHolder.setDescriptionContent(model.getDescriptionContent());
                Log.d("descriptionContent","descriptionContent"+model.getDescriptionContent());
                viewHolder.setUpvotes(model.getUpvotes());
                viewHolder.setLikeButton(post_key);

                viewHolder.setUserName(model.getUserName());

                viewHolder.dupvote.setOnClickListener(new View.OnClickListener() {

                    boolean shouldKeepProcessing1=false;
                    boolean shouldKeepProcessing2=false;
                    boolean jobIsFinished=false;

                    int realUpvotesInt;


                    int numUpvotesint;

                    @Override
                    public void onClick(View v) {

                        viewHolder.dupvote.setEnabled(false);
                        shouldKeepProcessing1=true;
                        shouldKeepProcessing2=true;



                        dDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(shouldKeepProcessing2){
                                    String realUpvotes= dataSnapshot.child(post_key).child("upvotes").getValue().toString();
                                    realUpvotesInt=Integer.parseInt(realUpvotes);
                                    shouldKeepProcessing2=false;
                                    jobIsFinished=true;
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

                                        viewHolder.dupvote.setImageResource(R.drawable.whitethumb);
                                        likedDatabase.child(post_key).child(userName).removeValue();
                                        //userdatabase mein add the upvotes
                                        //TODO



                                        numUpvotesint=realUpvotesInt-1;
                                        viewHolder.setUpvotes(numUpvotesint);
                                        dDatabase.child(post_key).child("upvotes").setValue(numUpvotesint);


                                        viewHolder.dupvote.setEnabled(true);
                                        shouldKeepProcessing1=false;
                                        jobIsFinished=false;


                                    }
                                    else {

                                        likedDatabase.child(post_key).child(userName).setValue("liked");


                                        viewHolder.dupvote.setImageResource(R.drawable.redthumb);

                                        numUpvotesint=(int)realUpvotesInt + 1;
                                        viewHolder.setUpvotes(numUpvotesint);

                                        dDatabase.child(post_key).child("upvotes").setValue(numUpvotesint);
                                        shouldKeepProcessing1=false;


                                        viewHolder.dupvote.setEnabled(true);
                                        jobIsFinished=false;

                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                                viewHolder.dupvote.setEnabled(true);
                            }
                        });


                    }
                });




            }
        };

        descriptionViewerRV.setAdapter(FBRA);





    }
    public static class DescriptionViewHolder extends RecyclerView.ViewHolder{

        View mView;
        ImageButton dupvote;
        TextView upvotestv;
        TextView usernametv;
        DatabaseReference likeDBR;
        String userName="Husain";



        public DescriptionViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            dupvote=(ImageButton)mView.findViewById(R.id.dupvote);
            upvotestv= (TextView)mView.findViewById(R.id.dnumOfUpvotes);
            usernametv=(TextView)mView.findViewById(R.id.dusername);

        }
        public void setDescriptionContent(String descriptionContent) {
            TextView descriptionContentTV= (TextView) mView.findViewById(R.id.ddescriptionContent);
            descriptionContentTV.setText(descriptionContent);

        }

        public void setUpvotes(int upvotes) {
            upvotestv.setText(String.valueOf(upvotes));
        }



        public void setUserName(String username) {
            usernametv.setText(username);
        }

        public void setLikeButton(final String post_key){
            likeDBR=FirebaseDatabase.getInstance().getReference().child("LikedUsers").child("DescriptionUsers");
            likeDBR.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(post_key).hasChild(userName)){
                        dupvote.setImageResource(R.drawable.redthumb);


                    }else {dupvote.setImageResource(R.drawable.whitethumb);}
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }
    }

}
