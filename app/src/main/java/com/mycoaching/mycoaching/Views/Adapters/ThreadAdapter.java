package com.mycoaching.mycoaching.Views.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mycoaching.mycoaching.Models.Thread;
import com.mycoaching.mycoaching.R;

import java.util.List;

/**
 * Created by kevin on 16/05/2018.
 */
public class ThreadAdapter extends RecyclerView.Adapter<ThreadAdapter.MyViewHolder>{

    private List<Thread> listThreads;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.subtopic_name);
            subtitle = view.findViewById(R.id.subtopic_subtitle);
        }
    }

    public ThreadAdapter(List<Thread> listThreads){
        this.listThreads = listThreads;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subtopic_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Thread thread = listThreads.get(position);
        holder.title.setText(thread.getTitle());
        //holder.subtitle.setText(thread.getSubtitle());
    }

    @Override
    public int getItemCount() {
        return listThreads.size();
    }
}
