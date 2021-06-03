package com.petshop.mart.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.petshop.mart.R;
import com.petshop.mart.localdata.SharePreUserManage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "kiki";
    SharePreUserManage sharePreUserManage;
    Button btnUpdateProfile;
    ImageView btnUploadImg;
    Uri uri;
    UploadTask uploadTask;
    StorageReference storageRef;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();


        btnUpdateProfile = findViewById(R.id.btn_update_profile);
        btnUploadImg = findViewById(R.id.btn_upload_img);

        btnUploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Complete action using"), 1);
            }
        });

        sharePreUserManage = new SharePreUserManage(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> addUser = new HashMap<>();

        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
        Calendar cal = Calendar.getInstance();

        Log.d(TAG, "onCreate: "+sharePreUserManage.getUser().getEmail()+" " +sharePreUserManage.getUser().getUsername()+" "+sharePreUserManage.getUser().getUID());

        // Update Profile Button
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser.put("address", "Los Angeles");
                addUser.put("state", dateFormat.format(cal.getTime()));
                addUser.put("email", sharePreUserManage.getUser().getEmail());
                addUser.put("isActive", true);
                addUser.put("username", sharePreUserManage.getUser().getUsername());
                addUser.put("phoneNo", "03320781234");
                addUser.put("UID", mAuth.getUid());


                // mAuth.getUid();
                db.collection("users").document(sharePreUserManage.getUser().getUID()).set(addUser)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditProfileActivity.this, "User Added", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: "+data.getData());
        if(requestCode == 1 && resultCode == RESULT_OK && data.getData() != null){
            uri = Uri.fromFile(new File(data.getData().getPath()));
            btnUploadImg.setImageURI(uri);
            StorageReference riversRef = storageRef.child("users").child(mAuth.getUid()).child("profile").child(uri.getLastPathSegment());
            uploadTask = riversRef.putFile(uri);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(EditProfileActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                }
            });
        }
    }
}