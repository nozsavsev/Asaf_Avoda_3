package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_SCHOOL_REQUEST_CODE = 1;
    private static final int EDIT_SCHOOL_REQUEST_CODE = 2;

    private ListView listView;
    private ArrayList<School> schools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);
        schools = new ArrayList<>();
        schools.add(new School("123 Main St", 100, BitmapFactory.decodeResource(getResources(), R.drawable.school1)));
        schools.add(new School("456 Elm St", 200, BitmapFactory.decodeResource(getResources(), R.drawable.school2)));
        schools.add(new School("789 Oak St", 300, BitmapFactory.decodeResource(getResources(), R.drawable.school2)));
        SchoolArrayAdapter adapter = new SchoolArrayAdapter(this, schools);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected School object
                School school = (School) parent.getItemAtPosition(position);

                // Create a new AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure you want to delete this school?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // User confirmed deletion, remove the selected item from the adapter
                                adapter.remove(school);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                // Return true to indicate that the event has been consumed
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected School object
                School school = (School) parent.getItemAtPosition(position);

                // Create a new Intent to start the EditActivity
                Intent intent = new Intent(MainActivity.this, editSchool.class);
                intent.putExtra("address", school.getAddress());
                intent.putExtra("numOfStudents", school.getNumofstudents());
                intent.putExtra("bitmap", Bitmap.createScaledBitmap(school.getBitmap(),300,300,false));
                startActivityForResult(intent, EDIT_SCHOOL_REQUEST_CODE);
            }
        });

        Button addSchoolButton = findViewById(R.id.add_button);
        addSchoolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddSchool.class);
                startActivityForResult(intent, ADD_SCHOOL_REQUEST_CODE);
            }
        });
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }







    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == ADD_SCHOOL_REQUEST_CODE && resultCode == RESULT_OK) {
            School school = new School(this);

            school.setAddress(data.getStringExtra("address"));
            school.setNumofstudents(data.getIntExtra("numOfStudents", 0));
            Bitmap bitmap = decodeBase64(data.getStringExtra("bitmap"));
            school.setBitmap(bitmap);

            schools.add(school);
            SchoolArrayAdapter adapter = (SchoolArrayAdapter) listView.getAdapter();
            adapter.notifyDataSetChanged();
        }
        else if (requestCode == EDIT_SCHOOL_REQUEST_CODE && resultCode == RESULT_OK) {

            School school = new School(this);

            school.setAddress(data.getStringExtra("address"));
            school.setNumofstudents(data.getIntExtra("numOfStudents", 0));
            Bitmap bitmap = decodeBase64(data.getStringExtra("bitmap"));
            school.setBitmap(bitmap);


            //replace school with same id
            for (int i = 0; i < schools.size(); i++) {
                if (schools.get(i).getAddress().equals(school.getAddress())) {
                    schools.set(i, school);
                    break;
                }
            }

            SchoolArrayAdapter adapter = (SchoolArrayAdapter) listView.getAdapter();
            adapter.notifyDataSetChanged();
        }
    }
}


