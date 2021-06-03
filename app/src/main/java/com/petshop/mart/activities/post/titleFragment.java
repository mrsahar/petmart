package com.petshop.mart.activities.post;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.petshop.mart.R;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


public class titleFragment extends Fragment {

    private static final String TAG = "kiki";
    String typeCategory = null;
    FirebaseFirestore db;
    ArrayList<String> categoryData;
    ArrayAdapter<String> spinnerAdapter;

    public titleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments().getString("category") != null){
            typeCategory = getArguments().getString("category");
        }else{
            Toast.makeText(getContext(), "Please Select Category", Toast.LENGTH_SHORT).show();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            CategoryFragment cf = new CategoryFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.main_frame,cf).commit();
            typeCategory = "fish";
        }
        categoryData = new ArrayList<>();

        //fireStore
        FirebaseStorage storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_title, container, false);
        Spinner spinner = v.findViewById(R.id.spinner_type_category);

        Log.d(TAG, "onCreateView: Running");
        DocumentReference docRef = db.collection("category").document(typeCategory);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            Map<String, Object> s = documentSnapshot.getData();
            Set keys = s.keySet();
            for (Object key : keys) {
                categoryData.add(s.get(key).toString());
            }
            if(categoryData != null || categoryData.size() != 0){
                spinnerAdapter= new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, categoryData);
                spinner.setAdapter(spinnerAdapter);
            }else{
                Toast.makeText(getContext(), "Please Select Category", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onCreateView: not running");
            }
        });


        Button btnNext = v.findViewById(R.id.next_1);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                imageloadingFragment cf = new imageloadingFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.main_frame,cf).commit();
            }
        });


        return v;
    }
}