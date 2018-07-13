package com.doodlz.husain.animemaniacs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class PredComments extends AppCompatActivity {

    DatabaseReference commentDBR;

    private RecyclerView commentPrediction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pred_comments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        commentPrediction=(RecyclerView)findViewById(R.id.commentPrediction);

        commentPrediction.setLayoutManager(new LinearLayoutManager(this));



        commentDBR= FirebaseDatabase.getInstance().getReference().child("Comments").child("Predictions");

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<CommentViewer,PredComment> FBRA =new FirebaseRecyclerAdapter<CommentViewer,PredComment>(
                CommentViewer.class,
                R.layout.comment_post,
                PredComment.class,
                commentDBR

        ) {

            @Override
            protected void populateViewHolder(PredComment viewHolder, CommentViewer model, int position) {

                final String pred_comment_id = getRef(position).getKey();
                viewHolder.setCommentContent(model.getCommentContent());
                viewHolder.setCommentUserName(model.getCommentUserName());
                viewHolder.setLikeCount(String.valueOf(model.getLikeCount()));



            }
        };
        commentPrediction.setAdapter(FBRA);
    }

    public static class PredComment extends RecyclerView.ViewHolder{

        TextView commentUserName;
        TextView commentContent;
        TextView likeCount;
        ImageButton commentLike;

        View mView;

        public PredComment(View itemView) {
            super(itemView);
            mView=itemView;
            commentContent=(TextView)mView.findViewById(R.id.commentContent);
            commentUserName=(TextView)mView.findViewById(R.id.commentUsername);
            likeCount=(TextView)mView.findViewById(R.id.commentUpvotes);
        }

        public void setCommentContent(String content){
            commentContent.setText(content);
        }
        public void setCommentUserName(String userName){

            commentUserName.setText(userName);
        }

        public void setLikeCount(String count){
            likeCount.setText(count);
        }
    }
}
