package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SchoolArrayAdapter extends ArrayAdapter<School> {

    public SchoolArrayAdapter(Context context, ArrayList<School> schools) {
        super(context, 0, schools);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_school_item, parent, false);
        }

        School school = getItem(position);

        TextView addressTextView = convertView.findViewById(R.id.address_text_view);
        addressTextView.setText(school.getAddress());

        TextView numOfStudentsTextView = convertView.findViewById(R.id.num_of_students_text_view);
        numOfStudentsTextView.setText(school.getNumofstudents() + " students");

        ImageView imageView = convertView.findViewById(R.id.image_view);
        imageView.setImageBitmap(school.getBitmap());

        return convertView;
    }
}