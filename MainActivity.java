package com.doodlz.husain.animemaniacs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mAnimeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAnimeList=(RecyclerView)findViewById(R.id.animeList);
        mAnimeList.setHasFixedSize(true);
        mAnimeList.setLayoutManager(new LinearLayoutManager(this));

    }
    public void onStart(){
        super.onStart();

    }
}








