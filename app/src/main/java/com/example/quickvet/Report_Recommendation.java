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

import com.example.quickvet.Data.Report;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Report_Recommendation extends AppCompatActivity {

    TextView name, description, recommendation;
    Button downloadBtn, saveBtn, updateBtn, deleteBtn;
    ImageView backBtn;

    FirebaseDatabase DB;
    DatabaseReference DB_Ref;

    String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_recommendation);

        name = findViewById(R.id.report_recommendation_name);
        description = findViewById(R.id.report_recommendation_description);
        recommendation = findViewById(R.id.report_recommendation);

        downloadBtn = findViewById(R.id.report_recommendation_download_btn);
        saveBtn = findViewById(R.id.report_recommendation_btn_1);
        updateBtn = findViewById(R.id.report_recommendation_btn_2);
        deleteBtn = findViewById(R.id.report_recommendation_btn_3);

        ID = getIntent().getStringExtra("ID").trim();

        //Back Button Code
        backBtn = findViewById(R.id.back_icon);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Report_Recommendation.this, Home.class);
                startActivity(intent);
            }
        });
        //Database Code
        DB = FirebaseDatabase.getInstance();
        DB_Ref = DB.getReference();
        DB_Ref.child("Report_Data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Display values
                name.setText(snapshot.child(ID).child("name").getValue(String.class));
                description.setText(snapshot.child(ID).child("about").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Report report = new Report();
                report.setComment(recommendation.getText().toString().trim());

                DB = FirebaseDatabase.getInstance();
                DB_Ref = DB.getReference();
                DB_Ref.child("Report_Data").child(ID).setValue(report);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRecommendation();
            }
        });

    }

    //Delete Clinic Button Method
    public void deleteRecommendation(){
        AlertDialog.Builder deleteAlert = new AlertDialog.Builder(Report_Recommendation.this);
        deleteAlert.setMessage("Do You Want to Delete Your Recommendation");
        deleteAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Database Code
                DB = FirebaseDatabase.getInstance();
                DB_Ref = DB.getReference();
                DB_Ref.child("Report_Data").child(ID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Report_Recommendation.this, "Recommendation Delete Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Report_Recommendation.this, Home.class);
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