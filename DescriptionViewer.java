package com.doodlz.husain.animemaniacs;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DescriptionViewer extends AppCompatActivity {

    private RecyclerView descriptionViewerRV;
    private FirebaseDatabase uFirebaseDatabase;
    private DatabaseReference pDatabase;
    private String anime="Naruto";
    private String range="0-30";

    String userName="Husain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_viewer);

        descriptionViewerRV=(RecyclerView)findViewById(R.id.descriptionViewer);
        descriptionViewerRV.setHasFixedSize(true);
        descriptionViewerRV.setLayoutManager(new LinearLayoutManager(this));

        uFirebaseDatabase = FirebaseDatabase.getInstance();
        pDatabase = uFirebaseDatabase.getReference().child("Description").child(anime).child(range);



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


            }
        };

        descriptionViewerRV.setAdapter(FBRA);





    }
    public static class DescriptionViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public DescriptionViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
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
