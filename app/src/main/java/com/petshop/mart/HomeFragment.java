package com.petshop.mart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.petshop.mart.localdata.PostModel;
import com.petshop.mart.utility.GridAdapterCategory;
import com.petshop.mart.utility.RVAdapterCategory;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String TAG = "kiki";
    RecyclerView rvMainCategory;
    List<String> liveCategory;

    RecyclerView postRecyclerView;
    ArrayList<PostModel> postList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        //top category
        rvMainCategory = v.findViewById(R.id.rv_category);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        rvMainCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        liveCategory = new ArrayList<>();

        //main post
        postRecyclerView = v.findViewById(R.id.rv_main_post);
        postRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2, LinearLayoutManager.VERTICAL,false));
        postList = new ArrayList<>();

        db.collection("category").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot d: queryDocumentSnapshots){
                    liveCategory.add(d.getId());
                }
                rvMainCategory.hasFixedSize();
                rvMainCategory.setAdapter(new RVAdapterCategory(liveCategory,getContext()));
            }
        });

        db.collection("ads").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot d: queryDocumentSnapshots){
                    postList.add(new PostModel(d.get("ads_img").toString(),
                            d.get("ads_title").toString(),d.get("ads_price").toString(),d.get("ads_id").toString()));
                    Log.d(TAG, "onSuccess: "+liveCategory);
                }
                Log.d(TAG, "going to rv: "+liveCategory);
                postRecyclerView.hasFixedSize();
                postRecyclerView.setAdapter(new GridAdapterCategory(postList,getContext()));
            }
        });
        return v;
    }
}