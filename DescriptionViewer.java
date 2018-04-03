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
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.doodlz.husain.animemaniacs.PredictionViewer.instance;

public class DescriptionViewer extends Fragment {

    private RecyclerView descriptionViewerRV;
    private FirebaseDatabase uFirebaseDatabase;
    private DatabaseReference pDatabase;
    private String anime;
    private String range;

    Button submitDescriptionButton;
    EditText descText;

    String userName="Husain";

    static  DescriptionViewer instance;


    public static DescriptionViewer getInstance() {
        if (instance == null) {
            instance = new DescriptionViewer();
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

        uFirebaseDatabase = FirebaseDatabase.getInstance();
        pDatabase = uFirebaseDatabase.getReference().child("Description").child(anime).child(range);
        submitDescriptionButton=(Button)getActivity().findViewById(R.id.submitBTN);
        descText=(EditText)getActivity().findViewById(R.id.commentET);


        submitDescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descContent=descText.getText().toString().trim();
                Description prediction=new Description(descContent,0,0,userName,anime);
                pDatabase.push().setValue(prediction);

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
                pDatabase


        ) {

            @Override
            protected void populateViewHolder(DescriptionViewHolder viewHolder, Description model, int position) {

                viewHolder.setDescriptionContent(model.getDescriptionContent());
                Log.d("descriptionContent","descriptionContent"+model.getDescriptionContent());
                viewHolder.setUpvotes(model.getDownvotes());
                viewHolder.setDownvotes(model.getDownvotes());
                viewHolder.setUserName(model.getUserName());

                viewHolder.dupvote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO
                        // implement upvote functionality

                    }
                });

                viewHolder.ddownvote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO
                        // implement downvote functionality
                    }
                });


            }
        };

        descriptionViewerRV.setAdapter(FBRA);





    }
    public static class DescriptionViewHolder extends RecyclerView.ViewHolder{

        View mView;
        Button dupvote;
        Button ddownvote;

        public DescriptionViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
             dupvote=(Button)mView.findViewById(R.id.dupvote);
             ddownvote=(Button)mView.findViewById(R.id.ddownvote);
        }
        public void setDescriptionContent(String descriptionContent) {
            TextView descriptionContentTV= (TextView) mView.findViewById(R.id.ddescriptionContent);
            descriptionContentTV.setText(descriptionContent);

        }
        public void setUpvotes(int upvotes) {
            TextView upvotestv= (TextView)mView.findViewById(R.id.dnumOfUpvotes);
            upvotestv.setText(String.valueOf(upvotes));
        }
        public void setDownvotes(int downvotes) {
            TextView downvotestvab=(TextView)mView.findViewById(R.id.dnumOfDownvotes);
            downvotestvab.setText(String.valueOf(downvotes));
        }
        public void setUserName(String username) {
            TextView usernametv=(TextView)mView.findViewById(R.id.dusername);
            usernametv.setText(username);
        }

    }

}
