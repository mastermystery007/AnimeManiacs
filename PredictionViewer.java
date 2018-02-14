package com.doodlz.husain.animemaniacs;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class PredictionViewer extends AppCompatActivity {

    private RecyclerView pPredictionList;
    private DatabaseReference pDatabase;
    String anime="Naruto";
    String range="0-30";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        pPredictionList=(RecyclerView) findViewById(R.id.activity_prediction_viewer);
        pPredictionList.setHasFixedSize(true);
        pPredictionList.setLayoutManager(new LinearLayoutManager(this));
        pDatabase= FirebaseDatabase.getInstance().getReference().child("Predictions").child(anime).child(range);

    }

    public void onStart(){
        super.onStart();


        FirebaseRecyclerAdapter<Predictions,PollViewHolder> FBRA =new FirebaseRecyclerAdapter<Predictions,PollViewHolder>(

                Predictions.class,
                R.layout.prediction_overlay,
                PollViewHolder.class,
                pDatabase

        ) {

            @Override
            protected void populateViewHolder(PollViewHolder viewHolder, Predictions model, int position) {

                viewHolder.setPredictionContent(model.getPredictionContent());
                viewHolder.setUpvotes(model.getUpvotes());
                viewHolder.setDownvotes(model.getDownvotes());
                viewHolder.setUserName(model.getUserName());
            }
        };

        pPredictionList.setAdapter(FBRA);
    }


    public static class PollViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public PollViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setPredictionContent(String predictionContent) {
            TextView predictionContenttv=(TextView)mView.findViewById(R.id.predictionContent);
            predictionContenttv.setText(predictionContent);
        }
        public void setUpvotes(String upvotes) {
            TextView option1tv=(TextView)mView.findViewById(R.id.upvotes);
            option1tv.setText(upvotes);
        }
        public void setDownvotes(String downvotes) {
            TextView downvotestv=(TextView)mView.findViewById(R.id.downvotes);
            downvotestv.setText(downvotes);
        }
        public void setUserName(String username) {
            TextView usernametv=(TextView)mView.findViewById(R.id.username);
            usernametv.setText(username);
        }

    }
}
