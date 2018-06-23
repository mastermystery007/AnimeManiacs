package com.doodlz.husain.animemaniacs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class UpdateProfile extends AppCompatActivity {

    EditText biotv,usernametv;

    DatabaseReference userDetails;
    StorageReference PPStorage;
    Button updateProfile;
    String FirebaseUserId;
    ImageView profilePic;
    @Nullable String profilePicture;


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
        profilePic=(ImageView) findViewById(R.id.profilePictureImageViewUpdateProfile);
        profilePicture=Users.getProfilePicture();

        Log.d("UpdateProfiless","the profilePic is "+profilePicture);
        if(profilePicture.equals("null")) {
            Picasso.get().load(profilePicture).into(profilePic);

        }else{profilePic.setImageResource(R.drawable.profilepic);}
    }



    @Override
    protected void onStart() {
        super.onStart();

        biotv.setText(Users.getBio());

        usernametv.setText(Users.getUserName());



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
                galleryIntent.setAction(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
                mProgress.setMessage("Uploading Photo...");
                mProgress.show();
            }
        });
    }

    private void resetAccount() {



        Log.d("yes" ,
                "boiz");
        final String bio=biotv.getText().toString();
        final String userName=usernametv.getText().toString();



        if(!TextUtils.isEmpty(userName)){
            mProgress.setMessage("Updating profile...");
            mProgress.show();
            userDetails.child(FirebaseUserId).child("userName").setValue(userName);
            userDetails.child(FirebaseUserId).child("bio").setValue(bio);
            Users.setUserName(userName);
            Users.setBio(bio);

            mProgress.dismiss();
        }else {
            Toast.makeText(getApplicationContext(),"Username Field Cannot Be Empty",Toast.LENGTH_LONG);

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK)
        {
           mImageUri=data.getData();
           StorageReference filepath = PPStorage.child(FirebaseUserId);

            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(getApplicationContext(),"Updated Profile Picture",Toast.LENGTH_LONG).show();

                    userDetails.child(FirebaseUserId).child("profilePicture").setValue(taskSnapshot.getDownloadUrl().toString());

                    profilePic.setImageURI(mImageUri);

                    mProgress.dismiss();
                }
            });



        }
        else{
            mProgress.dismiss();

            Toast.makeText(getApplicationContext(),"Failed to Update Profile Picture",Toast.LENGTH_LONG).show();


        }

    }


}
