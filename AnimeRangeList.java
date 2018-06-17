package com.doodlz.husain.animemaniacs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AnimeRangeList extends AppCompatActivity {

    private RecyclerView mAnimeRangeList;
    private DatabaseReference mDatabase;
    private  String anime_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_range);

        anime_name = getIntent().getExtras().getString("anime_name_key");

        mAnimeRangeList=(RecyclerView)findViewById(R.id.animeRangeRecyclerView);

        mAnimeRangeList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Range").child(anime_name);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<ChapterRange,ChapterRangeViewHolder> FBRA = new FirebaseRecyclerAdapter<ChapterRange, ChapterRangeViewHolder>(
                ChapterRange.class,
                R.layout.anime_range_card,
                ChapterRangeViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(ChapterRangeViewHolder viewHolder, ChapterRange model, int position) {
                final String range_key = getRef(position).getKey();

                viewHolder.setChapterTitle(model.getChapterTitle());
                viewHolder.setRange(model.getRange());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(AnimeRangeList.this,PredDescViewer.class);
                        intent.putExtra("anime_name_key",anime_name);
                        intent.putExtra("chapter_range_key",range_key);
                        startActivity(intent);

                    }
                });
            }
        };

        mAnimeRangeList.setAdapter(FBRA);
    }

    public void userProfile(View view) {
        startActivity(new Intent(AnimeRangeList.this,UserProfile.class));
    }

    public void pollButtonClicked(View view) {
        startActivity(new Intent(AnimeRangeList.this,ViewPoll.class));
    }


    public static class ChapterRangeViewHolder extends RecyclerView.ViewHolder{


        View mView;

        public ChapterRangeViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setChapterTitle(String chaptername){
            TextView chapterName=(TextView)mView.findViewById(R.id.chapterNameTV);
            chapterName.setText(chaptername);
        }
        public void setRange(String range){
            TextView chapterRange=(TextView)mView.findViewById(R.id.rangeTV);
            chapterRange.setText(range);
        }
    }
}
