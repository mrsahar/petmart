package com.petshop.mart.activities;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.firestore.FirebaseFirestore;
import com.petshop.mart.R;
import com.petshop.mart.activities.post.FirstCategoryFragment;

import java.util.List;

public class AddActivity extends AppCompatActivity {

    private static final String TAG = "kiki";

    EditText txtTitle, txtDescription, txtPrice, txtPhone_no, txtLocation, txtAnimal, txtFish, txtBird;
    ImageButton search;
    Button btnNext0, btnNext1, btnNext3, btnNext2;
    FrameLayout frame0, frame1, frame2, frame3;
    ImageView userImgUpload;
    ProgressBar progressBar;
    String getCategoryName;
    AppCompatSpinner spinnerTypeCategory;
    List<String> categoryData;
    String title, description, category;
    int price;
    Double phoneNo;
    FirebaseFirestore db;
    Uri uri;
    static Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        FragmentManager fm = getSupportFragmentManager();
        FirstCategoryFragment cf = new FirstCategoryFragment();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.main_frame,cf).commit();

         b = new Bundle();


//
//        //fireStore
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReference();
//        db = FirebaseFirestore.getInstance();
//
//        // Casting
//        userImgUpload = (ImageView) findViewById(R.id.ads_img);
//        txtTitle = findViewById(R.id.txt_ads_title);
//        txtDescription = findViewById(R.id.txt_ads_description);
//        txtPrice = findViewById(R.id.txt_ads_price);
//        txtPhone_no = findViewById(R.id.txt_ads_phone);
//        txtLocation = findViewById(R.id.txt_ads_Location);
//        search = findViewById(R.id.search_location);
//        btnNext1 = findViewById(R.id.next_1);
//        btnNext2 = findViewById(R.id.next_2);
//        btnNext3 = findViewById(R.id.next_3);
//        frame0 = findViewById(R.id.frame_0);
//        frame1 = findViewById(R.id.frame_1);
//        frame2 = findViewById(R.id.frame_2);
//        frame3 = findViewById(R.id.frame_3);
//        progressBar = findViewById(R.id.progressBar2);
//        spinnerTypeCategory = findViewById(R.id.spinner_type_category);
//
//        //saving data in firebase on a click
//        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
//        String rationale = "Please provide location permission so that you can ...";
//        Permissions.Options options = new Permissions.Options()
//                .setRationaleDialogTitle("Info")
//                .setSettingsDialogTitle("Warning");
//        Permissions.check(this/*context*/, permissions, rationale, options, new PermissionHandler() {
//            @Override
//            public void onGranted() {
//                // do your task.
//            }
//
//            @Override
//            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
//                Toast.makeText(AddActivity.this, "Please provide the permission", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        //Database live and local
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        SharePreUserManage sharePreUserManage = new SharePreUserManage(this);
//        String adsId = db.collection("ads").document().getId();
//
//        //Map for data to Send
//        HashMap<String, Object> userAds = new HashMap<>();
//
//        List<Double> location = new ArrayList();
//        location.add(0, -31546273D);
//        location.add(1, 71546273D);
//
//        userAds.put("ads_title", "Golden Fish");
//        userAds.put("ads_description", "this is my fish and i love it and i don't me that i want to live with her i just want to eat her");
//        userAds.put("ads_id", adsId);
//        userAds.put("ads_img", adsId);
//        userAds.put("ads_price", "3000");
//        userAds.put("ads_uid", sharePreUserManage.getUser().getUID());
//        userAds.put("ads_category", "Fish");
//        userAds.put("ads_location", location);
//        userAds.put("ads_user_phone_no", 9234567893D);

        //0 show category
//        btnNext0.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

//                frame0.setVisibility(View.GONE);
//                frame1.setVisibility(View.VISIBLE);
//            }
//        });

        //1 show types title and description and price
//
//        categoryData = new ArrayList<>();
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (categoryData == null) {
//                    handler.postDelayed(this, 1000);
//                } else {
//                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(AddActivity.this, android.R.layout.simple_list_item_1, categoryData);
//                    spinnerTypeCategory.setAdapter(spinnerAdapter);
//
//                }
//
//            }
//        }, 1000);

//        btnNext1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                title = txtTitle.getText().toString();
//                description = txtDescription.getText().toString();
//                price = Integer.parseInt(txtDescription.getText().toString());
//                frame1.setVisibility(View.GONE);
//                frame2.setVisibility(View.VISIBLE);
//            }
//        });

        //2 show upload pic
//        btnNext2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressBar.setVisibility(View.VISIBLE);
//                UploadTask uploadTask = storageRef.child(adsId).child(uri.getLastPathSegment()).putFile(uri);
//                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        frame2.setVisibility(View.GONE);
//                        frame3.setVisibility(View.VISIBLE);
//                        Toast.makeText(AddActivity.this, "Thi Giywa Wayyy", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                        progressBar.setProgress((int) progress);
//                        if (progress == 100) {
//                            progressBar.setVisibility(View.GONE);
//                        }
//
//                    }
//                });
//
//            }
//        });
//
//        //3 show location and phone
//        btnNext3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        //Listener that Send data to user Ads
//        db.collection("ads").document(adsId).set(userAds).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Void newDr = task.getResult();
//                    Log.d(TAG, "onComplete: isSuccessful " + newDr);
//                } else {
//                    Log.d(TAG, "onComplete: " + task.getException());
//                }
//            }
//        });

//        userImgUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent();
//                i.setType("image/*");
//                i.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(i, "Complete action using"), 1);
//
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 1 && data != null) {
//            uri = Uri.fromFile(new File(data.getData().getPath()));
//        } else {
//            Toast.makeText(this, "kafa shafa ni tivra", Toast.LENGTH_SHORT).show();
//        }
//    }

//    @Override
//    protected void onStart() {
//        frame0.setVisibility(View.VISIBLE);
//        frame1.setVisibility(View.GONE);
//        frame2.setVisibility(View.GONE);
//        frame3.setVisibility(View.GONE);
//        progressBar.setVisibility(View.GONE);
//        super.onStart();
//    }
//
//    public void animal(View view) {
//        getCategoryName = "animal";
//        frame0.setVisibility(View.GONE);
//        frame1.setVisibility(View.VISIBLE);
//        typesOfCategory(getCategoryName);
//    }
//
//    public void fish(View view) {
//        getCategoryName = "fish";
//        frame0.setVisibility(View.GONE);
//        frame1.setVisibility(View.VISIBLE);
//        typesOfCategory(getCategoryName);
//    }
//
//    public void bird(View view) {
//        getCategoryName = "bird";
//        frame0.setVisibility(View.GONE);
//        frame1.setVisibility(View.VISIBLE);
//        typesOfCategory(getCategoryName);
//    }
//
//    private void typesOfCategory(String CategoryName) {
//        DocumentReference docRef = db.collection("category").document(CategoryName);
//        docRef.get().addOnSuccessListener(documentSnapshot -> {
//            Map<String, Object> s = documentSnapshot.getData();
//            Set keys = s.keySet();
//            for (Object key : keys) {
//                categoryData.add(s.get(key).toString());
//                Log.d(TAG, "onSuccess: " + s.get(key));
//            }
//        });
//    }

}
}