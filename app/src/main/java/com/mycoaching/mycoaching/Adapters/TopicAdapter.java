package com.mycoaching.mycoaching.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mycoaching.mycoaching.Models.Topic;
import com.mycoaching.mycoaching.R;

import java.util.List;

/**
 * Created by kevin on 16/05/2018.
 */
public class TopicAdapter extends  RecyclerView.Adapter<TopicAdapter.MyViewHolder>{

    private List<Topic> listTopics;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv;
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            iv = view.findViewById(R.id.topic_icon);
            title = view.findViewById(R.id.topic_name);
        }
    }

    public TopicAdapter(List<Topic> listAppointments){
        this.listTopics = listAppointments;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topic_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Topic topic = listTopics.get(position);
        holder.iv.setImageResource(topic.getIcon());
        holder.title.setText(topic.getTitle());
    }

    @Override
    public int getItemCount() {
        return listTopics.size();
    }
}
