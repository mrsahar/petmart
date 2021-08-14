package com.petshop.mart.activities;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.petshop.mart.R;
import com.petshop.mart.chat.MessageActivity;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView postImage,postBack;
    TextView postTitle,postPrice,postDescription,postEmail,postPhone,postType,postDate, postLocation,postCategory,postCall,postChat;
    String number,userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Bundle b = getIntent().getExtras();
        String value = b.getString("ads_id");
        postImage = findViewById(R.id.post_image);
        postTitle = findViewById(R.id.post_title);
        postPrice = findViewById(R.id.post_price);
        postDescription = findViewById(R.id.post_description);
        postType = findViewById(R.id.post_Type);
        postDate = findViewById(R.id.post_date_added);
        postEmail = findViewById(R.id.post_email);
        postPhone = findViewById(R.id.post_phone_number);
        postBack = findViewById(R.id.post_back);
        postLocation = findViewById(R.id.post_location);
        postCategory = findViewById(R.id.post_category);
        postCall = findViewById(R.id.post_call);
        postChat = findViewById(R.id.post_chat);

        db.collection("ads").document(value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        userId = task.getResult().get("ads_uid").toString();
                        number=task.getResult().get("ads_user_phone_no").toString();
                        String img =  task.getResult().get("ads_img").toString();
                        Picasso.get().load(img).into(postImage);
                        postTitle.setText(task.getResult().get("ads_title").toString());
                        postPrice.setText("Rs "+task.getResult().get("ads_price").toString());
                        postDescription.setText(task.getResult().get("ads_description").toString());
                        postType.setText(task.getResult().get("ads_type").toString());
                        postDate.setText(task.getResult().get("ads_date").toString());
                        postEmail.setText(task.getResult().get("ads_title").toString()+"@gmail.com");
                        postPhone.setText(number);
                        postLocation.setText(task.getResult().get("ads_location").toString());
                        postCategory.setText(task.getResult().get("ads_category").toString());
                    }
                });

        postCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               callOnPhone(number);
            }
        });
        postBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        postChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
    }

    private void callOnPhone(String number) {
        Permissions.check(this/*context*/, Manifest.permission.CALL_PHONE, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+number));
                startActivity(callIntent);
            }
        });
    }
}