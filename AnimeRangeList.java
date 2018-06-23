package com.doodlz.husain.animemaniacs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import android.support.v7.app.AppCompatActivity;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

             viewHolder.addChar.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), TopCharActivity.class);
                    startActivity(intent);
                 }
             });



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

    private void openDialog() {


    }

    public void userProfile(View view) {
        startActivity(new Intent(AnimeRangeList.this,UserProfile.class));
    }

    public void pollButtonClicked(View view) {
        startActivity(new Intent(AnimeRangeList.this,ViewPoll.class));
    }




    public static class ChapterRangeViewHolder extends RecyclerView.ViewHolder{


        View mView;
        TextView chapterName;
        TextView chapterRange;
        ImageView mImageView;
        TextView topChars;
        Button addChar;

        public ChapterRangeViewHolder(final View itemView) {
            super(itemView);
            mView=itemView;
            chapterName=(TextView)mView.findViewById(R.id.chapterNameTV);
            chapterRange=(TextView)mView.findViewById(R.id.rangeTV);

            topChars=(TextView)mView.findViewById(R.id.top10chars);
            addChar=(Button)mView.findViewById(R.id.addCharBtn);



        }

        public void setChapterTitle(String chaptername){

            chapterName.setText(chaptername);
        }
        public void setRange(String range){

            chapterRange.setText(range);
        }
    }
}
