package com.mycoaching.mycoaching.Views.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mycoaching.mycoaching.Models.Retrofit.Thread;
import com.mycoaching.mycoaching.R;

import java.util.List;

/**
 * Created by kevin on 16/05/2018.
 * Version 1.0
 */

public class ThreadAdapter extends RecyclerView.Adapter<ThreadAdapter.MyViewHolder> {

    private List<Thread> listThreads;
    private Context c;

    /**
     * we define an OnClick interface in order to interact with each cell of the recyclerview
     */
    private OnClick onClick;
    public interface OnClick {
        void onItemClick(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView infos;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.thread_name);
            infos = view.findViewById(R.id.infos);
        }
    }

    public ThreadAdapter(List<Thread> listThreads, Context c) {
        this.listThreads = listThreads;
        this.c = c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.thread_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Thread thread = listThreads.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemClick(position);
            }
        });
        holder.title.setText(thread.getTitle());
        String name = thread.getLastUser().getFirstName() + " " + thread.getLastUser().getLastName();
        String[] time = thread.getLastUpdated().split(" ");
        holder.infos.setText(c.getString(R.string.last_message, time[0], time[1], name));
    }

    public void setOnClick(OnClick oc) {
        this.onClick = oc;
    }

    @Override
    public int getItemCount() {
        return listThreads.size();
    }
}
