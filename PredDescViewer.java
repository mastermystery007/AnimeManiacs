package com.doodlz.husain.animemaniacs;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class PredDescViewer extends AppCompatActivity {

    private TextView mTextMessage;
    private String animeName;
    private String chapterRange;
    Bundle bundle;
    FragmentManager fm = getSupportFragmentManager();












    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {



        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.prediction:
                    setTitle("Predictions");

                    PredictionViewer.getInstance().setArguments(bundle);
                    fm.beginTransaction().replace(R.id.container,PredictionViewer.getInstance()).commit();

                    return true;

                case R.id.description:
                    setTitle("Descriptions");



                    DescriptionViewer.getInstance().setArguments(bundle);
                    fm.beginTransaction().replace(R.id.container,DescriptionViewer.getInstance()).commit();
                    return true;}

                    return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pred_desc_viewer);


        animeName= getIntent().getExtras().getString("anime_name_key");
        chapterRange= getIntent().getExtras().getString("chapter_range_key");

        bundle=new Bundle();
        bundle.putString("anime_name_key",animeName);
        bundle.putString("chapter_range_key",chapterRange);

        setTitle("Predictions");

        PredictionViewer.getInstance().setArguments(bundle);
        fm.beginTransaction().replace(R.id.container,PredictionViewer.getInstance()).commit();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);






    }

}
