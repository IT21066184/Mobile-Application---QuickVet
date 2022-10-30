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

public class View_Report extends AppCompatActivity {

    TextView name, description, comment;
    Button saveBtn, deleteBtn;
    ImageView backBtn;

    FirebaseDatabase DB;
    DatabaseReference DB_Ref;

    String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        name = findViewById(R.id.view_report_name);
        description = findViewById(R.id.view_report_description);
        comment = findViewById(R.id.view_report_comment);

        saveBtn = findViewById(R.id.view_report_btn_1);
        deleteBtn = findViewById(R.id.view_report_btn_2);

        ID = getIntent().getStringExtra("ID").trim();

        //Back Button Code
        backBtn = findViewById(R.id.back_icon);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(View_Report.this, Home.class);
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
                comment.setText(snapshot.child(ID).child("comment").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteReport();
            }
        });
    }
    //Delete Clinic Button Method
    public void deleteReport(){
        AlertDialog.Builder deleteAlert = new AlertDialog.Builder(View_Report.this);
        deleteAlert.setMessage("Do You Want to Delete Your Report");
        deleteAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Database Code
                DB = FirebaseDatabase.getInstance();
                DB_Ref = DB.getReference();
                DB_Ref.child("Report_Data").child(ID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(View_Report.this, "Report Delete Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(View_Report.this, Home.class);
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