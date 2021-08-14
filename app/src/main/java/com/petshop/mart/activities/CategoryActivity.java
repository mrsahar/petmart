package com.petshop.mart.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.petshop.mart.R;
import com.petshop.mart.localdata.PostModel;
import com.petshop.mart.utility.GridAdapterCategory;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolBarTitle;
    RecyclerView postRecyclerView;
    ArrayList<PostModel> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Bundle b = getIntent().getExtras();
        String value = b.getString("category");

        toolbar=findViewById(R.id.toolbar);
        toolBarTitle=findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        findViewById(R.id.toolbar_back).setOnClickListener(v -> onBackPressed());

        // load the store fragment by default
        toolBarTitle.setText(value);

        //main post
        postRecyclerView = findViewById(R.id.rc_category);
        postRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2, LinearLayoutManager.VERTICAL,false));
        postList = new ArrayList<>();
        db.collection("ads").whereEqualTo("ads_category",value).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot d: queryDocumentSnapshots){
                    postList.add(new PostModel(d.get("ads_img").toString(),
                            d.get("ads_title").toString(),d.get("ads_price").toString(),d.get("ads_id").toString()));

                }
                postRecyclerView.hasFixedSize();
                postRecyclerView.setAdapter(new GridAdapterCategory(postList,getApplicationContext()));
            }
        });
    }
}