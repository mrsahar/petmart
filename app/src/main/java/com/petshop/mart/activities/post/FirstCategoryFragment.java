package com.petshop.mart.activities.post;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.petshop.mart.R;

public class FirstCategoryFragment extends Fragment {
    String category = null;
    Bundle b = new Bundle();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_category, container, false);

        CardView cvAnimal = v.findViewById(R.id.cv_animals);

        cvAnimal.setOnClickListener(v1 -> {
            category = "Animal";
            runFragment();
        });

        return v;
    }

    public void runFragment(){
        if (category != null ){
            FragmentManager fm = getActivity().getSupportFragmentManager();
            SecTitleFragment tf = new SecTitleFragment();
            b.putString("category",category);
            tf.setArguments(b);
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.main_frame,tf).commit();
        }else{
            Toast.makeText(getContext(), "Please select category", Toast.LENGTH_SHORT).show();
        }
    }
}