package com.petshop.mart.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.petshop.mart.R;
import com.petshop.mart.localdata.SharePreUserManage;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    SharePreUserManage sharePreUserManage;
    EditText userName,userEmail,userPassword;
    Button btnSignUp;
    private static final String TAG = "kiki";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        sharePreUserManage = new SharePreUserManage(this);

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
                                           Toast.makeText(RegisterActivity.this, "You Are Register now", Toast.LENGTH_SHORT).show();
                                           startActivity(new Intent(RegisterActivity.this, EditProfileActivity.class));

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
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
    }

    private Boolean validation() {
        return true;
    }


}