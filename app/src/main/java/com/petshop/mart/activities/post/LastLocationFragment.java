package com.petshop.mart.activities.post;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.petshop.mart.R;
import com.petshop.mart.localdata.SharePreUserManage;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class LastLocationFragment extends Fragment {

    Button post;
    EditText phone_No;
    EditText location;
    String phonepattern = "^[+][0-9]{10,13}$";

    private static final String TAG = "kiki";
    //Location in english
    Geocoder geocoder;
    Map<String, Object> userAds,inUserAds;
    List<Address> addresses;
    Double liveLat, liveLong;
    int LOCATION_REQUEST_CODE = 10001;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    String userName;
    Bundle b2;



    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }
            for (Location location : locationResult.getLocations()) {
                liveLat = location.getLatitude();
                liveLong = location.getLongitude();
                try {
                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                    addresses = geocoder.getFromLocation(liveLat, liveLong, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    String address;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userAds = new HashMap<>();
        inUserAds = new HashMap<>();
        b2 = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getContext() fragment
        View v = inflater.inflate(R.layout.fragment_location, container, false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        SharePreUserManage sharePreUserManage = new SharePreUserManage(getContext());
        userName = sharePreUserManage.getUser().getUID();
        String adsId = db.collection("ads").document().getId();

//        Date date = new Date();
//        DateFormat dateFormat = (DateFormat) android.text.format.DateFormat.format("dd-mm-yyyy hh:mm:ss a", new Date());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.US);

        String time = df.format(new Date());

        phone_No= v.findViewById(R.id.txt_ads_phone);
        location=v.findViewById(R.id.txt_ads_Location);
        post=v.findViewById(R.id.post_ads);


        if(b2 != null){

            //In user
            inUserAds.put("ads_title", b2.getString("txtTitle"));
            inUserAds.put("ads_img", b2.get("txtImage"));
            inUserAds.put("ads_id", adsId);
            //over all ads
            userAds.put("ads_title", b2.getString("txtTitle"));
            userAds.put("ads_description", b2.get("txtDescription"));
            userAds.put("ads_type", b2.get("txtType"));
            userAds.put("ads_id", adsId);
            userAds.put("ads_img", b2.get("txtImage"));
            userAds.put("ads_price", b2.get("txtPrice"));
            userAds.put("ads_uid", userName);
            userAds.put("ads_category", b2.getString("category"));

        }else{
            Toast.makeText(getContext(), "Error on bundle", Toast.LENGTH_SHORT).show();
        }
        

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        checkSettingsAndStartLocationUpdates();
        ImageButton imgBtnLocation = v.findViewById(R.id.search_location);
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        imgBtnLocation.setOnClickListener(v1 -> {
            address = addresses.get(0).getAddressLine(0);
            location.setText(address);
        });



        post.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String phone= phone_No.getText().toString();

               if( !phone.matches(phonepattern)){
                    phone_No.setError("Please enter valid phone number (+92..)!");
                    return;
                } if(location.getText().toString().isEmpty()){
                    location.setError("Loaction can't be empty!");
                  return;
                }
                else{
                    userAds.put("ads_location", location.getText().toString());
                    userAds.put("ads_user_phone_no", phone_No.getText().toString());
                    userAds.put("ads_date", time);
                    db.collection("ads").document(adsId)
                            .set(userAds)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error writing document", e);
                                }
                            });
                    db.collection("users").document(userName).collection("user_ads").document(adsId)
                            .set(inUserAds)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                    Toast.makeText(getContext(), "Your ads is successfully added", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error writing document", e);
                                    Toast.makeText(getContext(), "Error posting your ads ", Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            }
        });

        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            checkSettingsAndStartLocationUpdates();
        } else {
            askLocationPermission();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    private void checkSettingsAndStartLocationUpdates() {
        LocationSettingsRequest request = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).build();
        SettingsClient client = LocationServices.getSettingsClient(getContext());

        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLocationUpdates();
            }
        });
        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException apiException = (ResolvableApiException) e;
                    try {
                        apiException.startResolutionForResult(getActivity(), 1001);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }


    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private void askLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.d(TAG, "askLocationPermission: you should show an alert dialog...");
            }
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkSettingsAndStartLocationUpdates();
            } else {
                //Permission not granted
            }
        }
    }
}
