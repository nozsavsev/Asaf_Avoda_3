package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class editSchool extends AppCompatActivity {
    private School school;
    private EditText editTextAddress;
    private EditText editTextNumStudents;
    private ImageView imageView;

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 90, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_school);


String address = getIntent().getStringExtra("address");
 int numOfStudents = getIntent().getIntExtra("numOfStudents", 0);
Bitmap bitmap = getIntent().getParcelableExtra("bitmap");

        school = new School(address, numOfStudents, bitmap);

        // Get references to the edit controls
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextNumStudents = findViewById(R.id.editTextNumStudents);
        imageView = findViewById(R.id.imageView);

        // Populate the edit controls with the School properties
        editTextAddress.setText(school.getAddress());
        editTextNumStudents.setText(String.valueOf(school.getNumofstudents()));
        imageView.setImageBitmap(school.getBitmap());

        // Set a click listener for the save button
        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update the School object with the new values
                school.setAddress(editTextAddress.getText().toString());
                school.setNumofstudents(Integer.parseInt(editTextNumStudents.getText().toString()));

                ImageView imageView = findViewById(R.id.imageView);
                Bitmap bitmap = Bitmap.createBitmap(imageView.getWidth(), imageView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                imageView.draw(canvas);

                school.setBitmap(Bitmap.createScaledBitmap(bitmap, 500, 300, false));

                //use intent to pass the school object back to the main activity
                getIntent().putExtra("address", school.getAddress());
                getIntent().putExtra("numOfStudents", school.getNumofstudents());
                getIntent().putExtra("bitmap", encodeTobase64(school.getBitmap()));
                setResult(RESULT_OK, getIntent());
                finish();
            }
        });
    }


    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int MAKE_IMAGE_REQUEST = 2;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView logo = (ImageView) findViewById(R.id.imageView);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri mImageUri = data.getData();
            logo.setImageURI(mImageUri);
        } else if (requestCode == MAKE_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            logo.setImageBitmap(imageBitmap);
        }
    }


    public void changeImage(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change image");
        builder.setMessage("You want to make a photo or pick a file?");

        builder.setPositiveButton("Pick file", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        builder.setNegativeButton("make photo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //make a photo from camera
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, MAKE_IMAGE_REQUEST);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}