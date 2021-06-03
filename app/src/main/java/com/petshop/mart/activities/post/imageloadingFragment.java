package com.petshop.mart.activities.post;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.petshop.mart.R;
import com.petshop.mart.activities.AddActivity;
import com.petshop.mart.localdata.SharePreUserManage;

import java.io.File;


public class imageloadingFragment extends Fragment {

    Uri uri;
    ImageView adsImg;
    Boolean isUploaded = false;


    public imageloadingFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_imageloading, container, false);
        //fireStore
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        // Database live and local
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        SharePreUserManage sharePreUserManage = new SharePreUserManage(getContext());
        String adsId = db.collection("ads").document().getId();


        Button btnUploadAdsImage;

        adsImg = v.findViewById(R.id.ads_img);
        btnUploadAdsImage = v.findViewById(R.id.btn_upload_ads);

        adsImg.setOnClickListener(v1 -> {
            Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Complete action using"), 1);
        });


        btnUploadAdsImage.setOnClickListener(v2 ->{
            if(isUploaded){
                UploadTask uploadTask = storageRef.child(adsId).child(uri.getLastPathSegment()).putFile(uri);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        locationFragment cf = new locationFragment();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.main_frame,cf).commit();
                        Toast.makeText(getContext(), "Thi Giywa Wayyy", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Something went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                //TODO fragment need to remove from here
                FragmentManager fm = getActivity().getSupportFragmentManager();
                locationFragment cf = new locationFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.main_frame,cf).commit();
                Toast.makeText(getContext(), "Please Select Image first", Toast.LENGTH_SHORT).show();
            }
        });


        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            isUploaded  = true;
            uri = Uri.fromFile(new File(data.getData().getPath()));
            adsImg.setImageURI(uri);
        } else {
            Toast.makeText(getContext(), "kafa shafa ni tivra", Toast.LENGTH_SHORT).show();
        }
    }
}