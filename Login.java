package com.doodlz.husain.animemaniacs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private EditText mEmailField;
    private EditText mPasswordField;

    private Button mLoginButton;


    private FirebaseAuth mAuth;
    private DatabaseReference userDB;
    private String userId;
    Users user;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


        mEmailField=(EditText)findViewById(R.id.emailET);
        mPasswordField=(EditText)findViewById(R.id.passwordET);

        mLoginButton=(Button)findViewById(R.id.loginButton);
        mAuth = FirebaseAuth.getInstance();
        userDB=FirebaseDatabase.getInstance().getReference().child("Users");

         userId = mAuth.getCurrentUser().getUid();
        Log.d("UserProfile"," "+userId);


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();

    }

    private void startSignIn() {
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        userDB.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users.setUserName(dataSnapshot.child("userName").getValue().toString());
                Users.setEmailID(dataSnapshot.child("emailId").getValue().toString());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d("UserProfile"," "+Users.getUserName());
        Log.d("UserProfile"," "+Users.getEmailID());


        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){

            Toast.makeText(Login.this, "Fields are empty", Toast.LENGTH_LONG);

        }else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Login.this, "Good to go", Toast.LENGTH_LONG);

                        startActivity(new Intent(Login.this,MainActivity.class));}

                }
            });


        }

    }



    public void registerBtnClicked(View view) {
        Intent intent = new Intent(Login.this,Registration.class);
        startActivity(intent);
    }


}
