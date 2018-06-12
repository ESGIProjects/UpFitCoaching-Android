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

import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Models.Event;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Views.Adapters.EventAdapter;
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

public class EventFragment extends Fragment {

    private View v;
    private List<Event> listEvents = new ArrayList<>();
    private RecyclerView rv;
    private Realm r;
    private UserRealm ur;
    private EventAdapter ea;
    private boolean isCoach = false;
    private Bundle b;

    @BindView(R.id.calendar)
    MaterialCalendarView mcv;

    @OnClick(R.id.buttonEvents)
    void action() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_event, container, false);
        ButterKnife.bind(this, v);

        b = getArguments();
        isCoach = b.getBoolean("isCoach");

        r = Realm.getDefaultInstance();
        ur = r.where(UserRealm.class).findFirst();

        rv = new RecyclerView(getContext());

        rv = v.findViewById(R.id.listEvents);
        ea = new EventAdapter(listEvents,getContext(),isCoach,ur.getId());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        rv.setAdapter(ea);

        prepareData();

        return v;
    }

    private void prepareData() {
        ApiCall.getEvents(Integer.valueOf(ur.getId()), new ServiceResultListener() {
            @Override
            public void onResult(ApiResults ar) {
                if(ar.getResponseCode() == 200){
                    listEvents.addAll(ar.getListEvent());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ea.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }
}
