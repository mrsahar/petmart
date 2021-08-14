package com.petshop.mart.localdata;

public class PostModel {
    private String postImage,postName,postPrice,postId;

    public PostModel(String postImage, String postName, String postPrice, String postId) {
        this.postImage = postImage;
        this.postName = postName;
        this.postPrice = postPrice;
        this.postId = postId;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostPrice() {
        return postPrice;
    }

    public void setPostPrice(String postPrice) {
        this.postPrice = postPrice;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
