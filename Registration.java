package com.doodlz.husain.animemaniacs;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Registration extends AppCompatActivity {

    TextView emailRegistrationTV,passwordregistrationTV;
    EditText emailRegistrationET,passwordregistrationET;

    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

    emailRegistrationET=(EditText)findViewById(R.id.emailRegistrationET);
    passwordregistrationET=(EditText)findViewById(R.id.passwordRegistrationET);

    registerButton=(Button)findViewById(R.id.registerButton);




    }

}
