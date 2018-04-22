package com.mycoaching.mycoaching.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.mycoaching.mycoaching.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarFragment extends Fragment {

    View v;
    @BindView(R.id.calendar)
    CalendarView cv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_calendar, container, false);
        ButterKnife.bind(this,v);
        cv.setDate(1527119972094L);
        return v;
    }
}
