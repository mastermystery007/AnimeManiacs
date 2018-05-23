package com.doodlz.husain.animemaniacs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class DescriptionAdapter extends RecyclerView.Adapter<UserProfile.DescriptionViewHolder> {

    ArrayList<Description> myDescs=new ArrayList<>();
    Context c;


    public DescriptionAdapter(Context c, ArrayList<Description> mydescs) {
        this.c = c;
        this.myDescs = mydescs;
    }


    @Override
    public UserProfile.DescriptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.description_overlay, parent, false);
        return new UserProfile.DescriptionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserProfile.DescriptionViewHolder holder, int position) {

       //if(position==14){refillArrayAfter();}
        holder.setUserName(myDescs.get(position).getUserName());
        holder.setDescriptionContent(myDescs.get(position).getDescriptionContent());
        holder.setUpvotes(myDescs.get(position).getUpvotes());
        holder.showLikedButton(0);
    }





    @Override
    public int getItemCount() {
        return 0;
    }
}
