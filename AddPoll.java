package com.doodlz.husain.animemaniacs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPoll extends AppCompatActivity {

   private FirebaseDatabase sFirebaseDatabase;
   private EditText pollQuestiontv,option1tv,option2tv,option3tv,option4tv;
   private Button addPollbutton;
   DatabaseReference sDatabaseReference;
   String userName="Husain Mistry",animeName="Naruto";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poll);

        pollQuestiontv=(EditText)findViewById(R.id.pollQuestion);
        option1tv=(EditText)findViewById(R.id.option1);
        option2tv=(EditText)findViewById(R.id.option2);
        option3tv=(EditText)findViewById(R.id.option3);
        option4tv=(EditText)findViewById(R.id.option4);
        addPollbutton=(Button)findViewById(R.id.addPoll);
        sFirebaseDatabase=FirebaseDatabase.getInstance();
        sDatabaseReference=sFirebaseDatabase.getReference("Poll");


    }

    public void addPollClicked(View view) {
        String pollQuestion= pollQuestiontv.getText().toString();
        String option1= option1tv.getText().toString().trim();
        String option2= option2tv.getText().toString().trim();
        String option3= option3tv.getText().toString().trim();
        String option4= option4tv.getText().toString().trim();

        Poll userPoll=new Poll(pollQuestion,animeName,userName,option1,option2,option3,option4,0,0,0,0);
        sDatabaseReference.push().setValue(userPoll);
    }
}
