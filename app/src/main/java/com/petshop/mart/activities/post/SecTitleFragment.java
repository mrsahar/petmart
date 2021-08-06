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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.petshop.mart.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


public class SecTitleFragment extends Fragment {

    private static final String TAG = "kiki";
    String typeCategory = null;
    FirebaseFirestore db;
    ArrayList<String> categoryData;
    ArrayAdapter<String> spinnerAdapter;
    Bundle b;
  //  int minPrice = 50;
    //int price ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().getString("category") != null) {
            typeCategory = getArguments().getString("category");
        } else {
            Toast.makeText(getContext(), "Please Select Category", Toast.LENGTH_SHORT).show();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FirstCategoryFragment cf = new FirstCategoryFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.main_frame, cf).commit();
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
        View v = inflater.inflate(R.layout.fragment_title, container, false);

        b = this.getArguments();
        if (b != null) {

        }
        Spinner spinner = v.findViewById(R.id.spinner_type_category);
        EditText txtTitle = v.findViewById(R.id.txt_ads_title);
        EditText txtDescription = v.findViewById(R.id.txt_ads_description);
        EditText txtPrice = v.findViewById(R.id.txt_ads_price);

        Log.d(TAG, "onCreateView: Running");
        DocumentReference docRef = db.collection("category").document(typeCategory);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            Map<String, Object> s = documentSnapshot.getData();
            Set keys = s.keySet();
            for (Object key : keys) {
                categoryData.add(s.get(key).toString());
            }
            if (categoryData != null || categoryData.size() != 0) {
                spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, categoryData);
                spinner.setAdapter(spinnerAdapter);
            } else {
                Toast.makeText(getContext(), "Please Select Category", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onCreateView: not running");
            }
        });


        Button btnNext = v.findViewById(R.id.sectitle_next);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtTitle.getText().toString().length() < 5) {
                    txtTitle.setError("A minimum length of 5 character is required.");
                    return;
                }else if(txtDescription.getText().toString().length()<20){
                    txtDescription.setError("A minimum length of 20 character is required.");
                    return;
                } if (txtPrice.getText().toString().isEmpty() ){
                    txtPrice.setError("Value should be atleast 50.");
                    return;
                }
                else {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    ThirdImageLoadingFragment cf = new ThirdImageLoadingFragment();
                    b.putString("txtType", spinner.getSelectedItem().toString());
                    b.putString("txtTitle", txtTitle.getText().toString());
                    b.putString("txtPrice",txtPrice.getText().toString());
                    b.putString("txtDescription", txtDescription.getText().toString());
                    FragmentTransaction ft = fm.beginTransaction();
                    cf.setArguments(b);
                    ft.replace(R.id.main_frame, cf).commit();
                }
            }

        });


        return v;
    }
}