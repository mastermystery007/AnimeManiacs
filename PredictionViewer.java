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
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class PredictionViewer extends Fragment {

    private RecyclerView pPredictionList;
    private FirebaseDatabase uFirebaseDatabase;
    private DatabaseReference pDatabase;
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
        pPredictionList = (RecyclerView) view.findViewById(R.id.predictionLayout);


        LinearLayoutManager lineaerLayoutManager = new LinearLayoutManager(getActivity());
        pPredictionList.setLayoutManager(lineaerLayoutManager);
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
        submitpredButton=(Button) getActivity().findViewById(R.id.submitBTN);
        predText=(EditText) getActivity().findViewById(R.id.commentET);
        uFirebaseDatabase = FirebaseDatabase.getInstance();
        pDatabase = uFirebaseDatabase.getReference().child("Predictions").child(anime).child(range);


        submitpredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String predContent=predText.getText().toString().trim();
                Predictions prediction=new Predictions(0,predContent,0,userName);
                pDatabase.push().setValue(prediction);

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
            protected void populateViewHolder(PredictionViewHolder viewHolder, Predictions model, int position) {

                viewHolder.setPredictionContent(model.getPredictionContent());
                viewHolder.setUserName(model.getUserName());
                viewHolder.setUpvotes(model.getDownvotes());
                viewHolder.setDownvotes(model.getDownvotes());

                viewHolder.pUpvote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  TODO
                        // implement functionality
                    }
                });

                viewHolder.pDownvote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO
                        // implement functionality
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
        Button pUpvote;
        Button pDownvote;

        public PredictionViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            pUpvote=(Button)mView.findViewById(R.id.pupvote);
            pDownvote=(Button)mView.findViewById(R.id.pdownvote);
        }
        public void setPredictionContent(String predictionContent) {
            TextView predictionContenttv= (TextView) mView.findViewById(R.id.predictionContent);
            predictionContenttv.setText(predictionContent);
        }
        public void setUpvotes(int upvotes) {
            TextView upvotestv= (TextView)mView.findViewById(R.id.numOfUpvotes);
            upvotestv.setText(String.valueOf(upvotes));
        }
        public void setDownvotes(int downvotes) {
            TextView downvotestvab=(TextView)mView.findViewById(R.id.numOfDownvotes);
            downvotestvab.setText(String.valueOf(downvotes));
        }
        public void setUserName(String username) {
            TextView usernametv=(TextView)mView.findViewById(R.id.username);
            usernametv.setText(username);
        }

    }
}
