package com.mycoaching.mycoaching.Views.Fragments.UserMenu;

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

import com.mycoaching.mycoaching.Views.Adapters.AppointmentAdapter;
import com.mycoaching.mycoaching.Models.Event;
import com.mycoaching.mycoaching.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kevin on 28/04/2018.
 */


public class CalendarFragment extends Fragment {

    private View v;
    private List<Event> listEvents = new ArrayList<>();
    private RecyclerView rv;
    private AppointmentAdapter aa;
    private Event a;

    @OnClick(R.id.buttonCalendar)
    void action() {
        a = new Event("26 Mai", "Paris");
        listEvents.add(a);
        aa.notifyDataSetChanged();
    }

    @BindView(R.id.calendar)
    CalendarView cv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_calendar, container, false);
        ButterKnife.bind(this, v);

        rv = new RecyclerView(getContext());

        rv = v.findViewById(R.id.listCalendar);
        aa = new AppointmentAdapter(listEvents);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        rv.setAdapter(aa);

        prepareData();

        return v;
    }

    private void prepareData() {
        a = new Event("24 Mai", "nom de rue au hasard");
        listEvents.add(a);
        a = new Event("25 Mai", "45 rue des Saints Pères");
        listEvents.add(a);
        aa.notifyDataSetChanged();
    }
}
