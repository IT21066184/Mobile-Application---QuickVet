package com.example.quickvet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quickvet.Data.Client;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Create_Client_2 extends AppCompatActivity {

    EditText petType, address;
    Button uploadBtn, submitBtn;

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
        setContentView(R.layout.activity_create_client2);

        //Assigning Variables using IDs
        petType = findViewById(R.id.pet_type_input);
        address = findViewById(R.id.address_input);

        uploadBtn = findViewById(R.id.upload_btn);
        profile = findViewById(R.id.profile_image);

        //Choose Photo Button Code
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityIfNeeded(Intent.createChooser(intent, "select image"), 1);
            }
        });

        //Submit Button Code
        submitBtn = findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitClient();
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

    //Submit Button Method
    public void submitClient(){
        //Storage Code
        STORAGE = FirebaseStorage.getInstance();
        ST_REF = STORAGE.getReference("Client_Data");

        //DB Code
        DB = FirebaseDatabase.getInstance();
        DB_Ref = DB.getReference().child("Client_Data");

        //Validate Inputs
        if(petType.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your Pet Type", Toast.LENGTH_LONG).show();
        }else if(address.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your Current Address", Toast.LENGTH_LONG).show();
        }else if(petType.getText().toString().length() > 10){
            Toast.makeText(this, "Valid Pet Type Must Be Less Than 10 Characters", Toast.LENGTH_LONG).show();
        }else if(address.getText().toString().length() > 250){
            Toast.makeText(this, "Valid Address Must Be Less Than 250 Characters", Toast.LENGTH_LONG).show();
        }else{
            //Getting Form Values
            String fullName = getIntent().getStringExtra("fullName").trim();
            String mobileNumber = getIntent().getStringExtra("mobileNumber").trim();
            String email = getIntent().getStringExtra("email").trim();
            String password = getIntent().getStringExtra("password").trim();

            //Create new Client Object
            Client client = new Client();

            //Assign Values to the Client Object
            client.setClient_Name(fullName);
            client.setMobile_Number(mobileNumber);
            client.setEmail(email);
            client.setPassword(password);
            client.setPet_Type(petType.getText().toString().trim());
            client.setAddress(address.getText().toString().trim());

            //Passing Values to the DB
            DB_Ref.child(mobileNumber).setValue(client);
            Toast.makeText(getApplicationContext(), "Sign-Up Successfully", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "LogIn Using Your Valid Credentials", Toast.LENGTH_LONG).show();
            ClearControls();

            //Passing Image to the DB
            ST_REF.child(mobileNumber).putFile(ImageUri);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    //Cleaning Data
    private void ClearControls() {
        petType.setText("");
        address.setText("");
    }
}