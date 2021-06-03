package com.petshop.mart.localdata;

public class UserModel {

    String username,email,address,phoneNo,registerTime,profile,UID;
    Boolean IsActive;

    public UserModel() {
    }

    public UserModel(String username, String email, String UID) {
        this.username = username;
        this.email = email;
        this.UID = UID;
    }

    public UserModel(String username, String email, String address, String phoneNo, String registerTime, String profile, Boolean isActive, String UID) {
        this.username = username;
        this.email = email;
        this.address = address;
        this.phoneNo = phoneNo;
        this.registerTime = registerTime;
        this.profile = profile;
        this.UID = UID;
        IsActive = isActive;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Boolean getActive() {
        return IsActive;
    }

    public void setActive(Boolean active) {
        IsActive = active;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
