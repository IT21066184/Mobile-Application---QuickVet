package com.example.quickvet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Change_Password extends AppCompatActivity {

    EditText contactNumber, email, password, reType;
    Button changePassword;
    ImageView backBtn;

    FirebaseDatabase DB;
    DatabaseReference DB_Ref;

    //Validate Holders
    public String Mobile;
    public String Email;
    public String Type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        //Assigning Variables using IDs
        contactNumber = findViewById(R.id.change_password_number);
        email = findViewById(R.id.change_password_email);
        password = findViewById(R.id.change_password_password);
        reType = findViewById(R.id.change_password_re_type);

        changePassword = findViewById(R.id.change_password_btn_1);
        backBtn = findViewById(R.id.back_icon);

        //Back Button Code
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Change_Password.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //Change Password Button Code
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });

    }
    public void changePassword(){
        //Getting User Input Contact Number
        Mobile = contactNumber.getText().toString().trim();

        //Check If Have Valid Doctor
        DB = FirebaseDatabase.getInstance();
        DB_Ref = DB.getReference();
        DB_Ref.child("Doctor_Data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.child(Mobile).equals(Mobile)){
                    Email = snapshot.child(Mobile).child("email").getValue(String.class);
                    Type = "Doctor_Data";
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Check If Have Valid Client
        DB_Ref.child("Client_Data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.child(Mobile).equals(Mobile)){
                    Email = snapshot.child(Mobile).child("email").getValue(String.class);
                    Type = "Client_Data";
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Validate Patterns
        String passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";

        //Validate Inputs
        if(contactNumber.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your Mobile Number", Toast.LENGTH_LONG).show();
        }else if(email.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your Email Address", Toast.LENGTH_LONG).show();
        }else if(password.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your Password", Toast.LENGTH_LONG).show();
        }else if(reType.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your Password Again", Toast.LENGTH_LONG).show();
        }else if(!password.getText().toString().equals(reType.getText().toString())){
            Toast.makeText(this, "Password Mismatched - Check Your Password Again", Toast.LENGTH_LONG).show();
        }else if(!password.getText().toString().matches(passwordPattern)){
            Toast.makeText(this, "Password Need At least One Number,Letter And Special Character", Toast.LENGTH_LONG).show();
        }else if(!contactNumber.getText().toString().trim().equals(Mobile) && !email.getText().toString().trim().equals(Email)){
            Toast.makeText(this, "Wrong Account Details", Toast.LENGTH_LONG).show();
        }else{

            //Update Details
            if(Type == "Doctor_Data"){
                DB = FirebaseDatabase.getInstance();
                DB_Ref = DB.getReference().child("Doctor_Data");
                DB_Ref.child(Mobile).setValue(password.getText().toString().trim(), "password");
            }else if(Type == "Client_Data"){
                DB = FirebaseDatabase.getInstance();
                DB_Ref = DB.getReference().child("Client_Data");
                DB_Ref.child(Mobile).setValue(password.getText().toString().trim(), "password");
            }
        }
    }
}