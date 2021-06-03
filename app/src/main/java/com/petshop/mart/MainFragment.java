package com.petshop.mart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.petshop.mart.utility.RVAdapterCategory;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private static final String TAG = "kiki";
    RecyclerView rvMainCategory;
    List<String> liveCategory;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        rvMainCategory = v.findViewById(R.id.rv_category);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        rvMainCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));


        liveCategory = new ArrayList<>();



        db.collection("category").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot d: queryDocumentSnapshots){
                    liveCategory.add(d.getId());
                    Log.d(TAG, "onSuccess: "+liveCategory);
                }
                Log.d(TAG, "going to rv: "+liveCategory);
                rvMainCategory.hasFixedSize();
                rvMainCategory.setAdapter(new RVAdapterCategory(liveCategory));
            }
        });




        return v;

    }
}