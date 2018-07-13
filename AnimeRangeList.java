package com.doodlz.husain.animemaniacs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import android.support.v7.app.AppCompatActivity;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

public class AnimeRangeList extends Activity {

    private RecyclerView mAnimeRangeList;
    private DatabaseReference mDatabase;
    private  String anime_name;
    private String[] mainCharacters=new String[10];







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_range);



        Log.d("God Please"," Hello World");
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
            protected void populateViewHolder(ChapterRangeViewHolder viewHolder, final ChapterRange model, int position) {
                   final String range_key = getRef(position).getKey();


                   final int charCount=model.getCharCount();



/*
*
*     for(DataSnapshot ds : dataSnapshot.child("days").getChildren()) {
            String keyItem = ds.child("keyItem").getValue(String.class);
            String orderItem = ds.child("orderItem").getValue(String.class);
            String typeItem = ds.child("typeItem").getValue(String.class);
            Log.d("TAG", keyItem + " / " + orderItem + " / " + typeItem);
        }*/



                viewHolder.setChapterTitle(model.getChapterTitle());
                viewHolder.setRange(model.getRange());

                mDatabase.child(model.getRange()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String allMainChars=null;
                        for(DataSnapshot ds : dataSnapshot.child("mainChars").getChildren()) {
                            String keyItem = ds.getValue(String.class);

                            mainCharacters = keyItem.split("&");
                            Toast.makeText(AnimeRangeList.this, "No just No"+mainCharacters, Toast.LENGTH_LONG).show();



                        }

                      // model.setMainChars(allMainChars);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                viewHolder.topChars.setText(mainCharacters[0]);

                //viewHolder.setMainChars(model.getMainChars());



                //viewHolder.setMainChars(model.getMainChars());


             viewHolder.addChar.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     Toast.makeText(AnimeRangeList.this, "Good to go", Toast.LENGTH_LONG).show();
                     Intent intent = new Intent(AnimeRangeList.this,TopCharacter.class);
                     intent.putExtra("anime_name_key",anime_name);
                     intent.putExtra("range_name_key",range_key);
                     intent.putExtra("char_count",charCount);
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
        public void setMainChars(String mainC){
          topChars.setText(mainC);
        }


    }
}
