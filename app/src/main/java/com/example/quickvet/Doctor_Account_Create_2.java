package com.example.quickvet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickvet.Data.Doctor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Doctor_Account_Create_2 extends AppCompatActivity {

    EditText email, password, reType, about;
    Button createAccount, chooseBtn;

    CircleImageView profile;
    Bitmap bitmap;
    Uri ImageUri;

    FirebaseDatabase DB;
    DatabaseReference DB_Ref;

    FirebaseStorage STORAGE;
    StorageReference ST_REF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_account_create2);

        //Assigning Variables using IDs
        email = findViewById(R.id.doctor_account_fill_6);
        password = findViewById(R.id.doctor_account_fill_7);
        reType = findViewById(R.id.doctor_account_fill_8);
        about = findViewById(R.id.doctor_account_fill_9);

        chooseBtn = findViewById(R.id.doctor_account_fill_btn_2);
        profile = (CircleImageView)findViewById(R.id.doctor_account_fill_profile);

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
        createAccount = findViewById(R.id.doctor_account_fill_btn_3);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDoctor();
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
                profile.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //Create Account Button Method
    public void createDoctor(){
        //Storage Code
        STORAGE = FirebaseStorage.getInstance();
        ST_REF = STORAGE.getReference("Doctor_Data");

        //Database Code
        DB = FirebaseDatabase.getInstance();
        DB_Ref = DB.getReference().child("Doctor_Data");

        //Validate Patterns
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";

        //Validate Inputs
        if(email.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your Email Address", Toast.LENGTH_LONG).show();
        }else if(password.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your Password", Toast.LENGTH_LONG).show();
        }else if(reType.getText().toString().isEmpty()){
            Toast.makeText(this, "Re-Type Your Password", Toast.LENGTH_LONG).show();
        }else if(about.getText().toString().isEmpty()){
            Toast.makeText(this, "Fill Your About", Toast.LENGTH_LONG).show();
        }else if(!email.getText().toString().matches(emailPattern) || email.getText().toString().length() > 40){
            Toast.makeText(this, "Please Enter Valid Email Address", Toast.LENGTH_LONG).show();
        }else if(!password.getText().toString().equals(reType.getText().toString())){
            Toast.makeText(this, "Password Mismatched - Check Your Password Again", Toast.LENGTH_LONG).show();
        }else if(!password.getText().toString().matches(passwordPattern)){
            Toast.makeText(this, "Password Need At least One Number,Letter And Special Character", Toast.LENGTH_LONG).show();
        }else if(about.getText().toString().length() > 250){
            Toast.makeText(this, "Valid About Must Be Less Than 250 Character", Toast.LENGTH_LONG).show();
        }else {
            //Getting Form Values
            String fullName = getIntent().getStringExtra("fullName").trim();
            String specialization = getIntent().getStringExtra("doctorSpecialization").trim();
            String contactNumber = getIntent().getStringExtra("contactNumber").trim();
            String location = getIntent().getStringExtra("location").trim();
            String address = getIntent().getStringExtra("address").trim();

            //Create new Doctor Object
            Doctor doctor = new Doctor();

            //Assign Values to the Doctor Object
            doctor.setFull_Name(fullName);
            doctor.setSpecialization(specialization);
            doctor.setContact_Number(contactNumber);
            doctor.setLocation(location);
            doctor.setAddress(address);
            doctor.setEmail(email.getText().toString().trim());
            doctor.setPassword(password.getText().toString().trim());
            doctor.setAbout(about.getText().toString().trim());

            //Passing Values to the DB
            DB_Ref.child(contactNumber).setValue(doctor);
            Toast.makeText(getApplicationContext(), "Sign-Up Successfully", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "LogIn Using Your Valid Credentials", Toast.LENGTH_LONG).show();

            //Passing Image to the DB
            ST_REF.child(contactNumber).putFile(ImageUri);

            ClearControls();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    //Cleaning Data
    private void ClearControls() {
        email.setText("");
        password.setText("");
        reType.setText("");
    }
}