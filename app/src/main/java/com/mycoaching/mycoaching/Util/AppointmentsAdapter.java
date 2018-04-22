package com.mycoaching.mycoaching.Util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mycoaching.mycoaching.R;

import java.util.List;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.MyViewHolder> {

    private List<Appointment> listAppointments;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, address;

        public MyViewHolder(View view) {
            super(view);
            date = view.findViewById(R.id.date);
            address = view.findViewById(R.id.address);
        }
    }

    public AppointmentsAdapter(List<Appointment> listAppointments){
        this.listAppointments = listAppointments;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointment_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Appointment appointment = listAppointments.get(position);
        holder.date.setText(appointment.getDate());
        holder.address.setText(appointment.getAddress());
    }

    @Override
    public int getItemCount() {
        return listAppointments.size();
    }
}
