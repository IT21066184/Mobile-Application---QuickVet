package com.example.quickvet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quickvet.Data.Appointment;
import com.example.quickvet.Data.Doctor;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Place_Appointment_2 extends AppCompatActivity {

    CalendarView date;
    ImageView backBtn;
    Button submitBtn;

    FirebaseDatabase DB;
    DatabaseReference DB_Ref;

    String CheckDate;
    String ClientID, DoctorID;

    public String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_appointment2);

        backBtn = findViewById(R.id.back_icon);

        //Back Button Code
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Place_Appointment_2.this, Place_Appointment.class);
                startActivity(intent);
            }
        });

        //Get Selected Date Value
        date = findViewById(R.id.calendarView);
        date.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                CheckDate = (i1 + 1) + "/" + i2 + "/" + i;
            }
        });

        //Create Account Button Code
        submitBtn = findViewById(R.id.place_appointment_btn_1);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { placeAppointment();
            }
        });
    }
    //After Submit Button
    public void placeAppointment(){

        //Database Code
        DB = FirebaseDatabase.getInstance();
        DB_Ref = DB.getReference().child("Appointment_Data");

        //Validate Inputs
        if(CheckDate.isEmpty()){
            Toast.makeText(this, "Please Enter Appointment Date", Toast.LENGTH_LONG).show();
        }else{
            //Getting Form Values
            String Name = getIntent().getStringExtra("Name").trim();
            String Symptoms = getIntent().getStringExtra("Symptoms").trim();
            String selectedType = getIntent().getStringExtra("selectedType").trim();
            String selectedGender = getIntent().getStringExtra("selectedGender").trim();

            //Create new Doctor Object
            Appointment appointment = new Appointment();

            //Assign Values to the Doctor Object
            ID = getIntent().getStringExtra("ID").trim();
            appointment.setClientID(ClientID);
            appointment.setDoctorID(DoctorID);
            appointment.setName(Name);
            appointment.setSymptoms(Symptoms);
            appointment.setType(selectedType);
            appointment.setGender(selectedGender);
            appointment.setDate(CheckDate);

            //Passing Values to the DB
            DB_Ref.child(ID).setValue(appointment);
            Toast.makeText(getApplicationContext(), "Appointment Placed Successfully", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }
    }
}