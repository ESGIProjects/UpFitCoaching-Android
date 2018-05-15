package com.mycoaching.mycoaching.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mycoaching.mycoaching.Models.Message;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;

import java.util.List;

import io.realm.Realm;

/**
 * Created by kevin on 08/05/2018.
 */

public class MessageAdapter extends RecyclerView.Adapter{

    Realm r;
    int sent = 1;
    int received = 2;
    int receivedChained = 3;

    private List<Message> listMessages;

    public class SentHolder extends RecyclerView.ViewHolder {
        public TextView content;

        public SentHolder(View view) {
            super(view);
            content = view.findViewById(R.id.message_body);
        }

        public void bind(Message m){
            content.setText(m.getContent());
        }
    }

    public class ReceivedHolder extends RecyclerView.ViewHolder {
        public TextView content;

        public ReceivedHolder(View view) {
            super(view);
            content = view.findViewById(R.id.message_body);
        }

        public void bind(Message m){
            content.setText(m.getContent());
        }
    }

    public MessageAdapter(List<Message> listMessages){
        this.listMessages = listMessages;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = listMessages.get(position);
        r = Realm.getDefaultInstance();
        UserRealm ur = r.where(UserRealm.class).findFirst();

        if (message.getSender().getId().equals(ur.getId())) {
            return sent;
        } else {
            return received;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType == sent){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.self_message, parent, false);
            return new SentHolder(v);
        }
        else{
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message, parent, false);
            return new ReceivedHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = listMessages.get(position);
        if(holder.getItemViewType() == 1){
            ((SentHolder) holder).bind(message);
        }
        else{
            ((ReceivedHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return listMessages.size();
    }
}