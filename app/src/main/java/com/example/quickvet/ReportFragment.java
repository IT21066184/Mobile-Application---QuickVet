package com.example.quickvet;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReportFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView doctorName, about, date;

    FirebaseDatabase DB;
    DatabaseReference DB_Ref;

    ConstraintLayout reportPopup;
    String ID;

    public ReportFragment() {
        // Required empty public constructor
    }

    public static ReportFragment newInstance(String param1, String param2) {
        ReportFragment fragment = new ReportFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        doctorName = view.findViewById(R.id.display_report_doctor);
        about = view.findViewById(R.id.display_report_about);
        date = view.findViewById(R.id.display_report_date);

        ID = "0773030030";

        //Database Code
        DB = FirebaseDatabase.getInstance();
        DB_Ref = DB.getReference();
        DB_Ref.child("Doctor_Data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Display values
                doctorName.setText(snapshot.child(ID).child("name").getValue(String.class));
                about.setText(snapshot.child(ID).child("about").getValue(String.class));
                date.setText(snapshot.child(ID).child("comment").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reportPopup = view.findViewById(R.id.reportPopup);
        reportPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportPopup();
            }
        });

        return view;
    }

    public void reportPopup(){
        String Type = getActivity().getIntent().getStringExtra("TYPE").trim();

        if(Type == "Doctor_Data"){
            Intent intent = new Intent(getActivity(), Report_Recommendation.class);
            intent.putExtra("ID", ID);
            getActivity().startActivity(intent);
        }else if(Type == "Client_Data"){
            Intent intent = new Intent(getActivity(), View_Report.class);
            intent.putExtra("ID", ID);
            getActivity().startActivity(intent);
        }
    }
}