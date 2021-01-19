package com.example.android.inventoryAPP.data;

import android.graphics.Bitmap;

public class ImageModel {
    private static Bitmap image;

    public ImageModel(Bitmap image) {
        this.image = image;
    }

    public static Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
