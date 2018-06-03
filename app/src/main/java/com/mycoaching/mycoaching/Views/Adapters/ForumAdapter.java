package com.mycoaching.mycoaching.Views.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mycoaching.mycoaching.Models.Forum;
import com.mycoaching.mycoaching.R;

import java.util.List;

/**
 * Created by kevin on 16/05/2018.
 */
public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.MyViewHolder> {

    private List<Forum> listForums;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv;
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            iv = view.findViewById(R.id.topic_icon);
            title = view.findViewById(R.id.topic_name);
        }
    }

    public ForumAdapter(List<Forum> listForums) {
        this.listForums = listForums;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topic_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Forum forum = listForums.get(position);

        switch (forum.getId()) {
            case 1:
                holder.iv.setImageResource(R.drawable.ic_trending_up_black_24dp);
                break;
            case 2:
                holder.iv.setImageResource(R.drawable.ic_fitness_center_black_24dp);
                break;
            case 3:
                holder.iv.setImageResource(R.drawable.ic_forum_black_24dp);
                break;
            default:
                Log.i("Missing res for id : ", "" + forum.getId());

        }
        holder.title.setText(forum.getTitle());
    }

    @Override
    public int getItemCount() {
        return listForums.size();
    }
}
