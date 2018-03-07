package com.doodlz.husain.animemaniacs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class PredictionViewer extends AppCompatActivity {

    private RecyclerView pPredictionList;
    private FirebaseDatabase uFirebaseDatabase;
    private DatabaseReference pDatabase;
    String anime="Naruto";
    String range="0-30";
    Button submitpredButton;
    EditText predText;
    String userName="Husain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction_viewer);

        //initialization
        pPredictionList=(RecyclerView) findViewById(R.id.predictionLayout);
        pPredictionList.setHasFixedSize(true);
        pPredictionList.setLayoutManager(new LinearLayoutManager(this));

        submitpredButton=(Button)findViewById(R.id.submitPredbutton) ;
        predText = (EditText)findViewById(R.id.predictionet) ;

        uFirebaseDatabase = FirebaseDatabase.getInstance();
        pDatabase = uFirebaseDatabase.getReference().child("Predictions").child("Naruto").child(range);

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

                Log.d("godHusain", model.showData());

            }
        };

        pPredictionList.setAdapter(FBRA);

        submitpredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String predictionContent = predText.getText().toString();
                Predictions predictions = new Predictions(0,predictionContent,0,"Husain Mistry");
                FirebaseDatabase.getInstance().getReference("Predictions").child("Naruto").child(range).push().setValue(predictions);
            }
        });
    }



    public static class PredictionViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public PredictionViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
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
