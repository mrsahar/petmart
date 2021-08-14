package com.petshop.mart.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.petshop.mart.MainActivity;
import com.petshop.mart.R;
import com.petshop.mart.localdata.SharePreUserManage;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    Boolean isUploaded = false;
    private static final String TAG = "kiki";
    SharePreUserManage sharePreUserManage;
    Button btnUpdateProfile;
    ImageView btnUploadImg;
    Uri uri;
    UploadTask uploadTask;
    StorageReference storageRef;
    FirebaseAuth mAuth;
    String imgPathS;
    EditText userUsername, userPhone, userAddress;
    String fileName;
    String uploadedImg;
    boolean value = false;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        try {
            Bundle b = getIntent().getExtras();
            value = b.getBoolean("comingFromProfile", false);
        } catch (Exception e) {

        }
        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
        Calendar cal = Calendar.getInstance();
        Map<String, Object> addUser = new HashMap<>();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        sharePreUserManage = new SharePreUserManage(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        userUsername = findViewById(R.id.txt_ed_username);
        userPhone = findViewById(R.id.txt_ed_phone);
        userAddress = findViewById(R.id.txt_ed_address);

        btnUpdateProfile = findViewById(R.id.btn_update_profile);
        btnUploadImg = findViewById(R.id.btn_upload_img);
        TextView backLnk = findViewById(R.id.lnk_skip);

        if (value) {
            isUploaded = true;
            db.collection("users").document(user.getUid()).get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot != null) {
                    uploadedImg = documentSnapshot.get("user_Image").toString();
                    Picasso.get().load(uploadedImg).into(btnUploadImg);
                    userUsername.setText(documentSnapshot.get("username").toString());
                    userPhone.setText(documentSnapshot.get("phoneNo").toString());
                    userAddress.setText(documentSnapshot.get("address").toString());

                    addUser.put("profile_created", dateFormat.format(cal.getTime()));
                    addUser.put("email", documentSnapshot.get("username").toString());
                    addUser.put("isActive", true);
                    addUser.put("UID", mAuth.getUid());
                    backLnk.setText("Back To Profile");
                }
            });
        }

        backLnk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (backLnk.getText().toString().equals("skip")) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    onBackPressed();
                }
            }
        });

        btnUploadImg.setOnClickListener(v1 -> {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(i, "Complete action using"), 1);
        });

        String userId = user.getUid();
        reference = FirebaseDatabase.getInstance("https://pet-mart-19e46-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Users").child(userId);

        Log.d(TAG, "onCreate: " + sharePreUserManage.getUser().getEmail() + " " + sharePreUserManage.getUser().getUsername() + " " + sharePreUserManage.getUser().getUID());
        HashMap<String, String> hashMap = new HashMap<>();
        // Update Profile Button
        btnUpdateProfile.setOnClickListener(v -> {
            if (isUploaded) {
                btnUpdateProfile.setEnabled(false);
                StorageReference imgPath = storageRef.child(mAuth.getUid() + "/userprofile/" + fileName);
                if (!value) {
                    imgPath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(EditProfileActivity.this, "image uploaded successfully!", Toast.LENGTH_SHORT).show();
                            imgPath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imgPathS = uri.toString();
                                    hashMap.put("imageURL", imgPathS);
                                    hashMap.put("username",  userUsername.getText().toString());
                                    addUser.put("user_Image", imgPathS);
                                    addUser.put("address", userAddress.getText().toString());
                                    addUser.put("profile_created", dateFormat.format(cal.getTime()));
                                    addUser.put("email", sharePreUserManage.getUser().getEmail());
                                    addUser.put("isActive", true);
                                    addUser.put("username", userUsername.getText().toString());
                                    addUser.put("phoneNo", userPhone.getText().toString());
                                    addUser.put("UID", mAuth.getUid());
                                    reference.setValue(hashMap);

                                    // mAuth.getUid();
                                    db.collection("users").document(sharePreUserManage.getUser().getUID()).set(addUser)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(EditProfileActivity.this, "User Added", Toast.LENGTH_SHORT).show();
                                                    if (value) {
                                                        onBackPressed();
                                                    } else {
                                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                    }
                                                    finish();
                                                }
                                            });
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditProfileActivity.this, "Error while uploading", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    if (uri.toString().equals("")){
                        addUser.put("user_Image", uploadedImg);
                        hashMap.put("imageURL", uploadedImg);
                    }else{
                        addUser.put("user_Image", uri.toString());
                        hashMap.put("imageURL", uri.toString());
                    }

                    hashMap.put("username",  userUsername.getText().toString());
                    reference.setValue(hashMap);
                    hashMap.put("username",  userUsername.getText().toString());
                    addUser.put("address", userAddress.getText().toString());
                    addUser.put("username", userUsername.getText().toString());
                    addUser.put("phoneNo", userPhone.getText().toString());
                    // mAuth.getUid();
                    db.collection("users").document(sharePreUserManage.getUser().getUID()).set(addUser)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(EditProfileActivity.this, "User Updated", Toast.LENGTH_SHORT).show();
                                    if (value) {
                                        onBackPressed();
                                    } else {
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    }
                                    finish();
                                }
                            });

                }

            } else {
                Toast.makeText(EditProfileActivity.this, "Please Select Image first", Toast.LENGTH_SHORT).show();
                return;
            }

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data.getData() != null) {
            isUploaded = true;
            uri = data.getData();
            btnUploadImg.setImageURI(uri);
            fileName = data.getData().getPath().toLowerCase();
        } else {
            Toast.makeText(this, "Nothing Selected", Toast.LENGTH_SHORT).show();
        }
    }
}