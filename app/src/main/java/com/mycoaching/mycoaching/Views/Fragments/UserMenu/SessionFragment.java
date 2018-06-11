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

import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ApiUtils;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Models.Event;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.Views.Adapters.SessionAdapter;
import com.mycoaching.mycoaching.Models.Session;
import com.mycoaching.mycoaching.R;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by kevin on 28/04/2018.
 */

public class SessionFragment extends Fragment {

    private View v;
    private List<Event> listEvents = new ArrayList<>();
    private RecyclerView rv;
    private Realm r;
    private UserRealm ur;
    Session s;

    @BindView(R.id.calendar)
    MaterialCalendarView mcv;

    @OnClick(R.id.buttonSession)
    void action() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_session, container, false);
        ButterKnife.bind(this, v);

        r = Realm.getDefaultInstance();
        ur = r.where(UserRealm.class).findFirst();

        rv = new RecyclerView(getContext());

        rv = v.findViewById(R.id.listSession);
        //sa = new SessionAdapter(listSessions);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        //rv.setAdapter(sa);

        prepareData();

        return v;
    }

    private void prepareData() {
        ApiCall.getEvents(Integer.valueOf(ur.getId()), new ServiceResultListener() {
            @Override
            public void onResult(ApiResults ar) {

            }
        });
    }
}
