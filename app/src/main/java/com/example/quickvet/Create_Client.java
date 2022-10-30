package com.example.quickvet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Create_Client extends AppCompatActivity {

    EditText fullName, mobileNumber, email, password, reType;
    Button continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_client);

        //Assigning Variables using IDs
        fullName = findViewById(R.id.client_full_name);
        mobileNumber = findViewById(R.id.client_mobile_number);
        email = findViewById(R.id.client_email);
        password = findViewById(R.id.client_password);
        reType = findViewById(R.id.client_re_type);

        //Continue Button Code
        continueBtn = findViewById(R.id.continue_btn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNext();
            }
        });
    }
    //Continue Button Method
    public void createNext(){

        //Validate Patterns
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";

        //Validate Inputs
        if(fullName.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your Name", Toast.LENGTH_LONG).show();
        }else if(mobileNumber.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your Mobile Number", Toast.LENGTH_LONG).show();
        }else if(email.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your Email Address", Toast.LENGTH_LONG).show();
        }else if(password.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your Password", Toast.LENGTH_LONG).show();
        }else if(reType.getText().toString().isEmpty()){
            Toast.makeText(this, "Re-Type Your Password", Toast.LENGTH_LONG).show();
        }else if(fullName.getText().toString().length() > 40){
            Toast.makeText(this, "Valid User Name Must Be Less Than 40 Characters", Toast.LENGTH_LONG).show();
        }else if(mobileNumber.getText().toString().length() != 10){
            Toast.makeText(this, "Please Enter Valid Mobile Number", Toast.LENGTH_LONG).show();
        }else if(!email.getText().toString().matches(emailPattern) || email.getText().toString().length() > 40){
            Toast.makeText(this, "Valid Email Must Be Less Than 40 Characters", Toast.LENGTH_LONG).show();
        }else if(!password.getText().toString().matches(passwordPattern)){
            Toast.makeText(this, "Password Need At least One Number,Letter And Special Character", Toast.LENGTH_LONG).show();
        }else if(!password.getText().toString().equals(reType.getText().toString())){
            Toast.makeText(this, "Password Mismatched - Check Your Password Again", Toast.LENGTH_LONG).show();
        }else{
            //Getting Form Values
            String full_name = fullName.getText().toString();
            String mobile_number = mobileNumber.getText().toString();
            String user_email = email.getText().toString();
            String user_password = password.getText().toString();

            ClearControls();

            //Passing Form Values to Create_Client_2 Activity
            Intent intent = new Intent(this, Create_Client_2.class);
            intent.putExtra("fullName", full_name);
            intent.putExtra("mobileNumber", mobile_number);
            intent.putExtra("email", user_email);
            intent.putExtra("password", user_password);
            startActivity(intent);
        }
    }

    //Cleaning Data
    private void ClearControls() {
        fullName.setText("");
        mobileNumber.setText("");
        email.setText("");
        password.setText("");
        reType.setText("");
    }
}