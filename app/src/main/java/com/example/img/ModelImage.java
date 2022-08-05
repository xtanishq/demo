package com.example.img;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


public class ModelImage {
    private  String id,imageUrl;

    public ModelImage() {
    }

    public ModelImage(String id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
