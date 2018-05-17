package com.mycoaching.mycoaching.Views.Adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mycoaching.mycoaching.Models.Session;
import com.mycoaching.mycoaching.R;

import java.util.List;

/**
 * Created by kevin on 16/05/2018.
 */
public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.MyViewHolder>{

    private List<Session> listSession;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv;
        public TextView title,time,series,rep,status;

        public MyViewHolder(View view) {
            super(view);
            iv = view.findViewById(R.id.session_icon);
            title = view.findViewById(R.id.session_name);
            time = view.findViewById(R.id.session_time);
            series = view.findViewById(R.id.session_series);
            rep = view.findViewById(R.id.session_rep);
            status = view.findViewById(R.id.session_status);
        }
    }

    public SessionAdapter(List<Session> listSessions){
        this.listSession = listSessions;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.session_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Session session = listSession.get(position);
        holder.iv.setImageResource(session.getIcon());
        holder.title.setText(session.getTitle());
        holder.time.setText(session.getTime());
        holder.series.setText(session.getSeries());
        holder.rep.setText(session.getRep());
        if(session.getStatus().equals("A faire")){
            holder.status.setTextColor(Color.rgb(255,50,50));
        }
        else{
            holder.status.setTextColor(Color.rgb(50,255,50));
        }
        holder.status.setText(session.getStatus());
    }

    @Override
    public int getItemCount() {
        return listSession.size();
    }
}
