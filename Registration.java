package com.doodlz.husain.animemaniacs;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {

    TextView emailRegistrationTV,passwordregistrationTV;
    EditText emailRegistrationET,passwordregistrationET;

    private ProgressDialog progressDialog;
    Button registerButton;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

    emailRegistrationET=(EditText)findViewById(R.id.emailRegistrationET);
    passwordregistrationET=(EditText)findViewById(R.id.passwordRegistrationET);

    registerButton=(Button)findViewById(R.id.registerButton);
    progressDialog = new ProgressDialog(this);
    firebaseAuth= FirebaseAuth.getInstance();



    }

    public void registerButtonClicked(View view) {
        registerUser();
    }

    private void registerUser() {
        String email=emailRegistrationET.getText().toString().trim();
        String password=passwordregistrationET.getText().toString().trim();
        if(TextUtils.isEmpty(email)) {
            return;}

        if(TextUtils.isEmpty(password)) {

            return;}

        progressDialog.setMessage("Registering User");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Registration.this,"Registered Successfully",Toast.LENGTH_SHORT).show();

                }else{ Toast.makeText(Registration.this,"Could not Register",Toast.LENGTH_SHORT).show();}
            }
        });


    }
}
