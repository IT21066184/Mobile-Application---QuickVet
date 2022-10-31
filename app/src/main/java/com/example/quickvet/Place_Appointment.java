package com.example.quickvet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Place_Appointment extends AppCompatActivity {

    EditText name, symptoms;
    RadioGroup type, gender;
    RadioButton radioButton;
    Button nextBtn;
    ImageView backBtn;

    String selectedType, selectedGender;

    public String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_appointment);

        name = findViewById(R.id.place_appointment_name);
        symptoms = findViewById(R.id.place_appointment_about);
        nextBtn = findViewById(R.id.place_appointment_btn);

        backBtn = findViewById(R.id.back_icon);
        type = findViewById(R.id.place_appointment_type);
        gender = findViewById(R.id.place_appointment_gender);

        //Back Button Code
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Place_Appointment.this, Doctor_Popup.class);
                startActivity(intent);
            }
        });

        //Get Selected Pet Type
        type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedID = type.getCheckedRadioButtonId();
                radioButton = findViewById(selectedID);
                selectedType = radioButton.getText().toString();
            }
        });

        //Get Selected Gender
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedID = type.getCheckedRadioButtonId();
                radioButton = findViewById(selectedID);
                selectedGender = radioButton.getText().toString();
            }
        });

        //When Click Next Button
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextButton();
            }
        });
    }

    public  void nextButton(){
        //Validate Inputs
        if(name.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your Name", Toast.LENGTH_LONG).show();
        }else if(symptoms.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your Symptoms", Toast.LENGTH_LONG).show();
        }else if(selectedType.isEmpty()){
            Toast.makeText(this, "Enter Your Pet Type", Toast.LENGTH_LONG).show();
        }else if(selectedGender.isEmpty()){
            Toast.makeText(this, "Enter Your Pet Gender", Toast.LENGTH_LONG).show();
        }else if(name.getText().toString().length() > 60){
            Toast.makeText(this, "Valid User Name Must Be Less Than 60 Characters", Toast.LENGTH_LONG).show();
        }else if(symptoms.getText().toString().length() > 250){
            Toast.makeText(this, "Valid User Name Must Be Less Than 250 Characters", Toast.LENGTH_LONG).show();
        }else{
            //Getting Form Values
            String Name = name.getText().toString();
            String Symptoms = symptoms.getText().toString();

            ClearControls();

            //Passing Form Values to Place_Appointment_2 Activity
            Intent intent = new Intent(this, Place_Appointment_2.class);
            intent.putExtra("Name", Name);
            intent.putExtra("Symptoms", Symptoms);
            intent.putExtra("selectedType", selectedType);
            intent.putExtra("selectedGender", selectedGender);
            ID = getIntent().getStringExtra("ID").trim();
            intent.putExtra("ID", ID);
            startActivity(intent);
        }
    }

    //Cleaning Data
    private void ClearControls() {
        name.setText("");
        symptoms.setText("");
    }
}