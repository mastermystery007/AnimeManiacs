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

    private FirebaseAuth.AuthStateListener mAuthListener;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


        mEmailField=(EditText)findViewById(R.id.emailET);
        mPasswordField=(EditText)findViewById(R.id.passwordET);

        mLoginButton=(Button)findViewById(R.id.loginButton);
        mAuth = FirebaseAuth.getInstance();
        userDB=FirebaseDatabase.getInstance().getReference().child("Users");


        Log.d("Loginss"," "+userId);



        mAuthListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    userId = mAuth.getCurrentUser().getUid();

                    userDB.child(userId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Users.setUserName(dataSnapshot.child("userName").getValue().toString());
                            Users.setEmailID(dataSnapshot.child("emailId").getValue().toString());
                            Users.setProfilePicture(dataSnapshot.child("profilePicture").getValue().toString());
                            //Users.setBio(dataSnapshot.child("bio").getValue().toString());

                            Log.d("Loginss"," "+Users.getUserName());
                            Log.d("Loginss"," "+Users.getEmailID());
                            Log.d("Loginss"," "+Users.getProfilePicture());


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    startActivity(new Intent(Login.this,MainActivity.class));
                    finish();
                }



                }
            };



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
        mAuth.addAuthStateListener(mAuthListener);


    }

    private void startSignIn() {
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();


        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){

            Toast.makeText(Login.this, "Fields are empty", Toast.LENGTH_LONG);

        }else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        userId = mAuth.getCurrentUser().getUid();

                        userDB.child(userId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Users.setUserName(dataSnapshot.child("userName").getValue().toString());
                                Users.setEmailID(dataSnapshot.child("emailId").getValue().toString());
                                Users.setProfilePicture(dataSnapshot.child("profilePicture").getValue().toString());
                                Users.setBio(dataSnapshot.child("bio").getValue().toString());

                                Log.d("Loginss"," "+Users.getUserName());
                                Log.d("Loginss"," "+Users.getEmailID());
                                Log.d("Loginss"," "+Users.getProfilePicture());


                                finish();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        Toast.makeText(Login.this, "Good to go", Toast.LENGTH_LONG).show();

                        startActivity(new Intent(Login.this,MainActivity.class));}
                        else{

                        Toast.makeText(Login.this, "No just No", Toast.LENGTH_LONG).show();


                    }

                }
            });


        }


    }
    public void gotoRegister(View view) {
        Intent intent = new Intent(Login.this,Registration.class);
        startActivity(intent);
        finish();
    }
}
