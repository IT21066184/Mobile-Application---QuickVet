package com.example.quickvet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Trace;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Doctor_Account_Create extends AppCompatActivity {

    EditText fullName, specialization, contactNumber, location, address;
    Button continueBtn;

    FirebaseDatabase DB;
    DatabaseReference DB_Ref;

    //Validate Holders
    public String Mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_account_create);

        fullName = findViewById(R.id.doctor_account_fill_1);
        specialization = findViewById(R.id.doctor_account_fill_2);
        contactNumber = findViewById(R.id.doctor_account_fill_3);
        location = findViewById(R.id.doctor_account_fill_4);
        address = findViewById(R.id.doctor_account_fill_5);

        //When Click Continues Button
        continueBtn = findViewById(R.id.doctor_account_btn_1);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doctorContinue();
            }
        });
    }

    //After Click Continues Button
    public void doctorContinue(){

        //Database Code
        DB = FirebaseDatabase.getInstance();
        DB_Ref = DB.getReference().child("Doctor_Data");

        //Getting Contact Number Value
        String user_name = contactNumber.getText().toString().trim();

        //Check Validate of Contact Number
        DB = FirebaseDatabase.getInstance();
        DB_Ref = DB.getReference();
        DB_Ref.child("Doctor_Data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChild(user_name)){
                    Mobile = snapshot.child(user_name).child(user_name).getValue(String.class);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Validate Inputs
        if(fullName.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your Name", Toast.LENGTH_LONG).show();
        }else if(specialization.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your Specialization", Toast.LENGTH_LONG).show();
        }else if(contactNumber.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your Contact Number", Toast.LENGTH_LONG).show();
        }else if(location.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your Clinic Location", Toast.LENGTH_LONG).show();
        }else if(address.getText().toString().isEmpty()){
            Toast.makeText(this, "Re-Type Your Clinic Address", Toast.LENGTH_LONG).show();
        }else if(fullName.getText().toString().length() > 60){
            Toast.makeText(this, "Valid User Name Must Be Less Than 60 Characters", Toast.LENGTH_LONG).show();
        }else if(specialization.getText().toString().length() > 40){
            Toast.makeText(this, "Valid Specialization Be Less Than 40 Characters", Toast.LENGTH_LONG).show();
        }else if(contactNumber.getText().toString().length() != 10){
            Toast.makeText(this, "Please Enter Valid Mobile Number", Toast.LENGTH_LONG).show();
        }else if(location.getText().toString().length() > 20){
            Toast.makeText(this, "Valid Location Be Less Than 20 Characters", Toast.LENGTH_LONG).show();
        }else if(address.getText().toString().length() > 100){
            Toast.makeText(this, "Valid Address Be Less Than 100 Characters", Toast.LENGTH_LONG).show();
        }else if(contactNumber.getText().toString().trim().equals(Mobile)){
            Toast.makeText(this, "This Mobile Already Have an Account", Toast.LENGTH_LONG).show();
        }else {
            //Getting Form Values
            String full_name = fullName.getText().toString();
            String doctor_specialization = specialization.getText().toString();
            String contact_number = contactNumber.getText().toString();
            String clinic_location = location.getText().toString();
            String clinic_address = address.getText().toString();

            ClearControls();

            //Passing Form Values to Doctor_Account_Create_2 Activity
            Intent intent = new Intent(this, Doctor_Account_Create_2.class);
            intent.putExtra("fullName", full_name);
            intent.putExtra("doctorSpecialization", doctor_specialization);
            intent.putExtra("contactNumber", contact_number);
            intent.putExtra("location", clinic_location);
            intent.putExtra("address", clinic_address);
            startActivity(intent);
        }
    }

    //Cleaning Data
    private void ClearControls() {
        fullName.setText("");
        specialization.setText("");
        contactNumber.setText("");
        location.setText("");
        address.setText("");
    }
}