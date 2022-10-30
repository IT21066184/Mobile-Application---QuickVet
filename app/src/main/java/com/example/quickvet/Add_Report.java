package com.example.quickvet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quickvet.Data.Doctor;
import com.example.quickvet.Data.Report;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;

public class Add_Report extends AppCompatActivity {

    TextView name, about;
    Button chooseBtn, sendBtn;

    ImageView photo, backBtn;
    Bitmap bitmap;
    Uri ImageUri;

    FirebaseDatabase DB;
    DatabaseReference DB_Ref;

    FirebaseStorage STORAGE;
    StorageReference ST_REF;

    public String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);

        name = findViewById(R.id.add_report_name);
        about = findViewById(R.id.add_report_about);
        photo = findViewById(R.id.add_report_image);

        chooseBtn = findViewById(R.id.add_report_btn_1);
        sendBtn = findViewById(R.id.add_report_btn_2);

        //Back Button Code
        backBtn = findViewById(R.id.back_icon);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Add_Report.this, Doctor_Popup.class);
                startActivity(intent);
            }
        });

        //Choose Photo Button Code
        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityIfNeeded(Intent.createChooser(intent, "select image"), 1);
            }
        });
        //Create Account Button Code
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendReport();
            }
        });
    }
    //After Choose Photo Button
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if(requestCode == 1 && resultCode == RESULT_OK){
            ImageUri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(ImageUri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                photo.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    //Add Report Button Method
    public void sendReport(){
        //Storage Code
        STORAGE = FirebaseStorage.getInstance();
        ST_REF = STORAGE.getReference("Report_Data");

        //Database Code
        DB = FirebaseDatabase.getInstance();
        DB_Ref = DB.getReference().child("Report_Data");

        //Validate Inputs
        if(name.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your Name", Toast.LENGTH_LONG).show();
        }else if(about.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your About of Report", Toast.LENGTH_LONG).show();
        }else if(name.getText().toString().length() > 60){
            Toast.makeText(this, "Valid About Must Be Less Than 60 Character", Toast.LENGTH_LONG).show();
        }else if(about.getText().toString().length() > 250){
            Toast.makeText(this, "Valid About Must Be Less Than 250 Character", Toast.LENGTH_LONG).show();
        }else {
            //Create new Report Object
            Report report = new Report();

            //Assign Values to the Doctor Object
            ID = getIntent().getStringExtra("ID").trim();
            report.setName(name.getText().toString().trim());
            report.setAbout(about.getText().toString().trim());
            report.setComment("No Comment Yet");

            //Passing Values to the DB
            DB_Ref.child(ID).setValue(report);
            Toast.makeText(getApplicationContext(), "Successfully Report Added", Toast.LENGTH_LONG).show();

            //Passing Image to the DB
            ST_REF.child(ID).putFile(ImageUri);

            ClearControls();

            Intent intent = new Intent(this, Doctor_Popup.class);
            startActivity(intent);
        }
    }

    //Cleaning Data
    private void ClearControls() {
        name.setText("");
        about.setText("");
    }
}