package com.doodlz.husain.animemaniacs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewPoll extends AppCompatActivity {


    private RecyclerView spollList;
    private DatabaseReference sDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_poll);

        spollList=(RecyclerView)findViewById(R.id.animeList);
        spollList.setHasFixedSize(true);
        spollList.setLayoutManager(new LinearLayoutManager(this));
        sDatabase = FirebaseDatabase.getInstance().getReference().child("Poll");
    }
    public void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<Poll,PollViewHolder> FBRA =new FirebaseRecyclerAdapter<Poll,PollViewHolder>(

                Poll.class,
                R.layout.poll_overlay,
                PollViewHolder.class,
                sDatabase

        ) {

            @Override
            protected void populateViewHolder(PollViewHolder viewHolder, Poll model, int position) {

                viewHolder.setQuestion(model.getAnimeName());
                viewHolder.setOption1(model.getOption1());
            }
        };
        spollList.setAdapter(FBRA);
    }


    public static class PollViewHolder extends RecyclerView.ViewHolder{


        View mView;

        public PollViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setQuestion(String question){
            TextView poll_question=(TextView)mView.findViewById(R.id.pollQuestion);
            poll_question.setText(question);
        }
        public void setOption1(String option1){
            TextView poll_op_1=(TextView)mView.findViewById(R.id.pollOption1);
            poll_op_1.setText(option1);
        }
        //TODO {CREATE SET OPTIONS AND UPVOTES FOR ALL OPTIONS}
    }

    }



