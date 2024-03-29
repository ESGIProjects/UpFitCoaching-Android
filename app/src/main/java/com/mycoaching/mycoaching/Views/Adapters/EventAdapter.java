package com.mycoaching.mycoaching.Views.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mycoaching.mycoaching.Models.Retrofit.Event;
import com.mycoaching.mycoaching.R;

import java.util.List;

/**
 * Created by kevin on 16/05/2018.
 * Version 1.0
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    private List<Event> listEvents;
    private Context c;
    private Boolean isCoach;
    private String id;

    /**
     * we define an OnClick interface in order to interact with each cell of the recyclerview
     */
    private OnClick onClick;
    public interface OnClick {
        void onItemClick(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv;
        public TextView title, time, location;

        public MyViewHolder(View view) {
            super(view);
            iv = view.findViewById(R.id.event_icon);
            title = view.findViewById(R.id.event_name);
            time = view.findViewById(R.id.event_time);
            location = view.findViewById(R.id.event_location);
        }
    }

    public EventAdapter(List<Event> listEvents, Context c, Boolean isCoach, String id) {
        this.listEvents = listEvents;
        this.c = c;
        this.isCoach = isCoach;
        this.id = id;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Event event = listEvents.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemClick(position);
            }
        });
        if(event.getType().equals("0")){
            holder.iv.setImageResource(R.drawable.ic_trending_up_black_24dp);
        }
        else{
            holder.iv.setImageResource(R.drawable.ic_fitness_center_black_24dp);
        }
        if(isCoach){
            // we extract the name of the initial author of the event
            if(event.getFirstUser().getId().equals(id)){
                String name = event.getName() + " (" + event.getSecondUser().getFirstName() + " " +
                        event.getSecondUser().getLastName() + ")";
                holder.title.setText(name);
            }
            else{
                String name = event.getName() + " (" + event.getFirstUser().getFirstName() + " " +
                        event.getFirstUser().getLastName() + ")";
                holder.title.setText(name);
            }
        }
        else{
            holder.title.setText(event.getName() );
        }
        holder.time.setText(c.getString(R.string.duration,event.getStart(),event.getEnd()));
        //we define the address of the coach
        if(event.getType().equals("0")){
            if(event.getFirstUser().getCoach() != null){
                holder.location.setText(c.getString(R.string.event_location,event.getFirstUser().getCoach().getAddress(),
                        event.getFirstUser().getCoach().getCity()));
            }
            else{
                holder.location.setText(c.getString(R.string.event_location,event.getSecondUser().getCoach().getAddress(),
                        event.getSecondUser().getCoach().getCity()));
            }
        }
    }

    public void setOnClick(OnClick oc) {
        this.onClick = oc;
    }

    @Override
    public int getItemCount() {
        return listEvents.size();
    }
}
