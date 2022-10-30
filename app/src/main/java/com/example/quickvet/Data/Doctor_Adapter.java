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

public class Doctor_Adapter extends FirebaseRecyclerAdapter<Display_Doctor, Doctor_Adapter.DoctorViewHolder> {

    public Doctor_Adapter(@NonNull FirebaseRecyclerOptions<Display_Doctor> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DoctorViewHolder holder, int position, @NonNull Display_Doctor model) {
        holder.name.setText(model.getDoctorName());
        holder.specialist.setText(model.getSpecialist());
        holder.location.setText(model.getClinicLocation());
        Glide.with(holder.profile.getContext()).load(model.getpUrl()).into(holder.profile);

//        holder.profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AppCompatActivity activity = (AppCompatActivity)view.getContext();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment,new Display_Doctor(model.getProfileUrl(),model.getDoctorName(),model.getClinicLocation(),model.getSpecialist()).addToBackStack(null).commit();
//            }
//        });
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_popup_items, parent, false);
        return new Doctor_Adapter.DoctorViewHolder(view);
    }

    public class DoctorViewHolder extends RecyclerView.ViewHolder{
        CircleImageView profile;
        TextView name, specialist, location;

        public DoctorViewHolder(@NonNull View view){
            super(view);

            profile = view.findViewById(R.id.display_doctor_icon);
            name = view.findViewById(R.id.display_doctor_name);
            specialist = view.findViewById(R.id.display_doctor_specialist);
            location = view.findViewById(R.id.display_doctor_location);
        }
    }
}

//public class Doctor_Adapter extends RecyclerView.Adapter<Doctor_Adapter.ViewHolder>{
//
//    private static final String Tag = "RecyclerView";
//    private Context context;
//    private ArrayList<Display_Doctor> doctorList;
//
//    public Doctor_Adapter(Context context, ArrayList<Display_Doctor> doctorList) {
//        this.context = context;
//        this.doctorList = doctorList;
//    }
//
//    @NonNull
//    @Override
//    public Doctor_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.home_popup_items, parent, false);
//
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        //Display Text View
//        holder.name.setText(doctorList.get(position).getDoctorName());
//        holder.specialist.setText(doctorList.get(position).getSpecialist());
//        holder.location.setText(doctorList.get(position).getClinicLocation());
//
//        //Display Image View
//        Glide.with(context).load(doctorList.get(position).getpUrl()).into(holder.profile);
//    }
//
//    @Override
//    public int getItemCount() {
//        return doctorList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//        CircleImageView profile;
//        TextView name, specialist, location;
//
//        public ViewHolder(@NonNull View itemView){
//            super(itemView);
//
//            profile = itemView.findViewById(R.id.display_doctor_icon);
//            name = itemView.findViewById(R.id.display_doctor_name);
//            specialist = itemView.findViewById(R.id.display_doctor_specialist);
//            location = itemView.findViewById(R.id.display_doctor_location);
//        }
//    }
//}