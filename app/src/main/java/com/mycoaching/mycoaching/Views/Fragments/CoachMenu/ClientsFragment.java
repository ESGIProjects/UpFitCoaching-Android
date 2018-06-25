package com.mycoaching.mycoaching.Views.Fragments.CoachMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mycoaching.mycoaching.Models.Realm.Contact;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Views.Adapters.ClientsAdapter;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by kevin on 24/06/2018.
 */
public class ClientsFragment extends Fragment implements ClientsAdapter.OnClick{

    Realm r;
    View v;
    RecyclerView rv;
    ClientsAdapter ca;
    List<Contact> lc = new ArrayList<>();
    FragmentManager fm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_list_clients, container, false);

        r = Realm.getDefaultInstance();

        ca = new ClientsAdapter(lc);

        rv = v.findViewById(R.id.listClients);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        rv.setAdapter(ca);

        getClients();
        ca.setOnClick(this);


        return v;
    }

    public void getClients(){
        RealmResults<Contact> rc = r.where(Contact.class).findAll();
        lc.addAll(r.copyFromRealm(rc));
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ca.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ClientProfileFragment cpf = new ClientProfileFragment();
        ft.hide(getFragmentManager().findFragmentByTag("CPF"));
        ft.add(R.id.container, cpf,"PROFILE");
        ft.commit();
    }
}
