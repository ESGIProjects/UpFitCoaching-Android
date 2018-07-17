package com.mycoaching.mycoaching.Views.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mycoaching.mycoaching.Models.Retrofit.Post;
import com.mycoaching.mycoaching.R;

import java.util.List;

/**
 * Created by kevin on 02/06/2018.
 * Version 1.0
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private List<Post> listPosts;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView firstName, lastName, date, content;

        public MyViewHolder(View view) {
            super(view);
            firstName = view.findViewById(R.id.firstNamePost);
            lastName = view.findViewById(R.id.lastNamePost);
            date = view.findViewById(R.id.datePost);
            content = view.findViewById(R.id.contentPost);
        }
    }

    public PostAdapter(List<Post> listPosts) {
        this.listPosts = listPosts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Post post = listPosts.get(position);
        holder.firstName.setText(post.getUser().getFirstName());
        holder.lastName.setText(post.getUser().getLastName());
        holder.date.setText(post.getDate());
        holder.content.setText(post.getContent());
    }

    @Override
    public int getItemCount() {
        return listPosts.size();
    }
}
