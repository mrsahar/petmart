package com.petshop.mart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.petshop.mart.activities.EditProfileActivity;
import com.petshop.mart.activities.LoginActivity;
import com.petshop.mart.localdata.PostModel;
import com.petshop.mart.localdata.SharePreUserManage;
import com.petshop.mart.utility.GridProflieAdapterCategory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    RecyclerView postRecyclerView;
    ArrayList<PostModel> postList;
    SharePreUserManage sharePreUserManage;
    TextView profileName,userAdsCounts,btnMenu;
    int adsCount,wishList = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        FloatingActionButton btnEdit =  v.findViewById(R.id.btn_edit);
        profileName =  v.findViewById(R.id.profile_name);
        userAdsCounts =  v.findViewById(R.id.user_ads_counts);
        btnMenu =  v.findViewById(R.id.btn_menu);
        sharePreUserManage = new SharePreUserManage(getContext());


        Bundle b = new Bundle();
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),EditProfileActivity.class);
                b.putBoolean("comingFromProfile",true);
                i.putExtras(b);
                startActivity(i);
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getContext(), btnMenu);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_edit:
                                Intent i = new Intent(getContext(),EditProfileActivity.class);
                                b.putBoolean("comingFromProfile",true);
                                i.putExtras(b);
                                startActivity(i);
                                break;
                            case R.id.menu_logout:
                                sharePreUserManage.setUserLogoff();
                                startActivity(new Intent(getContext(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        SharePreUserManage sharePreUserManage = new SharePreUserManage(getContext());

        ImageView profileImg = v.findViewById(R.id.main_profile_img);
        try {
        db.collection("users").document(sharePreUserManage.getUser().getUID()).collection("user_ads")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (DocumentSnapshot document : task.getResult()) {
                                adsCount++;
                            }
                            userAdsCounts.setText(adsCount+"");
                        }
                    }
                });
        db.collection("users").document(sharePreUserManage.getUser().getUID())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            String img =  task.getResult().get("user_Image").toString();
                            Picasso.get().load(img).into(profileImg);
                            profileName.setText(task.getResult().get("username").toString());

                        }else {
                            Toast.makeText(getContext(), "Something wet wrong", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        }catch (Exception e){
            Toast.makeText(getContext(), "Something wet wrong", Toast.LENGTH_SHORT).show();
            Log.d("kiki", "onCreateView: "+e.toString());
        }
        //main post
        postRecyclerView = v.findViewById(R.id.rv_main_profile_post);
        postRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3, LinearLayoutManager.VERTICAL,false));
        postList = new ArrayList<>();
        db.collection("users").document(sharePreUserManage.getUser().getUID()).collection("user_ads").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot d: queryDocumentSnapshots){
                    postList.add(new PostModel(d.get("ads_img").toString(),
                            d.get("ads_title").toString(),d.get("ads_price").toString(),d.get("ads_id").toString()));
                }
                postRecyclerView.hasFixedSize();
                postRecyclerView.setAdapter(new GridProflieAdapterCategory(postList,getContext()));
            }
        });
        return v;
    }
}