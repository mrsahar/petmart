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

import java.util.ArrayList;

public class VetFragment extends Fragment {


    private static final String TAG = "kiki";
    RecyclerView vetRecyclerView;
    ArrayList<PostModel> vetList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_vet, container, false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //main post
        try {
            if (db.getFirestoreSettings() == null) {
                vetRecyclerView = v.findViewById(R.id.rv_main_post);
                vetRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
                vetList = new ArrayList<>();
                db.collection("vet").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot d : queryDocumentSnapshots) {
                            vetList.add(new PostModel(d.get("ads_img").toString(),
                                    d.get("ads_title").toString(), d.get("ads_price").toString(),d.get("ads_id").toString()));

                        }
                        Log.d(TAG, "going to rv: " + vetList);
                        vetRecyclerView.hasFixedSize();
                        vetRecyclerView.setAdapter(new GridAdapterCategory(vetList,getContext()));
                    }
                });
            }
        }catch (Exception e){
            Log.d(TAG, "onCreateView: "+e.toString());
        }


        return v;

    }
}