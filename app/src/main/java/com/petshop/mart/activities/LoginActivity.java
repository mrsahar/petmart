package com.petshop.mart.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.petshop.mart.MainActivity;
import com.petshop.mart.R;
import com.petshop.mart.localdata.SharePreUserManage;
import com.petshop.mart.localdata.UserModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText username,password;
    Button btnLogin;
    SharePreUserManage sharePreUserManage;
    UserModel userModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       // getActionBar().hide();

        FirebaseAuth loginAuth = FirebaseAuth.getInstance();
        sharePreUserManage = new SharePreUserManage(this);


        username = findViewById(R.id.edt_login_username);
        password = findViewById(R.id.edt_login_password);
        btnLogin = findViewById(R.id.btn_login);
        TextView txtRegister = findViewById(R.id.gotoRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = username.getText().toString().trim();
                String userPassword= password.getText().toString().trim();
                loginAuth.signInWithEmailAndPassword(email,userPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = loginAuth.getCurrentUser();
                        sharePreUserManage.setUserData(loginAuth.getUid(),email);
                        Toast.makeText(LoginActivity.this, "User Login", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finishAffinity();
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "User Login Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        userModel = sharePreUserManage.getUser();
        if (userModel.getUID() != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finishAffinity();
        }

    }
}