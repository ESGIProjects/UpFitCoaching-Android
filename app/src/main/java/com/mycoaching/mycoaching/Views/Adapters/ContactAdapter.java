package com.mycoaching.mycoaching.Views.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mycoaching.mycoaching.Models.Contact;
import com.mycoaching.mycoaching.R;

import java.util.List;

import io.realm.Realm;

/**
 * Created by kevin on 20/05/2018.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    Realm r;

    private List<Contact> listContacts;

    private OnClick onClick;

    public interface OnClick {
        void onItemClick(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView letter, firstName, lastName, lastMessage;

        public MyViewHolder(View view) {
            super(view);
            letter = view.findViewById(R.id.contact_letter);
            firstName = view.findViewById(R.id.contact_firstName);
            lastName = view.findViewById(R.id.contact_lastName);
            lastMessage = view.findViewById(R.id.contact_message);
        }
    }

    public ContactAdapter(List<Contact> listMessages) {
        this.listContacts = listMessages;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Contact c = listContacts.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemClick(position);
            }
        });
        holder.letter.setText(c.getFirstName().substring(0, 1));
        holder.firstName.setText(c.getFirstName());
        holder.lastName.setText(c.getLastName());
        holder.lastMessage.setText(c.getLastMessage());
    }

    @Override
    public int getItemCount() {
        return listContacts.size();
    }

    public void setOnClick(OnClick oc) {
        this.onClick = oc;
    }

}
