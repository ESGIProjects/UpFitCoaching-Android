package com.mycoaching.mycoaching.Fragments.Menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.mycoaching.mycoaching.Adapters.SessionAdapter;
import com.mycoaching.mycoaching.Models.Session;
import com.mycoaching.mycoaching.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kevin on 28/04/2018.
 */

public class SessionFragment extends Fragment {

    private View v;
    private List<Session> listSessions = new ArrayList<>();
    private RecyclerView rv;
    private SessionAdapter sa;
    Session s;

    @BindView(R.id.calendarSession)
    CalendarView cv;

    @OnClick(R.id.buttonSession) void action(){
        s = new Session("Footing","Durée : 1h","Séries : x","Rep : x","A faire",R.drawable.logo);
        listSessions.add(s);
        sa.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_session, container, false);
        ButterKnife.bind(this,v);

        rv = new RecyclerView(getContext());

        rv = v.findViewById(R.id.listSession);
        sa = new SessionAdapter(listSessions);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
        rv.setAdapter(sa);

        prepareData();

        return v;
    }

    private void prepareData(){
        s = new Session("Squat","Durée : 30m","Séries : 15"," Rep : 10","A faire",R.drawable.ic_fitness_center_black_24dp);
        listSessions.add(s);
        s = new Session("Pompes","Durée : 20m","Séries : 10","Rep : 15","Fait",R.drawable.ic_fitness_center_black_24dp);
        listSessions.add(s);
        sa.notifyDataSetChanged();
    }
}
