package com.petshop.mart.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.petshop.mart.MainActivity;
import com.petshop.mart.R;
import com.petshop.mart.localdata.SharePreUserManage;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    SharePreUserManage sharePreUserManage;
    EditText userName,userEmail,userPassword;
    Button btnSignUp;
    private static final String TAG = "kiki";
    ProgressBar progressBar;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        sharePreUserManage = new SharePreUserManage(this);
        progressBar = findViewById(R.id.progress_bar);

        userName = findViewById(R.id.user_username);
        userEmail = findViewById(R.id.user_email);
        userPassword = findViewById(R.id.user_password);
        btnSignUp = findViewById(R.id.btn_sign_up);
        TextView txtLogin = findViewById(R.id.goto_login);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()){
                    String username = userName.getText().toString().trim();
                    String email = userEmail.getText().toString().trim();
                    String password = userPassword.getText().toString().trim();
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        sharePreUserManage.setUserData(user.getUid(),email);
                                        register(username, username, email, password);


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }


                                }
                            });
                }


            }

        });
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }

    private Boolean validation() {
        return true;
    }


    private void register(final String fName, final String username,
                          final String email, final String password){
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();
        reference = FirebaseDatabase.getInstance("https://pet-mart-19e46-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Users").child(userId);
        progressBar.setVisibility(View.VISIBLE);

        HashMap<String, String> hashMap = new HashMap<>();
        putDataOnHash(hashMap, fName, email, userId, username);

        reference.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RegisterActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, EditProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: "+e.toString());
            }
        });

    }

    private void putDataOnHash(HashMap<String, String> hashMap, String fName, String email, String userId, String username) {
        String default_status = "Hey I'm using PetMart.";
        String default_user_status = "offline";
        String default_image = "default";
        hashMap.put("id", userId);
        hashMap.put("name", fName);
        hashMap.put("username", username);
        hashMap.put("email", email);
        hashMap.put("imageURL", default_image);
        hashMap.put("status", default_user_status);
        hashMap.put("searchable_name", username.toLowerCase());
        hashMap.put("user_about", default_status);
    }


}