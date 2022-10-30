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

public class Doctor_Profile extends AppCompatActivity {

    TextView doctorName, specialist, description, contactNumber, email, location, address;
    CircleImageView profile;
    ImageView backBtn;
    Button changePassword, deleteProfile, addClinic, deleteClinic;

    FirebaseDatabase DB;
    DatabaseReference DB_Ref;

    FirebaseStorage STORAGE;
    StorageReference ST_REF;
    Bitmap bitmap;

    String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        doctorName = findViewById(R.id.doctor_profile_name);
        specialist = findViewById(R.id.doctor_profile_specialist);
        description = findViewById(R.id.doctor_profile_description);
        contactNumber = findViewById(R.id.doctor_profile_contact_number);
        email = findViewById(R.id.doctor_profile_email);
        location = findViewById(R.id.doctor_profile_location);
        address = findViewById(R.id.doctor_profile_address);

        //Doctor Profile
        changePassword = findViewById(R.id.doctor_profile_btn_1);
        deleteProfile = findViewById(R.id.doctor_profile_btn_2);

        //Doctor Clinic
        addClinic = findViewById(R.id.doctor_profile_btn_3);
        deleteClinic = findViewById(R.id.doctor_profile_btn_4);

        backBtn = findViewById(R.id.back_icon);
        profile =findViewById(R.id.doctor_profile_image);

        //Back Button Code
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Doctor_Profile.this, Home.class);
                startActivity(intent);
            }
        });
        //Add Clinic Button Code
        addClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClinic();
            }
        });
        //Delete Clinic Button Code
        deleteClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteClinic();
            }
        });
        //Change Password Button Code
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
        //Delete Profile Button Code
        deleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProfile();
            }
        });

        //Getting ID
        ID = getIntent().getStringExtra("ID").trim();

        //STORAGE CODE
        STORAGE = FirebaseStorage.getInstance();
        ST_REF = STORAGE.getReference("Doctor_Data");

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
                    Toast.makeText(Doctor_Profile.this, "Profile Picture Loading Error Occurred", Toast.LENGTH_LONG).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Database Code
        DB = FirebaseDatabase.getInstance();
        DB_Ref = DB.getReference();
        DB_Ref.child("Doctor_Data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Display values
                doctorName.setText(snapshot.child(ID).child("full_Name").getValue(String.class));
                specialist.setText(snapshot.child(ID).child("specialization").getValue(String.class));
                description.setText(snapshot.child(ID).child("about").getValue(String.class));
                contactNumber.setText(snapshot.child(ID).child("contact_Number").getValue(String.class));
                email.setText(snapshot.child(ID).child("email").getValue(String.class));
                location.setText(snapshot.child(ID).child("location").getValue(String.class));
                address.setText(snapshot.child(ID).child("address").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //Add Clinic Button Method
    public void addClinic(){
        Intent intent = new Intent(Doctor_Profile.this, Clinic_Create.class);
        intent.putExtra("ID", ID);
        startActivity(intent);
    }
    //Delete Clinic Button Method
    public void deleteClinic(){
        AlertDialog.Builder deleteAlert = new AlertDialog.Builder(Doctor_Profile.this);
        deleteAlert.setMessage("Do You Want to Delete Your Clinic");
        deleteAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Database Code
                DB = FirebaseDatabase.getInstance();
                DB_Ref = DB.getReference();
                DB_Ref.child("Clinic_Data").child(ID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Doctor_Profile.this, "Clinic Delete Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Doctor_Profile.this, Home.class);
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
    //Change Password Button Method
    public void changePassword(){
        Intent intent = new Intent(Doctor_Profile.this, Change_Password.class);
        startActivity(intent);
    }
    //Delete Profile Button Method
    public void deleteProfile(){
        AlertDialog.Builder deleteAlert = new AlertDialog.Builder(Doctor_Profile.this);
        deleteAlert.setMessage("Do You Want to Delete Your Profile");
        deleteAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Database Code
                        DB = FirebaseDatabase.getInstance();
                        DB_Ref = DB.getReference();
                        DB_Ref.child("Doctor_Data").child(ID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Doctor_Profile.this, "Profile Delete Successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Doctor_Profile.this, Home.class);
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