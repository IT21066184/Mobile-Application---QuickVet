package com.example.quickvet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Client_Profile extends AppCompatActivity {

    TextView name, petType, contactNumber, email, address;
    CircleImageView profile;
    ImageView backBtn;
    Button changePassword, deleteButton;

    FirebaseDatabase DB;
    DatabaseReference DB_Ref;

    FirebaseStorage STORAGE;
    StorageReference ST_REF;
    Bitmap bitmap;

    String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);

        name = findViewById(R.id.client_profile_name);
        petType = findViewById(R.id.client_profile_pet_type);
        contactNumber = findViewById(R.id.client_profile_contact_number);
        email = findViewById(R.id.client_profile_email);
        address = findViewById(R.id.client_profile_address);

        backBtn = findViewById(R.id.back_icon);
        deleteButton = findViewById(R.id.client_profile_btn_2);
        profile =findViewById(R.id.client_profile_view);

        //Getting ID
        ID = getIntent().getStringExtra("ID").trim();

        //Back Button Code
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Client_Profile.this, Home.class);
                startActivity(intent);
            }
        });
        //Delete Button Code
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProfile();
            }
        });

        //STORAGE CODE
        STORAGE = FirebaseStorage.getInstance();
        ST_REF = STORAGE.getReference("Client_Data");

        try {
            File file = File.createTempFile("temp",".jpg");
            ST_REF.child(ID).getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    profile.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Client_Profile.this, "Profile Picture Loading Error Occurred", Toast.LENGTH_LONG).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        //DB Code
        DB = FirebaseDatabase.getInstance();
        DB_Ref = DB.getReference();
        DB_Ref.child("Client_Data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Display values
                name.setText(snapshot.child(ID).child("client_Name").getValue(String.class));
                petType.setText(snapshot.child(ID).child("pet_Type").getValue(String.class));
                contactNumber.setText(snapshot.child(ID).child("mobile_Number").getValue(String.class));
                email.setText(snapshot.child(ID).child("email").getValue(String.class));
                address.setText(snapshot.child(ID).child("address").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Change Password Button Code
        changePassword = findViewById(R.id.client_profile_btn_1);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
    }
    //Change Password Button Method
    public void changePassword(){
        Intent intent = new Intent(this, Change_Password.class);
        startActivity(intent);
    }
    //Delete Button Method
    public void deleteProfile(){
        AlertDialog.Builder deleteAlert = new AlertDialog.Builder(Client_Profile.this);
        deleteAlert.setMessage("Do You Want to Delete Your Profile");
        deleteAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Database Code
                DB = FirebaseDatabase.getInstance();
                DB_Ref = DB.getReference();
                DB_Ref.child("Client_Data").child(ID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Client_Profile.this, "Profile Delete Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Client_Profile.this, Home.class);
                        startActivity(intent);
                    }
                });
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alert = deleteAlert.create();
        alert.show();

    }
}