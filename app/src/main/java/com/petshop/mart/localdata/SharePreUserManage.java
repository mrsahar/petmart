package com.petshop.mart.localdata;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharePreUserManage {

    private static final String sharePreUsername = "UserDetail";
    private static final String TAG = "kiki";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;


    public SharePreUserManage(Context context) {
        this.context = context;
    }

    public void setUserData(String UID,String email){
        sharedPreferences= context.getSharedPreferences(sharePreUsername,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString("email",email);
        editor.putString("UID",UID);
        editor.putBoolean("isLoggedIn",true);
        editor.apply();
        Log.d(TAG, "setUserData: UserAdded.");
    }

    public UserModel getUser(){
        sharedPreferences= context.getSharedPreferences(sharePreUsername,Context.MODE_PRIVATE);

        return new UserModel(sharedPreferences.getString("username",null),
                sharedPreferences.getString("email",null),
                sharedPreferences.getString("UID",null));
    }

    public void setUserLogoff(){
        sharedPreferences= context.getSharedPreferences(sharePreUsername,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}