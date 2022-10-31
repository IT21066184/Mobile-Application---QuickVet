package com.example.quickvet;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class AppointmentFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    TextView doctorName, date;

    FirebaseDatabase DB;
    DatabaseReference DB_Ref;
    ConstraintLayout appointmentPopup;

    String ID;

    public AppointmentFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AppointmentFragment newInstance(String param1, String param2) {
        AppointmentFragment fragment = new AppointmentFragment();
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
        View view =  inflater.inflate(R.layout.fragment_appointment, container, false);

        doctorName = view.findViewById(R.id.display_appointment_name);
        date = view.findViewById(R.id.display_appointment_date);

        ID = "0773030030";

        //Database Code
        DB = FirebaseDatabase.getInstance();
        DB_Ref = DB.getReference();
        DB_Ref.child("Appointment_Data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Display values
                doctorName.setText(snapshot.child(ID).child("name").getValue(String.class));
                date.setText(snapshot.child(ID).child("date").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        appointmentPopup = view.findViewById(R.id.appointment_popup);
        appointmentPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appointmentPopup();
            }
        });

        return view;
    }
    public void appointmentPopup(){
        Intent intent = new Intent(getActivity(), View_Appointment.class);
        intent.putExtra("ID", ID);
        getActivity().startActivity(intent);
    }
}