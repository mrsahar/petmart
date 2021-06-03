package com.petshop.mart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.petshop.mart.activities.AddActivity;
import com.petshop.mart.activities.EditProfileActivity;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelected);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelected  = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.home:
                    selectedFragment = new MainFragment();
                    break;
                case R.id.chat:
                    selectedFragment = new ChatFragment();
                    break;
                case R.id.add:
                    startActivity(new Intent(MainActivity.this, AddActivity.class));
                    selectedFragment = null;
                    break;
                case R.id.vet:
                    selectedFragment = new VetFragment();
                    break;
                case R.id.profile:
                    selectedFragment = new ProfileFragment();
                    break;
            }
            if (selectedFragment != null)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new MainFragment()).commit();
    }
}