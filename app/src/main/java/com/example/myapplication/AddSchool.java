package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class AddSchool extends AppCompatActivity {

    private EditText addressEditText;
    private EditText numOfStudentsEditText;

    private byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 90, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_school);

        addressEditText = findViewById(R.id.address_edit_text);
        numOfStudentsEditText = findViewById(R.id.num_of_students_edit_text);

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String address = addressEditText.getText().toString();
                int numOfStudents = Integer.parseInt(numOfStudentsEditText.getText().toString());


                // Get the ImageView
                ImageView imageView = findViewById(R.id.imageView);
                Bitmap bitmap = Bitmap.createBitmap(imageView.getWidth(), imageView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                imageView.draw(canvas);

                School school = new School(address, numOfStudents, bitmap);
                Intent intent = new Intent();
                intent.putExtra("address", school.getAddress());
                intent.putExtra("numOfStudents", school.getNumofstudents());

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 500, 300, true);



                intent.putExtra("bitmap", encodeTobase64(scaledBitmap));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}