package com.example.quickvet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class View_Appointment extends AppCompatActivity {

    TextView name, type, date, symptoms;
    Button editBtn, cancelBtn;
    ImageView backBtn;

    FirebaseDatabase DB;
    DatabaseReference DB_Ref;

    public String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);

        name = findViewById(R.id.view_appointment_doctor);
        type = findViewById(R.id.view_appointment_pet_type);
        date = findViewById(R.id.view_appointment_date);
        symptoms = findViewById(R.id.view_appointment_symptoms);

        editBtn = findViewById(R.id.view_appointment_btn_1);
        cancelBtn = findViewById(R.id.view_appointment_btn_2);

        backBtn = findViewById(R.id.back_icon);
        //Back Button Code
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(View_Appointment.this, Home.class);
                startActivity(intent);
            }
        });

        //Getting Doctor ID
        ID = getIntent().getStringExtra("ID").trim();

        //Database Code
        DB = FirebaseDatabase.getInstance();
        DB_Ref = DB.getReference();
        DB_Ref.child("Appointment_Data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Display values
                name.setText(snapshot.child(ID).child("name").getValue(String.class));
                type.setText(snapshot.child(ID).child("type").getValue(String.class));
                date.setText(snapshot.child(ID).child("date").getValue(String.class));
                symptoms.setText(snapshot.child(ID).child("symptoms").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Cancel Appointment Button Code
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelAppointment();
            }
        });
    }
    //Cancel Appointment Button Method
    public void cancelAppointment(){
        AlertDialog.Builder deleteAlert = new AlertDialog.Builder(View_Appointment.this);
        deleteAlert.setMessage("Do You Want to Delete Your Appointment");
        deleteAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Database Code
                DB = FirebaseDatabase.getInstance();
                DB_Ref = DB.getReference();
                DB_Ref.child("Appointment_Data").child(ID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(View_Appointment.this, "Appointment Delete Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(View_Appointment.this, Home.class);
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