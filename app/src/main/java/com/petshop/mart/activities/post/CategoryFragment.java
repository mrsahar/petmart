package com.petshop.mart.activities.post;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petshop.mart.R;

public class CategoryFragment extends Fragment {
    String category = null;

    public CategoryFragment() {
        // Required empty public constructor
    }

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
            Bundle bundle = new Bundle();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            titleFragment tf = new titleFragment();
            bundle.putString("category",category);
            tf.setArguments(bundle);
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.main_frame,tf).commit();
        });
        return v;
    }
}