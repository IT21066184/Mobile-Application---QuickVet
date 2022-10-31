package com.example.quickvet.Data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quickvet.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class Appointment_Adapter extends FirebaseRecyclerAdapter<Display_Appointment, Appointment_Adapter.AppointmentViewHolder> {

    public Appointment_Adapter(@NonNull FirebaseRecyclerOptions<Display_Appointment> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Appointment_Adapter.AppointmentViewHolder holder, int position, @NonNull Display_Appointment model) {
        holder.name.setText(model.getDoctor_Name());
        holder.date.setText(model.getDate());
    }

    @NonNull
    @Override
    public Appointment_Adapter.AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_popup_items, parent, false);
        return new Appointment_Adapter.AppointmentViewHolder(view);
    }

    public class AppointmentViewHolder extends RecyclerView.ViewHolder{
        TextView name, date;

        public AppointmentViewHolder(@NonNull View view){
            super(view);

            name = view.findViewById(R.id.display_appointment_name);
            date = view.findViewById(R.id.display_appointment_date);
        }
    }
}