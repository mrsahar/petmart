package com.petshop.mart.activities.post;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

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
import com.petshop.mart.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class locationFragment extends Fragment {

    private static final String TAG = "kiki";
    //Location in english
    Geocoder geocoder;
    List<Address> addresses;

    Double liveLat, liveLong;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    String address;



    public locationFragment() {
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
        View v = inflater.inflate(R.layout.fragment_location, container, false);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        ImageButton imgBtnLocation = v.findViewById(R.id.search_location);
        geocoder = new Geocoder(getContext(), Locale.getDefault());
        addresses = new ArrayList<>();

         address = "none";
        try {
            if (addresses.size() > 0){
                addresses = geocoder.getFromLocation(liveLat, liveLong, 1);
                address = addresses.get(0).getAddressLine(0);}
            else{
                Log.d(TAG, "not running Fuis: ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        imgBtnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                }
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener((Activity) getContext(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        liveLat = location.getLatitude();
                        liveLong = location.getLongitude();
                    }
                });
                checkSettingAndStartLocationUpdate();


                try {
                    if (addresses.size() > 0){
                        addresses = geocoder.getFromLocation(liveLat, liveLong, 1);
                        address = addresses.get(0).getAddressLine(0);}
                    else{
                        Log.d(TAG, "not running Fuis: ");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("kiki", "onClick: "+address);
            }
        });


        return v;
    }

    //Checking for Location Setting
    private void checkSettingAndStartLocationUpdate() {
        LocationSettingsRequest request = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).build();
        SettingsClient client = LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> locationSettingsRequestTask = client.checkLocationSettings(request);
        locationSettingsRequestTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
             // location code here

            }
        });
        locationSettingsRequestTask.addOnFailureListener(new OnFailureListener() {
            @Override

            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException apiException = (ResolvableApiException) e;
                    try {
                        apiException.startResolutionForResult(getActivity(), 1005);

                    } catch (
                            IntentSender.SendIntentException sendIntentException) {
                        sendIntentException.printStackTrace();

                    }
                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        checkSettingAndStartLocationUpdate();
    }
}