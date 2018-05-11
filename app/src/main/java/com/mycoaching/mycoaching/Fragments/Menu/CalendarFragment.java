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

import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Models.Appointment;
import com.mycoaching.mycoaching.Adapters.AppointmentsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kevin on 28/04/2018.
 */


public class CalendarFragment extends Fragment {

    private View v;
    private List<Appointment> listAppointments = new ArrayList<>();
    private RecyclerView rv;
    private AppointmentsAdapter aa;

    @BindView(R.id.calendar)
    CalendarView cv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_calendar, container, false);
        ButterKnife.bind(this,v);

        rv = new RecyclerView(getContext());

        rv = v.findViewById(R.id.list);
        aa = new AppointmentsAdapter(listAppointments);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
        rv.setAdapter(aa);

        prepareData();

        return v;
    }

    private void prepareData(){
        Appointment a = new Appointment("24 Mai", "nom de rue au hasard");
        listAppointments.add(a);
        aa.notifyDataSetChanged();
    }
}
