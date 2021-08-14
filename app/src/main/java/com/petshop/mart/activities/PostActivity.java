package com.petshop.mart.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.petshop.mart.R;
import com.petshop.mart.activities.post.FirstCategoryFragment;

public class PostActivity extends AppCompatActivity {


    static Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        FragmentManager fm = getSupportFragmentManager();
        FirstCategoryFragment cf = new FirstCategoryFragment();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.main_frame, cf).commit();

        b = new Bundle();
        ImageView backbutton = findViewById(R.id.toolbar_back);
        backbutton.setOnClickListener(v -> onBackPressed()
        );
    }
}