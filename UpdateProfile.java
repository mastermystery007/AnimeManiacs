package com.doodlz.husain.animemaniacs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class UpdateProfile extends AppCompatActivity {

    EditText biotv,usernametv;

    DatabaseReference userDetails;
    StorageReference PPStorage;
    Button updateProfile;
    String FirebaseUserId;
    ImageButton profilePic;


     private Uri mImageUri=null;
     private static final int GALLERY_REQUEST = 1;
     private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        biotv=(EditText) findViewById(R.id.bioet);
        usernametv=(EditText)findViewById(R.id.usernameet);

        PPStorage= FirebaseStorage.getInstance().getReference().child("Profile_images");
        userDetails= FirebaseDatabase.getInstance().getReference().child("Users");
        FirebaseUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mProgress=new ProgressDialog(this);
        updateProfile=(Button)findViewById(R.id.updateProfile);
        profilePic=(ImageButton)findViewById(R.id.profilepicIB);

    }



    @Override
    protected void onStart() {
        super.onStart();


        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAccount();
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
            }
        });
    }

    private void resetAccount() {



        Log.d("yes" ,
                "boiz");
        final String bio=biotv.getText().toString();
        final String userName=usernametv.getText().toString();



        if(!TextUtils.isEmpty(userName)){
            mProgress.setMessage("Updating proile...");
            mProgress.show();
            userDetails.child(FirebaseUserId).child("userName").setValue(userName);
            userDetails.child(FirebaseUserId).child("bio").setValue(bio);
            Users.setUserName(userName);
            Users.setBio(bio);
        }else {
            Toast.makeText(getApplicationContext(),"Username Field Cannot Be Empty",Toast.LENGTH_LONG);

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK)
        {
            mImageUri = data.getData();
            profilePic.setImageURI(mImageUri);
            StorageReference filepath = PPStorage.child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri);
            mProgress.dismiss();


        }
    }
}
