package com.doodlz.husain.animemaniacs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mAnimeList;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAnimeList=(RecyclerView)findViewById(R.id.animeList);

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

             final String poll_key = getRef(position).getKey();
              viewHolder.setTitle(model.getAnimeName());
              viewHolder.setDescription(model.getDescription());
              viewHolder.setImage(model.getImage());
              model.inString(model);

              viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                 Intent intent = new Intent(MainActivity.this,AnimeRangeList.class);
                 intent.putExtra("anime_name_key",poll_key);
                  startActivity(intent);

                  }
              });
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

        public void setImage(String image){
            ImageView anime_image=(ImageView)mView.findViewById(R.id.animeImage);
            Picasso.get().load(image).into(anime_image);

        }
    }
}









