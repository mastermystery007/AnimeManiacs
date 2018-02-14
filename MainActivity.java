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

public class MainActivity extends AppCompatActivity {

    private RecyclerView mAnimeList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAnimeList=(RecyclerView)findViewById(R.id.animeList);
        mAnimeList.setHasFixedSize(true);
        mAnimeList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Animes");


    }
    public void onStart(){
        super.onStart();


      FirebaseRecyclerAdapter<Animes,AnimeViewHolder> FBRA =new FirebaseRecyclerAdapter<Animes,AnimeViewHolder>(

              Animes.class,
              R.layout.anime_card,
              AnimeViewHolder.class,
              mDatabase

      ) {

          @Override
          protected void populateViewHolder(AnimeViewHolder viewHolder, Animes model, int position) {

              viewHolder.setTitle(model.getAnimeName());
              viewHolder.setDescription(model.getDescription());
          }
      };
        mAnimeList.setAdapter(FBRA);
    }


    public static class AnimeViewHolder extends RecyclerView.ViewHolder{


        View mView;

        public AnimeViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setTitle(String title){
            TextView anime_title=(TextView)mView.findViewById(R.id.animeTitle);
            anime_title.setText(title);
        }
        public void setDescription(String description){
            TextView anime_title=(TextView)mView.findViewById(R.id.animeDescription);
            anime_title.setText(description);
        }
    }
}









