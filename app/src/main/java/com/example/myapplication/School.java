package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;

public class School {
    private String address;
    private int numofstudents;
    private Bitmap bitmap;

    private int id;
    private static int idCounter = 0;
    public School(Context context) {
        this.id = idCounter++;
        this.address = "";
        this.numofstudents = 0;
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.school2);
    }


    public School(String address, int numofstudents, Bitmap bitmap) {
        this.address = address;
        this.numofstudents = numofstudents;
        this.bitmap = bitmap;
        this.id = idCounter++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumofstudents() {
        return numofstudents;
    }

    public void setNumofstudents(int numofstudents) {
        this.numofstudents = numofstudents;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public String toString() {
        return address + " " + numofstudents;
    }
}