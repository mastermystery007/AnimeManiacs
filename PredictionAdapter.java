package com.doodlz.husain.animemaniacs;

import android.content.Context;
import android.gesture.Prediction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by husai on 09-05-2018.
 */


public class PredictionAdapter extends RecyclerView.Adapter<UserProfile.PredictionViewHolder>  {

    ArrayList<Predictions> myPreds=new ArrayList<>();

    public PredictionAdapter(Context c, ArrayList<Predictions> mypreds) {
        this.c = c;
        this.myPreds = mypreds;
    }
    Context c;

    @Override
    public UserProfile.PredictionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.prediction_overlay, parent, false);
        return new UserProfile.PredictionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserProfile.PredictionViewHolder holder, int position) {


    }



    @Override
    public int getItemCount() {
        return 0;
    }
}
