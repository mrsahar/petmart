package com.petshop.mart;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.petshop.mart.activities.PostActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolBarTitle;
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
//        if (mAuth.getUid().equals("")||mAuth.getUid().equals(null)){
//
//        }


        setContentView(R.layout.activity_main);


        toolbar=findViewById(R.id.toolbar);
        toolBarTitle=findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        findViewById(R.id.toolbar_back).setVisibility(View.GONE);

        // load the store fragment by default
        toolBarTitle.setText("Pet Mart");


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelected);
        loadFragment(new HomeFragment());
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelected = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.home:
                    toolBarTitle.setText("Pet Mart");
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.chat:
                    toolBarTitle.setText("Pet Mart Chats");
                    fragment = new ChatFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.add:
                    toolBarTitle.setText("Add");
                    startActivity(new Intent(MainActivity.this, PostActivity.class));
                    return false;
                case R.id.vet:
                    toolBarTitle.setText("Vet");
                    fragment = new VetFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.profile:
                    toolBarTitle.setText("Profile");
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
            }

            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void Status(){
        reference = FirebaseDatabase.getInstance("https://pet-mart-19e46-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Users").child(firebaseUser.getUid());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", "online");
        reference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Status();
    }
}